package com.example.asianfoodapp.catalog.web;

import com.example.asianfoodapp.catalog.application.port.CatalogInitializerUseCase;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
public class CatalogInitializerController {

    private CatalogInitializerUseCase catalogInitializerUseCase;

    @GetMapping("admin/api")
    @ResponseStatus(HttpStatus.OK)
    public void fetchData(@RequestParam @NotNull @PositiveOrZero Integer offset,
                          @RequestParam @NotNull @Min(1) Integer number) {
        catalogInitializerUseCase.fetchData( offset, number);
    }
}
