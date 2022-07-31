import java.sql.Date;
import java.util.Scanner;
import java.sql.*;


public class AvailabilityInput {

    private static final String CONNECTION = "jdbc:mysql://localhost:3306/mybnb"; 
    final String USER = "root";
    final String PASS = "giselle";

    public void getAvailabilityInfo(String HostID) throws ClassNotFoundException
    {
        try{
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
            int price = inputprice.nextInt();
    
    
            InsertAvailability myAvailability = new InsertAvailability(HostID, LID, date, availability, price);
            boolean result = myAvailability.createAvailability();
    
            String update2 = "UPDATE Listings SET price = " + price + " WHERE LID = " + LID;
    
            Connection con2 = null;
            con2 = DriverManager.getConnection(CONNECTION,USER,PASS);
            Statement stmt2 = con2.createStatement();
            stmt2.executeUpdate(update2);
    
    
            if(!result){
                System.out.println("Availability was successfully added!");
            }else{
                return;
            }
    

        }
        catch(SQLException e){
            e.printStackTrace();
        }
       
    }
}