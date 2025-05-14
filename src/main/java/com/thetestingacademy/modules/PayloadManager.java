package com.thetestingacademy.modules;

import com.github.javafaker.Faker;

import com.google.gson.Gson;
import com.thetestingacademy.pojos.request.Auth;
import com.thetestingacademy.pojos.request.Booking;
import com.thetestingacademy.pojos.request.BookingDates;
import com.thetestingacademy.pojos.response.BookingResponse;
import com.thetestingacademy.pojos.response.TokenResponse;

public class PayloadManager {

    // so for every request we need to write Request Payload (Serialization) & Response Payload (Deserialization) in PayloadManager

    Gson gson;
    Faker faker;

    // Convert the Java Object into the JSON String to use as Payload.
    // Serialization
    public String createPayloadBookingAsString(){
        Booking booking = new Booking();
        booking.setFirstname("Vidya");
        booking.setLastname("Nayak");
        booking.setTotalprice(112);
        booking.setDepositpaid(true);

        BookingDates bookingdates = new BookingDates();
        bookingdates.setCheckin("2024-02-01");
        bookingdates.setCheckout("2024-02-01");
        booking.setBookingDates(bookingdates);
        booking.setAdditionalneeds("Breakfast");

        System.out.println(booking);

        // Java Object -> JSON
        gson = new Gson();
        String jsonStringBooking = gson.toJson(booking);
        return jsonStringBooking;

    }

    // duplicate of above function with wrong data
    // passing future dates / chinese characters
    public String createPayloadBookingAsStringWrongBody(){
        Booking booking = new Booking();
        booking.setFirstname("会意; 會意");
        booking.setLastname("会意; 會意");
        booking.setTotalprice(112);
        booking.setDepositpaid(false);

        BookingDates bookingdates = new BookingDates();
        bookingdates.setCheckin("5025-02-01");
        bookingdates.setCheckout("5025-02-01");
        booking.setBookingDates(bookingdates);
        booking.setAdditionalneeds("会意; 會意");

        System.out.println(booking);

        // Java Object -> JSON
        gson = new Gson();
        String jsonStringBooking = gson.toJson(booking);
        return jsonStringBooking;

    }






    // Convert the JSON String to Java Object so that we can verify response Body
    // DeSerialization
    // whenever we make a post/ patch request we get  a booking response
    public BookingResponse bookingResponseJava(String responseString) {
        gson = new Gson();
        BookingResponse bookingResponse = gson.fromJson(responseString, BookingResponse.class);
        return bookingResponse;
    }

    // Java Object -> JSON
    // setting up Payload for Token
    public String setAuthPayload(){
        Auth auth = new Auth();
        auth.setUsername("admin");
        auth.setPassword("password123");
        gson = new Gson();
        String jsonPayloadString = gson.toJson(auth);
        System.out.println("Payload set to the -> " + jsonPayloadString);
        return jsonPayloadString;

    }

    // DeSer (JSON String -> Java Object)
    public String getTokenFromJSON(String tokenResponse){
        gson = new Gson();
        TokenResponse tokenResponse1 = gson.fromJson(tokenResponse, TokenResponse.class);
        return  tokenResponse1.getToken();
    }





    // by using Faker -> which creates random real data
    // duplicating above main function & trying to change the payload with random values
    public String createPayloadBookingFakerJS(){
        faker  = new Faker();
        Booking booking = new Booking();
        booking.setFirstname(faker.name().firstName());
        booking.setLastname(faker.name().lastName());
        booking.setTotalprice(faker.random().nextInt(1,1000)); // random integer from 1 to 1000
        booking.setDepositpaid(faker.random().nextBoolean());

        BookingDates bookingdates = new BookingDates();
        bookingdates.setCheckin("2024-02-01");
        bookingdates.setCheckout("2024-02-01");
        booking.setBookingDates(bookingdates);
        booking.setAdditionalneeds("Breakfast");

        System.out.println(booking);

        // Java Object -> JSON
        gson = new Gson();
        String jsonStringBooking = gson.toJson(booking);
        return jsonStringBooking;

    }




    // but whenever we make a get request we get that booking information as response
    public Booking getResponseFromJSON(String getResponse) {
        gson = new Gson();
        Booking booking = gson.fromJson(getResponse, Booking.class);
        return booking;
    }



    // Full Update -> PUT Request updates entire payload,
    // so we need to send all the details
    public String fullUpdatePayloadAsString() {
        Booking booking = new Booking();
        booking.setFirstname("Vaidehi");
        booking.setLastname("Kini");
        booking.setTotalprice(125);
        booking.setDepositpaid(true);

        BookingDates bookingdates = new BookingDates();
        bookingdates.setCheckin("2024-04-01");
        bookingdates.setCheckout("2024-04-05");
        booking.setBookingDates(bookingdates);
        booking.setAdditionalneeds("Lunch");
        return gson.toJson(booking);
    }


    // For Partial Update -> PATCH Request update some details using payload
    // so partial detail are enough to update against a bookingId
     public String partialUpdatePayloadAsString() {
        Booking booking = new Booking();
        booking.setFirstname("John");
        booking.setLastname("Grisham");
        return gson.toJson(booking);

    }

}