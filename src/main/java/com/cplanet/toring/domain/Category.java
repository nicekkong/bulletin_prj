package com.cplanet.toring.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@Accessors(chain = true)
public class Category {
    List<MainCategory> mainCategory;
    List<SubCategory> subCategory;

    @Data
    @Accessors(chain = true)
    public static class MainCategory {
        Long id;
        String name;
    }

    @Data
    @Accessors(chain = true)
    public static class SubCategory {
        Long id;
        String name;
        Long mainCategoryId;
    }
}
