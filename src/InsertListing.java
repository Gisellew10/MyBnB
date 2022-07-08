import java.sql.*;

public class InsertListing{
    private static final String CONNECTION = "jdbc:mysql://localhost:3306/mydb"; 

    private String type;
    private float latitude;
    private float longtitude;
    private String address;
    private String postal_code;
    private String amenities;
    private boolean success;

    public InsertListing(String type, float latitude, float longtitude, String address, String postal_code, String amenities){
        this.type = type;
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.address = address;
        this.postal_code = postal_code;
        this.amenities = amenities;
    }

    public boolean createListing(){
        final String USER = "root";
        final String PASS = "giselle";
        try{
            Connection con = null;
            String sql = "INSERT INTO Listings (type) VALUES (?)";
            
            con = DriverManager.getConnection(CONNECTION,USER,PASS);
            PreparedStatement ps = con.prepareStatement(sql);

            con.setAutoCommit(false);
            ps.setString(1, this.type);
            ps.setFloat(2, this.latitude);
            ps.setFloat(3, this.longtitude);
            ps.setString(4, this.address);
            ps.setString(5, this.postal_code);
            ps.setString(6, this.amenities);

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
