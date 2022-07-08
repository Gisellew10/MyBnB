import java.sql.*;

public class Main {
    private static final String CONNECTION = "jdbc:mysql://localhost:3306/mydb"; 
    public static void main(String[] args) throws ClassNotFoundException
    {
        Class.forName("com.mysql.cj.jdbc.Driver");
        final String USER = "root";
        final String PASS = "giselle";
        System.out.println("Connecting to database...");

        try
        {
            Connection con=DriverManager.getConnection(CONNECTION,USER,PASS);
            System.out.println("Successsfully connected to MySQL!");

            Statement stmt = con.createStatement();
            String sql = "SELECT * FROM Student;"; // need to change later
            ResultSet rs = stmt.executeQuery(sql);
            
            int count = 0;

            while(rs.next())
            {
                String firstName = rs.getString(2);
                String surName = rs.getString(3);
                count++;
                System.out.println("Student" + count + ":" + firstName + " " + surName);
            }
            con.close();
        }
        catch(Exception e)
        {
            System.out.print(e);
        }
    }
}