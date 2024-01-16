package com.yanolja_final.domain.search.facade;

import com.yanolja_final.domain.packages.entity.Continent;
import com.yanolja_final.domain.packages.entity.Nation;
import com.yanolja_final.domain.packages.repository.ContinentRepository;
import com.yanolja_final.domain.packages.repository.HashtagRepository;
import com.yanolja_final.domain.packages.repository.NationRepository;
import com.yanolja_final.domain.search.controller.response.ContinentNationResponse;
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
    private final NationRepository nationRepository;
    private final ContinentRepository continentRepository;

    public List<HashtagResponse> getHashtags() {
        return hashtagRepository.findAllByOrderByNameAsc().stream()
            .map(HashtagResponse::from)
            .collect(Collectors.toList());
    }

    public List<ContinentNationResponse> getAllContinentAndNationInfo() {
        List<Nation> nations = nationRepository.findAllByOrderByNameAsc();
        List<Continent> continents = continentRepository.findAllByOrderByNameAsc();
        return List.of(ContinentNationResponse.from(nations, continents));
    }
}
