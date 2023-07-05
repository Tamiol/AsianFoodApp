package com.example.asianfoodapp.catalog.application.port;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


public interface CatalogInitializerUseCase {

    void fetchData(int offset, int number);
}
