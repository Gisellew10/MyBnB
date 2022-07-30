
import java.sql.*;

public class InsertAvailability{
    private static final String CONNECTION = "jdbc:mysql://localhost:3306/mybnb"; 

    private int LID;
    private String HostID;
    private Date date;
    private String availability;
    private int price;
    private boolean success;

    public InsertAvailability(String HostID, int LID, Date date, String availability, int price){
        this.HostID = HostID;
        this.LID = LID;
        this.date = date;
        this.availability = availability;
        this.price = price;
    }

    public boolean createAvailability(){
        final String USER = "root";
        final String PASS = "giselle";
        try{
            Connection con = null;
            String sql = "INSERT INTO Availability (HostID, LID, date, availability, price) VALUES (?,?,?,?,?)";
            
            con = DriverManager.getConnection(CONNECTION,USER,PASS);
            PreparedStatement ps = con.prepareStatement(sql);

            con.setAutoCommit(false);
            ps.setString(1, this.HostID);
            ps.setInt(2, this.LID);
            ps.setDate(3, this.date);
            ps.setString(4, this.availability);
            ps.setInt(5, this.price);

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

