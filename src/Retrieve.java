import java.sql.*;

public class Retrieve {
    private static final String CONNECTION = "jdbc:mysql://localhost:3306/mybnb"; 

    public void ShowListings()throws ClassNotFoundException{

        Class.forName("com.mysql.cj.jdbc.Driver");
        final String USER = "root";
        final String PASS = "giselle";

        try{
            Connection con = null;
            ResultSet rs = null;
            String query = "SELECT * FROM Listings";

            con = DriverManager.getConnection(CONNECTION,USER,PASS);
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            if(rs.next()){
                System.out.println("The listing ID is: " + rs.getInt(1));
                System.out.println("The host ID is: " + rs.getInt(2));
                System.out.println("The type of listing is: " + rs.getString(3));
                System.out.println("Its latitude is: " + rs.getDouble(4));
                System.out.println("Its longtitude is: " + rs.getDouble(5));
                System.out.println("Its address is: " + rs.getString(6));
                System.out.println("The city of listing is: " + rs.getString(7));
                System.out.println("The country of listing is: " + rs.getString(8));
                System.out.println("Its postal code is: " + rs.getString(9));
                System.out.println("The amenities it offers: " + rs.getString(10));
                System.out.println();
            }
            rs.close();
            con.close();

        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
}
