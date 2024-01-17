package com.yanolja_final.domain.packages.service;

import com.yanolja_final.domain.packages.entity.Continent;
import com.yanolja_final.domain.packages.repository.ContinentRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContinentService {

    public final ContinentRepository continentRepository;

    public List<Continent> getAllContinentInfo() {
        return continentRepository.findAllByOrderByNameAsc();
    }
}
