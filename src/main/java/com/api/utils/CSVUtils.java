package com.api.utils;

import com.opencsv.CSVReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVUtils {

    public static Map<String, Map<String, String>> loadTestCaseMappings(String filePath) {
        Map<String, Map<String, String>> testCaseMap = new HashMap<>();
        try (CSVReader reader = new CSVReader(new FileReader(filePath))) {
            List<String[]> data = reader.readAll();
            String[] headers = data.get(0); // First row is the header

            for (int i = 1; i < data.size(); i++) {
                String testCaseId = data.get(i)[0];
                Map<String, String> testCaseDetails = new HashMap<>();

                for (int j = 1; j < headers.length; j++) {
                    testCaseDetails.put(headers[j], data.get(i)[j]);
                }
                testCaseMap.put(testCaseId, testCaseDetails);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return testCaseMap;
    }
}
