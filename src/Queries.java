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

            Scanner AmenitiesFilter = new Scanner(System.in);
            System.out.print("Would you like to filter the listings by your preferred amenities (Yes/No): ");
            String amenities_filter = AmenitiesFilter.nextLine();

            String amenities = null;

            if(amenities_filter.equals("Yes")){
                Scanner  inputAmenities = new Scanner(System.in);
                System.out.print("Enter the amenities: ");
                amenities = inputAmenities.nextLine();
            }


            Scanner PriceFilter = new Scanner(System.in);
            System.out.print("Would you like to filter the listings by your preferred price range (Yes/No): ");
            String price_filter = PriceFilter.nextLine();

            double lowest_price = 0.0;
            double highest_price = 0.0;

            if(price_filter.equals("Yes")){
                Scanner  inputLowestPrice = new Scanner(System.in);
                System.out.print("Enter the lowest price in your price range: ");
                lowest_price = inputLowestPrice.nextDouble();

                Scanner  inputHighestPrice = new Scanner(System.in);
                System.out.print("Enter the highest price in your price range: ");
                highest_price = inputHighestPrice.nextDouble();
            }



            String query;

            String get_distance = "(111.111 * DEGREES(ACOS(LEAST(1.0, COS(RADIANS(Listings.latitude)) * COS(RADIANS('" +latitude + "'" + ")) * COS(RADIANS(Listings.longtitude - '" + longtitude + "'" + ")) + SIN(RADIANS(Listings.latitude)) * SIN(RADIANS('" +latitude + "'" + "))))))";

            //rank by price
            if(RankByPrice.equals("Yes") && availability.equals("No") && amenities_filter.equals("No") && price_filter.equals("No")){

                query = "SELECT LID, type, address, city, country, postal_code, amenities, AVG(price), " + get_distance + " AS distance_in_km FROM Listings NATURAL JOIN Availability WHERE" + get_distance + "< '" + distance + "'" + "GROUP BY  LID, type, address, city, country, postal_code, amenities ORDER BY AVG(price) " + Rank;
            }

            //rank by price + availability filter
            else if(RankByPrice.equals("Yes") && availability.equals("Yes") && amenities_filter.equals("No") && price_filter.equals("No")){

                query = "SELECT LID, type, address, city, country, postal_code, amenities, AVG(price), " + get_distance + " AS distance_in_km FROM Listings NATURAL JOIN Availability WHERE availability =  'available'  AND date BETWEEN '" + start_date + "'" + "AND '" + end_date + "' AND" + get_distance + "< '" + distance + "'" + "GROUP BY  LID, type, address, city, country, postal_code, amenities ORDER BY AVG(price) " + Rank;
            }
            
            //availability filter
            else if(RankByPrice.equals("No") && availability.equals("Yes") && amenities_filter.equals("No") && price_filter.equals("No")){

                query = "SELECT LID, type, address, city, country, postal_code, amenities, AVG(price), " + get_distance + " AS distance_in_km FROM Listings NATURAL JOIN Availability WHERE availability =  'available'  AND date BETWEEN '" + start_date + "'" + "AND '" + end_date + "' AND" + get_distance + "< '" + distance + "'" + "GROUP BY  LID, type, address, city, country, postal_code, amenities";
            }

            //amenities filter
            else if(RankByPrice.equals("No") && availability.equals("No") && amenities_filter.equals("Yes") && price_filter.equals("No")){

                query = "SELECT LID, type, address, city, country, postal_code, amenities, AVG(price), " + get_distance + " AS distance_in_km FROM Listings NATURAL JOIN Availability WHERE amenities IN ('"+ amenities + "') AND" + get_distance + "< '" + distance + "'" + "GROUP BY  LID, type, address, city, country, postal_code, amenities";
            }

            //price range filter
            else if(RankByPrice.equals("No") && availability.equals("No") && amenities_filter.equals("No") && price_filter.equals("Yes")){

                query = "SELECT LID, type, address, city, country, postal_code, amenities, AVG(price), " + get_distance + " AS distance_in_km FROM Listings NATURAL JOIN Availability WHERE price BETWEEN '"+ lowest_price + "' AND '" + highest_price + "' AND" + get_distance + "< '" + distance + "'" + "GROUP BY  LID, type, address, city, country, postal_code, amenities";
            }

            //rank by price + amenities
            else if(RankByPrice.equals("Yes") && availability.equals("No") && amenities_filter.equals("Yes") && price_filter.equals("No")){

                query = "SELECT LID, type, address, city, country, postal_code, amenities, AVG(price), " + get_distance + " AS distance_in_km FROM Listings NATURAL JOIN Availability WHERE amenities IN ('"+ amenities + "') AND" + get_distance + "< '" + distance + "'" + "GROUP BY  LID, type, address, city, country, postal_code, amenities ORDER BY AVG(price) " + Rank;
            }

            //rank by price + price range
            else if(RankByPrice.equals("Yes") && availability.equals("No") && amenities_filter.equals("No") && price_filter.equals("Yes")){

                query = "SELECT LID, type, address, city, country, postal_code, amenities, AVG(price), " + get_distance + " AS distance_in_km FROM Listings NATURAL JOIN Availability WHERE price BETWEEN '"+ lowest_price + "' AND '" + highest_price + "' AND" + get_distance + "< '" + distance + "'" + "GROUP BY  LID, type, address, city, country, postal_code, amenities ORDER BY AVG(price) " + Rank;
            }

            //availability + amenities
            else if(RankByPrice.equals("No") && availability.equals("Yes") && amenities_filter.equals("Yes") && price_filter.equals("No")){

                query = "SELECT LID, type, address, city, country, postal_code, amenities, AVG(price), " + get_distance + " AS distance_in_km FROM Listings NATURAL JOIN Availability WHERE availability =  'available'  AND date BETWEEN '" + start_date + "'" + "AND '" + end_date + "' AND" + "amenities IN ('" + amenities + "') AND" + get_distance + "< '" + distance + "'" + "GROUP BY  LID, type, address, city, country, postal_code, amenities";
            }
            
            //availability + price range
            else if(RankByPrice.equals("No") && availability.equals("Yes") && amenities_filter.equals("No") && price_filter.equals("Yes")){

                query = "SELECT LID, type, address, city, country, postal_code, amenities, AVG(price), " + get_distance + " AS distance_in_km FROM Listings NATURAL JOIN Availability WHERE availability =  'available'  AND date BETWEEN '" + start_date + "'" + "AND '" + end_date + "'AND price BETWEEN '"+ lowest_price + "' AND '" + highest_price + "' AND" + get_distance + "< '" + distance + "'" + "GROUP BY  LID, type, address, city, country, postal_code, amenities";
            }

            //amenities + price range
            else if(RankByPrice.equals("No") && availability.equals("No") && amenities_filter.equals("Yes") && price_filter.equals("Yes")){

                query = "SELECT LID, type, address, city, country, postal_code, amenities, AVG(price), " + get_distance + " AS distance_in_km FROM Listings NATURAL JOIN Availability WHERE amenities IN ('"+ amenities + "') AND price BETWEEN '"+ lowest_price + "' AND '" + highest_price + "' AND" + get_distance + "< '" + distance + "'" + "GROUP BY  LID, type, address, city, country, postal_code, amenities";
            }

            //rank by price + availability + amenities
            else if(RankByPrice.equals("Yes") && availability.equals("Yes") && amenities_filter.equals("Yes") && price_filter.equals("No")){

                query = "SELECT LID, type, address, city, country, postal_code, amenities, AVG(price), " + get_distance + " AS distance_in_km FROM Listings NATURAL JOIN Availability WHERE availability =  'available'  AND date BETWEEN '" + start_date + "'" + "AND '" + end_date + "'AND amenities IN ('"+ amenities + "') AND" + get_distance + "< '" + distance + "'" + "GROUP BY  LID, type, address, city, country, postal_code, amenities ORDER BY AVG(price) " + Rank;
            }

            //rank by price + amenities + price range
            else if(RankByPrice.equals("Yes") && availability.equals("No") && amenities_filter.equals("Yes") && price_filter.equals("Yes")){

                query = "SELECT LID, type, address, city, country, postal_code, amenities, AVG(price), " + get_distance + " AS distance_in_km FROM Listings NATURAL JOIN Availability WHERE price BETWEEN '"+ lowest_price + "' AND '" + highest_price + "' AND amenities IN ('"+ amenities + "') AND" + get_distance + "< '" + distance + "'" + "GROUP BY  LID, type, address, city, country, postal_code, amenities ORDER BY AVG(price) " + Rank;
            }

            //rank by price + availability + price range
            else if(RankByPrice.equals("Yes") && availability.equals("Yes") && amenities_filter.equals("No") && price_filter.equals("Yes")){

                query = "SELECT LID, type, address, city, country, postal_code, amenities, AVG(price), " + get_distance + " AS distance_in_km FROM Listings NATURAL JOIN Availability WHERE price BETWEEN '"+ lowest_price + "' AND '" + highest_price + "' AND availability =  'available'  AND date BETWEEN '" + start_date + "'" + "AND '" + end_date + "' AND" + get_distance + "< '" + distance + "'" + "GROUP BY  LID, type, address, city, country, postal_code, amenities ORDER BY AVG(price) " + Rank;
            }

            //availability + amenities + price range
            else if(RankByPrice.equals("No") && availability.equals("Yes") && amenities_filter.equals("Yes") && price_filter.equals("No")){

                query = "SELECT LID, type, address, city, country, postal_code, amenities, AVG(price), " + get_distance + " AS distance_in_km FROM Listings NATURAL JOIN Availability WHERE availability =  'available'  AND date BETWEEN '" + start_date + "'" + "AND '" + end_date + "' AND price BETWEEN '"+ lowest_price + "' AND '" + highest_price + "' AND amenities IN ('" + amenities + "') AND" + get_distance + "< '" + distance + "'" + "GROUP BY  LID, type, address, city, country, postal_code, amenities";
            }

            //apply all
            else if(RankByPrice.equals("Yes") && availability.equals("Yes") && amenities_filter.equals("Yes") && price_filter.equals("Yes")){

                query = "SELECT LID, type, address, city, country, postal_code, amenities, AVG(price), " + get_distance + " AS distance_in_km FROM Listings NATURAL JOIN Availability WHERE amenities IN ('" + amenities + "') AND price BETWEEN '"+ lowest_price + "' AND '" + highest_price + "' AND availability =  'available'  AND date BETWEEN '" + start_date + "' AND '" + end_date + "' AND" + get_distance + "< '" + distance + "'" + "GROUP BY  LID, type, address, city, country, postal_code, amenities ORDER BY AVG(price) " + Rank;
            }

            //no filter and rank
            else{

                query = "SELECT LID, type, address, city, country, postal_code, amenities, AVG(price), " + get_distance + "AS distance_in_km FROM Listings NATURAL JOIN Availability WHERE" + get_distance + "< '" + distance + "'" + "GROUP BY  LID, type, address, city, country, postal_code, amenities ORDER BY distance_in_km ASC";
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

            Scanner AmenitiesFilter = new Scanner(System.in);
            System.out.print("Would you like to filter the listings by your preferred amenities (Yes/No): ");
            String amenities_filter = AmenitiesFilter.nextLine();

            String amenities = null;

            if(amenities_filter.equals("Yes")){
                Scanner  inputAmenities = new Scanner(System.in);
                System.out.print("Enter the amenities: ");
                amenities = inputAmenities.nextLine();
            }


            Scanner PriceFilter = new Scanner(System.in);
            System.out.print("Would you like to filter the listings by your preferred price range (Yes/No): ");
            String price_filter = PriceFilter.nextLine();

            double lowest_price = 0.0;
            double highest_price = 0.0;

            if(price_filter.equals("Yes")){
                Scanner  inputLowestPrice = new Scanner(System.in);
                System.out.print("Enter the lowest price in your price range: ");
                lowest_price = inputLowestPrice.nextDouble();

                Scanner  inputHighestPrice = new Scanner(System.in);
                System.out.print("Enter the highest price in your price range: ");
                highest_price = inputHighestPrice.nextDouble();
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

            String get_distance = "(111.111 * DEGREES(ACOS(LEAST(1.0, COS(RADIANS(Listings.latitude)) * COS(RADIANS('" +latitude + "'" + ")) * COS(RADIANS(Listings.longtitude - '" + longtitude + "'" + ")) + SIN(RADIANS(Listings.latitude)) * SIN(RADIANS('" +latitude + "'" + "))))))";

            //rank by price
            if(RankByPrice.equals("Yes") && availability.equals("No") && amenities_filter.equals("No") && price_filter.equals("No")){

                query = "SELECT LID, type, address, city, country, postal_code, amenities, AVG(price), " + get_distance + " AS distance_in_km FROM Listings NATURAL JOIN Availability WHERE" + get_distance + "< 5.0 GROUP BY  LID, type, address, city, country, postal_code, amenities ORDER BY AVG(price) " + Rank;
            }

            //rank by price + availability filter
            else if(RankByPrice.equals("Yes") && availability.equals("Yes") && amenities_filter.equals("No") && price_filter.equals("No")){

                query = "SELECT LID, type, address, city, country, postal_code, amenities, AVG(price), " + get_distance + " AS distance_in_km FROM Listings NATURAL JOIN Availability WHERE availability =  'available'  AND date BETWEEN '" + start_date + "'" + "AND '" + end_date + "' AND" + get_distance + "< 5.0 GROUP BY  LID, type, address, city, country, postal_code, amenities ORDER BY AVG(price) " + Rank;
            }
            
            //availability filter
            else if(RankByPrice.equals("No") && availability.equals("Yes") && amenities_filter.equals("No") && price_filter.equals("No")){

                query = "SELECT LID, type, address, city, country, postal_code, amenities, AVG(price), " + get_distance + " AS distance_in_km FROM Listings NATURAL JOIN Availability WHERE availability =  'available'  AND date BETWEEN '" + start_date + "'" + "AND '" + end_date + "' AND" + get_distance + "< 5.0 GROUP BY  LID, type, address, city, country, postal_code, amenities";
            }

            //amenities filter
            else if(RankByPrice.equals("No") && availability.equals("No") && amenities_filter.equals("Yes") && price_filter.equals("No")){

                query = "SELECT LID, type, address, city, country, postal_code, amenities, AVG(price), " + get_distance + " AS distance_in_km FROM Listings NATURAL JOIN Availability WHERE amenities IN ('"+ amenities + "') AND" + get_distance + "< 5.0 GROUP BY  LID, type, address, city, country, postal_code, amenities";
            }

            //price range filter
            else if(RankByPrice.equals("No") && availability.equals("No") && amenities_filter.equals("No") && price_filter.equals("Yes")){

                query = "SELECT LID, type, address, city, country, postal_code, amenities, AVG(price), " + get_distance + " AS distance_in_km FROM Listings NATURAL JOIN Availability WHERE price BETWEEN '"+ lowest_price + "' AND '" + highest_price + "' AND" + get_distance + "< 5.0 GROUP BY  LID, type, address, city, country, postal_code, amenities";
            }

            //rank by price + amenities
            else if(RankByPrice.equals("Yes") && availability.equals("No") && amenities_filter.equals("Yes") && price_filter.equals("No")){

                query = "SELECT LID, type, address, city, country, postal_code, amenities, AVG(price), " + get_distance + " AS distance_in_km FROM Listings NATURAL JOIN Availability WHERE amenities IN ('"+ amenities + "') AND" + get_distance + "< 5.0 GROUP BY  LID, type, address, city, country, postal_code, amenities ORDER BY AVG(price) " + Rank;
            }

            //rank by price + price range
            else if(RankByPrice.equals("Yes") && availability.equals("No") && amenities_filter.equals("No") && price_filter.equals("Yes")){

                query = "SELECT LID, type, address, city, country, postal_code, amenities, AVG(price), " + get_distance + " AS distance_in_km FROM Listings NATURAL JOIN Availability WHERE price BETWEEN '"+ lowest_price + "' AND '" + highest_price + "' AND" + get_distance + "< 5.0 GROUP BY  LID, type, address, city, country, postal_code, amenities ORDER BY AVG(price) " + Rank;
            }

            //availability + amenities
            else if(RankByPrice.equals("No") && availability.equals("Yes") && amenities_filter.equals("Yes") && price_filter.equals("No")){

                query = "SELECT LID, type, address, city, country, postal_code, amenities, AVG(price), " + get_distance + " AS distance_in_km FROM Listings NATURAL JOIN Availability WHERE availability =  'available'  AND date BETWEEN '" + start_date + "'" + "AND '" + end_date + "' AND" + "amenities IN ('" + amenities + "') AND" + get_distance + "< 5.0 GROUP BY  LID, type, address, city, country, postal_code, amenities";
            }
            
            //availability + price range
            else if(RankByPrice.equals("No") && availability.equals("Yes") && amenities_filter.equals("No") && price_filter.equals("Yes")){

                query = "SELECT LID, type, address, city, country, postal_code, amenities, AVG(price), " + get_distance + " AS distance_in_km FROM Listings NATURAL JOIN Availability WHERE availability =  'available'  AND date BETWEEN '" + start_date + "'" + "AND '" + end_date + "'AND price BETWEEN '"+ lowest_price + "' AND '" + highest_price + "' AND" + get_distance + "< 5.0 GROUP BY  LID, type, address, city, country, postal_code, amenities";
            }

            //amenities + price range
            else if(RankByPrice.equals("No") && availability.equals("No") && amenities_filter.equals("Yes") && price_filter.equals("Yes")){

                query = "SELECT LID, type, address, city, country, postal_code, amenities, AVG(price), " + get_distance + " AS distance_in_km FROM Listings NATURAL JOIN Availability WHERE amenities IN ('"+ amenities + "') AND price BETWEEN '"+ lowest_price + "' AND '" + highest_price + "' AND" + get_distance + "< 5.0 GROUP BY  LID, type, address, city, country, postal_code, amenities";
            }

            //rank by price + availability + amenities
            else if(RankByPrice.equals("Yes") && availability.equals("Yes") && amenities_filter.equals("Yes") && price_filter.equals("No")){

                query = "SELECT LID, type, address, city, country, postal_code, amenities, AVG(price), " + get_distance + " AS distance_in_km FROM Listings NATURAL JOIN Availability WHERE availability =  'available'  AND date BETWEEN '" + start_date + "'" + "AND '" + end_date + "'AND amenities IN ('"+ amenities + "') AND" + get_distance + "< 5.0 GROUP BY  LID, type, address, city, country, postal_code, amenities ORDER BY AVG(price) " + Rank;
            }

            //rank by price + amenities + price range
            else if(RankByPrice.equals("Yes") && availability.equals("No") && amenities_filter.equals("Yes") && price_filter.equals("Yes")){

                query = "SELECT LID, type, address, city, country, postal_code, amenities, AVG(price), " + get_distance + " AS distance_in_km FROM Listings NATURAL JOIN Availability WHERE price BETWEEN '"+ lowest_price + "' AND '" + highest_price + "' AND amenities IN ('"+ amenities + "') AND" + get_distance + "< 5.0 GROUP BY  LID, type, address, city, country, postal_code, amenities ORDER BY AVG(price) " + Rank;
            }

            //rank by price + availability + price range
            else if(RankByPrice.equals("Yes") && availability.equals("Yes") && amenities_filter.equals("No") && price_filter.equals("Yes")){

                query = "SELECT LID, type, address, city, country, postal_code, amenities, AVG(price), " + get_distance + " AS distance_in_km FROM Listings NATURAL JOIN Availability WHERE price BETWEEN '"+ lowest_price + "' AND '" + highest_price + "' AND availability =  'available'  AND date BETWEEN '" + start_date + "'" + "AND '" + end_date + "' AND" + get_distance + "< 5.0 GROUP BY  LID, type, address, city, country, postal_code, amenities ORDER BY AVG(price) " + Rank;
            }

            //availability + amenities + price range
            else if(RankByPrice.equals("No") && availability.equals("Yes") && amenities_filter.equals("Yes") && price_filter.equals("No")){

                query = "SELECT LID, type, address, city, country, postal_code, amenities, AVG(price), " + get_distance + " AS distance_in_km FROM Listings NATURAL JOIN Availability WHERE availability =  'available'  AND date BETWEEN '" + start_date + "'" + "AND '" + end_date + "' AND price BETWEEN '"+ lowest_price + "' AND '" + highest_price + "' AND amenities IN ('" + amenities + "') AND" + get_distance + "< 5.0 GROUP BY  LID, type, address, city, country, postal_code, amenities";
            }

            //apply all
            else if(RankByPrice.equals("Yes") && availability.equals("Yes") && amenities_filter.equals("Yes") && price_filter.equals("Yes")){

                query = "SELECT LID, type, address, city, country, postal_code, amenities, AVG(price), " + get_distance + " AS distance_in_km FROM Listings NATURAL JOIN Availability WHERE amenities IN ('" + amenities + "') AND price BETWEEN '"+ lowest_price + "' AND '" + highest_price + "' AND availability =  'available'  AND date BETWEEN '" + start_date + "' AND '" + end_date + "' AND" + get_distance + "< 5.0 GROUP BY  LID, type, address, city, country, postal_code, amenities ORDER BY AVG(price) " + Rank;
            }

            //no filter and rank
            else{

                query = "SELECT LID, type, address, city, country, postal_code, amenities, AVG(price), " + get_distance + "AS distance_in_km FROM Listings NATURAL JOIN Availability WHERE" + get_distance + "< 5.0 GROUP BY  LID, type, address, city, country, postal_code, amenities ORDER BY distance_in_km ASC";
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
