package CSE105;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;

/**
 * Created by Qianshan.chen15 1508670
 */
public class Garage {
    private String name;
    private ArrayList<Car> carsInGarage;// cars present in the garage.
    private ArrayList<Car> carsAssemblyLine;// all cars in and out, cars flow.
    private ArrayList<Car> carsSold;// cars sold out list.

    public Garage(String name) {
        this.name = name;
        this.carsInGarage = new ArrayList<Car>();
        this.carsAssemblyLine = new ArrayList<Car>();
        this.carsSold = new ArrayList<Car>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Car> getCarsAssemblyLine() {
        return carsAssemblyLine;
    }

    public ArrayList<Car> getCarsInGarage() {
        return carsInGarage;
    }

    public void setCarsInGarage(Car newCar) {
        this.carsInGarage.add(newCar);
    }

    public void setCarsAssemblyLine(Car newCar) {
        this.carsAssemblyLine.add(newCar);
    }

    public void setCarsSold(Car newCar) {
        this.carsSold.add(newCar);
    }

    // this method is to see whether there has
    // enough room for store more Car.
    // (which is required no more than 20 cars)
    public boolean canSetInGarage(){
        int presCarNumber = this.carsInGarage.size();
        if (presCarNumber >= 20){
            return false;
        }
        return true;
    }

    // add a new car.
    // First to judge the which array list should the car in,
    // then judge if it is repeated and add or not.
    public boolean addCar(Car newCar){//avoid
        Iterator<Car> it = carsAssemblyLine.iterator();
        while (it.hasNext()){
            Car car = it.next();
            if (car.equalsTo(newCar)){
                return false;
            }
        }
        this.setCarsAssemblyLine(newCar);
        if (newCar.isSold()){
            this.noteOnBills(newCar);
            this.setCarsSold(newCar);
        }else {
            if (canSetInGarage()) {
                this.buyCar(newCar);
                this.setCarsInGarage(newCar);
            }
        }
        return true;
    }

    // to judge whether the garage can afford a new car.
    // and go throw the bills, note the cash flows.
    public boolean buyCar(Car newCar){// take notes on bills
        Date now = new Date();
        if (newCar.dateOfPurchased == null) {
            newCar.dateOfPurchased = now;
        }
        this.repairCar(newCar);
        Bills.payMoney(newCar.getPrizeIn());
        return true;
    }

    // to judge whether the car can be sold (isRepaired or not).
    // note the date
    // and remove from InGarage ArrayList
    public boolean sellCar(Car repairedCar, double prize){
        if (!repairedCar.isRepaired()){
            return false;
        }
        if (repairedCar.isSold()){
            return false;
        }
        Date now = new Date();
        repairedCar.setPrizeOut(prize);
        repairedCar.setDateOfSold(now);
        Bills.addMoney(repairedCar.getPrizeOut());
        this.carsInGarage.remove(repairedCar);
        this.carsSold.add(repairedCar);
        return true;

    }

    // to demonstrate the list of car which is resent selling.
    public String showListOfSelling(){
        String desc =
                "----------------------------------------------------- On Sell List Of "+this.getName()+" ----------------------------------------------------\n" +
                        "Entry No.\tName\t\t\tPrize in\t\tPurchasedDate\tPrize out\t\tSell Date\t\tProfit\t\t\tStatus\t\t\tRepaired?\n"+
                        "------------------------------------------------------------------------------------------------------------------------------------------\n";
        Iterator<Car> it = this.carsInGarage.iterator();
        Car car;
        SortClass sort = new SortClass();
        Collections.sort(carsInGarage,sort);
        while (it.hasNext()){
            car = it.next();
            desc = desc + car.showDetails();//not ready.
        }
        desc ="\n" + desc +
                "------------------------------------------------------------------------------------------------------------------------------------------\n"
                +" Total Profit: "+ this.getProfit() /*+"\n Total Property: "+ this.getSize() +" cars and "+
                + Bills.getMoney() + " dollars\n"*/;
        return desc;
    }

    // to repair a car, just turn the boolean Repaired to "true".
    public void repairCar(Car car0/*, double repairCost*/){
        //car0.countRepairCost(repairCost);
        car0.setRepaired(true);
    }

    // help to take notes on Bills.
    // Note down the cash flows of each cars.
    public void noteOnBills(Car newCar){
        Bills.payMoney(newCar.getPrizeIn());
        Bills.addMoney(newCar.getPrizeOut());
    }

    // to demonstrate the list of all cars
    // and to write down to a txt file,
    // the final format written down can be read by this program itself( readObjects method in SystemMenu class)
    public String listOfAll(){
        String desc = this.getName() + "\n";
        Iterator<Car> it = this.carsAssemblyLine.iterator();
        Car car;
        SortClass sort = new SortClass();
        Collections.sort(carsAssemblyLine,sort);
        while (it.hasNext()){
            car = it.next();
            desc = desc + car.showDetails();//not ready.
        }
        return desc;
    }

    // to demonstrate the list of car which is resent selling.
    public String showListOfSold(){
        String desc =
                "-------------------------------------------------------- Sold List Of "+this.getName()+" ----------------------------------------------------\n" +
                        "Entry No.\tName\t\t\tPrize in\t\tPurchasedDate\tPrize out\t\tSell Date\t\tProfit\t\t\tStatus\t\t\tRepaired?\n"+
                        "------------------------------------------------------------------------------------------------------------------------------------------\n";
        Iterator<Car> it = this.carsSold.iterator();
        Car car;
        SortClass sort = new SortClass();
        Collections.sort(carsSold,sort);
        while (it.hasNext()){
            car = it.next();
            desc = desc + car.showDetails();//not ready.
        }
        desc ="\n" + desc +
                "------------------------------------------------------------------------------------------------------------------------------------------\n"
                +" Total Profit: "+ this.getProfit() +"\n Total Property: "+ this.getSize() +" cars and "+
                + Bills.getMoney() + " dollars\n";
        return desc;
    }

    public double getProfit(){
        Iterator<Car> it = carsAssemblyLine.iterator();
        Car car;
        double totalProfit = 0;
        while (it.hasNext()){
            car = it.next();
            totalProfit = totalProfit + car.getProfit();
        }
        return totalProfit;
    }

    // to get how many cars is now in the garage.
    public int getSize(){
        return this.carsInGarage.size();
    }
}
