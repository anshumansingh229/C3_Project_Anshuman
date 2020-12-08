
import java.time.LocalTime;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


@ExtendWith(MockitoExtension.class)
class RestaurantTest {

    Restaurant restaurant;
    RestaurantService service;
    LocalTime openingTime;
    LocalTime closingTime;

    //REFACTOR ALL THE REPEATED LINES OF CODE
    @BeforeEach
    public void setup() {
        service = new RestaurantService();
        openingTime = LocalTime.parse("10:30:00");
        closingTime = LocalTime.parse("22:00:00");
        restaurant = service.addRestaurant("Amelie's cafe","Chennai",openingTime,closingTime);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE


    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
        Restaurant restaurant1 = Mockito.mock(Restaurant.class);
        LocalTime at_thirteen_hours = LocalTime.now().withHour(13);
        System.out.println("At 13 hour:" + at_thirteen_hours);
        Mockito.when(restaurant1.getCurrentTime()).thenReturn(at_thirteen_hours);
        restaurant1.openingTime = this.openingTime;
        restaurant1.closingTime = this.closingTime;
        boolean restaurantStatus = service.isRestaurantOpen(restaurant1);
        assertTrue(restaurantStatus);
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        Restaurant restaurant1 = Mockito.mock(Restaurant.class);
        LocalTime at_twenty_three_hours = LocalTime.now().withHour(23);
        System.out.println("At 23 hour:" + at_twenty_three_hours);
        Mockito.when(restaurant1.getCurrentTime()).thenReturn(at_twenty_three_hours);
        restaurant1.openingTime = this.openingTime;
        restaurant1.closingTime = this.closingTime;
        boolean restaurantStatus = service.isRestaurantOpen(restaurant1);
        assertFalse(restaurantStatus);
    }
    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }

    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }

    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }

    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //>>>>>>>>>>>>>>>>>>>>>>>>>>>Order Cost<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Test
    public void selecting_item_from_menu_should_return_expected_order_cost(){
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        List<String> selectedItems = new ArrayList<>();
        selectedItems.add("Sweet corn soup");
        selectedItems.add("Vegetable lasagne");
        int orderCost = restaurant.getOrderCost(selectedItems);
        assertThat(orderCost, equalTo(388));
    }

    @Test
    public void selecting_item_from_menu_should_return_unexpected_order_cost(){
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        List<String> selectedItems = new ArrayList<>();
        selectedItems.add("Sweet corn soup");
        selectedItems.add("Vegetable lasagne");
        int orderCost = restaurant.getOrderCost(selectedItems);
        assertNotEquals(orderCost, 400);
    }
    //<<<<<<<<<<<<<<<<<<<<<<<Order Cost>>>>>>>>>>>>>>>>>>>>>

}