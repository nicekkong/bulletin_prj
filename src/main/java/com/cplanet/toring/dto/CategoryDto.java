package com.cplanet.toring.dto;

import com.cplanet.toring.domain.Category;
import lombok.Data;

@Data
public class CategoryDto {
    private Boolean success;
    private String message;
    private Category category;
}
