package com.cashdeskmodule.utils;

import com.cashdeskmodule.model.Currency;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static com.cashdeskmodule.utils.Constants.*;

public class FileUtil {

    public static void appendToFile(final String fileName,
                                    final String content) {
        try (final BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true))) {
            bw.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeBalancesToFile(final String fileName,
                                           final Map<Currency, Integer> balanceMap,
                                           final Map<Currency, Map<Integer, Integer>> denominationsMap) {
        try (final BufferedWriter bw = new BufferedWriter(new FileWriter(fileName))) {
            for (Currency currency : balanceMap.keySet()) {
                bw.write(CURRENCY + currency + NEW_LINE);
                bw.write(BALANCE + balanceMap.get(currency) + NEW_LINE);
                bw.write(DENOMINATIONS + NEW_LINE);
                final Map<Integer, Integer> denominations = denominationsMap.get(currency);
                for (final Integer denomValue : denominations.keySet()) {
                    bw.write(denomValue + X + denominations.get(denomValue) + NEW_LINE);
                }
                bw.write(SEPARATE_LINE + NEW_LINE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //TODO
    //FUNCTIONALITY MUST BE IMPLEMENTED IN THE NEAR FUTURE
    public static Map<Currency, Integer> readBalancesFromFile(final String fileName) {
        final Map<Currency, Integer> balanceMap = new HashMap<>();
        return balanceMap;
    }

    //TODO
    //FUNCTIONALITY MUST BE IMPLEMENTED IN THE NEAR FUTURE
    public static Map<Currency, Map<Integer, Integer>> readDenominationsFromFile(final String fileName) {
        final Map<Currency, Map<Integer, Integer>> denominationsMap = new HashMap<>();
        return denominationsMap;
    }

    public static boolean fileExists(final String fileName) {
        final File file = new File(fileName);
        return file.exists();
    }
}