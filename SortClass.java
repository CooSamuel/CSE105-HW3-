package CSE105;

import java.util.Comparator;

/**
 * Created by Qianshan.chen15 1508670
 */

// this method is to compare date to sort the ArrayList.
// this method will be used before showing a list.
class SortClass implements Comparator {
    public int compare(Object arg0,Object arg1){
        Car car0 = (Car)arg0;
        Car car1 = (Car)arg1;
        int flag = car0.getDateOfPurchased().
                compareTo(car1.getDateOfPurchased());
        return flag;
    }
}
