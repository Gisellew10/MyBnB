import java.sql.*;
public class Retrieve {
    private static final String CONNECTION = "jdbc:mysql://localhost:3306/mydb"; 
    public static void main(String[] args) throws ClassNotFoundException{

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
                System.out.println("The type of listing is: " + rs.getString(1));
                System.out.println("Its latitude is: " + rs.getFloat(2));
                System.out.println("Its longtitude is: " + rs.getFloat(3));
                System.out.println("Its address is: " + rs.getString(4));
                System.out.println("The city of listing is: " + rs.getString(5));
                System.out.println("The country of listing is: " + rs.getString(6));
                System.out.println("Its postal code is: " + rs.getString(7));
                System.out.println("The amenities it offers: " + rs.getString(8));
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
