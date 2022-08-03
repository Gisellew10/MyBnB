import java.sql.Date;
import java.util.Scanner;
import java.sql.*;


public class CommentInput{
    private static final String CONNECTION = "jdbc:mysql://localhost:3306/mybnb"; 

    public void getCommentInfo(String Reviewer_ID) throws ClassNotFoundException
    {
        Class.forName("com.mysql.cj.jdbc.Driver");
        final String USER = "root";
        final String PASS = "giselle";
        
        try{
    
            Scanner inputLID= new Scanner(System.in);
            System.out.print("Enter the Listing ID that you would like to comment: ");
            int LID = inputLID.nextInt();
    
            Scanner inputListingComments = new Scanner(System.in);
            System.out.print("Enter your comment: ");
            String Listing_Comments = inputListingComments.nextLine();
    
            Scanner inputListingRate = new Scanner(System.in);
            System.out.print("Please rate this listing (1-5): ");
            int Listing_Rate = inputListingRate.nextInt();
    
            Scanner inputReviewe = new Scanner(System.in);
            System.out.print("Enter the User ID that you would like to comment: ");
            String Reviewe_ID = inputReviewe.nextLine();
    
            Scanner inputUserComments= new Scanner(System.in);
            System.out.print("Enter your comment: ");
            String User_Comments = inputUserComments.nextLine();
    
            Scanner inputRevieweRate = new Scanner(System.in);
            System.out.print("Please rate this user (1-5): ");
            int Reviewe_Rate = inputRevieweRate.nextInt();
    
            Scanner inputdate = new Scanner(System.in);
            System.out.print("Enter the current date(YYYY-MM-DD): ");
            String date_s = inputdate.nextLine();
            Date date = Date.valueOf(date_s);
    
            String query = "SELECT min(start_date), RenterID, HostID FROM RentalHistory WHERE LID = '" + LID + "' GROUP BY RenterID, HostID" ;
    
    
            Connection con = null;
            ResultSet rs = null;
            con = DriverManager.getConnection(CONNECTION,USER,PASS);
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(query);
    
            Date start_date_s= null;
            String HostID = null;
            String RenterID = null;
    
            if(rs.next()){
                start_date_s = rs.getDate(1);
                RenterID = rs.getString(2);
                HostID = rs.getString(3);
            }

            String start_date = String.valueOf(start_date_s);
            boolean result = true;

            if(date_s.compareTo(start_date) > 0 && (RenterID.equals(Reviewe_ID) || RenterID.equals(Reviewer_ID)) && (HostID.equals(Reviewe_ID) || HostID.equals(Reviewer_ID))){
                InsertComment mycomment = new InsertComment(LID, Reviewe_ID, Reviewer_ID, User_Comments, Listing_Comments, Listing_Rate, Reviewe_Rate, date);
                result = mycomment.createComment();
            }else{
                System.out.println();
                System.out.println("Error! Reviewer can only comment on the Reviewe/Listing that has rented the listed place for a completed stay recently.");
                return;
            }
    
    
            if(!result){
                System.out.println("Comment was successfully added!");
            }else{
                return;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }

    }
}