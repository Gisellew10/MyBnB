import java.util.Scanner;
import java.sql.*;
import java.util.UUID;

public class RentalHistoryInput{

    private static final String CONNECTION = "jdbc:mysql://localhost:3306/mybnb"; 

    public void getRentingInfo(String RenterID) throws ClassNotFoundException
    {
        Class.forName("com.mysql.cj.jdbc.Driver");
        final String USER = "root";
        final String PASS = "giselle";

        try{

            Scanner inputLID = new Scanner(System.in);
            System.out.print("Enter the Listing ID that you would like to book: ");
            int LID = inputLID.nextInt();

            Scanner inputstartdate= new Scanner(System.in);
            System.out.print("Enter the start date of your renting(YYYY-MM-DD): ");
            String start_date_s= inputstartdate.nextLine();
            Date start_date = Date.valueOf(start_date_s);

            Scanner inputenddate = new Scanner(System.in);
            System.out.print("Enter the end date of your renting(YYYY-MM-DD): ");
            String end_date_s = inputenddate.nextLine();
            Date end_date = Date.valueOf(end_date_s);

            Scanner inputdate = new Scanner(System.in);
            System.out.print("Enter the current date(YYYY-MM-DD): ");
            String date_s = inputdate.nextLine();
            Date date = Date.valueOf(date_s);

            String query = "SELECT address, city, country, AVG(A.price), A.HostID FROM Listings L JOIN Availability A WHERE L.LID = A.LID AND A.LID = '" + LID + "' GROUP BY address, city, country, A.HostID" ;


            Connection con = null;
            ResultSet rs = null;
            con = DriverManager.getConnection(CONNECTION,USER,PASS);
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            String address = null;
            String city = null;
            String country = null;
            String HostID = null;
            double price_tmp = 0;
            int price = 0;
            boolean result = true;

            if(rs.next()){
                address = rs.getString(1);
                city = rs.getString(2);
                country = rs.getString(3);
                price_tmp = rs.getDouble(4);
                price = (int) price_tmp;
                HostID = rs.getString(5);

                String Booking_ID;
                UUID uuid = UUID.randomUUID();
                Booking_ID = "Booking"+ "-" + uuid;

                //need to check if the listing is available in the date range
                Connection con0 = null;
                ResultSet rs0 = null;
                String query0 = "SELECT availability FROM Availability WHERE LID = " + LID + " AND date BETWEEN '" + start_date + "'" + "AND '" + end_date + "'";
    
                con0 = DriverManager.getConnection(CONNECTION,USER,PASS);
                Statement stmt0 = con0.createStatement();
                rs0 = stmt0.executeQuery(query0);
    
                while(rs0.next()){
                    String availability = rs0.getString(1);
    
                    if(availability.equals("unavailable")){
                        System.out.println("Error! This listing is booked!");
                        return;
                    }
                }

                RentalHistory myRentalHistory= new RentalHistory(Booking_ID, LID, HostID, RenterID, date, address, city, country, price, start_date, end_date);
                result = myRentalHistory.createRentalHistory();
            }



            //update Availability

            String update = "UPDATE Availability SET availability = 'unavailable' WHERE LID = " + LID +" AND date BETWEEN '" + start_date + "'" + "AND '" + end_date + "'";


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