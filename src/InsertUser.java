import java.sql.*;

public class InsertUser{
    private static final String CONNECTION = "jdbc:mysql://localhost:3306/mybnb"; 

    private String UserID;
    private String address;
    private String date_of_birth;
    private String occupation;
    private int SIN;
    private boolean success;

    public InsertUser(String UserID, String address, String date_of_birth, String occupation, int SIN){
        this.UserID = UserID;
        this.address = address;
        this.date_of_birth = date_of_birth;
        this.occupation = occupation;
        this.SIN = SIN;
    }

    public boolean createUser(){
        final String USER = "root";
        final String PASS = "giselle";
        try{
            Connection con = null;
            String sql = "INSERT INTO Users (UserID, address, date_of_birth, occupation, SIN) VALUES (?,?,?,?,?)";
            
            con = DriverManager.getConnection(CONNECTION,USER,PASS);
            PreparedStatement ps = con.prepareStatement(sql);

            con.setAutoCommit(false);
            ps.setString(1, this.UserID);
            ps.setString(2, this.address);
            ps.setString(3, this.date_of_birth);
            ps.setString(4, this.occupation);
            ps.setInt(5, this.SIN);

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

