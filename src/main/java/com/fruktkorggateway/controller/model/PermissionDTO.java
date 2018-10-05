package com.fruktkorggateway.controller.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionDTO {
    @NotNull
    private String permission;
    @NotBlank
    private String personnummer;
}

