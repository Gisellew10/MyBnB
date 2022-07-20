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
            String date_s = inputdate.nextLine();
            Date date = Date.valueOf(date_s);

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
            String date_s = inputdate.nextLine();
            Date date = Date.valueOf(date_s);
    
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

    public void UpdateAmenities() throws ClassNotFoundException{
        try{

            Scanner inputUserID = new Scanner(System.in);
            System.out.print("Enter your User ID: ");
            String UserID = inputUserID.nextLine();
    
            Scanner inputLID = new Scanner(System.in);
            System.out.print("Enter the Listing ID that you would like to update its price: ");
            int LID = inputLID.nextInt();


            String query = "SELECT type, amenities FROM Listings WHERE LID = " + LID;

            ResultSet rs = null;
            Connection con = null;
            con = DriverManager.getConnection(CONNECTION,USER,PASS);
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            String type = null;
            String amenities = null;
            if(rs.next()){
                type = rs.getString(1);
                amenities = rs.getString(2);
            }

            HostToolkit hostToolkit = new HostToolkit();
            hostToolkit.SuggestAmenities(type, "Update", LID);
    
            Scanner inputamenities = new Scanner(System.in);
            System.out.print("Enter the amenities that you would like to update: ");
            String amenities_new = inputamenities.nextLine();


    
            String update = "UPDATE Listings SET amenities = '" + amenities + "," + amenities_new + "' WHERE LID = " + LID;
    
            Connection con2 = null;
            con2 = DriverManager.getConnection(CONNECTION,USER,PASS);
            Statement stmt2 = con2.createStatement();
            stmt2.executeUpdate(update);

            String[] tokens = amenities_new.split(",");
            double increased_revenue = 0;
            for(String t : tokens){
                String query2 = "SELECT price FROM AmenitiesData WHERE amenities = '" + t + "'";

                ResultSet rs3 = null;
                Connection con3 = null;
                con3 = DriverManager.getConnection(CONNECTION,USER,PASS);
                Statement stmt3 = con3.createStatement();
                rs3 = stmt3.executeQuery(query2);

                if(rs.next()){
                    increased_revenue = rs.getDouble(1);
                }

            }

            String update2 = "UPDATE Listings SET price = price + '" + increased_revenue + "' WHERE LID = " + LID;
    
            Connection con4 = null;
            con4 = DriverManager.getConnection(CONNECTION,USER,PASS);
            Statement stmt4 = con4.createStatement();
            stmt4.executeUpdate(update2);
    
            con.commit();
            con.close();
    
            System.out.println("Amenities was successfully updated!");

        }

        catch(SQLException e){
            e.printStackTrace();
        }

    }

    
}
