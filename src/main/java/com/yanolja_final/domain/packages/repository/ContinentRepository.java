package com.yanolja_final.domain.packages.repository;

import com.yanolja_final.domain.packages.entity.Continent;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContinentRepository extends JpaRepository<Continent, Long> {

    List<Continent> findAllByOrderByPriorityDesc();
}
