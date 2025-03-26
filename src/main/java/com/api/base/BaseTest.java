package com.api.base;
import com.api.utils.ConfigManager;
import com.api.utils.ExcelUtils;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeSuite;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import static io.restassured.RestAssured.given;

public class BaseTest {
    public static String authToken;
    public static String refreshToken;
    public static String filePath;
    public static String requestBody;
    public static String endpoint;
    public static String uploadFilePath;
    public static String uploadFileField;
    public static String proofMedia;
    public static String proofFilePath;
    public static String farmTaskId;
    public static String proofFileField;
    public static String type;
    public static String comments;
    public Map<String, Map<String, String>> testCaseMappings;
    public ExcelUtils excelManager;

    @BeforeSuite
    public void setup() {
        // Fetch values from config.properties
        String baseURL = ConfigManager.getProperty("baseURL");
        authToken = ConfigManager.getProperty("authToken");
        refreshToken = ConfigManager.getProperty("refreshToken");
        filePath = ConfigManager.getProperty("filePath");
        requestBody = ConfigManager.getProperty("requestBody");
        endpoint = ConfigManager.getProperty("API Endpoints");
        uploadFilePath = ConfigManager.getProperty("uploadFilePath");
        uploadFileField = ConfigManager.getProperty("uploadFileField");
        proofMedia = ConfigManager.getProperty("proofMedia");
        proofFilePath = ConfigManager.getProperty("proofFilePath");
        farmTaskId = ConfigManager.getProperty("farmtask_id");
        type = ConfigManager.getProperty("type");
        comments = ConfigManager.getProperty("comments");
        proofFileField = ConfigManager.getProperty("proofFileField");
        RestAssured.baseURI = baseURL;
    }

    public Map<String, Map<String, String>> readExcelSheet(String sheetName) throws IOException {
        excelManager = new ExcelUtils(filePath, sheetName);
        testCaseMappings = excelManager.loadTestCaseMappings();
        return testCaseMappings;
    }
    public Response executeRequest(String testCaseID) {
        return executeRequest(testCaseID, null); // Call the overloaded method with null filePath
    }
    public Response executeRequest(String testCaseID, String filePath) {
        Map<String, String> testCase = testCaseMappings.get(testCaseID);
        if (testCase == null) {
            throw new IllegalArgumentException("Test case ID not found: " + testCaseID);
        }
        String method = testCase.get("HTTP Request");
        String requestBody = testCase.get("Test Data ");
        String endpoint = testCase.get("API Endpoints");
        String authToken = testCase.get("Authorization");

        switch (method.toUpperCase()) {
            case "GET":
                return sendGetRequest(endpoint, authToken);
            case "POST":
                if (filePath != null && !filePath.isEmpty()) {
                    return sendFileUploadRequest(filePath, endpoint, authToken);
                } else if (proofMedia != null && !proofMedia.isEmpty()) {
                    return sendProofRequest(proofMedia, endpoint, authToken, type, comments, farmTaskId);
                } else {
                    return sendPostRequest(requestBody, endpoint, authToken);
                }
            case "PATCH":
                return sendPatchRequest(requestBody, endpoint, authToken);
            case "PUT":
                return sendPutRequest(requestBody, endpoint, authToken);
            case "DELETE":
                return sendDeleteRequest(endpoint, authToken);
            default:
                throw new IllegalArgumentException("Unsupported HTTP method: " + method);
        }
    }

    public Response sendGetRequest(String endpoint, String authToken) {
        return given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + authToken)
                .queryParams("farmtask_id", "7da24f13-bb07-4d61-a4f1-68606e0d1e0b")
                .when()
                .get(endpoint);
    }

    public Response sendPostRequest(Object requestBody, String endpoint, String authToken) {
        return given()
                .header("Authorization", "Bearer " + authToken)
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post(endpoint);
    }

    public Response sendFileUploadRequest(String filePath, String endpoint, String authToken) {
        File file = new File(filePath);
        return given()
                .header("Authorization", "Bearer " + authToken)
                .contentType("multipart/form-data")
                .multiPart(uploadFileField, file, "text/csv")
                .when()
                .post(endpoint);
    }

    public Response sendProofRequest(String proofMedia, String endpoint, String authToken, String type, String comments, String farmTaskId) {
        File file = new File(proofFilePath + proofMedia);
        String mimeType = "application/octet-stream"; // Default MIME type

        if (proofMedia.endsWith(".jpg") || proofMedia.endsWith(".jpeg")) {
            mimeType = "image/jpeg";
        } else if (proofMedia.endsWith(".mp3")) {
            mimeType = "image/mp3";
        } else if (proofMedia.endsWith(".mp4")) {
            mimeType = "video/mp4";
        }

        return given()
                .header("Authorization", "Bearer " + authToken)
                .contentType("multipart/form-data")
                .multiPart(proofFileField, file, mimeType)
                .formParam("farmtask_id", farmTaskId)
                .formParam("type", type)
                .formParam("comments", comments)
                .when()
                .post(endpoint);
    }

    public Response sendPatchRequest(String requestBody, String endpoint, String authToken) {
        return given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + authToken)
                .body(requestBody)
                .when()
                .patch(endpoint);
    }

    public Response sendPutRequest(String requestBody, String endpoint, String bearerToken) {
        return given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + bearerToken)
                .body(requestBody)
                .when()
                .put(endpoint);
    }

    public Response sendDeleteRequest(String endpoint, String bearerToken) {
        return given()
                .contentType("application/json")
                .header("Authorization", "Bearer " + bearerToken)
                .when()
                .delete(endpoint);
    }
}
