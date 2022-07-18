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
            System.out.print("Enter the start date (dd/mm/yyyy): ");
            String start_date = inputstartdate.nextLine();

            Scanner inputenddate = new Scanner(System.in);
            System.out.print("Enter the end date(dd/mm/yyyy): ");
            String end_date = inputenddate.nextLine();

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
                System.out.println("City: " + rs.getString(1) + "   " + "Total number of bookings: " + rs.getString(2));
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
                System.out.println("Zip Code: " + rs2.getString(1) + "   " + "City: " + rs2.getString(2) + "   " + "Total number of bookings: " + rs2.getString(3));
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
                System.out.println("Country: " + rs.getString(1) + "   " + "Total number of listings: " + rs.getString(2));
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
                System.out.println("City: " + rs2.getString(1) + "   "  + "Country: " + rs2.getString(2) + "   " + "Total number of listings: " + rs2.getString(3));
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
                System.out.println("City: " + rs3.getString(1) + "   " + "Country: " + rs3.getString(2) + "   " +  "Postal Code: " + rs3.getString(3) + "   "  + "Total number of listings: " + rs3.getString(4));
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
}
