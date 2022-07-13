import java.util.Scanner;


public class CommentInput{
    public void getCommentInfo() throws ClassNotFoundException
    {
        Scanner inputReviewer = new Scanner(System.in);
        System.out.print("Enter your User ID: ");
        String Reviewer_ID = inputReviewer.nextLine();

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

        InsertComment mycomment = new InsertComment(LID, Reviewe_ID, Reviewer_ID, User_Comments, Listing_Comments, Listing_Rate, Reviewe_Rate);
        boolean result = mycomment.createComment();


        if(!result){
            System.out.println("Comment was successfully added!");
        }else{
            return;
        }

    }
}