package com.cplanet.toring.dto;

import lombok.Data;

@Data
public class FileUploadResponseDto {
    private boolean success;
    private String name;
    private String path;
    private String type;
}
