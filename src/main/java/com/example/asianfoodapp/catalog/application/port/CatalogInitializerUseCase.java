package com.example.asianfoodapp.catalog.application.port;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.http.HttpStatus;

public interface CatalogInitializerUseCase {

    @Getter
    @Setter
    @JsonIgnoreProperties(ignoreUnknown = true)
    class RecipeJson {
        @JsonProperty("title")
        private String title;
        //Set<Ingredient> ingredients = new HashSet<>();
        @JsonProperty("readyInMinutes")
        private int readyInMinutes;

        @JsonProperty("instructions")
        private String instructions;

        @JsonProperty("vegetarian")
        private boolean vegetarian;

        @JsonProperty("vegan")
        private boolean vegan;

        @JsonProperty("glutenFree")
        private boolean glutenFree;
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
