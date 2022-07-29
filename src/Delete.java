import java.util.Scanner;
import java.sql.*;

public class Delete {

    private static final String CONNECTION = "jdbc:mysql://localhost:3306/mybnb"; 

    final String USER = "root";
    final String PASS = "giselle";
    private boolean success;

    public boolean DeleteUser(String UserID, String delete_date){

        //Assumption: if you delete a user, it will cancel any booking associated with the related user (renter)
        //Remove any listing associated with the related user (host)

        try{
            Connection con = null;
            String sql = "DELETE FROM Users WHERE UserID = '" + UserID +"'";
            
            con = DriverManager.getConnection(CONNECTION,USER,PASS);
            PreparedStatement ps = con.prepareStatement(sql);

            String copy = UserID;
            String[] tokens = copy.split("-");


            Connection con2 = null;
            ResultSet rs2 = null;
            String query = null;

            if(tokens[0].equals("Renter")){
                query = "SELECT Booking_ID from RentalHistory WHERE RenterID = '" + UserID + "' AND date >= '" + delete_date + "'";
                con2 = DriverManager.getConnection(CONNECTION,USER,PASS);
                Statement stmt2 = con2.createStatement();
                rs2 = stmt2.executeQuery(query);
    
                while(rs2.next()){
                    String Booking_ID = rs2.getString(1);
                    CancelBooking(UserID, Booking_ID, delete_date);
                }
            }else if(tokens[0].equals("Host")){
                query = "SELECT LID from Listings WHERE HostID = '" + UserID + "'";
                con2 = DriverManager.getConnection(CONNECTION,USER,PASS);
                Statement stmt2 = con2.createStatement();
                rs2 = stmt2.executeQuery(query);
    
                while(rs2.next()){
                    String LID = rs2.getString(1);
                    RemoveListing(UserID, LID);
                }
            }

            success = ps.execute();
            System.out.println("User was successfully deleted!");

            ps.close();
            con.close();

        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return success;
    }


    public boolean CancelBooking(String UserID, String Booking_ID, String cancellation_date_s){

        //Assumption: if a user cancel a booking, then the booking will be removed from RentalHistory

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
                String sql = "DELETE FROM RentalHistory WHERE Booking_ID = '" + Booking_ID + "'";
                
                con2 = DriverManager.getConnection(CONNECTION,USER,PASS);
                PreparedStatement ps = con2.prepareStatement(sql);

                String update = "UPDATE Availability SET availability = 'available' WHERE date BETWEEN '" + start_date + "'" + "AND '" + end_date + "'";

                Connection con3 = null;
                con3 = DriverManager.getConnection(CONNECTION,USER,PASS);
                Statement stmt2 = con3.createStatement();
                stmt2.executeUpdate(update);

                Cancellation mycancellation = new Cancellation(Booking_ID, LID, UserID, cancellation_date, start_date, end_date);
                boolean result = mycancellation.recordCancellation();


                success = ps.execute();
                System.out.println("Booking was successfully canceled!");

                ps.close();
                con.close();
                con2.close();
                con3.close();
            }else{
                System.out.println("Booking was not successfully canceled!");
            }

        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return success;

    }


    public boolean RemoveListing(String HostID, String LID){

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

        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return success;
    }
    
}
