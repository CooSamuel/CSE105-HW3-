package CSE105;

/**
 * Created by Qianshan.chen15 1508670
 */
public class Bills {
    private static double totalMoney = 5000;//initial money 1,000,000

    // this method is to add money
    // which means sell and get money.
    public static void addMoney(double incomes) {
        totalMoney = totalMoney + incomes;
    }
    //this method is to pay for the money buying a car
    public static boolean payMoney(double expense) {

        if ((totalMoney - expense) > 0) {
            totalMoney = totalMoney - expense;
            return true;
        }
        return false;
    }

    public static double getMoney() {
        return totalMoney;
    }
}
