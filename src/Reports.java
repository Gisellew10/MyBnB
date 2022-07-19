import java.sql.*;
import java.util.Scanner;

public class Reports {
    private static final String CONNECTION = "jdbc:mysql://localhost:3306/mybnb"; 


    public void TotalBookingsReport() throws ClassNotFoundException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        final String USER = "root";
        final String PASS = "giselle";

        try{
            Scanner inputstartdate= new Scanner(System.in);
            System.out.print("Enter the start date (YYYY-MM-DD): ");
            String start_date_s = inputstartdate.nextLine();
            Date start_date = Date.valueOf(start_date_s);

            Scanner inputenddate = new Scanner(System.in);
            System.out.print("Enter the end date(YYYY-MM-DD): ");
            String end_date_s = inputenddate.nextLine();
            Date end_date = Date.valueOf(end_date_s);

            System.out.println();


            Connection con = null;
            ResultSet rs = null;
            String query = "SELECT city, COUNT(Booking_ID) FROM RentalHistory WHERE date BETWEEN '" + start_date + "'" + "AND '" + end_date + "' GROUP BY city";

            con = DriverManager.getConnection(CONNECTION,USER,PASS);
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(query);


            System.out.println("---Total Number Of Bookings In A Specific Date Range By City Report---");
            System.out.println();

            while(rs.next()){
                System.out.println("City: " + rs.getString(1) + "   " + "Total number of bookings: " + rs.getInt(2));
                System.out.println();
            }


            Connection con2 = null;
            ResultSet rs2 = null;
            String query2 = "SELECT postal_code, city, COUNT(Booking_ID) FROM RentalHistory  NATURAL JOIN Listings WHERE date BETWEEN '" + start_date + "'" + "AND '" + end_date + "' GROUP BY postal_code, city";

            con2 = DriverManager.getConnection(CONNECTION,USER,PASS);
            Statement stmt2 = con2.createStatement();
            rs2 = stmt2.executeQuery(query2);
            

            System.out.println("---Total Number Of Bookings In A Specific Date Range By Zip Code Within A City Report---");
            System.out.println();

            while(rs2.next()){
                System.out.println("Zip Code: " + rs2.getString(1) + "   " + "City: " + rs2.getString(2) + "   " + "Total number of bookings: " + rs2.getInt(3));
                System.out.println();
            }

            rs.close();
            con.close();

            
            rs2.close();
            con2.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }


    public void TotalListingsReport() throws ClassNotFoundException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        final String USER = "root";
        final String PASS = "giselle";

        try{
            Connection con = null;
            ResultSet rs = null;
            String query = "SELECT country, COUNT(LID) FROM Listings GROUP BY country";

            con = DriverManager.getConnection(CONNECTION,USER,PASS);
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(query);


            System.out.println("---Total Number Of Listings Per Country Report---");
            System.out.println();

            while(rs.next()){
                System.out.println("Country: " + rs.getString(1) + "   " + "Total number of listings: " + rs.getInt(2));
                System.out.println();
            }
            

            Connection con2 = null;
            ResultSet rs2 = null;
            String query2 = "SELECT city, country, COUNT(LID) FROM Listings GROUP BY city,country";

            con2 = DriverManager.getConnection(CONNECTION,USER,PASS);
            Statement stmt2 = con2.createStatement();
            rs2 = stmt2.executeQuery(query2);
            

            System.out.println("---Total Number Of Listings Per Country and City Report---");
            System.out.println();

            while(rs2.next()){
                System.out.println("City: " + rs2.getString(1) + "   "  + "Country: " + rs2.getString(2) + "   " + "Total number of listings: " + rs2.getInt(3));
                System.out.println();
            }

            Connection con3 = null;
            ResultSet rs3 = null;
            String query3 = "SELECT city, country, postal_code, COUNT(LID) FROM Listings GROUP BY city,country, postal_code";

            con3 = DriverManager.getConnection(CONNECTION,USER,PASS);
            Statement stmt3 = con3.createStatement();
            rs3 = stmt3.executeQuery(query3);
            

            System.out.println("---Total Number Of Listings Per Country, City, And Postal Code Report---");
            System.out.println();

            while(rs3.next()){
                System.out.println("City: " + rs3.getString(1) + "   " + "Country: " + rs3.getString(2) + "   " +  "Postal Code: " + rs3.getString(3) + "   "  + "Total number of listings: " + rs3.getInt(4));
                System.out.println();
            }

            rs.close();
            con.close();

            
            rs2.close();
            con2.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }



    public void RankTheHosts() throws ClassNotFoundException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        final String USER = "root";
        final String PASS = "giselle";

