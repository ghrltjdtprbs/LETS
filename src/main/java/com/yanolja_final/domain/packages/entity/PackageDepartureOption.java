package com.yanolja_final.domain.packages.entity;

import com.yanolja_final.global.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class PackageDepartureOption extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "package_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Package aPackage;

    @Column(nullable = false)
    private LocalDate departureDate;

    private Integer adultPrice;

    private Integer infantPrice;

    private Integer babyPrice;

    private Integer currentReservationCount;

    private Integer minReservationCount;

    private Integer maxReservationCount;

    public int getIncrementCurrentReservationCount(int totalPeople) {
        return this.currentReservationCount += totalPeople;
    }

    public boolean isNotExpired() {
        return !departureDate.isBefore(LocalDate.now());
    }

    public int getRemainReservationCount() {
        return this.maxReservationCount - this.currentReservationCount;
    }

    public int getLodgeDays() {
        return this.aPackage.getLodgeDays();
    }

    public int getTripDays() {
        return this.aPackage.getTripDays();
    }

    public Long calculateDday() {
        LocalDate currentDate = LocalDate.now();
        long dday = ChronoUnit.DAYS.between(currentDate, this.departureDate);
        return dday;
    }

    public String formattedDepartureDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String departureDate = this.departureDate.format(formatter);
        return departureDate;
    }

    public String formattedEndDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate endDate = this.departureDate.plusDays(getTripDays() - 1);
        return endDate.format(formatter);
    }
}
