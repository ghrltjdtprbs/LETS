package com.yanolja_final.domain.packages.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yanolja_final.domain.packages.dto.response.PackageListItemResponse;
import com.yanolja_final.domain.packages.dto.response.PackageScheduleResponse;
import com.yanolja_final.domain.order.exception.MaximumCapacityExceededException;
import com.yanolja_final.domain.packages.entity.Package;
import com.yanolja_final.domain.packages.entity.PackageDepartureOption;
import com.yanolja_final.domain.packages.exception.PackageDateNotFoundException;
import com.yanolja_final.domain.packages.exception.PackageDepartureOptionNotFoundException;
import com.yanolja_final.domain.packages.exception.PackageNotFoundException;
import com.yanolja_final.domain.packages.repository.PackageDepartureOptionRepository;
import com.yanolja_final.domain.packages.repository.PackageRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PackageService {

    private final PackageRepository packageRepository;
    private final ObjectMapper objectMapper;
    private final PackageDepartureOptionRepository packageDepartureOptionRepository;

    // Package
    public Package getPackageWithIncrementPurchasedCount(Long id) {
        Package aPackage = findById(id);
        aPackage.plusPurchasedCount();
        return packageRepository.save(aPackage);
    }

    public Package findById(Long id) {
        return packageRepository.findById(id)
            .orElseThrow(PackageNotFoundException::new);
    }

    public Page<PackageListItemResponse> getAllList(Pageable pageable) {
        Page<Package> packages = packageRepository.findAll(pageable);
        return packages.map(PackageListItemResponse::from);
    }

    public List<PackageScheduleResponse> getSchedulesById(Long packageId) {
        Package aPackage = findById(packageId);
        try {
            return List.of(
                objectMapper.readValue(aPackage.getSchedules(), PackageScheduleResponse[].class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public void viewed(Package aPackage) {
        aPackage.viewed();
        packageRepository.save(aPackage);
    }

    // PackageDepartureOption
    public void updateCurrentPeopleWithOrder(Long id, Long packageId, int orderTotalPeople) {
        PackageDepartureOption packageDepartureOption = findByDepartureOptionId(id);

        packageDepartureOptionRepository.findByPackageIdAndDepartureOptionId(packageId, id)
            .orElseThrow(
                PackageDateNotFoundException::new
            );

        if (packageDepartureOption.getIncrementCurrentReservationCount(orderTotalPeople)
            > packageDepartureOption.getMaxReservationCount()) {
            throw new MaximumCapacityExceededException();
        }
        packageDepartureOptionRepository.save(packageDepartureOption);
    }

    public PackageDepartureOption findByDepartureOptionId(Long id) {
        return packageDepartureOptionRepository.findById(id).orElseThrow(
            PackageDepartureOptionNotFoundException::new);
    }
}
