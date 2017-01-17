import java.util.Scanner; // 1508670 Qianshan.chen15

public class Assignment2 {

    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        System.out.println("Please input your Social security number: ");
        String ssNumber = kb.nextLine();
        if (isValidId(ssNumber)) {
            System.out.println("Your input is validated");
        } else {
            System.out.println("Your input is INVALID");
        }
    }

     public static boolean isValidId(String s){   //to judge the input is valid or not.
         if (s.length() != 11){
             return false;
         }
         if (!isRightStartAndEnds(s)){
             return false;
         }
         if (!isPure(s)){
             return false;
         }
         if (!isInForm(s)){
            return false;
         }
         return true;
     }
   

     public static boolean isRightStartAndEnds(String s){// To make sure not start with "000" or "666"
         String[] sArr = s.split("-");                   // and not end with all zeros in third group.
         String start = sArr[0];
         String ends = sArr[2];
         if (start.compareTo("000") == 0 || start.compareTo("666") == 0) {
             return false;
         }
         if (ends.compareTo("0000") == 0){
             return false;
         }
         return true;
     }

     public static boolean isPure(String s){   //To make sure contain only digits and '-'.
         String a = removeChar(s, '-');
         try {
             int i = Integer.parseInt(a);
             return true;
         }catch (Exception e){
             return false;
         }
     }

     public static boolean isInForm(String s){//make sure the format is xxx-xx-xxxx.
         String[] sArr = s.split("-"); // split String based on spaces int arrayLength = sArr.length;

         if (sArr[0].length() != 3){
             return false;
         }
         if (sArr[1].length() != 2){
             return false;
         }
         if (sArr[2].length() != 4){
             return false;
         }
         return true;
    }

    public static String removeChar(String a, char rem) {//A tool to remove '-' in numbers.
        String s = a;
        char[] ca = a.toCharArray();
        int len = ca.length;
        for (int i = 0; i < len; i++) {
            if (ca[i] != rem) {
                s = s + String.valueOf(ca[i]);
            }
        }
        return s;
    }
}
