package ua.kpi.ist.springlab1.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class Product {
    private String name;
    private String description;
    private double price;
}