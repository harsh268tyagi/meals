package com.assignment.meal.client;

import com.assignment.meal.enums.CuisineType;
import com.assignment.meal.model.Recipe;
import com.assignment.meal.props.EdamamProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import org.springframework.http.HttpHeaders;

import java.util.List;

@Component
@AllArgsConstructor
public class EdamamClient {


    private EdamamProperties edamamConfig;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public List<Recipe> getRecipes(CuisineType cuisineType, int page, int[] recipesCount) {
        String url = UriComponentsBuilder.fromUriString(edamamConfig.getUrl())
                .queryParam("cuisineType", cuisineType.name())
                .queryParam("app_id", edamamConfig.getAppId())
                .queryParam("app_key", edamamConfig.getAppKey())
                .queryParam("type", "public")
                .toUriString();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Edamam-Account-User", edamamConfig.getAccountUser());
        HttpEntity<Void> entity = new HttpEntity<>(httpHeaders);
        try {
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET,entity, String.class);
            return parseRecipes(response.getBody(),page, recipesCount);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch recipes from Edamam API", e);
        }
    }
    private List<Recipe> parseRecipes(String stringEdamamResponse, int page, int[] recipesCount) throws JsonProcessingException {
        List<Recipe> recipes = new java.util.ArrayList<>();
        JsonNode root = objectMapper.readTree(stringEdamamResponse);
        JsonNode hitsNode = root.path("hits");
        if (!(hitsNode.isMissingNode() || hitsNode.isEmpty())) {
            recipesCount[0] = hitsNode.size();
        }
        int start = (page - 1) * 10;
        int end = start + 10;
        int index = 0;
        for (JsonNode hit : hitsNode) {
            if (index >= start && index < end) {
                JsonNode recipeNode = hit.path("recipe");
                Recipe recipe = new Recipe();
                recipe.setCuisineType(recipeNode.path("cuisineType").path(0).asText());
                recipe.setTitle(recipeNode.path("label").asText());
                recipe.setUrl(recipeNode.path("url").asText());
                recipe.setCalories(recipeNode.path("calories").asDouble());
                recipes.add(recipe);
            }
            index++;
            if (index >= end) {
                break;
            }
        }
        return recipes;
    }
}
