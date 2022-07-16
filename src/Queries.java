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

            Scanner inputRankByPrice = new Scanner(System.in);
            System.out.print("Would you like to rank the listings by price (Yes/No): ");
            String RankByPrice = inputRankByPrice.nextLine();

            String query;

            if(RankByPrice.equals("Yes")){
                Scanner inputRank = new Scanner(System.in);
                System.out.print("Rank in ascending(ASC) or descending(DESC): ");
                String Rank = inputRank.nextLine();

                query = "SELECT LID, type, address, city, country, postal_code, amenities, AVG(price), (111.111 * DEGREES(ACOS(LEAST(1.0, COS(RADIANS(Listings.latitude)) * COS(RADIANS('" +latitude + "'" + ")) * COS(RADIANS(Listings.longtitude - '" + longtitude + "'" + ")) + SIN(RADIANS(Listings.latitude)) * SIN(RADIANS('" +latitude + "'" + ")))))) AS distance_in_km FROM Listings NATURAL JOIN Availability WHERE (111.111 * DEGREES(ACOS(LEAST(1.0, COS(RADIANS(Listings.latitude)) * COS(RADIANS('" +latitude + "'" + ")) * COS(RADIANS(Listings.longtitude - '" + longtitude + "'" + ")) + SIN(RADIANS(Listings.latitude)) * SIN(RADIANS('" +latitude + "'" + "))))))  < '" + distance + "'" + "GROUP BY  LID, type, address, city, country, postal_code, amenities ORDER BY AVG(price) " + Rank;
            }else{
                query = "SELECT LID, type, address, city, country, postal_code, amenities, AVG(price), (111.111 * DEGREES(ACOS(LEAST(1.0, COS(RADIANS(Listings.latitude)) * COS(RADIANS('" +latitude + "'" + ")) * COS(RADIANS(Listings.longtitude - '" + longtitude + "'" + ")) + SIN(RADIANS(Listings.latitude)) * SIN(RADIANS('" +latitude + "'" + ")))))) AS distance_in_km FROM Listings NATURAL JOIN Availability WHERE (111.111 * DEGREES(ACOS(LEAST(1.0, COS(RADIANS(Listings.latitude)) * COS(RADIANS('" +latitude + "'" + ")) * COS(RADIANS(Listings.longtitude - '" + longtitude + "'" + ")) + SIN(RADIANS(Listings.latitude)) * SIN(RADIANS('" +latitude + "'" + "))))))  < '" + distance + "'" + "GROUP BY  LID, type, address, city, country, postal_code, amenities ORDER BY distance_in_km ASC";
            }

            System.out.println();

            
            Connection con = null;
            ResultSet rs = null;

            con = DriverManager.getConnection(CONNECTION,USER,PASS);
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(query);


            while(rs.next()){
                System.out.println("The listing ID is: " + rs.getInt(1));
                System.out.println("The type of listing is: " + rs.getString(2));
                System.out.println("Its address is: " + rs.getString(3) + ", " + rs.getString(4) + ", " + rs.getString(5));
                System.out.println("Its postal code is: " + rs.getString(6));
                System.out.println("The amenities it offers: " + rs.getString(7));
                System.out.println("The average price of listing is: " + rs.getString(8));
                System.out.println();
            }


        }
        catch(SQLException e){
            e.printStackTrace();
        }

    }
    

    public void SearchByPostalCode() throws ClassNotFoundException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        final String USER = "root";
        final String PASS = "giselle";

        try{
            Scanner input_postal_code = new Scanner(System.in);
            System.out.print("Enter a postal code: ");
            String postal_code = input_postal_code.nextLine();

            Scanner inputRankByPrice = new Scanner(System.in);
            System.out.print("Would you like to rank the listings by price (Yes/No): ");
            String RankByPrice = inputRankByPrice.nextLine();

            String Rank = null;
            if(RankByPrice.equals("Yes")){
                Scanner inputRank = new Scanner(System.in);
                System.out.print("Rank in ascending(ASC) or descending(DESC): ");
                Rank = inputRank.nextLine();
            }

            Scanner inputAvailability = new Scanner(System.in);
            System.out.print("Would you like to filter the listings by your preferred date range (Yes/No): ");
            String availability = inputAvailability.nextLine();

            String start_date = null;
            String end_date = null;

            if(availability.equals("Yes")){
                Scanner inputstartdate= new Scanner(System.in);
                System.out.print("Enter the start date (dd/mm/yyyy): ");
                start_date = inputstartdate.nextLine();
    
                Scanner inputenddate = new Scanner(System.in);
                System.out.print("Enter the end date(dd/mm/yyyy): ");
                end_date = inputenddate.nextLine();
            }

            String sql = "SELECT LID, type, address, city, country, postal_code, amenities, AVG(price), latitude, longtitude FROM Listings NATURAL JOIN Availability WHERE Listings.postal_code = '" + postal_code + "'" + "GROUP BY LID, type, address, city, country, postal_code, amenities,latitude, longtitude";

            Connection con = null;
            ResultSet rs = null;

            con = DriverManager.getConnection(CONNECTION,USER,PASS);
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            Double latitude = 0.0;
            Double longtitude = 0.0;

            if(rs.next()){
                latitude = rs.getDouble(9);
                longtitude = rs.getDouble(10);
            }
            
            String query;

            if(RankByPrice.equals("Yes") && availability.equals("No")){

                query = "SELECT LID, type, address, city, country, postal_code, amenities, AVG(price), (111.111 * DEGREES(ACOS(LEAST(1.0, COS(RADIANS(Listings.latitude)) * COS(RADIANS('" +latitude + "'" + ")) * COS(RADIANS(Listings.longtitude - '" + longtitude + "'" + ")) + SIN(RADIANS(Listings.latitude)) * SIN(RADIANS('" +latitude + "'" + ")))))) AS distance_in_km FROM Listings NATURAL JOIN Availability WHERE (111.111 * DEGREES(ACOS(LEAST(1.0, COS(RADIANS(Listings.latitude)) * COS(RADIANS('" +latitude + "'" + ")) * COS(RADIANS(Listings.longtitude - '" + longtitude + "'" + ")) + SIN(RADIANS(Listings.latitude)) * SIN(RADIANS('" +latitude + "'" + "))))))  < 5.0 GROUP BY  LID, type, address, city, country, postal_code, amenities ORDER BY AVG(price) " + Rank;
            }if(RankByPrice.equals("Yes") && availability.equals("Yes")){
                query = "SELECT LID, type, address, city, country, postal_code, amenities, AVG(price), (111.111 * DEGREES(ACOS(LEAST(1.0, COS(RADIANS(Listings.latitude)) * COS(RADIANS('" +latitude + "'" + ")) * COS(RADIANS(Listings.longtitude - '" + longtitude + "'" + ")) + SIN(RADIANS(Listings.latitude)) * SIN(RADIANS('" +latitude + "'" + ")))))) AS distance_in_km FROM Listings NATURAL JOIN Availability WHERE availability =  'available'  AND date BETWEEN '" + start_date + "'" + "AND '" + end_date + "' AND (111.111 * DEGREES(ACOS(LEAST(1.0, COS(RADIANS(Listings.latitude)) * COS(RADIANS('" +latitude + "'" + ")) * COS(RADIANS(Listings.longtitude - '" + longtitude + "'" + ")) + SIN(RADIANS(Listings.latitude)) * SIN(RADIANS('" +latitude + "'" + "))))))  < 5.0 GROUP BY  LID, type, address, city, country, postal_code, amenities ORDER BY AVG(price) " + Rank;
            }else if(RankByPrice.equals("No") && availability.equals("Yes")){
                query = "SELECT LID, type, address, city, country, postal_code, amenities, AVG(price), (111.111 * DEGREES(ACOS(LEAST(1.0, COS(RADIANS(Listings.latitude)) * COS(RADIANS('" +latitude + "'" + ")) * COS(RADIANS(Listings.longtitude - '" + longtitude + "'" + ")) + SIN(RADIANS(Listings.latitude)) * SIN(RADIANS('" +latitude + "'" + ")))))) AS distance_in_km FROM Listings NATURAL JOIN Availability WHERE availability =  'available'  AND date BETWEEN '" + start_date + "'" + "AND '" + end_date + "' AND (111.111 * DEGREES(ACOS(LEAST(1.0, COS(RADIANS(Listings.latitude)) * COS(RADIANS('" +latitude + "'" + ")) * COS(RADIANS(Listings.longtitude - '" + longtitude + "'" + ")) + SIN(RADIANS(Listings.latitude)) * SIN(RADIANS('" +latitude + "'" + "))))))  < 5.0 GROUP BY  LID, type, address, city, country, postal_code, amenities ORDER BY AVG(price) ";
            }else{
                query = "SELECT LID, type, address, city, country, postal_code, amenities, AVG(price), (111.111 * DEGREES(ACOS(LEAST(1.0, COS(RADIANS(Listings.latitude)) * COS(RADIANS('" +latitude + "'" + ")) * COS(RADIANS(Listings.longtitude - '" + longtitude + "'" + ")) + SIN(RADIANS(Listings.latitude)) * SIN(RADIANS('" +latitude + "'" + ")))))) AS distance_in_km FROM Listings NATURAL JOIN Availability WHERE (111.111 * DEGREES(ACOS(LEAST(1.0, COS(RADIANS(Listings.latitude)) * COS(RADIANS('" +latitude + "'" + ")) * COS(RADIANS(Listings.longtitude - '" + longtitude + "'" + ")) + SIN(RADIANS(Listings.latitude)) * SIN(RADIANS('" +latitude + "'" + "))))))  < 5.0 GROUP BY  LID, type, address, city, country, postal_code, amenities ORDER BY distance_in_km ASC";
            }

            System.out.println();

            Connection con2 = null;
            ResultSet rs2 = null;

            con2 = DriverManager.getConnection(CONNECTION,USER,PASS);
            Statement stmt2 = con2.createStatement();
            rs2 = stmt2.executeQuery(query);

            while(rs2.next()){
                System.out.println("The listing ID is: " + rs2.getInt(1));
                System.out.println("The type of listing is: " + rs2.getString(2));
                System.out.println("Its address is: " + rs2.getString(3) + ", " + rs2.getString(4) + ", " + rs2.getString(5));
                System.out.println("Its postal code is: " + rs2.getString(6));
                System.out.println("The amenities it offers: " + rs2.getString(7));
                System.out.println("The average price of listing is: " + rs2.getString(8));
                System.out.println();
            }


        }
        catch(SQLException e){
            e.printStackTrace();
        }

    }



    public void SearchByAddress() throws ClassNotFoundException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        final String USER = "root";
        final String PASS = "giselle";

        try{
            Scanner inputaddress = new Scanner(System.in);
            System.out.print("Enter an address: ");
            String address = inputaddress.nextLine();

            String sql = "SELECT LID, type, address, city, country, postal_code, amenities, AVG(price) FROM Listings NATURAL JOIN Availability WHERE Listings.address = '" + address + "'" + "GROUP BY LID, type, address, city, country, postal_code, amenities";

            Connection con = null;
            ResultSet rs = null;

            con = DriverManager.getConnection(CONNECTION,USER,PASS);
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            if(rs.next()){
                System.out.println("The listing ID is: " + rs.getInt(1));
                System.out.println("The type of listing is: " + rs.getString(2));
                System.out.println("Its address is: " + rs.getString(3) + ", " + rs.getString(4) + ", " + rs.getString(5));
                System.out.println("Its postal code is: " + rs.getString(6));
                System.out.println("The amenities it offers: " + rs.getString(7));
                System.out.println("The average price of listing is: " + rs.getString(8));
                System.out.println();
            }else{
                System.out.println("No Listing Found!");
                System.out.println();
            }


        }
        catch(SQLException e){
            e.printStackTrace();
        }

    }
}
