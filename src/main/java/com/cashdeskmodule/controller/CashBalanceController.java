package com.cashdeskmodule.controller;

import com.cashdeskmodule.model.CashBalanceResponse;
import com.cashdeskmodule.service.CashOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import static com.cashdeskmodule.utils.Constants.*;

@RestController
@RequestMapping("/api/v1")
public class CashBalanceController {

    @Autowired
    private CashOperationService cashOperationService;

    @GetMapping("/cash-balance")
    public CashBalanceResponse getCashBalance(@RequestHeader(AUTH_HEADER) final String apiKey) {
        return cashOperationService.getCashBalance();
    }
}