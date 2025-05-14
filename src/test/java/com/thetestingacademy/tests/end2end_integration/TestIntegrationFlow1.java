package com.thetestingacademy.tests.end2end_integration;

import com.thetestingacademy.base.BaseTest;
import com.thetestingacademy.endpoints.APIConstants;
import com.thetestingacademy.pojos.request.Booking;
import com.thetestingacademy.pojos.response.BookingResponse;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.annotations.Test;

public class TestIntegrationFlow1 extends BaseTest {

    // TestE2EFlow_01

    //  Test E2E Scenario 1 -
    // 1. Create a Booking -> bookingID
    // 2. Create Token -> token
    // 3. Verify that the Create Booking is working - GET Request to bookingID
    // 4. Update the booking ( bookingID, Token) - Need to get the token, bookingID from above request
    // 5. Delete the Booking - Need to get the token, bookingID from above request

    // to handle SSL in RestAssured we shall use relaxedHTTPSValidation()

    // we need to transfer data from 1 testcase to another such as bookingId & token
    // i.e. from 1 & 2 to 3, 4 & 5

    // take a copy from sample -> TestSampleIntegration

    // Testcase 1
    @Test(groups = "qa", priority = 1)
    @Owner("Vidya")
    @Description("TC#INT1 - Step 1. Verify that the Booking can be Created")
    // we shall pass the ITestContext listener
    public void testCreateBooking(ITestContext iTestContext){

        requestSpecification.basePath(APIConstants.CREATE_UPDATE_BOOKING_URL);
        response = RestAssured.given(requestSpecification)
                .when().body(payloadManager.createPayloadBookingAsString())
                .post();
        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(200);
        BookingResponse bookingResponse = payloadManager.bookingResponseJava(response.asString());
        assertActions.verifyStringKey(bookingResponse.getBooking().getFirstname(), "Vidya");
        assertActions.verifyStringKeyNotNull(bookingResponse.getBookingid());

        // we need bookingId from this testcase payload in further testcases
        // method to  setAttribute
        iTestContext.setAttribute("bookingid",bookingResponse.getBookingid());
    }

    // Testcase no. 2
    @Test(groups = "qa", priority = 2)
    @Owner("Vidya")
    @Description("TC#INT1 - Step 2. Verify that the Booking By ID")
    public void testVerifyBookingId(ITestContext iTestContext){
        // we need bookingId so getAttribute() method will get
        // reverse function of setAttribute
        // it will give us in String -> so convert it into Integer
        Integer bookingid = (Integer) iTestContext.getAttribute("bookingid");

        String basePathGET = APIConstants.CREATE_UPDATE_BOOKING_URL+"/" + bookingid;
        System.out.println(basePathGET);

        requestSpecification.basePath(basePathGET);
        response = RestAssured
                .given(requestSpecification)
                .when().get();
        validatableResponse = response.then().log().all();
        // Validatable Assertion
        validatableResponse.statusCode(200);

        // we get a booking -> Booking Class as response
        Booking booking = payloadManager.getResponseFromJSON(response.asString());
        assertActions.verifyStringKeyNotNull(booking.getFirstname());
    }

    // If we have more integration test scenarios where we need to create booking say 100 times
    // ideal way is to add it in BaseTest and then call the function
    // but bookingId we can manipulate & Token is what we cannot manipulate so getToken() is added in BaseTest
    // manipulating means -> Update, Delete
    // Token we just generate & use it

    @Test(groups = "qa", priority = 3)
    @Owner("Vidya")
    @Description("TC#INT1 - Step 3. Verify Updated Booking by ID")
    public void testUpdateBookingByID(ITestContext iTestContext){

        // For Updation we require Token & BookingId
        Integer bookingid = (Integer) iTestContext.getAttribute("bookingid");
        // Creating a Token
        String token = getToken();
        // Setting this token to a setAttribute() for reusing
        iTestContext.setAttribute("token",token);


        String basePathPUTPATCH = APIConstants.CREATE_UPDATE_BOOKING_URL + "/" + bookingid;
        System.out.println(basePathPUTPATCH);

        requestSpecification.basePath(basePathPUTPATCH);

        response = RestAssured
                .given(requestSpecification).cookie("token", token)
                .when().body(payloadManager.fullUpdatePayloadAsString()).put();


        validatableResponse = response.then().log().all();

        // Validatable Assertion
        validatableResponse.statusCode(200);

        Booking booking = payloadManager.getResponseFromJSON(response.asString());

        assertActions.verifyStringKeyNotNull(booking.getFirstname());
        assertActions.verifyStringKey(booking.getFirstname(),"Vaidehi");


    }

    @Test(groups = "qa", priority = 4)
    @Owner("Vidya")
    @Description("TC#INT1 - Step 4. Delete the Booking by ID")
    public void testDeleteBookingById(ITestContext iTestContext){

        // Again I don't need to create Token
        // I have set this which I can reuse

        Integer bookingid = (Integer) iTestContext.getAttribute("bookingid");
        String token = (String)iTestContext.getAttribute("token");

        // if token gets expired
        // then I can again create it
        /* if(token.equalsIgnoreCase(null)){
            token = getToken();
        } */

        String basePathDELETE = APIConstants.CREATE_UPDATE_BOOKING_URL + "/" + bookingid;

        requestSpecification.basePath(basePathDELETE).cookie("token", token);
        validatableResponse = RestAssured.given().spec(requestSpecification)
                .when().delete().then().log().all();
        validatableResponse.statusCode(201);




    }

}
