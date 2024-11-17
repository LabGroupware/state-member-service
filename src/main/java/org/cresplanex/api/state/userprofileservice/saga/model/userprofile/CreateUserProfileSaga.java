package org.cresplanex.api.state.userprofileservice.saga.model.userprofile;

import org.cresplanex.api.state.common.event.model.userprofile.UserProfileCreated;
import org.cresplanex.api.state.common.event.model.userprofile.UserProfileDomainEvent;
import org.cresplanex.api.state.common.event.publisher.AggregateDomainEventPublisher;
import org.cresplanex.api.state.common.saga.SagaCommandChannel;
import org.cresplanex.api.state.common.saga.data.userprofile.CreateUserProfileResultData;
import org.cresplanex.api.state.common.saga.model.SagaModel;
import org.cresplanex.api.state.common.saga.reply.userpreference.CreateUserPreferenceReply;
import org.cresplanex.api.state.common.saga.reply.userprofile.CreateUserProfileReply;
import org.cresplanex.api.state.common.saga.type.UserProfileSagaType;
import org.cresplanex.api.state.userprofileservice.entity.UserProfileEntity;
import org.cresplanex.api.state.userprofileservice.event.publisher.UserProfileDomainEventPublisher;
import org.cresplanex.api.state.userprofileservice.saga.proxy.UserPreferenceServiceProxy;
import org.cresplanex.api.state.userprofileservice.saga.proxy.UserProfileServiceProxy;
import org.cresplanex.api.state.userprofileservice.saga.state.userprofile.CreateUserProfileSagaState;
import org.cresplanex.core.saga.orchestration.SagaDefinition;
import org.springframework.stereotype.Component;

@Component
public class CreateUserProfileSaga extends SagaModel<
        UserProfileEntity,
        UserProfileDomainEvent,
        CreateUserProfileSaga.Action,
        CreateUserProfileSagaState> {

    private final SagaDefinition<CreateUserProfileSagaState> sagaDefinition;
    private final UserProfileDomainEventPublisher domainEventPublisher;

    public CreateUserProfileSaga(
            UserProfileServiceProxy userProfileService,
            UserPreferenceServiceProxy userPreferenceService,
            UserProfileDomainEventPublisher domainEventPublisher
    ) {
        this.sagaDefinition = step()
                .invokeParticipant(
                        userProfileService.createUserProfile,
                        CreateUserProfileSagaState::makeCreateUserProfileCommand
                )
                .onReply(
                        CreateUserProfileReply.Success.class,
                        CreateUserProfileReply.Success.TYPE,
                        this::handleCreateUserProfileReply
                )
                .onReply(
                        CreateUserProfileReply.Failure.class,
                        CreateUserProfileReply.Failure.TYPE,
                        this::handleFailureReply
                )
                .withCompensation(
                        userProfileService.undoCreateUserProfile,
                        CreateUserProfileSagaState::makeUndoCreateUserProfileCommand
                )
                .step()
                .invokeParticipant(
                        userPreferenceService.createUserPreference,
                        CreateUserProfileSagaState::makeCreateUserPreferenceCommand
                )
                .onReply(
                        CreateUserPreferenceReply.Success.class,
                        CreateUserPreferenceReply.Success.TYPE,
                        this::handleCreateUserPreferenceReply
                )
                .onReply(
                        CreateUserPreferenceReply.Failure.class,
                        CreateUserPreferenceReply.Failure.TYPE,
                        this::handleFailureReply
                )
                .withCompensation(
                        userPreferenceService.undoCreateUserPreference,
                        CreateUserProfileSagaState::makeUndoCreateUserPreferenceCommand
                )
                .build();
        this.domainEventPublisher = domainEventPublisher;
    }

    @Override
    protected AggregateDomainEventPublisher<UserProfileEntity, UserProfileDomainEvent>
    getDomainEventPublisher() {
        return domainEventPublisher;
    }

    @Override
    protected Action[] getActions() {
        return Action.values();
    }

    @Override
    protected String getBeginEventType() {
        return UserProfileCreated.BeginJobDomainEvent.TYPE;
    }

    @Override
    protected String getProcessedEventType() {
        return UserProfileCreated.ProcessedJobDomainEvent.TYPE;
    }

    @Override
    protected String getFailedEventType() {
        return UserProfileCreated.FailedJobDomainEvent.TYPE;
    }

    @Override
    protected String getSuccessfullyEventType() {
        return UserProfileCreated.SuccessJobDomainEvent.TYPE;
    }

    private void handleCreateUserProfileReply(
            CreateUserProfileSagaState state, CreateUserProfileReply.Success reply) {
        CreateUserProfileReply.Success.Data data = reply.getData();
        state.setUserProfileDto(data.getUserProfile());
        this.processedEventPublish(state, reply);
    }

    private void handleCreateUserPreferenceReply(
            CreateUserProfileSagaState state, CreateUserPreferenceReply.Success reply) {
        CreateUserPreferenceReply.Success.Data data = reply.getData();
        state.setUserPreferenceDto(data.getUserPreference());
        this.processedEventPublish(state, reply);
    }

    @Override
    public void onSagaCompletedSuccessfully(String sagaId, CreateUserProfileSagaState data) {
        CreateUserProfileResultData resultData = new CreateUserProfileResultData(
                data.getUserProfileDto(),
                data.getUserPreferenceDto()
        );
        successfullyEventPublish(data, resultData);
    }

    public enum Action {
        CREATE_USER_PROFILE,
        CREATE_USER_PREFERENCE
    }

    @Override
    public SagaDefinition<CreateUserProfileSagaState> getSagaDefinition() {
        return sagaDefinition;
    }

    @Override
    public String getSagaType() {
        return UserProfileSagaType.CREATE_USER_PROFILE;
    }

    @Override
    public String getSagaCommandSelfChannel() {
        return SagaCommandChannel.USER_PROFILE;
    }
}
