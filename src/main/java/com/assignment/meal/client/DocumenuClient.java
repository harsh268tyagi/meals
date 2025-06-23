package com.assignment.meal.client;

import com.assignment.meal.enums.CuisineType;
import com.assignment.meal.model.Restaurant;
import com.assignment.meal.props.DocumenuProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.List;

@Component
@AllArgsConstructor
public class DocumenuClient {

    private DocumenuProperties documenuProperties;

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public List<Restaurant> getRestaurants(CuisineType cuisineType, String city, int[] restaurantsCount) {
        String url = UriComponentsBuilder.fromUriString(documenuProperties.getApiUrl())
                .queryParam("restaurant_name", cuisineType)
                .queryParam("address", city)
                .toUriString();
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-API-KEY", documenuProperties.getApiKey());
        try{
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    org.springframework.http.HttpMethod.GET,
                    new org.springframework.http.HttpEntity<>(headers),
                    String.class);
            return parseRestaurants(response.getBody(), restaurantsCount);
        }
        catch (Exception e) {
            throw new RuntimeException("Error fetching restaurants from Documenu API", e);
        }


    }
    private List<Restaurant> parseRestaurants(String documenuResponse, int[] restaurantCount) throws JsonProcessingException {
        List<Restaurant> restaurants = new ArrayList<>();
        try {
            JsonNode root = objectMapper.readTree(documenuResponse);
            JsonNode dataNode = root.path("data");
            if (!(dataNode.isMissingNode() || dataNode.isEmpty())) {
                restaurantCount[0] = dataNode.size();
            }
            for (JsonNode restaurantNode : dataNode) {
                Restaurant restaurant = new Restaurant();
                restaurant.setName(restaurantNode.path("restaurant_name").asText());
                restaurant.setAddress(restaurantNode.path("address").asText());
                restaurant.setCuisineType(restaurantNode.path("cuisine_type").asText());
                JsonNode menuItemsNode = restaurantNode.path("menu_items");
                List<String> menuItems = new ArrayList<>();
                for (JsonNode item : menuItemsNode) {
                    menuItems.add(item.path("name").asText());
                }
                restaurant.setMenuItems(menuItems);
                restaurants.add(restaurant);
            }
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing Documenu response", e);
        }
        return restaurants;
    }

}
