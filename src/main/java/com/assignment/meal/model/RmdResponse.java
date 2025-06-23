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
public class RmdResponse {
//    private String source;
    private int totalResults;
    private int page;
    private String keyword;
    private List<Recipe> recipes;
    private List<Restaurant> restaurants;
    private long elapsedTime;
    private int prevPage;
    private int nextPage;

}
