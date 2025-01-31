package ScooterTests;

import ApiRequests.CourierRequests;
import com.example.Courier;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class CreateCourierTests  {
    Courier courier = new Courier( "Fish", "underwater", "Karasik");
    CourierRequests courierRequests = new CourierRequests();

    @Before
    public void setUp(){courierRequests.setUp();}

    @Test
    @DisplayName("Создание курьера")
    @Description("Проверка успешного создания курьера")
    public void checkCreateCourier () {
        courierRequests.setCourier(courier);
        courierRequests.createCourier()
                .then().assertThat().body("ok", is(true))
                .and()
                .statusCode(SC_CREATED);
    }

    @Test
    @DisplayName("Создания уже существующего курьера(негатив)")
    @Description("Проверка ограничения на создание дубликата курьера")
    public void checkCreateDuplicateCourier (){
        courierRequests.setCourier(courier);
        courierRequests.createCourier();
        courierRequests.createCourier()
                .then().assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой."))
                .and()
                .statusCode(SC_CONFLICT);
    }

    @Test
    @DisplayName("Создание курьера без указания логина")
    @Description("Проверка невозможности создать курьера без указания логин")
    public void checkCreateCourierWithoutLogin() {
        courierRequests.setCourier(new Courier("", "underwater", "Karasik"));
        courierRequests.createCourier()
                .then().assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи"))
                .and()
                .statusCode(SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Создание курьера без указания имени (позитив)")
    @Description("Проверка возможности создать курьера без имени")
    public void checkCreateCourierWithoutFirstName() {
        courierRequests.setCourier(new Courier("Fish", "underwater", ""));
        courierRequests.createCourier()
                .then().assertThat().body("ok", is(true))
                .and()
                .statusCode(SC_CREATED);
    }

    @After
    public void cleanData(){courierRequests.deleteCourier(); }
}

