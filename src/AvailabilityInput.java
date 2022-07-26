import java.sql.Date;
import java.util.Scanner;


public class AvailabilityInput {
    public void getAvailabilityInfo(String HostID) throws ClassNotFoundException
    {

        Scanner inputLID = new Scanner(System.in);
        System.out.print("Enter the Listing ID: ");
        int LID = inputLID.nextInt();

        Scanner inputdate= new Scanner(System.in);
        System.out.print("Enter the date in the form YYYY-MM-DD: ");
        String date_s = inputdate.nextLine();
        Date date = Date.valueOf(date_s);

        Scanner inputavailability= new Scanner(System.in);
        System.out.print("Enter the availability (available/unavailable): ");
        String availability  = inputavailability.nextLine();

        HostToolkit suggest = new HostToolkit();
        suggest.SuggestPrice(LID);
        Scanner inputprice = new Scanner(System.in);
        System.out.print("Enter the price: ");
        double price = inputprice.nextDouble();


        InsertAvailability myAvailability = new InsertAvailability(HostID, LID, date, availability, price);
        boolean result = myAvailability.createAvailability();

        inputLID.close();
        inputdate.close();
        inputavailability.close();
        inputprice.close();

        if(!result){
            System.out.println("Availability was successfully added!");
        }else{
            return;
        }

    }
}