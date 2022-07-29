import java.sql.*;

public class Retrieve {
    private static final String CONNECTION = "jdbc:mysql://localhost:3306/mybnb"; 

    public void ShowListings(String HostID)throws ClassNotFoundException{

        Class.forName("com.mysql.cj.jdbc.Driver");
        final String USER = "root";
        final String PASS = "giselle";

        try{
            Connection con = null;
            ResultSet rs = null;
            String query = "SELECT * FROM Listings WHERE HostID = '" + HostID + "'";

            con = DriverManager.getConnection(CONNECTION,USER,PASS);
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(query);
            while(rs.next()){
                System.out.println("The listing ID is: " + rs.getInt(1));
                System.out.println("The host ID is: " + rs.getString(2));
                System.out.println("The type of listing is: " + rs.getString(3));
                System.out.println("Its latitude is: " + rs.getDouble(4));
                System.out.println("Its longtitude is: " + rs.getDouble(5));
                System.out.println("Its address is: " + rs.getString(6));
                System.out.println("The city of listing is: " + rs.getString(7));
                System.out.println("The country of listing is: " + rs.getString(8));
                System.out.println("Its postal code is: " + rs.getString(9));
                System.out.println("The amenities it offers: " + rs.getString(10));
                System.out.println("Number of bedrooms: " + rs.getInt(11));
                System.out.println("Number of bathrooms: " + rs.getInt(12));
                System.out.println("Number of beds: " + rs.getString(13));
                System.out.println("----------------------------------------");
                System.out.println();
            }

            rs.close();
            con.close();

        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }

    public void ShowRentalHistory(String UserID)throws ClassNotFoundException{

        Class.forName("com.mysql.cj.jdbc.Driver");
        final String USER = "root";
        final String PASS = "giselle";

        try{
            String copy = UserID;
            String[] tokens = copy.split("-");

            Connection con = null;
            ResultSet rs = null;
            String query = null;
            if(tokens[0].equals("Host")){
                query = "SELECT * FROM RentalHistory WHERE HostID = '" + UserID + "' ORDER BY date DESC";
            }else{
                query = "SELECT * FROM RentalHistory WHERE RenterID = '" + UserID + "' ORDER BY date DESC";
            }

            con = DriverManager.getConnection(CONNECTION,USER,PASS);
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            while(rs.next()){
                System.out.println("The booking ID is: " + rs.getString(1));
                System.out.println("The listing ID is: " + rs.getInt(2));
                System.out.println("The host ID is: " + rs.getString(3));
                System.out.println("The renter ID is: " + rs.getString(4));
                System.out.println("Its address is: " + rs.getString(5));
                System.out.println("The city of listing is: " + rs.getString(6));
                System.out.println("The country of listing is: " + rs.getString(7));
                System.out.println("The date of transaction: " + rs.getDate(8));
                System.out.println("Its price is: " + rs.getDouble(9));
                System.out.println("Checkin: " + rs.getDate(10));
                System.out.println("Checkout: " + rs.getDate(11));
                System.out.println("----------------------------------------");
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
