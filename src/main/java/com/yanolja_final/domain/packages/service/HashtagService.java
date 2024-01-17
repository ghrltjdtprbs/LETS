package com.yanolja_final.domain.packages.service;

import com.yanolja_final.domain.packages.repository.HashtagRepository;
import com.yanolja_final.domain.search.controller.response.HashTagNamesResponse;
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

    public List<HashtagResponse> getAllHashtagInfo() {
        return hashtagRepository.findAllByOrderByNameAsc().stream()
            .map(HashtagResponse::from)
            .collect(Collectors.toList());
    }

    public HashTagNamesResponse findAllByOrderBySearchCountDesc() {
        return HashTagNamesResponse.from(hashtagRepository.findByOrderBySearchedCountDesc());
    }
}
