package com.assignment.playermaker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDto {

    private String error;

    @JsonProperty("error_description")
    private String errorDescription;
}
