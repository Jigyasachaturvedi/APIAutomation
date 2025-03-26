package com.api.tests;
import com.api.base.BaseTest;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.io.IOException;

public class UploadFileTests extends BaseTest {
    String sheetName = "Upload File TC";

    @BeforeClass
    public void setUp() throws IOException {
        testCaseMappings = super.readExcelSheet(sheetName);
    }

    @Test(description = "TC001", priority = 1)
    public void testMissingMandatoryFields() {
        Response response = sendFileUploadRequest("src/test/resources/uploadFile/MissingMandatoryFields.csv", "/api/v1/farms/tasks/upload-csv", authToken);
        response.then().statusCode(400); // Expected failure when mandatory fields are left empty
        System.out.println("Upload Response: " + response.getBody().asString());
    }


    @Test(description = "TC002", priority = 2)
    public void testIncorrectColumnCasing() {
        Response response = sendFileUploadRequest("src/test/resources/uploadFile/IncorrectColumnCasing.csv", "/api/v1/farms/tasks/upload-csv", authToken);
        response.then().statusCode(400); // Expected failure when Incorrect Column Heading is used
        System.out.println("Response Body: " + response.getBody().asString());
    }

    @Test(description = "TC003", priority = 3)
    public void testDuplicity() {
        Response response = sendFileUploadRequest("src/test/resources/uploadFile/AlreadyExistingFarmTask.csv", "/api/v1/farms/tasks/upload-csv", authToken);
        response.then().statusCode(200); // Expected success when file with already existing farm task on dashboard is uploaded (No overwrite)
        System.out.println("Upload Progress Response: " + response.getBody().asString());
        String data = response.jsonPath().getString("data");
        System.out.println("Upload ID: " + data);
        String statusEndpoint = "/api/v1/farms/tasks/status/" + data;
        Response statusResponse = sendGetRequest(statusEndpoint, authToken);
        statusResponse.then().statusCode(200);
        System.out.println("Upload Status Response: " + statusResponse.getBody().asString());
    }

    @Test(description = "TC004", priority = 4)
    public void testDuplicateFarmRows() {
        Response response = sendFileUploadRequest("src/test/resources/uploadFile/DuplicateFarmRows.csv", "/api/v1/farms/tasks/upload-csv", authToken);
        response.then().statusCode(200); // Expected success when two exact same rows are present in csv file
        System.out.println("Upload Progress Response: " + response.getBody().asString());
        String data = response.jsonPath().getString("data");
        System.out.println("Upload ID: " + data);
        String statusEndpoint = "/api/v1/farms/tasks/status/" + data;
        Response statusResponse = sendGetRequest(statusEndpoint, authToken);
        statusResponse.then().statusCode(200);
        System.out.println("Upload Status Response: " + statusResponse.getBody().asString());
    }

    @Test(description = "TC005", priority = 5)
    public void testEmptyCSVFile() {
        Response response = sendFileUploadRequest("src/test/resources/uploadFile/EmptyCSVFile.csv", "/api/v1/farms/tasks/upload-csv", authToken);
        response.then().statusCode(400); // Expected failure when empty csv file is uploaded
        System.out.println("Response Body: " + response.getBody().asString());
    }

    @Test(description = "TC006", priority = 6)
    public void testColumnMissingInBetween() {
        Response response = sendFileUploadRequest("src/test/resources/uploadFile/ColumnEmptyInBetween.csv", "/api/v1/farms/tasks/upload-csv", authToken);
        response.then().statusCode(200); // Expected success when column is left empty in between
        System.out.println("Upload Progress Response: " + response.getBody().asString());
        String data = response.jsonPath().getString("data");
        System.out.println("Upload ID: " + data);
        String statusEndpoint = "/api/v1/farms/tasks/status/" + data;
        Response statusResponse = sendGetRequest(statusEndpoint, authToken);
        statusResponse.then().statusCode(200);
        System.out.println("Upload Status Response: " + statusResponse.getBody().asString());
    }

