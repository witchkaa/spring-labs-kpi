package ua.kpi.ist.springlab1.model;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    private Long id;
    private String name;
}