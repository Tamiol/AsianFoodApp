package com.example.asianfoodapp.catalog.application.port;

import com.example.asianfoodapp.catalog.application.CatalogInitializerService;
import com.example.asianfoodapp.catalog.domain.Ingredient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.http.HttpStatus;

public interface CatalogInitializerServiceUseCase{

    @Value
    @JsonIgnoreProperties(ignoreUnknown = true)
    class RecipeJson {
        @JsonProperty("title")
        String name;
        //Set<Ingredient> ingredients = new HashSet<>();
        @JsonProperty("readyInMinutes")
        int readyInMinutes;

        @JsonProperty("sourceUrl")
        String instructions;

        @JsonProperty("vegetarian")
        boolean vegetarian;

        @JsonProperty("vegan")
        boolean vegan;

        @JsonProperty("glutenFree")
        boolean glutenFree;
    }

    @AllArgsConstructor
    @Getter
    enum Response {
        ACCEPTED(HttpStatus.ACCEPTED),
        NOT_FOUND(HttpStatus.NOT_FOUND),
        FORBIDDEN(HttpStatus.FORBIDDEN);

        private final HttpStatus status;
    }
}
