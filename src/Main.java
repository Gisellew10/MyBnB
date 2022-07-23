import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException{
        int year = 2022;

        // System.out.print("------Menu------");

        // Scanner input = new Scanner(System.in);
        // System.out.print("Enter: ");
        // String input_string = input.nextLine();

        //ask for UserID

        // while(! (input_string.equals("exit"))){

        //     System.out.print("Enter: ");
        //     input_string = input.nextLine();
        // }


        // UserInput user = new UserInput();
        // user.getUserInfo(year);

        // ListingInput listing = new ListingInput();
        // listing.getListingInfo();

        // AvailabilityInput availability = new AvailabilityInput();
        // availability.getAvailabilityInfo();

        // Update update = new Update();
        // update.UpdatePrice();

        // Delete delete = new Delete();
        // delete.DeleteUser(UserID);

        // RentalHistoryInput rental_history = new RentalHistoryInput();
        // rental_history.getRentingInfo();

        // CommentInput comment = new CommentInput();
        // comment.getCommentInfo();

        // delete.CancelBooking();

        // delete.RemoveListing();

        // Queries query = new Queries();
        // query.SearchByLatAndLon();

        // Queries query = new Queries();
        // query.SearchByPostalCode();

        // Queries query = new Queries();
        // query.SearchByAddress();

        // Reports report = new Reports();
        // report.TotalBookingsReport();
        
        // Reports report = new Reports();
        // report.TotalListingsReport();

        // Reports report = new Reports();
        // report.RankTheHosts();

        // Reports report = new Reports();
        // report.IdentifyCommercialHosts();

        // Reports report = new Reports();
        // report.RankTheRenters(year);

        // Reports report = new Reports();
        // report.LargestCancellationReport(year);

        Reports report = new Reports();
        report.MostPopularNP();




    }
    
}
