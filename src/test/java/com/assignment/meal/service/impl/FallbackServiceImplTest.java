package com.assignment.meal.service.impl;

import com.assignment.meal.enums.CuisineType;
import com.assignment.meal.model.Recipe;
import com.assignment.meal.model.Restaurant;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class FallbackServiceImplTest {

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private FallbackServiceImpl fallbackService;

    @Test
    void getRecipesReturnsFilteredRecipesForValidCuisineType() throws IOException {
        List<Recipe> mockRecipes = List.of(
                new Recipe("Recipe1", 350.01, "www.example.com", CuisineType.Italian.name()),
                new Recipe("Recipe2", 390.88, "www.example.com", CuisineType.Indian.name())
        );
        InputStream mockInputStream = new ByteArrayInputStream(new ObjectMapper().writeValueAsBytes(mockRecipes));
        Mockito.when(objectMapper.readValue(Mockito.any(InputStream.class), Mockito.any(TypeReference.class)))
                .thenReturn(mockRecipes);

        int[] recipesCount = new int[1];
        List<Recipe> result = fallbackService.getRecipes(1, CuisineType.Italian, recipesCount);

        assertEquals(1, result.size());
        assertEquals("Recipe1", result.get(0).getTitle());
        assertEquals(1, recipesCount[0]);
    }

    @Test
    void getRecipesReturnsEmptyListForInvalidCuisineType() throws IOException {
        List<Recipe> mockRecipes = List.of(
                new Recipe("Recipe1", 350.01, "www.example.com", CuisineType.Italian.name()),
                new Recipe("Recipe2", 390.88, "www.example.com", CuisineType.Indian.name())
        );
        InputStream mockInputStream = new ByteArrayInputStream(new ObjectMapper().writeValueAsBytes(mockRecipes));
        Mockito.when(objectMapper.readValue(Mockito.any(InputStream.class), Mockito.any(TypeReference.class)))
                .thenReturn(mockRecipes);

        int[] recipesCount = new int[1];
        List<Recipe> result = fallbackService.getRecipes(1, CuisineType.Chinese, recipesCount);

        assertTrue(result.isEmpty());
        assertEquals(0, recipesCount[0]);
    }

    @Test
    void getRestaurantsReturnsFilteredRestaurantsForValidCity() throws IOException {
        List<Restaurant> mockRestaurants = List.of(
                new Restaurant("Restaurant1", "American", "Noida", List.of("Masala Dosa", "Watermelon Juice")),
                new Restaurant("Restaurant2", "American", "Fariadabad", List.of("Chilli Chicken"))
        );
        InputStream mockInputStream = new ByteArrayInputStream(new ObjectMapper().writeValueAsBytes(mockRestaurants));
        Mockito.when(objectMapper.readValue(Mockito.any(InputStream.class), Mockito.any(TypeReference.class)))
                .thenReturn(mockRestaurants);

        int[] restaurantsCount = new int[1];
        List<Restaurant> result = fallbackService.getRestaurants(1, "Noida", restaurantsCount);

        assertEquals(1, result.size());
        assertEquals("Restaurant1", result.get(0).getName());
        assertEquals(1, restaurantsCount[0]);
    }

    @Test
    void getRestaurantsReturnsEmptyListForInvalidCity() throws IOException {
        List<Restaurant> mockRestaurants = List.of(
                new Restaurant("Restaurant1", "American", "Noida", List.of("Masala Dosa", "Watermelon Juice")),
                new Restaurant("Restaurant2", "American", "Fariadabad", List.of("Chilli Chicken"))
        );
        InputStream mockInputStream = new ByteArrayInputStream(new ObjectMapper().writeValueAsBytes(mockRestaurants));
        Mockito.when(objectMapper.readValue(Mockito.any(InputStream.class), Mockito.any(TypeReference.class)))
                .thenReturn(mockRestaurants);

        int[] restaurantsCount = new int[1];
        List<Restaurant> result = fallbackService.getRestaurants(1, "Chicago", restaurantsCount);

        assertTrue(result.isEmpty());
        assertEquals(0, restaurantsCount[0]);
    }

    @Test
    void getRecipesThrowsRuntimeExceptionWhenJsonIsInvalid() throws IOException {
        Mockito.when(objectMapper.readValue(Mockito.any(InputStream.class), Mockito.any(TypeReference.class)))
                .thenThrow(new IOException("Invalid JSON"));

        int[] recipesCount = new int[1];
        assertThrows(RuntimeException.class, () -> fallbackService.getRecipes(1, CuisineType.Italian, recipesCount));
    }

    @Test
    void getRestaurantsThrowsRuntimeExceptionWhenJsonIsInvalid() throws IOException {
        Mockito.when(objectMapper.readValue(Mockito.any(InputStream.class), Mockito.any(TypeReference.class)))
                .thenThrow(new IOException("Invalid JSON"));

        int[] restaurantsCount = new int[1];
        assertThrows(RuntimeException.class, () -> fallbackService.getRestaurants(1, "Noida", restaurantsCount));
    }
}

