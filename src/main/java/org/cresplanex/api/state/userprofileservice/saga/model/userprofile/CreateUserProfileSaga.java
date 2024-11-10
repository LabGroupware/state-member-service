package org.cresplanex.api.state.userprofileservice.saga.model.userprofile;

import org.cresplanex.api.state.userprofileservice.saga.SagaCommandChannel;
import org.cresplanex.api.state.userprofileservice.saga.SagaType;
import org.cresplanex.api.state.userprofileservice.saga.proxy.UserProfileServiceProxy;
import org.cresplanex.api.state.userprofileservice.saga.reply.userprofile.CreateUserProfileReply;
import org.cresplanex.api.state.userprofileservice.saga.state.userprofile.CreateUserProfileSagaState;
import org.cresplanex.core.saga.orchestration.SagaDefinition;
import org.cresplanex.core.saga.simpledsl.SimpleSaga;
import org.springframework.stereotype.Component;

@Component
public class CreateUserProfileSaga implements SimpleSaga<CreateUserProfileSagaState> {

    private final SagaDefinition<CreateUserProfileSagaState> sagaDefinition;

    public CreateUserProfileSaga(
            UserProfileServiceProxy userProfileService
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
                .withCompensation(
                        userProfileService.undoCreateUserProfile,
                        CreateUserProfileSagaState::makeUndoCreateUserProfileCommand
                )
                .build();
    }

    private void handleCreateUserProfileReply(CreateUserProfileSagaState state, CreateUserProfileReply reply) {
        state.setUserProfileId(reply.getUserProfileId());
    }

    @Override
    public void onSagaCompletedSuccessfully(String sagaId, CreateUserProfileSagaState data) {
        //        domainEventPublisher.publish(EventAggregateTypes.USER, userId,
//                Collections.singletonList(new UserCreated(userId, "tesst", "email@example.com")),
//                EventTypes.USER_CREATED);
    }

    @Override
    public void onStarting(String sagaId, CreateUserProfileSagaState data) {

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
        return SagaType.CREATE_USER_PROFILE;
    }

    @Override
    public String getSagaCommandSelfChannel() {
        return SagaCommandChannel.USER_PROFILE;
    }
}
