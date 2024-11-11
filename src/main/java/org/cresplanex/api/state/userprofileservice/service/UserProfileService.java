package org.cresplanex.api.state.userprofileservice.service;

import build.buf.gen.job.v1.CreateJobRequest;
import build.buf.gen.job.v1.JobServiceGrpc;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.cresplanex.api.state.userprofileservice.entity.UserProfileEntity;
import org.cresplanex.api.state.userprofileservice.exception.UserProfileNotFoundException;
import org.cresplanex.api.state.userprofileservice.repository.UserProfileRepository;
import org.cresplanex.api.state.userprofileservice.saga.model.userprofile.CreateUserProfileSaga;
import org.cresplanex.api.state.userprofileservice.saga.state.userprofile.CreateUserProfileSagaState;
import org.cresplanex.api.state.userprofileservice.saga.state.userprofile.UserProfileSimplifiedDetail;
import org.cresplanex.core.saga.orchestration.SagaInstanceFactory;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final SagaInstanceFactory sagaInstanceFactory;

    @GrpcClient("jobService")
    private JobServiceGrpc.JobServiceBlockingStub jobServiceBlockingStub;

    private final CreateUserProfileSaga createUserProfileSaga;

    public UserProfileEntity findById(String userProfileId) {
        return userProfileRepository.findById(userProfileId).orElseThrow(() -> {
            return new UserProfileNotFoundException(
                    UserProfileNotFoundException.FindType.BY_ID,
                    userProfileId
            );
        });
    }

    public UserProfileEntity findByUserId(String userId) {
        return userProfileRepository.findByUserId(userId).orElseThrow(() -> {
            return new UserProfileNotFoundException(
                    UserProfileNotFoundException.FindType.BY_USER_ID,
                    userId
            );
        });
    }

    public UserProfileEntity findByEmail(String email) {
        return userProfileRepository.findByEmail(email).orElseThrow(() -> {
            return new UserProfileNotFoundException(
                    UserProfileNotFoundException.FindType.BY_EMAIL,
                    email
            );
        });
    }

    public List<UserProfileEntity> get() {
        return userProfileRepository.findAll();
    }

    public void beginCreate(UserProfileEntity profile) {
        UserProfileSimplifiedDetail detail = new UserProfileSimplifiedDetail();
        detail.setUserId(profile.getUserId());
        detail.setName(profile.getName());
        detail.setEmail(profile.getEmail());
        detail.setNickname(profile.getNickname());
        CreateUserProfileSagaState state = new CreateUserProfileSagaState();
        state.setUserProfileDetail(detail);

        String jobId = jobServiceBlockingStub.createJob(
                CreateJobRequest.newBuilder().build()
        ).getJobId();
        state.setJobId(jobId);

        sagaInstanceFactory.create(createUserProfileSaga, state);
    }

    public UserProfileEntity create(UserProfileEntity profile) {
        return userProfileRepository.save(profile);
    }

    public void undoCreate(String userProfileId) {
        UserProfileEntity profile = findById(userProfileId);
        if (profile == null) {
            throw new UserProfileNotFoundException(
                    UserProfileNotFoundException.FindType.BY_ID,
                    userProfileId
            );
        }
        userProfileRepository.delete(profile);
    }
}
