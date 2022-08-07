
import java.sql.*;

public class InsertComment{
    private static final String CONNECTION = "jdbc:mysql://localhost:3306/mybnb"; 

    private int LID;
    private String Reviewe_ID; //person who receive comments;
    private String Reviewer_ID; //person who writes comments
    private String Listing_Comments;
    private String User_Comments;
    int Listing_Rate;
    int Reviewe_Rate;
    Date date;
    private boolean success;

    public InsertComment(int LID, String Reviewe_ID, String Reviewer_ID, String User_Comments, String Listing_Comments, int Listing_Rate, int Reviewe_Rate, Date date){
        this.LID = LID;
        this.Reviewe_ID = Reviewe_ID;
        this.Reviewer_ID = Reviewer_ID;
        this.User_Comments = User_Comments;
        this.Listing_Comments = Listing_Comments;
        this.Listing_Rate = Listing_Rate;
        this.Reviewe_Rate = Reviewe_Rate;
        this.date = date;
    }

    public boolean createComment(){
        final String USER = "root";
        final String PASS = "giselle";
        try{
            Connection con = null;
            String sql = null;
            if(Listing_Rate == -1 && Reviewe_Rate != -1){
                sql = "INSERT INTO Comments (LID, Reviewe_ID, Reviewer_ID, User_Comments, Listing_Comments, Listing_Rate, Reviewe_Rate, date) VALUES (?,?,?,?,?,NULL,?,?)";
                con = DriverManager.getConnection(CONNECTION,USER,PASS);
                PreparedStatement ps = con.prepareStatement(sql);
    
                con.setAutoCommit(false);
                ps.setInt(1, this.LID);
                ps.setString(2, this.Reviewe_ID);
                ps.setString(3, this.Reviewer_ID);
                ps.setString(4, this.User_Comments);
                ps.setString(5, this.Listing_Comments);
                ps.setInt(6, this.Reviewe_Rate);
                ps.setDate(7, this.date);
    
                success = ps.execute();
            }else if(Listing_Rate != -1 && Reviewe_Rate == -1){
                sql = "INSERT INTO Comments (LID, Reviewe_ID, Reviewer_ID, User_Comments, Listing_Comments, Listing_Rate, Reviewe_Rate, date) VALUES (?,?,?,?,?,?,NULL,?)";

                con = DriverManager.getConnection(CONNECTION,USER,PASS);
                PreparedStatement ps = con.prepareStatement(sql);
    
                con.setAutoCommit(false);
                ps.setInt(1, this.LID);
                ps.setString(2, this.Reviewe_ID);
                ps.setString(3, this.Reviewer_ID);
                ps.setString(4, this.User_Comments);
                ps.setString(5, this.Listing_Comments);
                ps.setInt(6, this.Listing_Rate);
                ps.setDate(7, this.date);
    
                success = ps.execute();
            }else if(Listing_Rate == -1 && Reviewe_Rate == -1){
                sql = "INSERT INTO Comments (LID, Reviewe_ID, Reviewer_ID, User_Comments, Listing_Comments, Listing_Rate, Reviewe_Rate, date) VALUES (?,?,?,?,?,NULL,NULL,?)";

                con = DriverManager.getConnection(CONNECTION,USER,PASS);
                PreparedStatement ps = con.prepareStatement(sql);
    
                con.setAutoCommit(false);
                ps.setInt(1, this.LID);
                ps.setString(2, this.Reviewe_ID);
                ps.setString(3, this.Reviewer_ID);
                ps.setString(4, this.User_Comments);
                ps.setString(5, this.Listing_Comments);
                ps.setDate(6, this.date);
    
                success = ps.execute();
            }else{
                sql = "INSERT INTO Comments (LID, Reviewe_ID, Reviewer_ID, User_Comments, Listing_Comments, Listing_Rate, Reviewe_Rate, date) VALUES (?,?,?,?,?,?,?,?)";

                con = DriverManager.getConnection(CONNECTION,USER,PASS);
                PreparedStatement ps = con.prepareStatement(sql);
    
                con.setAutoCommit(false);
                ps.setInt(1, this.LID);
                ps.setString(2, this.Reviewe_ID);
                ps.setString(3, this.Reviewer_ID);
                ps.setString(4, this.User_Comments);
                ps.setString(5, this.Listing_Comments);
                ps.setInt(6, this.Listing_Rate);
                ps.setInt(7, this.Reviewe_Rate);
                ps.setDate(8, this.date);
    
                success = ps.execute();
            }
            

            String update = "UPDATE Comments SET Listing_Comments = NULL WHERE Listing_Comments = ' '";
    
            Connection con2 = null;
            con2 = DriverManager.getConnection(CONNECTION,USER,PASS);
            Statement stmt2 = con2.createStatement();
            stmt2.executeUpdate(update);

            con.commit();
            con.close();

        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return success;
    }
    
}


