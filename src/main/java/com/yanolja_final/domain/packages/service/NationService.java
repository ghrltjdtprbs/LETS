package com.yanolja_final.domain.packages.service;

import com.yanolja_final.domain.packages.entity.Nation;
import com.yanolja_final.domain.packages.repository.NationRepository;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NationService {

    public final NationRepository nationRepository;

    // 일본, 중국, 태국, 베트남, 미국, 대만 순서대로 국가 ID를 리스트에 담습니다.
    private static final List<Long> nationIds = Arrays.asList(5L, 19L, 8L, 1L, 13L, 17L);

    public List<Nation> getPopularNationInfo() {
        return nationRepository.findByIdIn(nationIds);
    }
}
