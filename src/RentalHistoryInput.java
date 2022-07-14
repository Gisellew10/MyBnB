import java.util.Scanner;
import java.sql.*;
import java.util.UUID;

public class RentalHistoryInput{

    private static final String CONNECTION = "jdbc:mysql://localhost:3306/mybnb"; 

    public void getRentingInfo() throws ClassNotFoundException
    {
        Class.forName("com.mysql.cj.jdbc.Driver");
        final String USER = "root";
        final String PASS = "giselle";

        try{
                
            Scanner inputUID = new Scanner(System.in);
            System.out.print("Enter your User ID: ");
            String UserID = inputUID.nextLine();

            Scanner inputLID = new Scanner(System.in);
            System.out.print("Enter the Listing ID that you would like to book: ");
            int LID = inputLID.nextInt();

            Scanner inputstartdate= new Scanner(System.in);
            System.out.print("Enter the start date of your renting: ");
            String start_date= inputstartdate.nextLine();

            Scanner inputenddate = new Scanner(System.in);
            System.out.print("Enter the end date of your renting: ");
            String end_date = inputenddate.nextLine();

            Scanner inputdate = new Scanner(System.in);
            System.out.print("Enter the current date: ");
            String date = inputdate.nextLine();

            String query = "SELECT address, city, country, price FROM Listings NATURAL JOIN Availability WHERE LID = '" + LID + "'";


            Connection con = null;
            ResultSet rs = null;
            con = DriverManager.getConnection(CONNECTION,USER,PASS);
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            String address = null;
            String city = null;
            String country = null;
            float price = 0;
            boolean result = true;

            if(rs.next()){
                address = rs.getString(1);
                city = rs.getString(2);
                country = rs.getString(3);
                price = rs.getFloat(4);

                String Booking_ID;
                UUID uuid = UUID.randomUUID();
                Booking_ID = "Booking"+ "-" + uuid;

                //need to have 'check' to check the listing is available

                RentalHistory myRentalHistory= new RentalHistory(Booking_ID, LID, UserID, date, address, city, country, price, start_date, end_date);
                result = myRentalHistory.createRentalHistory();
            }



            //update Availability

            String update = "UPDATE Availability SET availability = 'unavailable' WHERE date BETWEEN '" + start_date + "'" + "AND '" + end_date + "'";


            Connection con2 = null;
            con2 = DriverManager.getConnection(CONNECTION,USER,PASS);
            Statement stmt2 = con2.createStatement();
            stmt2.executeUpdate(update);


            if(!result){
                System.out.println("Booking was successfully added!");
            }else{
                return;
            }
        }

        catch(SQLException e){
            e.printStackTrace();
        }

    }
}