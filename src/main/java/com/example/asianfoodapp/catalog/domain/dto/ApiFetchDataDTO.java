package com.example.asianfoodapp.catalog.domain.dto;

import java.util.ArrayList;

public record ApiFetchDataDTO(ArrayList<ApiFetchDataResultDTO> results, int offset, int number, int totalResults) {
}
