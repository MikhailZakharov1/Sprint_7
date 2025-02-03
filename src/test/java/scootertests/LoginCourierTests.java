package scootertests;

import apirequests.CourierRequests;
import com.example.Courier;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.*;

public class LoginCourierTests {
    Courier courier = new Courier("Fish", "underwater");
    CourierRequests courierRequests = new CourierRequests();


    @Before
    public void setUp(){courierRequests.setUp();}

    @Test
    @DisplayName("Авторизация курьера в системе")
    @Description("Проверка авторизации с корректными логином и паролем")
    public void checkLoginCourier() {
        courierRequests.setCourier(courier);
        courierRequests.createCourier();
        courierRequests.loginCourier()
                .then().assertThat().body("id", notNullValue())
                .and()
                .statusCode(SC_OK);

    }

    @Test
    @DisplayName("Авторизация без логина")
    @Description("Проверка невозможности авторизоваться без указания логина")
    public void checkLoginWithoutLogin(){
        courierRequests.setCourier(new Courier("","underwater"));
        courierRequests.loginCourier()
                .then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Авторизация без пароля")
    @Description("Проверка невозможности авторизоваться без указания пароля")
    public void checkLoginWithoutPassword(){
        courierRequests.setCourier(new Courier("SuperCourier",""));
        courierRequests.loginCourier()
                .then().assertThat().body("message", equalTo("Недостаточно данных для входа"))
                .and()
                .statusCode(SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Авторизация несуществующим курьером")
    @Description("Проверка авторизации ранее не созданным курьером")
    public void checkLoginNonExisting(){
        courierRequests.setCourier(courier);
        courierRequests.loginCourier()
                .then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(SC_NOT_FOUND);
    }

    @Test
    @DisplayName("Авторизация с указанием неверного логина")
    @Description("Проверка авторизации с вводом неверного логина")
    public void checkLoginIncorrectLogin() {
        courierRequests.setCourier(courier);
        courierRequests.createCourier();
        courierRequests.setCourier(new Courier("CorgieWillie","Sobaka"));
        courierRequests.loginCourier()
                .then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(SC_NOT_FOUND);
        courierRequests.setCourier(courier);
    }

    @Test
    @DisplayName("Авторизация с указанием неверного пароля")
    @Description("Проверка авторизации с вводом неверного пароля")
    public void checkLoginIncorrectPassword() {
        courierRequests.setCourier(courier);
        courierRequests.createCourier();
        courierRequests.setCourier(new Courier("Fish","onwater"));
        courierRequests.loginCourier()
                .then().assertThat().body("message", equalTo("Учетная запись не найдена"))
                .and()
                .statusCode(SC_NOT_FOUND);
        courierRequests.setCourier(courier);
    }

    @After
    public void cleanData(){courierRequests.deleteCourier(); }
}
