import java.util.Scanner;
import java.sql.*;

public class Update {

    private static final String CONNECTION = "jdbc:mysql://localhost:3306/mybnb"; 

    final String USER = "root";
    final String PASS = "giselle";

    public void UpdatePrice(){
        try{

            Scanner inputUserID = new Scanner(System.in);
            System.out.print("Enter your User ID: ");
            String UserID = inputUserID.nextLine();

            Scanner inputLID = new Scanner(System.in);
            System.out.print("Enter the Listing ID that you would like to update its price: ");
            int LID = inputLID.nextInt();

            Scanner inputdate = new Scanner(System.in);
            System.out.print("Enter the date of Listing that you would like to update its price: ");
            String date = inputdate.nextLine();

            Scanner inputprice= new Scanner(System.in);
            System.out.print("Enter the updated price: ");
            double price = inputprice.nextDouble();

            String update = "UPDATE Availability SET price = " + price + "WHERE LID = " + LID + "AND date = '" + date + "'" ;

            Connection con = null;
            con = DriverManager.getConnection(CONNECTION,USER,PASS);
            Statement stmt = con.createStatement();
            stmt.executeUpdate(update);

            con.commit();
            con.close();

            System.out.println("Price was successfully updated!");

        }

        catch(SQLException e){
            e.printStackTrace();
        }

    }


    public void UpdateAvailability(){
        try{

            Scanner inputUserID = new Scanner(System.in);
            System.out.print("Enter your User ID: ");
            String UserID = inputUserID.nextLine();
    
            Scanner inputLID = new Scanner(System.in);
            System.out.print("Enter the Listing ID that you would like to update its price: ");
            int LID = inputLID.nextInt();
    
            Scanner inputdate = new Scanner(System.in);
            System.out.print("Enter the date of Listing that you would like to update its price: ");
            String date = inputdate.nextLine();
    
            Scanner inputprice= new Scanner(System.in);
            System.out.print("Enter the updated price: ");
            double price = inputprice.nextDouble();
    
            String update = "UPDATE Availability SET price = " + price + "WHERE LID = " + LID + "AND date = '" + date + "'" ;
    
            Connection con = null;
            con = DriverManager.getConnection(CONNECTION,USER,PASS);
            Statement stmt = con.createStatement();
            stmt.executeUpdate(update);
    
            con.commit();
            con.close();
    
            System.out.println("Price was successfully updated!");

        }

        catch(SQLException e){
            e.printStackTrace();
        }

    }

    
}
