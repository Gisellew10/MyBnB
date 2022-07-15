import java.sql.*;
import java.util.Scanner;

public class Queries {

    private static final String CONNECTION = "jdbc:mysql://localhost:3306/mybnb"; 


    public void SearchByLatAndLon() throws ClassNotFoundException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        final String USER = "root";
        final String PASS = "giselle";

        try{
            Scanner inputlatitude = new Scanner(System.in);
            System.out.print("Enter a latitude: ");
            double latitude = inputlatitude.nextDouble();

            Scanner inputlongtitude = new Scanner(System.in);
            System.out.print("Enter a longtitude: ");
            double longtitude = inputlongtitude.nextDouble();

            Scanner inputdistance = new Scanner(System.in);
            System.out.print("Enter a preferred distance (default is 1.0 km): ");
            double distance = inputdistance.nextDouble();


            Connection con = null;
            ResultSet rs = null;
            String declare1 = "DECLARE @latitude AS double";

            con = DriverManager.getConnection(CONNECTION,USER,PASS);
            Statement stmt1 = con.createStatement();
            rs = stmt1.executeQuery(declare1);


            Connection con2 = null;
            ResultSet rs2 = null;
            String declare2 = "DECLARE @longtitude AS double";

            con2 = DriverManager.getConnection(CONNECTION,USER,PASS);
            Statement stmt2 = con2.createStatement();
            rs2 = stmt2.executeQuery(declare2);


            Connection con3 = null;
            ResultSet rs3 = null;
            String declare3 = "DECLARE @distance AS double";

            con3 = DriverManager.getConnection(CONNECTION,USER,PASS);
            Statement stmt3 = con3.createStatement();
            rs3 = stmt3.executeQuery(declare3);

            
            Connection con4 = null;
            ResultSet rs4 = null;
            String query = "SELECT LID, type, address, city, country, postal_code, amenities" +
            "111.111 * DEGREES(ACOS(LEAST(1.0, COS(RADIANS(Listings.latitude))" +
            "* COS(RADIANS(@latitude)) * COS(RADIANS(Listings.longitude - @longitude))" +
            "+ SIN(RADIANS(Listings.latitude)) * SIN(RADIANS(@latitude))))) AS distance_in_km FROM Listings" +
            "WHERE distance_in_km <= @distance ORDER BY distance_in_km ASC";

            con4 = DriverManager.getConnection(CONNECTION,USER,PASS);
            Statement stmt4 = con4.createStatement();
            rs4 = stmt4.executeQuery(query);


            rs2.close();
            con2.close();

        }
        catch(SQLException e){
            e.printStackTrace();
        }

    }
    
}
