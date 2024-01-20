package com.yanolja_final.domain.packages.service;

import com.yanolja_final.domain.packages.entity.Hashtag;
import com.yanolja_final.domain.packages.repository.HashtagRepository;
import com.yanolja_final.domain.search.controller.response.HashtagResponse;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HashtagService {

    private final HashtagRepository hashtagRepository;

    public Hashtag getHashtagByKeyword(String keyword) {
        Hashtag hashtag = hashtagRepository.findFirstByNameContaining(keyword).orElse(null);
        incrementSearchedCount(hashtag);
        return hashtag;
    }

    public List<HashtagResponse> getAllHashtagInfo() {
        return hashtagRepository.findAllByOrderByNameAsc().stream()
            .map(HashtagResponse::from)
            .collect(Collectors.toList());
    }

    public List<Hashtag> findAllByOrderBySearchedCountDesc() {
        return hashtagRepository.findByOrderBySearchedCountDesc();
    }

    private void incrementSearchedCount(Hashtag hashtag) {
        if (hashtag == null) {
            return;
        }
        hashtag.increaseSearchedCount();
        hashtagRepository.save(hashtag);
    }
}
