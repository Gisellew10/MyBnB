import java.sql.*;

public class HostToolkit {
    private static final String CONNECTION = "jdbc:mysql://localhost:3306/mybnb"; 

    public void SuggestPrice(int LID) throws ClassNotFoundException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        final String USER = "root";
        final String PASS = "giselle";

        try{
            String sql = "SELECT bedroom, bathroom, city, country, amenities FROM Listings WHERE LID = '" + LID + "'";
            Connection con = null;
            ResultSet rs = null;

            con = DriverManager.getConnection(CONNECTION,USER,PASS);
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            if(rs.next()){
                int bedroom = rs.getInt(1);
                int bathroom = rs.getInt(2);
                String city = rs.getString(3);
                String country = rs.getString(4);
                String amenities = rs.getString(5);

                String sql2 = "SELECT price FROM PriceData WHERE city = '" + city + "' AND country = '" + country + "'";
                Connection con2 = null;
                ResultSet rs2 = null;
    
                con2 = DriverManager.getConnection(CONNECTION,USER,PASS);
                Statement stmt2 = con.createStatement();
                rs2 = stmt2.executeQuery(sql2);

                int suggest_price = 0;

                if(rs2.next()){
                    int base_price = rs2.getInt(1);
                    //increase one bedroom will increase 20% of price
                    //increase one bathroom will increase 10% of price
                    //for every amenities you add, you increased 5% of price
                    suggest_price = (int) (base_price + (base_price * 0.2) * (bedroom -1) + (base_price * 0.1) * (bathroom -1));

                    String[] tokens = amenities.split(",");
                    int increased_revenue = 0;
                    for(String t : tokens){
                        increased_revenue = (int) (increased_revenue + (suggest_price * 0.05));
                    }

                    suggest_price = suggest_price + increased_revenue;

                    System.out.println();
                    System.out.println("Suggested price: $" + suggest_price);
                }
            }
        }

        catch(SQLException e){
            e.printStackTrace();
        }
        return;
    }


    public void SuggestAmenities(String type, String check, int LID) throws ClassNotFoundException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        final String USER = "root";
        final String PASS = "giselle";

        try{
            if(check.equals("Insert")){
                String sql = "SELECT amenities FROM AmenitiesData WHERE type = '" + type + "'";

                Connection con = null;
                ResultSet rs = null;
    
                con = DriverManager.getConnection(CONNECTION,USER,PASS);
                Statement stmt = con.createStatement();
                rs = stmt.executeQuery(sql);
    
                System.out.println();
                System.out.println("---Suggested Amenities---");
    
                while(rs.next()){
                    System.out.println("amenities: " + rs.getString(1));
                }
                System.out.println();
            }else if(check.equals("Update")){

                Connection con0 = null;
                con0 = DriverManager.getConnection(CONNECTION,USER,PASS);
                Statement stmt0 = con0.createStatement();
    
                String sql = "CREATE TABLE AmenitiesTemp (amenities varchar(30));";
                stmt0.executeUpdate(sql);


                String sql1 = "SELECT amenities FROM Listings WHERE LID = " + LID;

                Connection con1 = null;
                ResultSet rs1 = null;
    
                con1 = DriverManager.getConnection(CONNECTION,USER,PASS);
                Statement stmt1 = con1.createStatement();
                rs1 = stmt1.executeQuery(sql1);
                String amenities = null;
                if(rs1.next()){
                    amenities = rs1.getString(1);
                }

                String[] tokens = amenities.split(",");

                Connection con2 = null;
                con2 = DriverManager.getConnection(CONNECTION,USER,PASS);
                Statement stmt2 = con2.createStatement();


                for(String t : tokens){
                    stmt2.executeUpdate("INSERT INTO AmenitiesTemp VALUES ('" + t + "')"); 
                }

                String sql3 = "SELECT amenities FROM AmenitiesData WHERE type = '" + type + "' AND amenities NOT IN(SELECT amenities FROM AmenitiesTemp)" ;

                Connection con3 = null;
                ResultSet rs3 = null;
    
                con3 = DriverManager.getConnection(CONNECTION,USER,PASS);
                Statement stmt3 = con3.createStatement();
                rs3 = stmt3.executeQuery(sql3);
    
                System.out.println();
                System.out.println("---Suggested Amenities---");
    
                while(rs3.next()){
                    System.out.println("amenities: " + rs3.getString(1));
                }
                System.out.println();


                Connection con4 = null;
                con4 = DriverManager.getConnection(CONNECTION,USER,PASS);
                Statement stmt4 = con4.createStatement();
    
                String sql4 = "DROP TABLE AmenitiesTemp;";
                stmt4.executeUpdate(sql4);
            }
        }

        catch(SQLException e){
            e.printStackTrace();
        }
    }
    
}
