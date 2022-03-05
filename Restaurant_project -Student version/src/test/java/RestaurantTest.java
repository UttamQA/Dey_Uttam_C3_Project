import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.time.LocalTime;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class RestaurantTest {
    Restaurant restaurant;

    @BeforeEach
    public void beforeTest(){
        LocalTime openingTime = LocalTime.parse("10:30:00");
        LocalTime closingTime = LocalTime.parse("22:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);
    }

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time() {
        LocalTime time = LocalTime.of(21,30,00);
        Restaurant mockRestaurant = Mockito.spy(restaurant);
        Mockito.when(mockRestaurant.getCurrentTime()).thenReturn(time);
        boolean restaurantOpen = mockRestaurant.isRestaurantOpen();
        assertTrue(restaurantOpen);
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        LocalTime time = LocalTime.of(00,30,00);
        Restaurant mockRestaurant = Mockito.spy(restaurant);
        Mockito.when(mockRestaurant.getCurrentTime()).thenReturn(time);
        boolean restaurantOpen = mockRestaurant.isRestaurantOpen();
        assertFalse(restaurantOpen);
    }

    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }

    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }

    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }

    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @Test
    public void total_order_cost_should_be_0_when_no_item_selected_from_menu(){
        List<String> itemChosen = new ArrayList<>();
        assertEquals(0,restaurant.getTotalOrder(itemChosen));
    }

    @Test
    public void total_order_cost_should_match_the_sum_of_items_cost_chosen_from_the_menu(){
        HashMap<String,Integer> itemChosen = new HashMap<>();
        itemChosen.put("Vegetable lasagne",269);
        Collection<Integer> itemChosenCostValue = itemChosen.values();
        Set<String> selectedItemsName = itemChosen.keySet();
        List<String> listSelectedItemsName = new ArrayList<>(selectedItemsName);
        int expectedTotalOrderCost =0 ;
        for(Integer i : itemChosenCostValue){
            expectedTotalOrderCost+=i;
        }
        assertEquals(expectedTotalOrderCost,restaurant.getTotalOrder(listSelectedItemsName));
    }
}