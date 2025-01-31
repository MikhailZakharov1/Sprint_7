package ScooterTests;

import ApiRequests.OrderRequest;
import com.example.Order;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.apache.http.HttpStatus.*;

import static org.hamcrest.Matchers.*;

@RunWith(Parameterized.class)
public class CreateOrderTests {
    Order order;

    public CreateOrderTests(Order order) {
        this.order = order;
    }

    @Before
    public void setUp(){orderRequest.setUp();}

    @Parameterized.Parameters
    public static  Object[][] getTestData(){
        return new Object[][]{
                {new Order("Вилли","Корги","Царицинский 4к2","егонету","+79876544554",2,"2025-10-02","Позвонить за час",new String[]{"BLACK"})},
                {new Order("Маша","Кошка","Мира пр-кт","станция Саратов","89764545455",3,"2025-02-13","Позвонить за вчера",new String[]{"GREY"})},
                {new Order("Michail","Archangel","Петровка 8","райскиесады","+79643223233",4,"2025-04-02","Позвонить за сутки",new String[]{"BLACK","GREY"})},
                {new Order("TANK","300","КНР","Чаек","89324432233",5,"2025-03-10","Позвонить пока на колесах",new String[]{})}
        };
    }


    OrderRequest orderRequest = new OrderRequest();

    @Test
    @DisplayName("Создание заказа самоката с разным цветом")
    @Description("Проверка создания заказа самоката с разным цветом")
    public void checkCreateOrder(){
        orderRequest.setOrder(order);
        orderRequest.createOrderRequest()
                .then().statusCode(SC_CREATED)
                .and()
                .assertThat().body("track", notNullValue());
    }

}
