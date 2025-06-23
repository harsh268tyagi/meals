package com.assignment.meal.controller;

import com.assignment.meal.enums.CuisineType;
import com.assignment.meal.model.RmdResponse;
import com.assignment.meal.service.MealService;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/meals")
@AllArgsConstructor
public class MealController {

    private MealService mealService;

    @GetMapping("/suggestions")
    public ResponseEntity<RmdResponse> getSuggestions(
            @RequestParam (value = "cuisineType") CuisineType cuisineType,
            @RequestParam (value = "city", required = false) String city,
            @RequestParam (value = "page", defaultValue = "1", required = false) int page,
            @RequestParam (value = "mode", defaultValue = "false", required = false) boolean offline
    ) {
        long startTime = System.currentTimeMillis();
        RmdResponse response = mealService.getSuggestions(cuisineType, city, page, offline);
        long elapsedTime = System.currentTimeMillis() - startTime;
        response.setElapsedTime(elapsedTime);
        return ResponseEntity.ok(response);
    }

}