        try{
            Connection con = null;
            ResultSet rs = null;
            String query = "SELECT UserID, first_name, last_name, city, country, COUNT(LID) FROM Listings JOIN Users WHERE UserID = HostID GROUP BY UserID, first_name, last_name, city, country ORDER BY COUNT(LID) DESC ";

            con = DriverManager.getConnection(CONNECTION,USER,PASS);
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(query);


            System.out.println("---Rank The Hosts Report---");
            System.out.println();

            while(rs.next()){
                System.out.println("Host ID: " + rs.getString(1) + "   " + "Host Name: " + rs.getString(2) + " " +rs.getString(3) + "   " + "City: " + rs.getString(4) + "   " + "Country: " + rs.getString(5) + "   " + "Number of listings: " + rs.getInt(6)) ;
                System.out.println();
            }


            rs.close();
            con.close();

        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }



    public void IdentifyCommercialHosts() throws ClassNotFoundException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        final String USER = "root";
        final String PASS = "giselle";

        try{
            Connection con = null;
            ResultSet rs = null;
            String query = "SELECT UserID, first_name, last_name, city, country FROM Listings L1 JOIN Users WHERE UserID = HostID GROUP BY UserID, city, country HAVING (count(*) / (SELECT COUNT(LID) FROM Listings L2 WHERE L1.city = L2.city AND L1.country = L2.country))  > 0.1 ";

            con = DriverManager.getConnection(CONNECTION,USER,PASS);
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(query);


            System.out.println("---Hosts That Have A Number Of Listings That Is More Than 10% Of The Number Of Listings In That City And Country---");
            System.out.println();

            while(rs.next()){
                System.out.println("Host ID: " + rs.getString(1) + "   " + "Host Name: " + rs.getString(2) + " " +rs.getString(3) + "   " + "City: " + rs.getString(4) + "   " + "Country: " + rs.getString(5)) ;
                System.out.println();
            }


            rs.close();
            con.close();

        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }


    public void RankTheRenters(int year) throws ClassNotFoundException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        final String USER = "root";
        final String PASS = "giselle";

        try{

            Scanner inputstartdate= new Scanner(System.in);
            System.out.print("Enter the start date (YYYY-MM-DD): ");
            String start_date_s = inputstartdate.nextLine();
            Date start_date = Date.valueOf(start_date_s);

            Scanner inputenddate = new Scanner(System.in);
            System.out.print("Enter the end date(YYYY-MM-DD): ");
            String end_date_s = inputenddate.nextLine();
            Date end_date = Date.valueOf(end_date_s);


            System.out.println();

            Connection con = null;
            ResultSet rs = null;
            String query = "SELECT R.UserID, first_name, last_name, COUNT(Booking_ID) FROM RentalHistory R JOIN Users U WHERE R.UserID = U.UserID AND date BETWEEN '" + start_date + "' AND '" + end_date + "' GROUP BY UserID, first_name, last_name ORDER BY COUNT(Booking_ID) DESC";

            con = DriverManager.getConnection(CONNECTION,USER,PASS);
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(query);


            System.out.println("---Rank The Renters In A Specific Time Period Report---");
            System.out.println();

            while(rs.next()){
                System.out.println("Renter ID: " + rs.getString(1) + "   " + "Renter's Name: " + rs.getString(2) + " " +rs.getString(3) + "   " +  "Number of bookings: " + rs.getInt(4)) ;
                System.out.println();
            }

            Connection con2 = null;
            ResultSet rs2 = null;

            String query2 = "SELECT R.UserID, first_name, last_name, city, COUNT(Booking_ID) FROM RentalHistory R JOIN Users U WHERE R.UserID = U.UserID AND date BETWEEN '" + start_date + "' AND '" + end_date + "' GROUP BY R.UserID, first_name, last_name, city HAVING (SELECT COUNT(Booking_ID) FROM RentalHistory WHERE (SELECT EXTRACT(YEAR FROM date)) = " + year + ") >= 2 ORDER BY COUNT(Booking_ID) DESC";

            con2 = DriverManager.getConnection(CONNECTION,USER,PASS);
            Statement stmt2 = con2.createStatement();
            rs2 = stmt2.executeQuery(query2);


            System.out.println("---Rank The Renters In A Specific Time Per City Period Report---");
            System.out.println();

            while(rs2.next()){
                System.out.println("Renter ID: " + rs2.getString(1) + "   " + "Renter's Name: " + rs2.getString(2) + " " +rs2.getString(3) + "   " + "City: " + rs2.getString(4) + "   "+ "Number of bookings: " + rs2.getInt(5)) ;
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
