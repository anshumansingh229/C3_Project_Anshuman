import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RestaurantService {
    private static List<Restaurant> restaurants = new ArrayList<>();

    public Restaurant findRestaurantByName(String restaurantName){
       for (Restaurant restaurant : restaurants){
           if(restaurant.getName().equalsIgnoreCase(restaurantName))
               return restaurant;
       }
       return null;
    }


    public Restaurant addRestaurant(String name, String location, LocalTime openingTime, LocalTime closingTime) {
        Restaurant newRestaurant = new Restaurant(name, location, openingTime, closingTime);
        restaurants.add(newRestaurant);
        return newRestaurant;
    }

    public Restaurant removeRestaurant(String restaurantName) throws restaurantNotFoundException {
        Restaurant restaurantToBeRemoved = findRestaurantByName(restaurantName);
        if (restaurantToBeRemoved == null)
            throw new restaurantNotFoundException(restaurantName);
        restaurants.remove(restaurantToBeRemoved);
        return restaurantToBeRemoved;
    }

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }


    public boolean isRestaurantOpen(Restaurant restaurant){
        LocalTime currentTime = restaurant.getCurrentTime();
        System.out.println("Current Time is:"+ currentTime);
        System.out.println("Restaurant open Time is:"+ restaurant.openingTime);
        System.out.println("Restaurant close Time is:"+ restaurant.closingTime);
        if(currentTime.isAfter(restaurant.openingTime) && currentTime.isBefore(restaurant.closingTime))
            return  true;
        else
            return  false;
    }
}
