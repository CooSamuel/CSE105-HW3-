package CSE105;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Qianshan.chen15 1508670
 */
public class Car {
    protected String name;
    protected double prizeIn;
    protected double prizeOut;
    protected Date dateOfPurchased;
    protected Date dateOfSold;
    protected double profit;
    protected int idNumber;
    protected static int lastIdNumber;
    protected boolean repaired;
    protected boolean sold;



    static {
        lastIdNumber = 2000;
    }

    // this method is to create a Car last. the parameter String is
    // (name,prizeIn,dateOfPurchased,prizeOut,dateOfSold)
    // to get these parameters of a car.
    public Car(String line) {
        SimpleDateFormat df = new SimpleDateFormat("y-M-d");
        String[] sArr = line.split(",");
        this.setName(sArr[0]);
        this.setPrizeIn(Double.parseDouble(sArr[1]));
        lastIdNumber++;
        this.idNumber = lastIdNumber;
        try{
            this.setDateOfPurchased(df.parse(sArr[2]));
        }catch (ParseException e) {
            this.setDateOfPurchased(null);
        }
        try {
            this.setPrizeOut(Double.parseDouble(sArr[3]));
            this.setDateOfSold(df.parse(sArr[4]));
        }catch (Exception e){
            this.setPrizeOut(0);
            this.setDateOfSold(null);
            this.setRepaired(false);
        }
    }

    public int getIdNumber() {
        return this.idNumber;
    }

    public boolean isRepaired() {
        return repaired;
    }

    public void setRepaired(boolean repaired) {
        this.repaired = repaired;
    }

    // transfer the repaired status
    // from boolean into string to show out in the list
    public String showStatusOfRepair(){
        if (this.isRepaired()){
            return "Repaired";
        }
        return "Not Repaired";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrizeIn() {
        return prizeIn;
    }

    public void setPrizeIn(double prizeIn) {
        this.prizeIn = prizeIn;
    }

    public double getPrizeOut() {
        return prizeOut;
    }

    // transfer the sell prize from double
    // into string to show out in the list
    public String showPrizeOut(){
        String prize = "";
        if (this.prizeOut > 0){
            prize = Double.toString(this.prizeOut);
        }
        return prize;
    }

    public void setPrizeOut(double prizeOut) {
        if (prizeOut != 0){
            setSold(true);
            setRepaired(true);
        }
        this.prizeOut = prizeOut;
    }

    public Date getDateOfPurchased() {
        return dateOfPurchased;
    }

    // transfer the date from Date
    // into string to show out in the list
    public String showDateOfPurchase(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String date = df.format(this.getDateOfPurchased());
        return date;
    }

    public void setDateOfPurchased(Date dateOfPurchased) {
        this.dateOfPurchased = dateOfPurchased;
    }

    public Date getDateOfSold() {
        return dateOfSold;
    }

    // transfer the date from Date
    // into string to show out in the list
    public String showDateOfSold(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String date;
        try {
            date = df.format(this.getDateOfSold());
        }catch (NullPointerException e){
            date = "";
        }
            return date;
    }

    public void setDateOfSold(Date dateOfSold) {
        this.dateOfSold = dateOfSold;
    }

    public double getProfit() {
        profit = this.prizeOut - this.prizeIn;
        return profit;
    }

    public boolean isSold() {
        return sold;
    }

    public void setSold(boolean sold) {
        this.sold = sold;
    }

    // transfer the Sold status from boolean
    // into string to show out in the list
    public String showStatusOfSold(){
        if (this.isSold()){
            return "Sold";
        }
        return "Selling";
    }

    // this method is to compare two Car classes,
    // if they are the same we should avoid repetition
    public boolean equalsTo(Car car0){
        if (!(this.getName().equals(car0.getName()))){
            return false;
        }
        /*f (!(this.getDateOfPurchased().equals(car0.getDateOfPurchased()))){
            return false;
        }*/
        if (!(this.getPrizeIn() == (car0.getPrizeIn()))){
            return false;
        }
        return true;
    }

    // to help the tabulation.
    // showing in the list
    public String adjust(String s, int n){
        int lens;
        String s1 = "";
        s = s.trim();
        if ((lens = n - s.length()) != 0){
            StringBuffer buf = new StringBuffer();
            for (int i = 0; i < lens; ++i) {
                buf.append(" ");
            }
            s1 = buf.toString();
        }
        s1 = s + s1;
        return s1;
    }

    //to show each car in one line
    public String showDetails(){
        String details = "";//Entry No.	    Name    Prize in    Purchased Date  Prize out   Sell Date	Profit	Status	Repaired?
        details = details + this.getIdNumber() +
                "\t\t" + adjust(this.getName(),10) +
                "\t\t" + adjust(Double.toString(this.getPrizeIn()),10) +
                "\t\t" + adjust(this.showDateOfPurchase(),0) +
                "\t\t" + adjust(this.showPrizeOut(),10) +
                "\t\t" + adjust(this.showDateOfSold(),10)+
                "\t\t" + adjust(Double.toString(this.getProfit()),10) +
                "\t\t" + adjust(this.showStatusOfSold(),8) +
                "\t\t" + adjust(this.showStatusOfRepair(),12) +
                "\n";
        return details;
    }



}
