package com.example.bolnicaui.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PharmaCompanyDto {
    private Long id;
    private String name;
    private List<Long> drugs;
}
