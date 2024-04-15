package com.springboot.jwt_authentication.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ErrorResponse {

    @JsonProperty("title")
    private String title;
    @JsonProperty("status")
    private int status;
    @JsonProperty("validation_errors")
    private Map<String, String> validationErrors = new HashMap<>();
    @JsonProperty("error_message")
    private String errorMessage;
    @JsonProperty("path")
    private String path;

}