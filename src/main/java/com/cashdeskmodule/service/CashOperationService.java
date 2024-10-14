package com.cashdeskmodule.service;

import com.cashdeskmodule.model.*;
import com.cashdeskmodule.model.Currency;
import com.cashdeskmodule.utils.FileUtil;
import org.springframework.stereotype.Service;
import static com.cashdeskmodule.utils.Constants.*;

import java.util.*;

@Service
public class CashOperationService {

    private static final String CASH_BALANCE_FILE = "cash_balances.txt";
    private static final String TRANSACTION_HISTORY_FILE = "transaction_history.txt";

    private Map<Currency, Map<Integer, Integer>> denominationsMap = new HashMap<>();
    private Map<Currency, Integer> balanceMap = new HashMap<>();

    public CashOperationService() {
        loadBalances();
    }

    public void processOperation(final CashOperationRequest request) {
        validateRequest(request);
        final String operationType = request.getOperationType();
        final Currency currency = request.getCurrency();
        final List<Denomination> denominations = request.getDenominations();

        final Map<Integer, Integer> currencyDenominations = denominationsMap.getOrDefault(currency, new HashMap<>());

        int totalAmount = 0;
        for (Denomination denom : denominations) {
            final int value = denom.getValue();
            final int count = denom.getCount();
            totalAmount += value * count;

            if (operationType.equalsIgnoreCase(DEPOSIT)) {
                currencyDenominations.put(value, currencyDenominations.getOrDefault(value, 0) + count);
            } else if (operationType.equalsIgnoreCase(WITHDRAWAL)) {
                final int currentCount = currencyDenominations.getOrDefault(value, 0);
                if (currentCount < count) {
                    throw new IllegalArgumentException(INSUFFICIENT_DENOMINATIONS_WITHDRAWAL);
                }
                currencyDenominations.put(value, currentCount - count);
            }
        }

        denominationsMap.put(currency, currencyDenominations);

        final int currentBalance = balanceMap.getOrDefault(currency, 0);
        if (operationType.equalsIgnoreCase(DEPOSIT)) {
            balanceMap.put(currency, currentBalance + totalAmount);
        } else if (operationType.equalsIgnoreCase(WITHDRAWAL)) {
            if (currentBalance < totalAmount) {
                throw new IllegalArgumentException(INSUFFICIENT_BALANCE_WITHDRAWAL);
            }
            balanceMap.put(currency, currentBalance - totalAmount);
        }

        FileUtil.appendToFile(TRANSACTION_HISTORY_FILE, formatTransaction(request, totalAmount));

        saveBalances();
    }

    public CashBalanceResponse getCashBalance() {
        final CashBalanceResponse response = new CashBalanceResponse();
        response.setBalances(balanceMap);
        response.setDenominations(denominationsMap);
        return response;
    }

    private void loadBalances() {
        if (!FileUtil.fileExists(CASH_BALANCE_FILE)) {
            initializeBalances();
            saveBalances();
        } else {
            balanceMap = FileUtil.readBalancesFromFile(CASH_BALANCE_FILE);
            denominationsMap = FileUtil.readDenominationsFromFile(CASH_BALANCE_FILE);
        }
    }

    private void initializeBalances() {
        balanceMap.put(Currency.BGN, 1000);
        balanceMap.put(Currency.EUR, 2000);

        final Map<Integer, Integer> bgnDenominations = new HashMap<>();
        bgnDenominations.put(50, 10);
        bgnDenominations.put(10, 50);
        denominationsMap.put(Currency.BGN, bgnDenominations);

        final Map<Integer, Integer> eurDenominations = new HashMap<>();
        eurDenominations.put(10, 100);
        eurDenominations.put(50, 20);
        denominationsMap.put(Currency.EUR, eurDenominations);
    }

    private void saveBalances() {
        FileUtil.writeBalancesToFile(CASH_BALANCE_FILE, balanceMap, denominationsMap);
    }

    private String formatTransaction(final CashOperationRequest request,
                                     final int totalAmount) {
        final StringBuilder transactionDetails = new StringBuilder();
        transactionDetails.append(USER)
                .append(NEW_LINE);

        transactionDetails.append(OPERATION).append(request.getOperationType())
                .append(NEW_LINE);

        transactionDetails.append(CURRENCY).append(request.getCurrency())
                .append(NEW_LINE);

        transactionDetails.append(TOTAL_AMOUNT).append(totalAmount)
                .append(NEW_LINE);

        transactionDetails.append(DENOMINATIONS)
                .append(NEW_LINE);

        for (final Denomination denomination : request.getDenominations()) {
            transactionDetails.append(denomination.getValue()).append(X).append(denomination.getCount())
                    .append(NEW_LINE);
        }
        transactionDetails.append(DATE).append(new Date())
                .append(NEW_LINE);
        transactionDetails.append(SEPARATE_LINE)
                .append(NEW_LINE);

        return transactionDetails.toString();
    }

    private void validateRequest(final CashOperationRequest request) {
        if (!request.getOperationType().equalsIgnoreCase(DEPOSIT)
                && !request.getOperationType().equalsIgnoreCase(WITHDRAWAL)) {
            throw new IllegalArgumentException(OPERATION_TYPE_ERR);
        }

        if (request.getCurrency() != Currency.BGN && request.getCurrency() != Currency.EUR) {
            throw new IllegalArgumentException(CURRENCY_ERR);
        }

        for (final Denomination requestDenom : request.getDenominations()) {
            final int requestValue = requestDenom.getValue();
            final int requestCount = requestDenom.getCount();

            if (requestValue <= 0 || requestCount <= 0) {
                throw new IllegalArgumentException(DENOMINATION_ERR);
            }
        }
    }
}