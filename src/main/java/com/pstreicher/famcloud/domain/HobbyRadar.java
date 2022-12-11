package com.pstreicher.famcloud.domain;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class HobbyRadar {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private Integer sportsValue;
    private Integer gamesValue;
    private Integer creativityValue;
    private Integer outdoorValue;
    private Integer travelValue;
    private Integer readingValue;
    private Integer filmsSeriesValue;
    private String color;

    @OneToOne
    UserInfo userInfo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        HobbyRadar that = (HobbyRadar) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
