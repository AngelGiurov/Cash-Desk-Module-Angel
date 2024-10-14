package com.cashdeskmodule.model;

import jakarta.validation.constraints.NotEmpty;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CashOperationRequest {

    @NotNull
    private String operationType;

    @NotNull
    private Currency currency;

    @NotEmpty
    private List<Denomination> denominations;
}