    @Test(description = "TC007", priority = 7)
    public void testLargeCSVFile() {
        Response response = sendFileUploadRequest("src/test/resources/uploadFile/OptimizationCheck.csv", "/api/v1/farms/tasks/upload-csv", authToken);
        response.then().statusCode(200); // Expected success when large data csv file is uploaded
        System.out.println("Upload Progress Response: " + response.getBody().asString());
        String data = response.jsonPath().getString("data");
        System.out.println("Upload ID: " + data);
        String statusEndpoint = "/api/v1/farms/tasks/status/" + data;
        Response statusResponse = sendGetRequest(statusEndpoint, authToken);
        statusResponse.then().statusCode(200);
        System.out.println("Upload Status Response: " + statusResponse.getBody().asString());
    }

    @Test(description = "TC008", priority = 8)
    public void testPastDateData() {
        Response response = sendFileUploadRequest("src/test/resources/uploadFile/PastDateData.csv", "/api/v1/farms/tasks/upload-csv", authToken);
        response.then().statusCode(200); // Expected success when past date data is uploaded (No overwrite)
        System.out.println("Upload Progress Response: " + response.getBody().asString());
        String data = response.jsonPath().getString("data");
        System.out.println("Upload ID: " + data);
        String statusEndpoint = "/api/v1/farms/tasks/status/" + data;
        Response statusResponse = sendGetRequest(statusEndpoint, authToken);
        statusResponse.then().statusCode(200);
        System.out.println("Upload Status Response: " + statusResponse.getBody().asString());
    }

    @Test(description = "TC009", priority = 9)
    public void testOneRowEmpty() {
        Response response = sendFileUploadRequest("src/test/resources/uploadFile/LeavingOneRow.csv", "/api/v1/farms/tasks/upload-csv", authToken);
        response.then().statusCode(200); // Expected success when one row is left empty in csv
        System.out.println("Upload Progress Response: " + response.getBody().asString());
        String data = response.jsonPath().getString("data");
        System.out.println("Upload ID: " + data);
        String statusEndpoint = "/api/v1/farms/tasks/status/" + data;
        Response statusResponse = sendGetRequest(statusEndpoint, authToken);
        statusResponse.then().statusCode(200);
        System.out.println("Upload Status Response: " + statusResponse.getBody().asString());
    }

    @Test(description = "TC010", priority = 10)
    public void testSuccessfulCSVUpload() {
        Response response = sendFileUploadRequest("src/test/resources/uploadFile/SuccessfulCSVUpload.csv", "/api/v1/farms/tasks/upload-csv", authToken);
        response.then().statusCode(200); // Expected success when valid csv is uploaded
        System.out.println("Upload Progress Response: " + response.getBody().asString());
        String data = response.jsonPath().getString("data");
        System.out.println("Upload ID: " + data);
        String statusEndpoint = "/api/v1/farms/tasks/status/" + data;
        Response statusResponse = sendGetRequest(statusEndpoint, authToken);
        statusResponse.then().statusCode(200);
        System.out.println("Upload Status Response: " + statusResponse.getBody().asString());
    }

    @Test(description = "TC011", priority = 11)
    public void testNotExistentEntries() {
        Response response = sendFileUploadRequest("src/test/resources/uploadFile/NonExistentFarmUserCrop.csv", "/api/v1/farms/tasks/upload-csv", authToken);
        response.then().statusCode(200);
        System.out.println("Upload Progress Response: " + response.getBody().asString());
        String data = response.jsonPath().getString("data");
        System.out.println("Upload ID: " + data);
        String statusEndpoint = "/api/v1/farms/tasks/status/" + data;
        Response statusResponse = sendGetRequest(statusEndpoint, authToken);
        statusResponse.then().statusCode(200);
        System.out.println("Upload Status Response: " + statusResponse.getBody().asString());
    }

    @Test(description = "TC012", priority = 12)
    public void testInvalidPriority() {
        Response response = sendFileUploadRequest("src/test/resources/uploadFile/InvalidPriority.csv", "/api/v1/farms/tasks/upload-csv", authToken);
        response.then().statusCode(200);
        System.out.println("Upload Progress Response: " + response.getBody().asString());
        String data = response.jsonPath().getString("data");
        System.out.println("Upload ID: " + data);
        String statusEndpoint = "/api/v1/farms/tasks/status/" + data;
        Response statusResponse = sendGetRequest(statusEndpoint, authToken);
        statusResponse.then().statusCode(200);
        System.out.println("Upload Status Response: " + statusResponse.getBody().asString());
    }

