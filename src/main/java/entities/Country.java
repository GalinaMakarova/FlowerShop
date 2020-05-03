package entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "country")
@NamedQueries({
        @NamedQuery(name = "Country.findAll", query = "SELECT c from Country c"),
        @NamedQuery(name = "Country.findById", query = "SELECT c from Country c WHERE c.id = :id")
}
)
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "country_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "countries")
    private Set<Flower> flowers = new HashSet<>();

    public Country() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Flower> getFlowers() {
        return flowers;
    }

    public void setFlowers(Set<Flower> flowers) {
        this.flowers = flowers;
    }

    @Override
    public String toString() {
        return "Country{" +
                "name='" + name + '\'' +
                '}';
    }

    public void addFlower(Flower flower) {
        if (!flowers.contains(flower)) {
            flowers.add(flower);
            flower.addCountry(this);
        }
    }

    public void deleteFlower(Flower flower) {
        if (flowers.contains(flower)) {
            flowers.remove(flower);
            flower.deleteCountry(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Country country = (Country) o;
        return Objects.equals(id, country.id) &&
                Objects.equals(name, country.name) &&
                Objects.equals(flowers, country.flowers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, flowers);
    }
}
