package com.yanolja_final.domain.advertisement.entity;

import com.yanolja_final.domain.packages.entity.Continent;
import com.yanolja_final.domain.packages.entity.Nation;
import com.yanolja_final.domain.packages.entity.Package;
import com.yanolja_final.global.common.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.List;
import java.util.Set;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Advertisement extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, nullable = false)
    private String name;

    @Column(length = 300, nullable = false)
    private String imageUrl;

    @OneToOne
    private Continent continent;

    @OneToOne
    private Nation nation;

    public Advertisement(String name, String imageUrl, Continent continent, Nation nation) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.continent = continent;
        this.nation = nation;
    }
}
