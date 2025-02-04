package apirequests;

import io.restassured.RestAssured;

public class ApiBase {

    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }
}
