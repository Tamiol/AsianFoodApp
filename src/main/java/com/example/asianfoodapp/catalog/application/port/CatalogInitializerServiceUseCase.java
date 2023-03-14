package com.example.asianfoodapp.catalog.application.port;

import com.example.asianfoodapp.catalog.application.CatalogInitializerService;
import com.example.asianfoodapp.catalog.domain.Ingredient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;
import org.springframework.http.HttpStatus;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

public interface CatalogInitializerServiceUseCase{

    @Value
    @JsonIgnoreProperties(ignoreUnknown = true)
    class RecipeJson {
        @JsonProperty("name")
        String name;
        Set<Ingredient> ingredients = new HashSet<>();
        int readyInMinutes;
        String instructions;
        boolean vegetarian;
        boolean vegan;
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
