
import java.sql.*;

public class RentalHistory{
    private static final String CONNECTION = "jdbc:mysql://localhost:3306/mybnb"; 

    private int LID;
    private String UserID;
    private String date;
    private String address;
    private String city;
    private String country;
    private float price;
    private boolean success;

    public RentalHistory(int LID, String UserID, String date, String address, String city, String country, float price){
        this.LID = LID;
        this.UserID = UserID;
        this.address = address;
        this.city = city;
        this.country = country;
        this.date = date;
        this.price = price;
    }

    public boolean createRentalHistory(){
        final String USER = "root";
        final String PASS = "giselle";
        try{
            Connection con = null;
            String sql = "INSERT INTO RentalHistory(LID, UserID, address, city, country, date, price) VALUES (?,?,?,?,?,?,?)";
            
            con = DriverManager.getConnection(CONNECTION,USER,PASS);
            PreparedStatement ps = con.prepareStatement(sql);

            con.setAutoCommit(false);
            ps.setInt(1, this.LID);
            ps.setString(2, this.UserID);
            ps.setString(3, this.address);
            ps.setString(4, this.city);
            ps.setString(5, this.country);
            ps.setString(6, this.date);
            ps.setFloat(7, this.price);

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

