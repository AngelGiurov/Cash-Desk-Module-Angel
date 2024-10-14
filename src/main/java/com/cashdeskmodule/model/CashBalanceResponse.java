package com.cashdeskmodule.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class CashBalanceResponse {

    private Map<Currency, Integer> balances;
    private Map<Currency, Map<Integer, Integer>> denominations;
}