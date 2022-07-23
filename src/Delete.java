import java.util.Scanner;
import java.sql.*;

public class Delete {

    private static final String CONNECTION = "jdbc:mysql://localhost:3306/mybnb"; 

    final String USER = "root";
    final String PASS = "giselle";
    private boolean success;

    public boolean DeleteUser(String UserID){
        try{
            Connection con = null;
            String sql = "DELETE FROM Users WHERE UserID = '" + UserID +"'";
            
            con = DriverManager.getConnection(CONNECTION,USER,PASS);
            PreparedStatement ps = con.prepareStatement(sql);

            System.out.println("User was successfully deleted!");

            success = ps.execute();

            ps.close();
            con.close();

        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return success;
    }


    public boolean CancelBooking(){

        Scanner inputUserID = new Scanner(System.in);
        System.out.print("Enter your User ID ");
        String UserID = inputUserID.nextLine();

        Scanner inputBooking = new Scanner(System.in);
        System.out.print("Enter the Booking ID that you would like to cancel: ");
        String Booking_ID = inputBooking.nextLine();

        Scanner inputdate = new Scanner(System.in);
        System.out.print("Enter today's date: ");
        String cancellation_date_s = inputdate.nextLine();
        Date cancellation_date = Date.valueOf(cancellation_date_s);

        try{

            Connection con = null;
            ResultSet rs = null;
            String query = "SELECT start_date, end_date, LID, HostID, RenterID FROM RentalHistory WHERE Booking_ID = '" + Booking_ID + "'";

            con = DriverManager.getConnection(CONNECTION,USER,PASS);
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            if(rs.next()){
                Date start_date = rs.getDate(1);
                Date end_date = rs.getDate(2);
                int LID = rs.getInt(3);
                String HostID = rs.getString(4);
                String RenterID = rs.getString(5);

                if(!(UserID.equals(HostID) || UserID.equals(RenterID))){
                    System.out.println("Error! Booking can only cancel by related host/renter!");
                    return false;
                }

                Connection con2 = null;
                String sql = "DELETE FROM RentalHistory WHERE Booking_ID = '" + Booking_ID + "''";
                
                con2 = DriverManager.getConnection(CONNECTION,USER,PASS);
                PreparedStatement ps = con2.prepareStatement(sql);

                String update = "UPDATE Availability SET availability = 'available' WHERE date BETWEEN '" + start_date + "'" + "AND '" + end_date + "'";

                Connection con3 = null;
                con3 = DriverManager.getConnection(CONNECTION,USER,PASS);
                Statement stmt2 = con3.createStatement();
                stmt2.executeUpdate(update);

                Cancellation mycancellation = new Cancellation(Booking_ID, LID, UserID, cancellation_date, start_date, end_date);
                boolean result = mycancellation.recordCancellation();

                System.out.println("Booking was successfully canceled!");

                success = ps.execute();

                ps.close();
                con.close();
                con2.close();
                con3.close();
                inputBooking.close();
            }else{
                System.out.println("Booking was not successfully canceled!");
            }

        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return success;

    }


    public boolean RemoveListing(){

        Scanner inputHostID = new Scanner(System.in);
        System.out.print("Enter your User ID ");
        String HostID = inputHostID.nextLine();


        Scanner inputLID = new Scanner(System.in);
        System.out.print("Enter the Listing ID that you would like to remove: ");
        String LID = inputLID.nextLine();

        try{
            Connection con0 = null;
            ResultSet rs0 = null;
            String query0 = "SELECT HostID FROM Listings WHERE LID = '" + LID + "'";

            con0 = DriverManager.getConnection(CONNECTION,USER,PASS);
            Statement stmt0 = con0.createStatement();
            rs0 = stmt0.executeQuery(query0);

            if(rs0.next()){
                String HostID_check = rs0.getString(1);

                if(!(HostID.equals(HostID_check))){
                    System.out.println("Error! Listing can only remove by related host!");
                    return false;
                }
            }

            Connection con = null;
            String sql = "DELETE FROM Listings WHERE LID = '" + LID +"'";
            
            con = DriverManager.getConnection(CONNECTION,USER,PASS);
            PreparedStatement ps = con.prepareStatement(sql);

            Connection con2 = null;
            String sql2 = "DELETE FROM RentalHistory WHERE LID = '" + LID +"'";
            
            con2 = DriverManager.getConnection(CONNECTION,USER,PASS);
            PreparedStatement ps2 = con2.prepareStatement(sql2);
            ps2.execute();

            Connection con3 = null;
            String sql3 = "DELETE FROM Availability WHERE LID = '" + LID +"'";
            
            con3 = DriverManager.getConnection(CONNECTION,USER,PASS);
            PreparedStatement ps3 = con3.prepareStatement(sql3);
            ps3.execute();


            System.out.println("Listing was successfully removed!");

            success = ps.execute();

            ps.close();
            con.close();
            con2.close();
            con3.close();
            inputLID.close();

        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return success;
    }
    
}
