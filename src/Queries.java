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

            System.out.println();


            
            Connection con4 = null;
            ResultSet rs4 = null;
            String query = "SELECT LID, type, address, city, country, postal_code, amenities, AVG(price), (111.111 * DEGREES(ACOS(LEAST(1.0, COS(RADIANS(Listings.latitude)) * COS(RADIANS('" +latitude + "'" + ")) * COS(RADIANS(Listings.longtitude - '" + longtitude + "'" + ")) + SIN(RADIANS(Listings.latitude)) * SIN(RADIANS('" +latitude + "'" + ")))))) AS distance_in_km FROM Listings NATURAL JOIN Availability WHERE (111.111 * DEGREES(ACOS(LEAST(1.0, COS(RADIANS(Listings.latitude)) * COS(RADIANS('" +latitude + "'" + ")) * COS(RADIANS(Listings.longtitude - '" + longtitude + "'" + ")) + SIN(RADIANS(Listings.latitude)) * SIN(RADIANS('" +latitude + "'" + "))))))  < '" + distance + "'" + " GROUP BY LID, type, address, city, country, postal_code, amenities ORDER BY distance_in_km ASC";

            con4 = DriverManager.getConnection(CONNECTION,USER,PASS);
            Statement stmt4 = con4.createStatement();
            rs4 = stmt4.executeQuery(query);

            if(rs4.next()){
                System.out.println("The listing ID is: " + rs4.getInt(1));
                System.out.println("The type of listing is: " + rs4.getString(2));
                System.out.println("Its address is: " + rs4.getString(3) + ", " + rs4.getString(4) + ", " + rs4.getString(5));
                System.out.println("Its postal code is: " + rs4.getString(6));
                System.out.println("The amenities it offers: " + rs4.getString(7));
                System.out.println("The average price of listing is: " + rs4.getString(8));
                System.out.println();
            }


        }
        catch(SQLException e){
            e.printStackTrace();
        }

    }
    
}
