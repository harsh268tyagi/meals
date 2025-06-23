package com.assignment.meal.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Recipe {
    private String title;
    private Double calories;
    private String url;
    private String cuisineType;

}
