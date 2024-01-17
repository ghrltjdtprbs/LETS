package com.yanolja_final.domain.packages.service;

import com.yanolja_final.domain.packages.entity.Nation;
import com.yanolja_final.domain.packages.repository.NationRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NationService {

    public final NationRepository nationRepository;

    public List<Nation> getAllNationInfo() {
        return nationRepository.findAllByOrderByNameAsc();
    }
}
