package ua.kpi.ist.springlab1.model;

import lombok.*;

@Value
public class Product {
    Long id;
    String name;
    String description;
    double price;
}