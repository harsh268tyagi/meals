package com.assignment.meal.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant {
    private String name;
    private String cuisineType;
    private String address;
    private List<String> menuItems;

}
