package entities;

import utils.HibernateUtil;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "flower")
@NamedQueries({
        @NamedQuery(name = "Flower.findAll", query = "SELECT f from Flower f"),
        @NamedQuery(name = "Flower.findById", query = "SELECT f from Flower f WHERE f.id = :id")
}
)
public class Flower {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "flower_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinColumn(name = "fk_store")
    private Store store;

    @ManyToMany(
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            }
    )
    @JoinTable(name = "flower_country",
            joinColumns = @JoinColumn(name = "flower_id"),
            inverseJoinColumns = @JoinColumn(name = "country_id")
    )
    private Set<Country> countries = new HashSet<>();


    public Flower() {
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

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Set<Country> getCountries() {
        return countries;
    }

    public void setCountries(Set<Country> countries) {
        this.countries = countries;
    }

    @Override
    public String toString() {
        return "Flower{" +
                "name='" + name + '\'' + ", " +
                "Countries=['" + findAllCountries(this) + ']' +
                '}';
    }

    public void addCountry(Country country) {
        if (!countries.contains(country)) {
            countries.add(country);
            country.addFlower(this);
        }
    }

    public void deleteCountry(Country country) {
        if (countries.contains(country)) {
            countries.remove(country);
            country.deleteFlower(this);
        }
    }

    public String findAllCountries(Flower flower) {
        Set<Country> countries = HibernateUtil.getSessionFactory().openSession().get(Flower.class, flower.getId()).getCountries();
        String result = "";
        if (countries.size() > 0) {
            for (Country country : countries){
                if (result.length() > 0){
                    result += ", " + country.toString();
                } else {
                    result = country.toString();
                }
            }
        }
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flower flower = (Flower) o;
        return id.equals(flower.id) &&
                name.equals(flower.name) &&
                Objects.equals(store, flower.store) &&
                Objects.equals(countries, flower.countries);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, store, countries);
    }
}



