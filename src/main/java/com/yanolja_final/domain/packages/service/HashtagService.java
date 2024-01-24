package com.yanolja_final.domain.packages.service;

import com.yanolja_final.domain.packages.entity.Hashtag;
import com.yanolja_final.domain.packages.exception.HashtagNotFoundException;
import com.yanolja_final.domain.packages.repository.HashtagRepository;
import com.yanolja_final.domain.search.controller.response.HashtagResponse;
import java.util.ArrayList;
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

    @Transactional
    public Hashtag getHashtagByKeywordWithIncrementSearchedCount(String keyword) {
        Hashtag hashtag = hashtagRepository.findFirstByNameContaining(keyword)
            .orElseThrow(HashtagNotFoundException::new);
        incrementSearchedCount(hashtag);
        return hashtag;
    }

    @Transactional
    public List<Hashtag> getHashtagWithIncrementSearchedCount(String hashtagString) {
        List<Hashtag> result = new ArrayList<>();

        String[] hashtagNames = extractHashtags(hashtagString);
        if (hashtagNames != null) {
            for (String hashtagName : hashtagNames) {
                Hashtag hashtag = hashtagRepository.findByName(hashtagName)
                    .orElseThrow(HashtagNotFoundException::new);
                if (hashtag != null) {
                    incrementSearchedCount(hashtag);
                    result.add(hashtag);
                }
            }
        }
        return result;
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

    private String[] extractHashtags(String hashtags) {
        if (hashtags == null) {
            return null;
        }
        return hashtags.split(",");
    }
}
