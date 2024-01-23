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

    public Long countByAdultPriceRangeAndFilters(int minPrice, int maxPrice, String[] nations,
        String[] continents, String[] hashtags) {
        QPackageDepartureOption qPackageDepartureOptionSub =
            new QPackageDepartureOption("packageDepartureSub");

        JPAQuery<Long> minPriceSubQuery = new JPAQuery<Long>()
            .select(qPackageDepartureOptionSub.aPackage.id)
            .from(qPackageDepartureOptionSub)
            .groupBy(qPackageDepartureOptionSub.aPackage.id)
            .having(qPackageDepartureOptionSub.adultPrice.min().between(minPrice, maxPrice));

        BooleanBuilder builder = new BooleanBuilder(qPackage.id.in(minPriceSubQuery));
        if ((nations != null && nations.length > 0) || (continents != null
            && continents.length > 0)) {
            BooleanBuilder nationOrContinentBuilder = new BooleanBuilder();
            if (nations != null && nations.length > 0) {
                nationOrContinentBuilder.or(qPackage.nation.name.in(nations));
            }
            if (continents != null && continents.length > 0) {
                nationOrContinentBuilder.or(qPackage.continent.name.in(continents));
            }
            builder.and(nationOrContinentBuilder);
        }
        if (hashtags != null && hashtags.length > 0) {
            builder.and(qPackage.hashtags.any().name.in(hashtags));
        }

        return jpaQueryFactory
            .select(qPackage.count())
            .from(qPackage)
            .where(builder)
            .fetchOne();
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

        QPackageDepartureOption qPackageDepartureOptionSub =
            new QPackageDepartureOption("packageDepartureSub");

        JPAQuery<Long> minPriceSubQuery = new JPAQuery<Long>()
            .select(qPackageDepartureOptionSub.aPackage.id)
            .from(qPackageDepartureOptionSub)
            .groupBy(qPackageDepartureOptionSub.aPackage.id)
            .having(qPackageDepartureOptionSub.adultPrice.min().between(minPrice, maxPrice));

        BooleanBuilder builder = new BooleanBuilder(qPackage.id.in(minPriceSubQuery));
        if ((nations != null && nations.length > 0) || (continents != null
            && continents.length > 0)) {
            BooleanBuilder nationOrContinentBuilder = new BooleanBuilder();
            if (nations != null && nations.length > 0) {
                nationOrContinentBuilder.or(qPackage.nation.name.in(nations));
            }
            if (continents != null && continents.length > 0) {
                nationOrContinentBuilder.or(qPackage.continent.name.in(continents));
            }
            builder.and(nationOrContinentBuilder);
        }
        if (hashtags != null && hashtags.length > 0) {
            builder.and(qPackage.hashtags.any().name.in(hashtags));
        }

        JPAQuery<Package> resultPackages = jpaQueryFactory
            .select(qPackage)
            .from(qPackage)
            .leftJoin(qPackage.availableDates, qPackageDepartureOption)
            .where(builder)
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
            .where(builder)
            .fetch().size();

        return new PageImpl<>(result, pageable, totalCount);
    }
}
