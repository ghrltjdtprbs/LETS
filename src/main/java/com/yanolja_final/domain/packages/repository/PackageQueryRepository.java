package com.yanolja_final.domain.packages.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yanolja_final.domain.packages.entity.QPackage;
import com.yanolja_final.domain.packages.entity.QPackageDepartureOption;
import jakarta.persistence.EntityManager;
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
}
