
import java.sql.*;

public class Cancellation{
    private static final String CONNECTION = "jdbc:mysql://localhost:3306/mybnb"; 

    private String Booking_ID;
    private int LID;
    private String UserID;
    private Date cancellation_date;
    private boolean success;

    public Cancellation(String Booking_ID, int LID, String UserID, Date cancellation_date){
        this.Booking_ID = Booking_ID;
        this.LID = LID;
        this.UserID = UserID;
        this.cancellation_date = cancellation_date;
    }

    public boolean recordCancellation(){
        final String USER = "root";
        final String PASS = "giselle";
        try{
            Connection con = null;
            String sql = "INSERT INTO Cancellation(Booking_ID, LID, UserID, cancellation_date) VALUES (?,?,?,?)";
            
            con = DriverManager.getConnection(CONNECTION,USER,PASS);
            PreparedStatement ps = con.prepareStatement(sql);

            con.setAutoCommit(false);
            ps.setString(1, this.Booking_ID);
            ps.setInt(2, this.LID);
            ps.setString(3, this.UserID);
            ps.setDate(4, this.cancellation_date);

            success = ps.execute();

            con.commit();
            ps.close();
            con.close();

        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return success;
    }
    
}

