package CSE105;

/**
 * Created by Qianshan.chen15 1508670
 */
//This class is for testing
public class SystemTest {

    public static void test(){

        //make a garage
        Garage z = new Garage("Samuel");

        // create 4 new car in different ways
        Car c = new Car("Piper,20000,1997-08-02,23000,1997-10-19");// completed info.
        Car c1 = new Car("Samuel,20001,now,,");//leak of buy time, sell prize, sell time
        Car c3 = new Car("Samuel,20000,1997-08-02,,");//leak of sell prize and sell time.
        Car c2 = new Car("Samuel,20000,1997-08-02,,");// exactly the same as c3 to test repeat.

        //store cars into garage.
        z.addCar(c);
        z.addCar(c1);
        z.buyCar(c1);
        z.addCar(c3);
        z.addCar(c2);
        z.sellCar(c2,40000);

        //show in different lists
        System.out.println(c1.isSold());
        System.out.println(z.showListOfSelling());
        System.out.println(c.isSold() + c.getName());
        System.out.println(z.showListOfSold());
    }
}
