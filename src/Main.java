public class Main {
    public static void main(String[] args) throws ClassNotFoundException{
        int year = 2022;
        // UserInput user = new UserInput();
        // user.getUserInfo();

        // ListingInput listing = new ListingInput();
        // listing.getListingInfo();

        // AvailabilityInput availability = new AvailabilityInput();
        // availability.getAvailabilityInfo();

        // Delete delete = new Delete();
        // delete.DeleteUser();

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

        Reports report = new Reports();
        report.RankTheRenters(year);




    }
    
}
