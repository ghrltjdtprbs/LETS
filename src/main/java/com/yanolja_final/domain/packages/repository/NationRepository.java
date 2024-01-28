package com.yanolja_final.domain.packages.repository;

import com.yanolja_final.domain.packages.entity.Nation;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NationRepository extends JpaRepository<Nation, Long> {

    List<Nation> findByIdInOrderByPriorityDesc(List<Long> ids);
}
