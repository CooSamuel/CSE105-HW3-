package CSE105;

import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Qianshan.chen15 1508670
 */
public class SystemMenu {

    static Garage theGarage = new Garage("Samuel's Garage");
    private static int max;
    public static void menuRun(){
        if (!readObjects()) {
            theGarage = new Garage("Samuel's Garage");
        }
        System.out.println("Welcome to " + theGarage.getName() + " Used Car management System");
        boolean exit = false;
        String select = "";
        // keep processing menu requests until exit
        while (!exit) {
            System.out.println("0= exit." +
                    "\n1= Buy a new car." +
                    "\n2= Sell a repaired car." +
                    "\n3= Show my Garage selling." + //now
                    "\n4= Show the Bills sold." + //past
                    "\n5= Display the Assembly line of my garage."); //all
            System.out.println("Enter a number to choose operation: ");
            select = DataInput.inputString();
            System.out.println();
            switch (select) {
                case "0":
                    exit = true;
                    break;
                case "1":
                    carIn();
                    break;
                case "2":
                    carOut();
                    break;
                case "3":
                    showOnSale();
                    break;
                case "4":
                    showSoldBills();
                    break;
                case "5":
                    showAllCars();
                    break;
                default:
                    System.out.println("Invalid option");
            }
        }
        storeObjects();
    }

    // enter details about a new car.
    // add that car into the garage.
    public static void carIn() {
        String prizeOut = "0";
        String dateOut = "null";
        Car car0;
        System.out.println("Enter your car's name:");
        String name = DataInput.inputString();
        System.out.println("Enter your car's Purchased in prize: ");
        //String s = DataInput.inputString();
        //double prizeIn = Double.parseDouble(s);
        double prizeI = DataInput.inputDouble();
        //System.out.println("Enter your car's Purchased date(if it is today, type in '0'):");
        //String dateIn = DataInput.inputString();
        Date dateIn = new Date();
        /*System.out.println("Enter your car's Sold out prize(if it is not sold, type in '0'): ");
        String prizeOut = DataInput.inputString();
        System.out.println("Enter your car's Sold date(if it is today, type '0'):");
        String dateOut = DataInput.inputString();*/

        String regular = name+","+prizeI+","+ dateIn +","+ prizeOut +","+ dateOut;
        car0 = new Car(regular);
        if (!theGarage.addCar(car0)){
            System.out.println("This car has existed. ");
        }else {
            System.out.println("Buy successfully!");
        }
    }

    // remove a car from the garage
    // and note it has been sold
    public static void carOut() {
        if (theGarage.getCarsInGarage().size() == 0){
            System.out.print("There is no car to sell\n\n");
            return;
        }
        Car choose = chooseCar();
        double prizeOut;
        System.out.println("Enter your car's Sold prize: ");
        prizeOut = DataInput.inputDouble();
        theGarage.sellCar(choose,prizeOut);
        if (theGarage.sellCar(choose,prizeOut)) {
            System.out.println("Not Repaired yet!");
        }
        System.out.println("Sold the Car "+ choose.getName() + " (" + choose.getIdNumber() + ") "+
                " successfully with the prize of " + prizeOut + ", and the profit is "
        +choose.getProfit()+". ");
    }

    // show selling cars in all garage
    public static void showOnSale() {
        System.out.println(theGarage.showListOfSelling());
    }

    // show sold cars in all garage
    public static void showSoldBills(){
        System.out.println(theGarage.showListOfSold());
    }

    // show all cars in all garage
    public static void showAllCars(){
        System.out.print(theGarage.listOfAll());
    }

    // this method is to help to choose one car in the list
    // use the entry number to identify each one of them.
    public static Car chooseCar(){
        Car chosen = new Car("NULL,0,1970-01-01,0,1970-01-01");
        boolean validInput = false;
        Car c;
        int id;
        max = 0;
        HashMap<Integer, Car> carBook = new HashMap<Integer, Car>();
        Iterator<Car> it = theGarage.getCarsAssemblyLine().iterator();
        while (it.hasNext()) {
            c = it.next();
            carBook.put(c.getIdNumber(), c);
            int idNumb = c.getIdNumber();
            if (idNumb > max){
                max = idNumb;
            }
        }
        while(!validInput) {
            System.out.println("Enter your cars's id number to sell: ");
            id = DataInput.inputIntegerRange(2000,max);
            chosen = carBook.get(id);
            validInput = true;
            if (chosen.isSold()){
                System.out.println("This car has been sold! Please Check the number.");
                System.out.println(theGarage.showListOfSelling());
                validInput = false;
            }
        }
        return chosen;
    }

    // this method is used to note that
    // the owner has repaired a used car so that the car can be on selling
    // but now the function is limited
    public static double repairCost(){
        System.out.println("Input cost of maintenance: ");
        double repairCost = DataInput.inputDouble();
        return repairCost;
    }

    // write down to a text file
    public static void storeObjects() {
        File file = new File("Bills.txt");
        try {
            BufferedWriter fw = new BufferedWriter(new FileWriter(file,false));
            fw.write(theGarage.listOfAll());
            fw.flush();
            fw.close();
            System.out.println("Objects written to file");
        } catch (IOException e) {
            System.out.println("Error writing to file");
        }
    }

    // read objects from a text file
    public static boolean readObjects() {
        boolean success = false;
        File file = new File("Bills.txt");
        String line;
        try {
            BufferedReader fr = new BufferedReader(new FileReader(file));
            line = fr.readLine();
            if (line != null) {
                theGarage = new Garage(line);
            } else {
                return false;
            }
            while ((line = fr.readLine()) != null) {
                String[] sArr = line.split("\t\t");
                String name = sArr[1].trim();
                String prizeIn = sArr[2].trim();
                String purchasedDate = sArr[3].trim();
                String prizeOut = sArr[4].trim();
                String soldDate = sArr[5].trim();
                String regulator = name + "," + prizeIn + "," + purchasedDate + "," + prizeOut + "," + soldDate;//(name,prizeIn,dateOfPurchased,prizeOut,dateOfSold)
                Car c = new Car(regulator);
                theGarage.addCar(c);
            }
            fr.close();
            success = true;
        } catch (FileNotFoundException e) {
            System.out.println("Error finding file");
        } catch (IOException e1) {
            System.out.println("Error reading file");
        } catch (NumberFormatException e3) {
            System.out.println("Error. Date is not a long or id number not an integer");
        } catch (ArrayIndexOutOfBoundsException e2) {
            System.out.println("Error. Wrong number of fields");
        }
        return success;
    }
}
