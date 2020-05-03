package entities;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "store")
@NamedQueries({
        @NamedQuery(name = "Store.findAll", query = "SELECT s from Store s"),
        @NamedQuery(name = "Store.findById", query = "SELECT s from Store s WHERE s.id = :id")
}
)
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "store_id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "employee")
    private Employee employee;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Flower> flowers = new HashSet<>();

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        if (employee == null) {
            if (this.employee != null) {
                this.employee.setStore(null);
            }
        } else {
            employee.setStore(this);
        }
        this.employee = employee;
    }

    public Set<Flower> getFlowers() {
        return flowers;
    }

    public void setFlowers(Set<Flower> flowers) {
        this.flowers = flowers;
    }

    public void addFlower(Flower flower) {
        flowers.add(flower);
        flower.setStore(this);
    }

    public void deleteFlower(Flower flower) {
        flowers.remove(flower);
        flower.setStore(null);
    }

    @Override
    public String toString() {
        return "Store{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Store store = (Store) o;
        return id.equals(store.id) &&
                name.equals(store.name) &&
                Objects.equals(address, store.address) &&
                Objects.equals(employee, store.employee) &&
                Objects.equals(flowers, store.flowers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, employee, flowers);
    }
}

