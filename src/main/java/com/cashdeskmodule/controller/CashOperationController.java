package com.cashdeskmodule.controller;

import com.cashdeskmodule.model.CashOperationRequest;
import com.cashdeskmodule.service.CashOperationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static com.cashdeskmodule.utils.Constants.*;

@RestController
@RequestMapping("/api/v1")
public class CashOperationController {

    @Autowired
    private CashOperationService cashOperationService;

    @PostMapping("/cash-operation")
    public ResponseEntity<String> cashOperation(
            @RequestHeader(AUTH_HEADER) final String apiKey,
            @Valid @RequestBody final CashOperationRequest request) {
        try {
            cashOperationService.processOperation(request);
            return ResponseEntity.ok(OPERATION_SUCCESSFUL);
        } catch (IllegalArgumentException e) {
            throw e;
        }
    }
}