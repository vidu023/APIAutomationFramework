package com.thetestingacademy.base;

import com.thetestingacademy.asserts.AssertActions;
import com.thetestingacademy.endpoints.APIConstants;
import com.thetestingacademy.modules.PayloadManager;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class BaseTest {

     // CommonToAll Testcase
    // Base URL, Content Type - json - common

    // before (setup)& after(tearDown/ finish) running tests/ method/ class levels... I want to do something
    // we shall be calling RequestSpecification, AssertAction, PayloadManager, JsonPath, Response & ValidatableResponse

    // so it will run accordingly

    public RequestSpecification requestSpecification;
    public AssertActions assertActions;
    public PayloadManager payloadManager;
    public JsonPath jsonPath;
    public Response response;
    public ValidatableResponse validatableResponse;


    @BeforeTest
    public void setup() {
        System.out.println("Starting of the Test");
        payloadManager = new PayloadManager();
        assertActions = new AssertActions();

//        requestSpecification = RestAssured.given();
//        requestSpecification.baseUri(APIConstants.BASE_URL);
//        requestSpecification.contentType(ContentType.JSON).log().all();

        requestSpecification = new RequestSpecBuilder()
                // instead of using RestAssured.given() we shall use RequestSpecBuilder class
                .setBaseUri(APIConstants.BASE_URL)
                .addHeader("Content-Type", "application/json")
                .build().log().all();

        // before running any testcase we need the above url to be called

    }

        // Token is one thing that is required by almost all testcases,
        // so it would be a good practice to add getToken in BaseTest class
        // Token is generally generated onetime & is applicable throughout the session

        // we don't add @BeforeTest because it shall start generating Token for every test,
        // and we don't want to generate token for each test
        // 1 token is enough for the entire test case integration that is why just a normal function
      public  String getToken(){
        requestSpecification = RestAssured.given();
        requestSpecification.baseUri(APIConstants.BASE_URL)
                .basePath(APIConstants.AUTH_URL);
        // Setting the payload
        String payload = payloadManager.setAuthPayload();
        // Get the Token from above response
        response = requestSpecification.contentType(ContentType.JSON).body(payload).when().post();
        String token = payloadManager.getTokenFromJSON(response.asString());
        return token;

    }


    @AfterTest
    public void tearDown() {
        System.out.println("Finished the Test!");
    }


}
