
import java.sql.*;

public class Cancellation{
    private static final String CONNECTION = "jdbc:mysql://localhost:3306/mybnb"; 

    private String Booking_ID;
    private int LID;
    private String UserID;
    private String cancellation_date;
    private Date start_date;
    private Date end_date;
    private boolean success;

    public Cancellation(String Booking_ID, int LID, String UserID, String cancellation_date, Date start_date, Date end_date){
        this.Booking_ID = Booking_ID;
        this.LID = LID;
        this.UserID = UserID;
        this.cancellation_date = cancellation_date;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public boolean recordCancellation(){
        final String USER = "root";
        final String PASS = "giselle";
        try{
            Connection con = null;
            String sql = "INSERT INTO Cancellation(Booking_ID, LID, UserID, cancellation_date, start_date, end_date) VALUES (?,?,?,?,?,?))";
            
            con = DriverManager.getConnection(CONNECTION,USER,PASS);
            PreparedStatement ps = con.prepareStatement(sql);

            con.setAutoCommit(false);
            ps.setString(1, this.Booking_ID);
            ps.setInt(2, this.LID);
            ps.setString(3, this.UserID);
            ps.setString(4, this.cancellation_date);
            ps.setDate(5, this.start_date);
            ps.setDate(6, this.end_date);

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

