
import java.sql.*;

public class PaymentInfo{
    private static final String CONNECTION = "jdbc:mysql://localhost:3306/mybnb"; 

    private String UserID;
    private int CreditCard_Num;
    private String ExpDate;
    private String Postal_Code;
    private int CVC;
    private boolean success;

    public PaymentInfo(String UserID, int CreditCard_Num, String ExpDate, String Postal_Code, int CVC){
        this.UserID = UserID;
        this.CreditCard_Num = CreditCard_Num;
        this.ExpDate = ExpDate;
        this.Postal_Code = Postal_Code;
        this.CVC = CVC;
    }

    public boolean recordPaymentInfo(){
        final String USER = "root";
        final String PASS = "giselle";
        try{
            Connection con = null;
            String sql = "INSERT INTO Calendar (UserID, CreditCard_Num, ExpDate, Postal_Code, CVC) VALUES (?,?,?,?,?)";
            
            con = DriverManager.getConnection(CONNECTION,USER,PASS);
            PreparedStatement ps = con.prepareStatement(sql);

            con.setAutoCommit(false);
            ps.setString(1, this.UserID);
            ps.setInt(2, this.CreditCard_Num);
            ps.setString(3, this.ExpDate);
            ps.setString(4, this.Postal_Code);
            ps.setInt(5, this.CVC);

            success = ps.execute();

            con.commit();
            ps.close();
            con.close();

        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return success;
    }
    
}

