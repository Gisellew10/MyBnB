import java.sql.*;

public class InsertListing{
    private static final String CONNECTION = "jdbc:mysql://localhost:3306/mybnb"; 

    private String HostID;
    private String type;
    private float latitude;
    private float longtitude;
    private String address;
    private String city;
    private String country;
    private String postal_code;
    private String amenities;
    private boolean success;

    public InsertListing(String HostID, String type, float latitude, float longtitude, String address, String city, String country, String postal_code, String amenities){
        this.HostID = HostID;
        this.type = type;
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.address = address;
        this.city = city;
        this.country = country;
        this.postal_code = postal_code;
        this.amenities = amenities;
    }

    public boolean createListing(){
        final String USER = "root";
        final String PASS = "giselle";
        try{
            Connection con = null;
            String sql = "INSERT INTO Listings (HostID, type, latitude, longtitude, address, city, country, postal_code, amenities) VALUES (?,?,?,?,?,?,?,?)";
            
            con = DriverManager.getConnection(CONNECTION,USER,PASS);
            PreparedStatement ps = con.prepareStatement(sql);

            con.setAutoCommit(false);
            ps.setString(1, this.HostID);
            ps.setString(2, this.type);
            ps.setFloat(3, this.latitude);
            ps.setFloat(4, this.longtitude);
            ps.setString(5, this.address);
            ps.setString(6, this.city);
            ps.setString(7, this.country);
            ps.setString(8, this.postal_code);
            ps.setString(9, this.amenities);

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
