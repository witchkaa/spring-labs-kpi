package ua.kpi.ist.springlab1.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name="categories")
@NamedQuery(
        name = "Category.findByName",
        query = "SELECT c FROM Category c WHERE c.name = :name"
)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "category")
    private List<Product> products;

}