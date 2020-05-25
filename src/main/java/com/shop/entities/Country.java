package com.shop.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Data
@Entity
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id", scope = Country.class)
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    public Country(String name) {
        this.name = name;
    }

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "countryList")
    private List<Flower> flowerList;

    @Override
    public String toString() {
        return "Country{" + "name='" + name + '\'' + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return Objects.equals(id, country.id) &&
                Objects.equals(name, country.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
