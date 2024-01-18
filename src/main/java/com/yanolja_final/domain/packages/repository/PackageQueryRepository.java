package com.yanolja_final.domain.packages.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
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

    public Integer countByAdultPriceRangeAndFilters(int minPrice, int maxPrice, String[] nations,
        String[] continents, String[] hashtags) {

        BooleanBuilder builder = new BooleanBuilder();
        if (nations != null) {
            builder.or(qPackage.nation.name.in(nations));
        }
        if (continents != null) {
            builder.or(qPackage.continent.name.in(continents));
        }
        if (hashtags != null) {
//            builder.or(qPackage.hashtags.any().name.in(hashtags));  구현중...
        }

        List<Tuple> result = jpaQueryFactory
            .select(qPackage.id, qPackageDepartureOption.adultPrice.min())
            .from(qPackage)
            .join(qPackage.availableDates, qPackageDepartureOption)
            .where(builder)
            .groupBy(qPackage.id)
            .having(qPackageDepartureOption.adultPrice.min().between(minPrice, maxPrice))
            .fetch();

        return result.size();
    }

}
