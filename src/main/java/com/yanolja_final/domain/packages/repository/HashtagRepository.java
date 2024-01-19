package com.yanolja_final.domain.packages.repository;

import com.yanolja_final.domain.packages.entity.Hashtag;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

    List<Hashtag> findAllByOrderByNameAsc();

    List<Hashtag> findByOrderBySearchedCountDesc();

    Optional<Hashtag> findByNameContaining(String keyword);
}
