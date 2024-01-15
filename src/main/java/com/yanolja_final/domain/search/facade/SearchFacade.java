package com.yanolja_final.domain.search.facade;

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
public class SearchFacade {

    private final HashtagRepository hashtagRepository;

    public List<HashtagResponse> getHashtags() {
        return hashtagRepository.findAll().stream()
            .map(HashtagResponse::from)
            .collect(Collectors.toList());
    }
}
