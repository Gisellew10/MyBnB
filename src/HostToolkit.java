import java.sql.*;

public class HostToolkit {
    private static final String CONNECTION = "jdbc:mysql://localhost:3306/mybnb"; 

    public double SuggestPrice(int LID) throws ClassNotFoundException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        final String USER = "root";
        final String PASS = "giselle";
        double suggest_price = 0;

        try{
            String sql = "SELECT bedroom, bathroom, type, city FROM Listings WHERE LID = '" + LID + "'";
            Connection con = null;
            ResultSet rs = null;

            con = DriverManager.getConnection(CONNECTION,USER,PASS);
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            if(rs.next()){
                int bedroom = rs.getInt(1);
                int bathroom = rs.getInt(2);
                String type = rs.getString(3);
                String city = rs.getString(4);

                String sql2 = "SELECT price FROM PriceData WHERE city = '" + city + "' AND type = '" + type + "'";
                Connection con2 = null;
                ResultSet rs2 = null;
    
                con2 = DriverManager.getConnection(CONNECTION,USER,PASS);
                Statement stmt2 = con.createStatement();
                rs2 = stmt2.executeQuery(sql2);

                if(rs2.next()){
                    double base_price = rs.getDouble(1);
                    //increase one bedroom will increase 20% of price
                    //increase one bathroom will increase 10% of price
                    suggest_price = base_price + (base_price * 0.2) * (bedroom -1) + (base_price * 0.1) * (bathroom -1);
                }
            }
        }

        catch(SQLException e){
            e.printStackTrace();
        }
        return suggest_price;
    }


    public void SuggestAmenities(String type, String check, int LID) throws ClassNotFoundException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        final String USER = "root";
        final String PASS = "giselle";

        try{
            String sql = null;
            if(check.equals("Insert")){
                sql = "SELECT amenities, price FROM AmenitiesData WHERE type = '" + type + "'";
            }else if(check.equals("Update")){
                sql = "SELECT amenities, price FROM AmenitiesData WHERE type = '" + type + "' AND amenities NOT IN (SELECT amenities FROM Listings WHERE LID = '" + LID + "')" ;
            }
            Connection con = null;
            ResultSet rs = null;

            con = DriverManager.getConnection(CONNECTION,USER,PASS);
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(sql);

            System.out.println();
            System.out.println("---Suggested Amenities And Expected Increased Revenue---");

            while(rs.next()){
                System.out.println("amenities: " + rs.getString(1) + "   " + "price: " + rs.getDouble((2)));
                System.out.println();
            }
        }

        catch(SQLException e){
            e.printStackTrace();
        }
    }
    
}
