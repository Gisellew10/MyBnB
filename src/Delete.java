import java.util.Scanner;
import java.sql.*;

public class Delete {

    private static final String CONNECTION = "jdbc:mysql://localhost:3306/mybnb"; 

    final String USER = "root";
    final String PASS = "giselle";
    private boolean success;

    public boolean DeleteUser(){
        Scanner inputUserType = new Scanner(System.in);
        System.out.print("Enter the User ID that you want to delete: ");
        String UID = inputUserType.nextLine();

        try{
            Connection con = null;
            String sql = "DELETE FROM Users WHERE UserID = '" + UID +"'";
            
            con = DriverManager.getConnection(CONNECTION,USER,PASS);
            PreparedStatement ps = con.prepareStatement(sql);

            System.out.println("User was successfully deleted!");

            success = ps.execute();

            ps.close();
            con.close();
            inputUserType.close();

        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return success;
    }
    
}
