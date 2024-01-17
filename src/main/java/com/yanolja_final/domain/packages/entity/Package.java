package com.yanolja_final.domain.packages.entity;

import com.yanolja_final.domain.packages.exception.AvailableDateNotFoundException;
import com.yanolja_final.domain.packages.exception.PackageNotFoundException;
import com.yanolja_final.global.common.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Entity
@NoArgsConstructor
@Getter
@Slf4j
public class Package extends BaseEntity {

    @Id
    @Column
    private Long id;

    @Column
    private LocalTime departureTime;

    @Column
    private LocalTime endTime;

    @ManyToOne
    private Nation nation;

    @ManyToOne
    private Continent continent;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 30, nullable = false)
    private String transportation;

    @Column(length = 100, nullable = false)
    private String info;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "package_id")
    private List<PackageIntroImage> introImages;

    @Column(nullable = false)
    private Integer lodgeDays;

    @Column(nullable = false)
    private Integer tripDays;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String inclusionList;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String exclusionList;

    @Column(nullable = false)
    private Integer hotelStars;

    @Column(nullable = false)
    private Integer viewedCount = 0;

    @Column(nullable = false)
    private Integer purchasedCount = 0;

    @Column(nullable = false)
    private Integer monthlyPurchasedCount = 0;

    @Column(nullable = false)
    private Integer shoppingCount = 0;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String schedules;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "package_id")
    private List<PackageDepartureOption> availableDates;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "package_id")
    private List<PackageImage> images;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "package_hashtag",
        joinColumns = {
            @JoinColumn(name = "package_id", referencedColumnName = "id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))},
        inverseJoinColumns = {
            @JoinColumn(name = "hashtag_id", referencedColumnName = "id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))}
    )
    private Set<Hashtag> hashtags;

    public String getNationName() {
        return this.nation.getName();
    }

    public List<String> getHashtagNames() {
        return this.hashtags.stream().map(Hashtag::getName).collect(Collectors.toList());
    }

    public String getThumbnailImageUrl() {
        return this.images.get(0).getImageUrl();
    }

    public int getMinPrice() {
        return availableDates.stream()
            .filter(PackageDepartureOption::isNotExpired)
            .mapToInt(PackageDepartureOption::getAdultPrice)
            .min()
            .orElseThrow(PackageNotFoundException::new);
    }

    public PackageDepartureOption getAvailableDate(String strDepartDate) {
        if (strDepartDate == null) {
            int minPrice = getMinPrice();
            return availableDates.stream().filter(ad -> ad.getAdultPrice().equals(minPrice)).findFirst().orElseThrow(PackageNotFoundException::new);
        }
        LocalDate departDate = LocalDate.parse(strDepartDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        return availableDates.stream().filter(ad -> ad.getDepartureDate().equals(departDate)).findFirst().orElseThrow(
            AvailableDateNotFoundException::new);
    }

    public List<String> getImageUrls() {
        return this.images.stream().map(PackageImage::getImageUrl).collect(Collectors.toList());
    }

    public String getContinentName() {
        return this.continent.getName();
    }

    public List<String> getIntroImageUrls() {
        return this.introImages.stream().map(PackageIntroImage::getImageUrl).collect(Collectors.toList());
    }

    public void viewed() {
        this.viewedCount++;
    }
}