    @Test(description = "TC013", priority = 13)
    public void testInvalidFileFormat() {
        Response response = sendFileUploadRequest("src/test/resources/uploadFile/InvalidFileFormat.jpg", "/api/v1/farms/tasks/upload-csv", authToken);
        response.then().statusCode(422);
        System.out.println("Response Body: " + response.getBody().asString());
    }

    @Test(description = "TC014", priority = 14)
    public void testExtraFieldCSV() {
        Response response = sendFileUploadRequest("src/test/resources/uploadFile/TestExtraField.csv", "/api/v1/farms/tasks/upload-csv", authToken);
        response.then().statusCode(400);
        System.out.println("Response Body: " + response.getBody().asString());
    }

    @Test(description = "TC015", priority = 15)
    public void testMissingMandatoryData() {
        Response response = sendFileUploadRequest("src/test/resources/uploadFile/MissingMandatoryData.csv", "/api/v1/farms/tasks/upload-csv", authToken);
        response.then().statusCode(400);
        System.out.println("Upload Progress Response: " + response.getBody().asString());
    }

    @Test(description = "TC016", priority = 16)
    public void testReuploadOfDownloadedFile() {
        Response response = sendFileUploadRequest("src/test/resources/uploadFile/ReuploadDownloadedFile.csv", "/api/v1/farms/tasks/upload-csv", authToken);
        response.then().statusCode(200);
        System.out.println("Upload Progress Response: " + response.getBody().asString());
        String data = response.jsonPath().getString("data");
        System.out.println("Upload ID: " + data);
        String statusEndpoint = "/api/v1/farms/tasks/status/" + data;
        Response statusResponse = sendGetRequest(statusEndpoint, authToken);
        statusResponse.then().statusCode(200);
        System.out.println("Upload Status Response: " + statusResponse.getBody().asString());
    }

    @Test(description = "TC017", priority = 17)
    public void testExtraFieldXlsx() {
        Response response = sendFileUploadRequest("src/test/resources/uploadFile/TestExtraField.xlsx", "/api/v1/farms/tasks/upload-csv", authToken);
        response.then().statusCode(400);
        System.out.println("Response Body: " + response.getBody().asString());
    }

    @Test(description = "TC018", priority = 18)
    public void testMissingMandatoryDataXlsx() {
        Response response = sendFileUploadRequest("src/test/resources/uploadFile/MissingMandatoryData.xlsx", "/api/v1/farms/tasks/upload-csv", authToken);
        response.then().statusCode(400);
        System.out.println("Upload Progress Response: " + response.getBody().asString());
    }

    @Test(description = "TC019", priority = 19)
    public void testMissingMandatoryFieldsXlsx() {
        Response response = sendFileUploadRequest("src/test/resources/uploadFile/MissingMandatoryFields.xlsx", "/api/v1/farms/tasks/upload-csv", authToken);
        response.then().statusCode(400);
        System.out.println("Upload Progress Response: " + response.getBody().asString());
    }

    @Test(description = "TC020", priority = 20)
    public void testEmptyXLSXFile() {
        Response response = sendFileUploadRequest("src/test/resources/uploadFile/EmptyCSVFile.xlsx", "/api/v1/farms/tasks/upload-csv", authToken);
        response.then().statusCode(400);
        System.out.println("Response Body: " + response.getBody().asString());
    }

    @Test(description = "TC021", priority = 21)
    public void testIncorrectColumnCasingExcel() {
        Response response = sendFileUploadRequest("src/test/resources/uploadFile/IncorrectColumnCasing.xlsx", "/api/v1/farms/tasks/upload-csv", authToken);
        response.then().statusCode(400);
        System.out.println("Response Body: " + response.getBody().asString());
    }

