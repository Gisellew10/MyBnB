
import java.sql.*;

public class InsertAvailability{
    private static final String CONNECTION = "jdbc:mysql://localhost:3306/mybnb"; 

    private int LID;
    private String date;
    private String availability;
    private float price;
    private boolean success;

    public InsertAvailability(int LID, String date, String availability, float price){
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
            String sql = "INSERT INTO Calendar (LID, date, availability, price) VALUES (?,?,?,?)";
            
            con = DriverManager.getConnection(CONNECTION,USER,PASS);
            PreparedStatement ps = con.prepareStatement(sql);

            con.setAutoCommit(false);
            ps.setInt(1, this.LID);
            ps.setString(2, this.date);
            ps.setString(3, this.availability);
            ps.setFloat(4, this.price);

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

