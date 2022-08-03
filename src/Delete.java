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

            Connection con1 = null;
            String sql1 = "SELECT LID, start_date, end_date FROM RentalHistory WHERE RenterID = '" + UserID + "'";

            con1 = DriverManager.getConnection(CONNECTION,USER,PASS);
            PreparedStatement ps1 = con1.prepareStatement(sql1);
            ResultSet rs1 = ps1.executeQuery();

            while(rs1.next()){
                int LID = rs1.getInt(1);
                Date start_date = rs1.getDate(2);
                Date end_date = rs1.getDate(3);
    
                String update = "UPDATE Availability SET availability = 'available' WHERE date > '"+ delete_date + "' AND LID = " + LID + " AND date BETWEEN '" + start_date +"' AND '" + end_date +"'";
    
                Connection con3 = null;
                con3 = DriverManager.getConnection(CONNECTION,USER,PASS);
                Statement stmt2 = con3.createStatement();
                stmt2.executeUpdate(update);
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
                ps.execute();


                String update = "UPDATE Availability SET availability = 'available' WHERE date BETWEEN '" + start_date + "'" + "AND '" + end_date + "'";

                Connection con3 = null;
                con3 = DriverManager.getConnection(CONNECTION,USER,PASS);
                Statement stmt2 = con3.createStatement();
                stmt2.executeUpdate(update);

                Cancellation mycancellation = new Cancellation(Booking_ID, LID, UserID, cancellation_date, start_date, end_date);
                boolean result = mycancellation.recordCancellation();

                System.out.println("Booking was successfully canceled!");

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
            }else{
                System.out.println("No listing found!");
                return false;
            }

            Connection con = null;
            String sql = "DELETE FROM Listings WHERE LID = '" + LID +"'";
            
            con = DriverManager.getConnection(CONNECTION,USER,PASS);
            PreparedStatement ps = con.prepareStatement(sql);

            System.out.println("Listing was successfully removed!");

            success = ps.execute();

            ps.close();
            con.close();

        }
        catch(SQLException e){
            e.printStackTrace();
        }

        return success;
    }
    
}
