package com.yanolja_final.domain.order.entity;

import com.yanolja_final.domain.order.exception.OrderNotFoundException;
import com.yanolja_final.domain.packages.entity.Package;
import com.yanolja_final.domain.packages.entity.PackageDepartureOption;
import com.yanolja_final.domain.packages.exception.PackageDateNotFoundException;
import com.yanolja_final.domain.packages.exception.PackageNotFoundException;
import com.yanolja_final.domain.review.entity.Review;
import com.yanolja_final.domain.user.entity.User;
import com.yanolja_final.global.common.SoftDeletableBaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "orders")
@Entity
@NoArgsConstructor
@Getter
public class Order extends SoftDeletableBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "package_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Package aPackage;

    @OneToOne(mappedBy = "order", fetch = FetchType.LAZY)
    private Review review;

    @Column(nullable = false)
    private Long availableDateId;

    @Column(nullable = false)
    private Integer totalPrice;

    @Column(length = 100, nullable = false)
    private String code;

    @Column(name = "detail", columnDefinition = "TEXT", nullable = false)
    private String detailInfo;

    @Builder
    public Order(User user, Package aPackage, Review review, Long availableDateId,
        Integer totalPrice, String code,
        String detailInfo) {
        this.user = user;
        this.aPackage = aPackage;
        this.review = review;
        this.availableDateId = availableDateId;
        this.totalPrice = totalPrice;
        this.code = code;
        this.detailInfo = detailInfo;
    }

    public static Order userOrderWithEarliestDepartureDate(User user) {
        List<Order> userOrders = user.getOrders();
        Order userOrderWithEarliestDepartureDate = userOrders.stream()
            .min(Comparator.comparing(order -> order.departureDateForOrder(order.getPackageDepartureOption())))
            .orElseThrow(OrderNotFoundException::new);
        return userOrderWithEarliestDepartureDate;
    }

    private LocalDate departureDateForOrder(PackageDepartureOption option) {
        return option.getDepartureDate();
    }

    public PackageDepartureOption getPackageDepartureOption() {
        List<PackageDepartureOption> availableDates = aPackage.getAvailableDates();
        if (availableDates.isEmpty()) {
            throw new PackageDateNotFoundException();
        }
        Long availableDateId = this.availableDateId;
        PackageDepartureOption packageDepartureOption = availableDates.stream()
            .filter(option -> option.getId().equals(availableDateId))
            .findFirst()
            .orElseThrow(PackageNotFoundException::new);
        return packageDepartureOption;
    }
}
