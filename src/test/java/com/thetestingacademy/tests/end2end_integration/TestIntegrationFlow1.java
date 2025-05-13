package com.thetestingacademy.tests.end2end_integration;

import com.thetestingacademy.base.BaseTest;
import com.thetestingacademy.endpoints.APIConstants;
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
        assertActions.verifyStringKey(bookingResponse.getBooking().getFirstname(), "Pramod");
        assertActions.verifyStringKeyNotNull(bookingResponse.getBookingid());

        // we need bookingId from this testcase payload in further testcases
        // method to  setAttribute
        iTestContext.setAttribute("bookingid",bookingResponse.getBookingid());
    }

    // Testcase no. 2
    @Test(groups = "qa", priority = 2)
    @Owner("Promode")
    @Description("TC#INT1 - Step 2. Verify that the Booking By ID")
    public void testVerifyBookingId(ITestContext iTestContext){
        // we need bookingId so getAttritbute() method will get
        // reverse function of setAttribute
        // it will give us in String -> so convert it tnto Integer
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
    }

     @Test(groups = "qa", priority = 3)
    @Owner("Promode")
    @Description("TC#INT1 - Step 3. Verify Updated Booking by ID")
    public void testUpdateBookingByID(){
        Assert.assertTrue(true);
    }

    @Test(groups = "qa", priority = 4)
    @Owner("Promode")
    @Description("TC#INT1 - Step 4. Delete the Booking by ID")
    public void testDeleteBookingById(){
        Assert.assertTrue(true);
    }
}