    @Test(description = "TC022", priority = 22)
    public void testDuplicityExcel() {
        Response response = sendFileUploadRequest("src/test/resources/uploadFile/AlreadyExistingFarmTask.xlsx", "/api/v1/farms/tasks/upload-csv", authToken);
        response.then().statusCode(200);
        System.out.println("Upload Progress Response: " + response.getBody().asString());
        String data = response.jsonPath().getString("data");
        System.out.println("Upload ID: " + data);
        String statusEndpoint = "/api/v1/farms/tasks/status/" + data;
        Response statusResponse = sendGetRequest(statusEndpoint, authToken);
        statusResponse.then().statusCode(200);
        System.out.println("Upload Status Response: " + statusResponse.getBody().asString());
    }

    @Test(description = "TC023", priority = 23)
    public void testDuplicateFarmRowsExcel() {
        Response response = sendFileUploadRequest("src/test/resources/uploadFile/DuplicateFarmRows.xlsx", "/api/v1/farms/tasks/upload-csv", authToken);
        response.then().statusCode(200);
        System.out.println("Upload Progress Response: " + response.getBody().asString());
        String data = response.jsonPath().getString("data");
        System.out.println("Upload ID: " + data);
        String statusEndpoint = "/api/v1/farms/tasks/status/" + data;
        Response statusResponse = sendGetRequest(statusEndpoint, authToken);
        statusResponse.then().statusCode(200);
        System.out.println("Upload Status Response: " + statusResponse.getBody().asString());
    }

    @Test(description = "TC024", priority = 24)
    public void testColumnMissingInBetweenExcel() {
        Response response = sendFileUploadRequest("src/test/resources/uploadFile/ColumnEmptyInBetween.xlsx", "/api/v1/farms/tasks/upload-csv", authToken);
        response.then().statusCode(200);
        System.out.println("Upload Progress Response: " + response.getBody().asString());
        String data = response.jsonPath().getString("data");
        System.out.println("Upload ID: " + data);
        String statusEndpoint = "/api/v1/farms/tasks/status/" + data;
        Response statusResponse = sendGetRequest(statusEndpoint, authToken);
        statusResponse.then().statusCode(200);
        System.out.println("Upload Status Response: " + statusResponse.getBody().asString());
    }

    @Test(description = "TC025", priority = 25)
    public void testLargeExcelFile() {
        Response response = sendFileUploadRequest("src/test/resources/uploadFile/OptimizationCheck.xlsx", "/api/v1/farms/tasks/upload-csv", authToken);
        response.then().statusCode(200);
        System.out.println("Upload Progress Response: " + response.getBody().asString());
        String data = response.jsonPath().getString("data");
        System.out.println("Upload ID: " + data);
        String statusEndpoint = "/api/v1/farms/tasks/status/" + data;
        Response statusResponse = sendGetRequest(statusEndpoint, authToken);
        statusResponse.then().statusCode(200);
        System.out.println("Upload Status Response: " + statusResponse.getBody().asString());
    }

    @Test(description = "TC026", priority = 26)
    public void testPastDateDataExcel() {
        Response response = sendFileUploadRequest("src/test/resources/uploadFile/PastDateData.xlsx", "/api/v1/farms/tasks/upload-csv", authToken);
        response.then().statusCode(200);
        System.out.println("Upload Progress Response: " + response.getBody().asString());
        String data = response.jsonPath().getString("data");
        System.out.println("Upload ID: " + data);
        String statusEndpoint = "/api/v1/farms/tasks/status/" + data;
        Response statusResponse = sendGetRequest(statusEndpoint, authToken);
        statusResponse.then().statusCode(200);
        System.out.println("Upload Status Response: " + statusResponse.getBody().asString());
    }

    @Test(description = "TC027", priority = 27)
    public void testOneRowEmptyExcel() {
        Response response = sendFileUploadRequest("src/test/resources/uploadFile/LeavingOneRow.xlsx", "/api/v1/farms/tasks/upload-csv", authToken);
        response.then().statusCode(200);
        System.out.println("Upload Progress Response: " + response.getBody().asString());
        String data = response.jsonPath().getString("data");
        System.out.println("Upload ID: " + data);
        String statusEndpoint = "/api/v1/farms/tasks/status/" + data;
        Response statusResponse = sendGetRequest(statusEndpoint, authToken);
        statusResponse.then().statusCode(200);
        System.out.println("Upload Status Response: " + statusResponse.getBody().asString());
    }

