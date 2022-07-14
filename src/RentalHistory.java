
import java.sql.*;

public class RentalHistory{
    private static final String CONNECTION = "jdbc:mysql://localhost:3306/mybnb"; 

    private String Booking_ID;
    private int LID;
    private String UserID;
    private String date;
    private String address;
    private String city;
    private String country;
    private float price;
    private String start_date;
    private String end_date;
    private boolean success;

    public RentalHistory(String Booking_ID, int LID, String UserID, String date, String address, String city, String country, float price, String start_date, String end_date){
        this.Booking_ID = Booking_ID;
        this.LID = LID;
        this.UserID = UserID;
        this.address = address;
        this.city = city;
        this.country = country;
        this.date = date;
        this.price = price;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public boolean createRentalHistory(){
        final String USER = "root";
        final String PASS = "giselle";
        try{
            Connection con = null;
            String sql = "INSERT INTO RentalHistory(Booking_ID, LID, UserID, address, city, country, date, price, start_date, end_date) VALUES (?,?,?,?,?,?,?,?,?,?)";
            
            con = DriverManager.getConnection(CONNECTION,USER,PASS);
            PreparedStatement ps = con.prepareStatement(sql);

            con.setAutoCommit(false);
            ps.setString(1, this.Booking_ID);
            ps.setInt(2, this.LID);
            ps.setString(3, this.UserID);
            ps.setString(4, this.address);
            ps.setString(5, this.city);
            ps.setString(6, this.country);
            ps.setString(7, this.date);
            ps.setFloat(8, this.price);
            ps.setString(9, this.start_date);
            ps.setString(10, this.end_date);

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

