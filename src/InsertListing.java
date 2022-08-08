import java.sql.*;

public class InsertListing{
    private static final String CONNECTION = "jdbc:mysql://localhost:3306/mybnb"; 

    private String HostID;
    private String title;
    private String type;
    private double latitude;
    private double longitude;
    private String address;
    private String city;
    private String country;
    private String postal_code;
    private String amenities;
    private int bedroom;
    private int bathroom;
    private int bed;
    private int price;
    private boolean success;

    public InsertListing(String HostID, String title, String type, double latitude, double longitude, String address, String city, String country, String postal_code, String amenities, int bedroom, int bathroom, int bed, int price){
        this.HostID = HostID;
        this.title = title;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.city = city;
        this.country = country;
        this.postal_code = postal_code;
        this.amenities = amenities;
        this.bedroom = bedroom;
        this.bathroom = bathroom;
        this.bed = bed;
        this.price = price;
    }

    public boolean createListing(){
        final String USER = "root";
        final String PASS = "giselle";
        try{
            Connection con = null;
            String sql = "INSERT INTO Listings (HostID, title, type, latitude, longitude, address, city, country, postal_code, amenities, bedroom, bathroom, bed, price) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            
            con = DriverManager.getConnection(CONNECTION,USER,PASS);
            PreparedStatement ps = con.prepareStatement(sql);

            con.setAutoCommit(false);
            ps.setString(1, this.HostID);
            ps.setString(2, this.title);
            ps.setString(3, this.type);
            ps.setDouble(4, this.latitude);
            ps.setDouble(5, this.longitude);
            ps.setString(6, this.address);
            ps.setString(7, this.city);
            ps.setString(8, this.country);
            ps.setString(9, this.postal_code);
            ps.setString(10, this.amenities);
            ps.setInt(11, this.bedroom);
            ps.setInt(12, this.bathroom);
            ps.setInt(13, this.bed);
            ps.setInt(14, this.price);

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
