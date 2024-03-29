
import java.sql.*;

public class RentalHistory{
    private static final String CONNECTION = "jdbc:mysql://localhost:3306/mybnb"; 

    private String Booking_ID;
    private int LID;
    private String HostID;
    private String RenterID;
    private Date date;
    private String address;
    private String city;
    private String country;
    private int price;
    private Date start_date;
    private Date end_date;
    private boolean success;

    public RentalHistory(String Booking_ID, int LID, String HostID, String RenterID, Date date, String address, String city, String country, int price, Date start_date, Date end_date){
        this.Booking_ID = Booking_ID;
        this.LID = LID;
        this.HostID = HostID;
        this.RenterID = RenterID;
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
            String sql = "INSERT INTO RentalHistory(Booking_ID, LID, HostID, RenterID, address, city, country, date, price, start_date, end_date) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
            
            con = DriverManager.getConnection(CONNECTION,USER,PASS);
            PreparedStatement ps = con.prepareStatement(sql);

            con.setAutoCommit(false);
            ps.setString(1, this.Booking_ID);
            ps.setInt(2, this.LID);
            ps.setString(3, this.HostID);
            ps.setString(4, this.RenterID);
            ps.setString(5, this.address);
            ps.setString(6, this.city);
            ps.setString(7, this.country);
            ps.setDate(8, this.date);
            ps.setInt(9, this.price);
            ps.setDate(10, this.start_date);
            ps.setDate(11, this.end_date);

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

