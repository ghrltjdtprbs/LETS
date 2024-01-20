package com.yanolja_final.domain.packages.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.yanolja_final.domain.packages.entity.QPackage;
import com.yanolja_final.domain.packages.entity.QPackageDepartureOption;
import jakarta.persistence.EntityManager;
import java.util.List;
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

    public List<Long> countByAdultPriceRangeAndFilters(int minPrice, int maxPrice, String[] nations,
        String[] continents, String[] hashtags) {

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
}
