package ua.kpi.ist.springlab1.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class CategoryProductDto {
    private Long categoryId;
    private String categoryName;
    private Long productId;
    private String productName;
}