package org.cresplanex.api.state.userprofileservice.saga.model.userprofile;

import org.cresplanex.api.state.userprofileservice.entity.UserProfileEntity;
import org.cresplanex.api.state.userprofileservice.event.EventDummyId;
import org.cresplanex.api.state.userprofileservice.event.model.userprofile.BeginCreateUserProfile;
import org.cresplanex.api.state.userprofileservice.event.model.userprofile.FailedCreateUserProfile;
import org.cresplanex.api.state.userprofileservice.event.model.userprofile.ProcessedCreateUserProfile;
import org.cresplanex.api.state.userprofileservice.event.model.userprofile.SuccessfullyCreateUserProfile;
import org.cresplanex.api.state.userprofileservice.event.publisher.UserProfileDomainEventPublisher;
import org.cresplanex.api.state.userprofileservice.saga.SagaCommandChannel;
import org.cresplanex.api.state.userprofileservice.saga.data.userprofile.CreateUserProfileData;
import org.cresplanex.api.state.userprofileservice.saga.proxy.UserProfileServiceProxy;
import org.cresplanex.api.state.userprofileservice.saga.reply.BaseSuccessfullyReply;
import org.cresplanex.api.state.userprofileservice.saga.reply.userprofile.CreateUserProfileReply;
import org.cresplanex.api.state.userprofileservice.saga.reply.userprofile.FailureCreateUserProfileReply;
import org.cresplanex.api.state.userprofileservice.saga.state.userprofile.CreateUserProfileSagaState;
import org.cresplanex.core.saga.orchestration.SagaDefinition;
import org.cresplanex.core.saga.simpledsl.SimpleSaga;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class CreateUserProfileSaga implements SimpleSaga<CreateUserProfileSagaState> {
    public static final String TYPE = "org.cresplanex.nova.service.userprofile.saga.CreateUserProfileSaga";

    private final SagaDefinition<CreateUserProfileSagaState> sagaDefinition;
    private final UserProfileDomainEventPublisher domainEventPublisher;

    public CreateUserProfileSaga(
            UserProfileServiceProxy userProfileService, UserProfileDomainEventPublisher domainEventPublisher
    ) {
        this.sagaDefinition = step()
                .invokeParticipant(
                        userProfileService.createUserProfile,
                        CreateUserProfileSagaState::makeCreateUserProfileCommand
                )
                .onReply(
                        CreateUserProfileReply.class,
                        CreateUserProfileReply.TYPE,
                        this::handleCreateUserProfileReply
                )
                .onReply(
                        FailureCreateUserProfileReply.class,
                        FailureCreateUserProfileReply.TYPE,
                        this::handleFailureReply
                )
                .withCompensation(
                        userProfileService.undoCreateUserProfile,
                        CreateUserProfileSagaState::makeUndoCreateUserProfileCommand
                )
                .build();
        this.domainEventPublisher = domainEventPublisher;
    }

    private void handleFailureReply(CreateUserProfileSagaState state, FailureCreateUserProfileReply reply) {
        UserProfileEntity entity = new UserProfileEntity();
        entity.setUserProfileId(state.getUserProfileId() == null ? EventDummyId.NOT_INITIALIZED : state.getUserProfileId());
        Map<String, Object> endedErrorAttributes = new HashMap<>();
        endedErrorAttributes.put("actionCode", state.getNextAction().name());
        endedErrorAttributes.put("detail", reply.getData());

        this.domainEventPublisher.publish(
                entity,
                Collections.singletonList(
                        new FailedCreateUserProfile(
                                state.getJobId(),
                                reply.getData(),
                                state.getNextAction().name(),
                                reply.getCode(),
                                reply.getCaption(),
                                reply.getTimestamp(),
                                endedErrorAttributes
                        )
                ),
                FailedCreateUserProfile.TYPE);
    }

    private void handleCreateUserProfileReply(CreateUserProfileSagaState state, CreateUserProfileReply reply) {
        CreateUserProfileReply.Data data = reply.getData();
        state.setUserProfileId(data.getUserProfileId());
        processedEventPublish(state, reply);
    }

    private <Data> void processedEventPublish(CreateUserProfileSagaState state, BaseSuccessfullyReply<Data> reply) {
        Data data = reply.getData();
        UserProfileEntity entity = new UserProfileEntity();
        entity.setUserProfileId(state.getUserProfileId() == null ? EventDummyId.NOT_INITIALIZED : state.getUserProfileId());
        this.domainEventPublisher.publish(
                entity,
                Collections.singletonList(
                        new ProcessedCreateUserProfile(
                                state.getJobId(),
                                data,
                                state.getNextAction().name(),
                                reply.getCode(),
                                reply.getCaption(),
                                reply.getTimestamp()
                        )
                ),
                ProcessedCreateUserProfile.TYPE);
        state.setNextAction(getNext(state.getNextAction()));
    }

    public enum Action {
        CREATE_USER_PROFILE,
//        CREATE_USER_PRESENCE
    }

    private Action getStartAction() {
        return Action.CREATE_USER_PROFILE;
    }

    private Action getNext(Action current) {
        List<Action> actions = Arrays.asList(Action.values());
        int index = actions.indexOf(current);
        if (index == actions.size() - 1) {
            return null;
        }
        return actions.get(index + 1);
    }

    private List<String> getActionNames() {
        return Arrays.stream(Action.values()).map(Enum::name).toList();
    }

    @Override
    public void onStarting(String sagaId, CreateUserProfileSagaState data) {
        data.setNextAction(getStartAction());
        data.setStartedAt(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

        UserProfileEntity entity = new UserProfileEntity();
        entity.setUserProfileId(data.getUserProfileId() == null ? EventDummyId.NOT_INITIALIZED : data.getUserProfileId());
        List<String> actionNames = this.getActionNames();

        String firstAction = actionNames.getFirst();
        List<String> nextActions = actionNames.subList(1, actionNames.size());

        this.domainEventPublisher.publish(
                entity,
                Collections.singletonList(
                        new BeginCreateUserProfile(
                                data.getJobId(),
                                nextActions,
                                firstAction,
                                data.getStartedAt()
                        )
                ),
                BeginCreateUserProfile.TYPE);
    }

    @Override
    public void onSagaCompletedSuccessfully(String sagaId, CreateUserProfileSagaState data) {
        UserProfileEntity entity = new UserProfileEntity();
        entity.setUserProfileId(data.getUserProfileId() == null ? EventDummyId.NOT_INITIALIZED : data.getUserProfileId());
        CreateUserProfileData d = new CreateUserProfileData(
                data.getUserProfileId()
        );
        this.domainEventPublisher.publish(
                entity,
                Collections.singletonList(
                        new SuccessfullyCreateUserProfile(
                                data.getJobId(),
                                d
                        )
                ),
                SuccessfullyCreateUserProfile.TYPE);
    }

    @Override
    public void onSagaRolledBack(String sagaId, CreateUserProfileSagaState data) {
    }

    @Override
    public void onSagaFailed(String sagaId, CreateUserProfileSagaState data) {
    }

    @Override
    public SagaDefinition<CreateUserProfileSagaState> getSagaDefinition() {
        return sagaDefinition;
    }

    @Override
    public String getSagaType() {
        return CreateUserProfileSaga.TYPE;
    }

    @Override
    public String getSagaCommandSelfChannel() {
        return SagaCommandChannel.USER_PROFILE;
    }
}
