package com.yanolja_final.domain.advertisement.repository;

import com.yanolja_final.domain.advertisement.entity.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdRepository extends JpaRepository<Advertisement, Long> {

}
