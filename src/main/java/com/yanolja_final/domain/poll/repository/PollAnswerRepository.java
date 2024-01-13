package com.yanolja_final.domain.poll.repository;

import com.yanolja_final.domain.poll.entity.PollAnswer;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PollAnswerRepository extends JpaRepository<PollAnswer, Long> {

    Optional<PollAnswer> findByUserIdAndPollId(Long userId, Long PollId);

    boolean existsByUserIdAndPollId(Long userId, Long pollId);
}
