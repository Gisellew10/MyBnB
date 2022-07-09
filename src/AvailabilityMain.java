import java.sql.*;
import java.util.Scanner;


public class AvailabilityMain {
    public static void main(String[] args) throws ClassNotFoundException
    {
        Scanner inputLID = new Scanner(System.in);
        System.out.print("Enter the Listing ID: ");
        int LID = inputLID.nextInt();

        Scanner inputdate= new Scanner(System.in);
        System.out.print("Enter the date in the form dd/mm/yyyy: ");
        String date = inputdate.nextLine();

        Scanner inputavailability= new Scanner(System.in);
        System.out.print("Enter the availability (available/not available): ");
        String availability  = inputavailability.nextLine();

        Scanner inputprice = new Scanner(System.in);
        System.out.print("Enter the price: ");
        float price = inputprice.nextFloat();


        InsertAvailability myAvailability = new InsertAvailability(LID, date, availability, price);
        boolean result = myAvailability.createAvailability();

        if(!result){
            System.out.println("Availability was successfully added!");
        }else{
            return;
        }

    }
}