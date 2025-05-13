package com.thetestingacademy.tests.crud;

import com.thetestingacademy.base.BaseTest;
import com.thetestingacademy.endpoints.APIConstants;
import com.thetestingacademy.pojos.response.BookingResponse;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import org.testng.annotations.Test;

public class TestCreateBooking  extends BaseTest {

    // extend BaseTest to run certain functions

    @Test(groups = "reg", priority = 1)
    @Owner("Vidya")
    @Description("TC#1 - Verify that the Booking can be Created")
    public void testCreateBookingPOST_Positive() {

        // Setup and Making a Request.
        requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);
        response = RestAssured.given(requestSpecification).when().body(payloadManager.createPayloadBookingAsString()).log().all().post();
        System.out.println(response.asString());
        // Extraction
        BookingResponse bookingResponse = payloadManager.bookingResponseJava(response.asString());

        // Verification Part
        assertActions.verifyStringKeyNotNull(bookingResponse.getBookingid());
        assertActions.verifyStringKey(bookingResponse.getBooking().getFirstname(),"Vidya");

    }

    @Test(groups = "reg", priority = 1)
    @Owner("Vidya")
    @Description("TC#1 - Verify that the Booking can't be Created, When Payload is null")
    public void testCreateBookingPOST_Negative() {

        // Setup and Making a Request.
        // payload will be empty -> negative test case (empty body)
        // so no extraction just validatableResponse
        requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);
        response = RestAssured.given(requestSpecification).when().body("{}").log().all().post();
        System.out.println(response.asString());

        validatableResponse  = response.then().log().all();
        validatableResponse.statusCode(500);

    }

    @Test(groups = "reg", priority = 1)
    @Owner("Vidya")
    @Description("TC#1 - Verify that the Booking can be Created, When Payload is CHINESE")
    public void testCreateBookingPOST_POSITIVE_CHINESE() {

        // Setup and Making a Request.
        requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);
        response = RestAssured.given(requestSpecification).when().body(payloadManager.createPayloadBookingAsStringWrongBody()).log().all().post();
        System.out.println(response.asString());

        validatableResponse  = response.then().log().all();
        validatableResponse.statusCode(200);

    }

    // using Faker
    @Test(groups = "reg", priority = 1)
    @Owner("Vidya")
    @Description("TC#1 - Verify that the Booking can be Created, When Payload is RANDOM")
    public void testCreateBookingPOST_POSITIVE_RANDOM_DATA() {

        // Setup and Making a Request.
        requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);
        response = RestAssured.given(requestSpecification).when().body(payloadManager.createPayloadBookingFakerJS()).log().all().post();
        System.out.println(response.asString());

        validatableResponse  = response.then().log().all();
        validatableResponse.statusCode(200);

    }

}
