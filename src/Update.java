import java.util.Scanner;
import java.sql.*;

public class Update {

    private static final String CONNECTION = "jdbc:mysql://localhost:3306/mybnb"; 
    final String USER = "root";
    final String PASS = "giselle";

    public void UpdatePrice(String UserID){
        
        try{

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

            Connection con0 = null;
            ResultSet rs0 = null;
            String query0 = "SELECT availability FROM Availability WHERE LID = " + LID + " AND date = '" + date + "'";

            con0 = DriverManager.getConnection(CONNECTION,USER,PASS);
            Statement stmt0 = con0.createStatement();
            rs0 = stmt0.executeQuery(query0);

            if(rs0.next()){
                String availability = rs0.getString(1);

                if(availability.equals("unavailable")){
                    System.out.println("Error! Price cannot be changed after it is booked!");
                    return;
                }
            }


            String update = "UPDATE Availability SET price = " + price + "WHERE LID = " + LID + " AND date = '" + date + "'";

            Connection con = null;
            con = DriverManager.getConnection(CONNECTION,USER,PASS);
            Statement stmt = con.createStatement();
            stmt.executeUpdate(update);

            System.out.println("Price was successfully updated!");

        }

        catch(SQLException e){
            e.printStackTrace();
        }

    }


    public void UpdateAvailability(String UserID){
        try{
    
            Scanner inputLID = new Scanner(System.in);
            System.out.print("Enter the Listing ID that you would like to update its availability: ");
            int LID = inputLID.nextInt();
    
            Scanner inputdate = new Scanner(System.in);
            System.out.print("Enter the date of Listing that you would like to update: ");
            String date_s = inputdate.nextLine();
            Date date = Date.valueOf(date_s);
    
            Scanner inputprice= new Scanner(System.in);
            System.out.print("Choose available or unavailable: ");
            String availability = inputprice.nextLine();

            Connection con0 = null;
            ResultSet rs0 = null;
            String query0 = "SELECT availability FROM Availability WHERE EXISTS (SELECT * FROM RentalHistory WHERE LID = " + LID + " AND '" + date + "' BETWEEN start_date AND end_date) AND LID = " + LID + " AND date = '" + date + "'";

            con0 = DriverManager.getConnection(CONNECTION,USER,PASS);
            Statement stmt0 = con0.createStatement();
            rs0 = stmt0.executeQuery(query0);

            if(rs0.next()){
                String availability_check = rs0.getString(1);

                if(availability_check.equals("unavailable")){
                    System.out.println("Error! Availability cannot be changed after it is booked!");
                    return;
                }
            }

    
            String update = "UPDATE Availability SET availability = '" + availability + "' WHERE LID = " + LID + " AND date = '" + date + "'";
    
            Connection con = null;
            con = DriverManager.getConnection(CONNECTION,USER,PASS);
            Statement stmt = con.createStatement();
            stmt.executeUpdate(update);
    
            System.out.println("Availability was successfully updated!");

        }

        catch(SQLException e){
            e.printStackTrace();
        }

    }

    public void UpdateAmenities(String UserID) throws ClassNotFoundException{
        //Assumption: for every amenities you add, you increased 5% of price
        try{
    
            Scanner inputLID = new Scanner(System.in);
            System.out.print("Enter the Listing ID that you would like to update its amenities: ");
            int LID = inputLID.nextInt();


            String query = "SELECT type, amenities, price FROM Listings WHERE LID = " + LID;

            ResultSet rs = null;
            Connection con = null;
            con = DriverManager.getConnection(CONNECTION,USER,PASS);
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            String type = null;
            String amenities = null;
            int price = 0;
            if(rs.next()){
                type = rs.getString(1);
                amenities = rs.getString(2);
                price = rs.getInt(3);
            }

            HostToolkit hostToolkit = new HostToolkit();
            hostToolkit.SuggestAmenities(type, "Update", LID);
    
            Scanner inputamenities = new Scanner(System.in);
            System.out.print("Enter the updated amenities: ");
            String amenities_new = inputamenities.nextLine();


    
            String update = "UPDATE Listings SET amenities = '" + amenities + amenities_new + "' WHERE LID = " + LID;
    
            Connection con2 = null;
            con2 = DriverManager.getConnection(CONNECTION,USER,PASS);
            Statement stmt2 = con2.createStatement();
            stmt2.executeUpdate(update);

            String[] tokens = amenities_new.split(",");
            double increased_revenue = 0;
            for(String t : tokens){
                increased_revenue = increased_revenue + (price * 0.05);
            }
            System.out.println();

            System.out.println("Expected increased revenue is: " + increased_revenue);

            String update2 = "UPDATE Listings SET price = price + '" + increased_revenue + "' WHERE LID = " + LID;
    
            Connection con4 = null;
            con4 = DriverManager.getConnection(CONNECTION,USER,PASS);
            Statement stmt4 = con4.createStatement();
            stmt4.executeUpdate(update2);

            String update3 = "UPDATE Availability SET price = price + '" + increased_revenue + "' WHERE availability = 'available' AND LID = " + LID;
    
            Connection con5 = null;
            con5 = DriverManager.getConnection(CONNECTION,USER,PASS);
            Statement stmt5 = con5.createStatement();
            stmt5.executeUpdate(update3);
    
            con.commit();
            con.close();
    
            System.out.println("Amenities was successfully updated!");

        }

        catch(SQLException e){
            e.printStackTrace();
        }

    }

    
}
