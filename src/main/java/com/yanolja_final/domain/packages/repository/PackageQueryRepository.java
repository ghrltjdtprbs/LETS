package com.yanolja_final.domain.packages.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yanolja_final.domain.packages.entity.Package;
import com.yanolja_final.domain.packages.entity.QPackage;
import com.yanolja_final.domain.packages.entity.QPackageDepartureOption;
import jakarta.persistence.EntityManager;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class PackageQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final QPackage qPackage = QPackage.package$;
    private final QPackageDepartureOption qPackageDepartureOption =
        QPackageDepartureOption.packageDepartureOption;

    public PackageQueryRepository(EntityManager entityManager) {
        this.jpaQueryFactory = new JPAQueryFactory(entityManager);
    }

    public List<Long> countByAdultPriceRangeAndFilters(
        int minPrice,
        int maxPrice,
        String[] nations,
        String[] continents,
        String[] hashtags
    ) {

        List<Long> packageIds = jpaQueryFactory
            .select(qPackage.id)
            .from(qPackage)
            .join(qPackage.availableDates, qPackageDepartureOption)
            .groupBy(qPackage.id)
            .having(qPackageDepartureOption.adultPrice.min().between(minPrice, maxPrice))
            .fetch();

        if (packageIds.isEmpty()) {
            return null;
        }

        BooleanBuilder builder = new BooleanBuilder();

        if (nations != null) {
            builder.or(qPackage.nation.name.in(nations));
        }
        if (continents != null) {
            builder.or(qPackage.continent.name.in(continents));
        }
        if (hashtags != null) {
            builder.or(qPackage.hashtags.any().name.in(hashtags));
        }

        List<Long> result = jpaQueryFactory
            .select(qPackage.id)
            .from(qPackage)
            .where(
                qPackage.id.in(packageIds),
                builder
            )
            .fetch();

        return result;
    }

    public Page<Package> packageInfoByAdultPriceRangeAndFilters(
        int minPrice,
        int maxPrice,
        String[] nations,
        String[] continents,
        String[] hashtags,
        String sortBy,
        Pageable pageable
    ) {

        List<Long> packageIds = jpaQueryFactory
            .select(qPackage.id)
            .from(qPackage)
            .leftJoin(qPackage.availableDates, qPackageDepartureOption)
            .groupBy(qPackage.id)
            .having(qPackageDepartureOption.adultPrice.min().between(minPrice, maxPrice))
            .fetch();

        if (packageIds.isEmpty()) {
            return null;
        }

        BooleanBuilder builder = new BooleanBuilder();
        if (nations != null) {
            builder.or(qPackage.nation.name.in(nations));
        }
        if (continents != null) {
            builder.or(qPackage.continent.name.in(continents));
        }
        if (hashtags != null) {
            builder.or(qPackage.hashtags.any().name.in(hashtags));
        }

        JPAQuery<Package> resultPackages = jpaQueryFactory
            .select(qPackage)
            .from(qPackage)
            .leftJoin(qPackage.availableDates, qPackageDepartureOption)
            .where(
                qPackage.id.in(packageIds),
                builder
            )
            .groupBy(qPackage.id);

        if ("departure_date".equals(sortBy)) {
            resultPackages
                .orderBy(qPackageDepartureOption.departureDate.min().asc());
        } else if ("price_asc".equals(sortBy)) {
            resultPackages
                .orderBy(qPackageDepartureOption.adultPrice.min().asc());
        } else if ("price_desc".equals(sortBy)) {
            resultPackages
                .orderBy(qPackageDepartureOption.adultPrice.min().desc());
        }

        List<Package> result = resultPackages
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        Integer totalCount = jpaQueryFactory
            .select(qPackage)
            .from(qPackage)
            .where(
                qPackage.id.in(packageIds),
                builder
            )
            .fetch().size();

        return new PageImpl<>(result, pageable, totalCount);
    }
}