    @Test(description = "TC028", priority = 28)
    public void testSuccessfulCSVUploadExcel() {
        Response response = sendFileUploadRequest("src/test/resources/uploadFile/SuccessfulCSVUpload.xlsx", "/api/v1/farms/tasks/upload-csv", authToken);
        response.then().statusCode(200); // Expected success when valid csv is uploaded
        System.out.println("Upload Progress Response: " + response.getBody().asString());
        String data = response.jsonPath().getString("data");
        System.out.println("Upload ID: " + data);
        String statusEndpoint = "/api/v1/farms/tasks/status/" + data;
        Response statusResponse = sendGetRequest(statusEndpoint, authToken);
        statusResponse.then().statusCode(200);
        System.out.println("Upload Status Response: " + statusResponse.getBody().asString());
    }

    @Test(description = "TC029", priority = 29)
    public void testNotExistentEntriesExcel() {
        Response response = sendFileUploadRequest("src/test/resources/uploadFile/NonExistentFarmUserCrop.xlsx", "/api/v1/farms/tasks/upload-csv", authToken);
        response.then().statusCode(200);
        System.out.println("Upload Progress Response: " + response.getBody().asString());
        String data = response.jsonPath().getString("data");
        System.out.println("Upload ID: " + data);
        String statusEndpoint = "/api/v1/farms/tasks/status/" + data;
        Response statusResponse = sendGetRequest(statusEndpoint, authToken);
        statusResponse.then().statusCode(200);
        System.out.println("Upload Status Response: " + statusResponse.getBody().asString());
    }

    @Test(description = "TC030", priority = 30)
    public void testInvalidPriorityExcel() {
        Response response = sendFileUploadRequest("src/test/resources/uploadFile/InvalidPriority.xlsx", "/api/v1/farms/tasks/upload-csv", authToken);
        response.then().statusCode(200);
        System.out.println("Upload Progress Response: " + response.getBody().asString());
        String data = response.jsonPath().getString("data");
        System.out.println("Upload ID: " + data);
        String statusEndpoint = "/api/v1/farms/tasks/status/" + data;
        Response statusResponse = sendGetRequest(statusEndpoint, authToken);
        statusResponse.then().statusCode(200);
        System.out.println("Upload Status Response: " + statusResponse.getBody().asString());
    }

    @Test(description = "TC031", priority = 31)
    public void testReuploadOfDownloadedFileExcel() {
        Response response = sendFileUploadRequest("src/test/resources/uploadFile/ReuploadDownloadedFile.xlsx", "/api/v1/farms/tasks/upload-csv", authToken);
        response.then().statusCode(200);
        System.out.println("Upload Progress Response: " + response.getBody().asString());
        String data = response.jsonPath().getString("data");
        System.out.println("Upload ID: " + data);
        String statusEndpoint = "/api/v1/farms/tasks/status/" + data;
        Response statusResponse = sendGetRequest(statusEndpoint, authToken);
        statusResponse.then().statusCode(200);
        System.out.println("Upload Status Response: " + statusResponse.getBody().asString());
    }

    @Test(description = "TC032", priority = 32)
    public void testInvalidAssignedDate() {
        Response response = sendFileUploadRequest("src/test/resources/uploadFile/InvalidAssignedDate.csv", "/api/v1/farms/tasks/upload-csv", authToken);
        response.then().statusCode(200);
        System.out.println("Response Body: " + response.getBody().asString());
        String data = response.jsonPath().getString("data");
        System.out.println("Upload ID: " + data);
        String statusEndpoint = "/api/v1/farms/tasks/status/" + data;
        Response statusResponse = sendGetRequest(statusEndpoint, authToken);
        statusResponse.then().statusCode(200); //This is a bug!
        System.out.println("Upload Status Response: " + statusResponse.getBody().asString());
    }
}

