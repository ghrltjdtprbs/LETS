package com.yanolja_final.domain.poll.service;

import com.yanolja_final.domain.poll.controller.request.PollAnswerRequest;
import com.yanolja_final.domain.poll.controller.response.PollResponse;
import com.yanolja_final.domain.poll.controller.response.PollResultResponse;
import com.yanolja_final.domain.poll.entity.Poll;
import com.yanolja_final.domain.poll.entity.PollAnswer;
import com.yanolja_final.domain.poll.exception.InvalidOptionException;
import com.yanolja_final.domain.poll.exception.PollAnswerException;
import com.yanolja_final.domain.poll.exception.PollNotFoundException;
import com.yanolja_final.domain.poll.exception.PollNotVotedException;
import com.yanolja_final.domain.poll.repository.PollAnswerRepository;
import com.yanolja_final.domain.poll.repository.PollRepository;
import com.yanolja_final.domain.user.entity.User;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PollService {

    private final PollRepository pollRepository;
    private final PollAnswerRepository pollAnswerRepository;

    public PollResponse getActivePollForMainPage() {
        Poll poll = findPollMaxId();
        return PollResponse.from(poll);
    }

    public PollResponse getActivePoll(User user) {
        Poll poll = findPollMaxId();
        Optional<PollAnswer> pollAnswer =
            pollAnswerRepository.findByUserIdAndPollId(user.getId(), poll.getId());

        if (pollAnswer.isPresent()) {
            return PollResponse.from(poll, true);
        }
        return PollResponse.from(poll, false);
    }

    @Transactional
    public void savePollAnswer(User user, PollAnswerRequest request) {
        Poll poll = findPollMaxId();

        if (pollAnswerRepository.existsByUserIdAndPollId(user.getId(), poll.getId())) {
            throw new PollAnswerException();
        }

        PollAnswer pollAnswer = request.toEntity(user, poll);
        pollAnswerRepository.save(pollAnswer);

        updatePollCount(request.choose(), poll);
        pollRepository.save(poll);
    }

    public PollResultResponse getActivePollResult(User user) {
        Poll poll = findPollMaxId();
        PollAnswer pollAnswer =
            pollAnswerRepository.findByUserIdAndPollId(user.getId(), poll.getId())
                .orElseThrow(PollNotVotedException::new);

        return PollResultResponse.from(poll, pollAnswer.getAnswer());
    }

    private void updatePollCount(char option, Poll poll) {
        switch (Character.toUpperCase(option)) {
            case 'A':
                poll.incrementACount();
                break;
            case 'B':
                poll.incrementBCount();
                break;
            default:
                throw new InvalidOptionException();
        }
    }

    private Poll findPollMaxId() {
        return pollRepository.findPollWithMaxId().orElseThrow(PollNotFoundException::new);
    }
}
