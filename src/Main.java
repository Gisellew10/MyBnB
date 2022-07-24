import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException{
        int year = 2022;

        System.out.println();
        System.out.print("Hello and Welcome to Airbnb!");
        System.out.println();
        System.out.print("1. Create an Account");
        System.out.print("2. Login to Existing Account");
        
        Scanner create = new Scanner(System.in);
        System.out.print("Please Enter your choice: ");
        int create_input = create.nextInt();

        if(create_input == 1){
            UserInput user = new UserInput();
            user.getUserInfo(year);
        }

        Scanner login = new Scanner(System.in);
        System.out.print("Please Enter your User ID: ");
        String UserID = login.nextLine();

        String copy = UserID;
        String[] tokens = copy.split("-");

        if(tokens[0].equals("Host")){
            System.out.print("------Menu------");
            System.out.print("1. Create a new listing");
            System.out.print("2. Remove a listing");
            System.out.print("3. Cancel a booking");
            System.out.print("4. Insert new availability");
            System.out.print("5. Update availability");
            System.out.print("6. Update listing price");
            System.out.print("7. Update listing amenities");
            System.out.print("8. Comment and rate a renter");
            System.out.print("9. Delete your account");
            System.out.print("------Enter exit if you want to sign out------");

            Scanner input = new Scanner(System.in);
            System.out.print("Please Enter your choice: ");
            String input_string = input.nextLine();


            while(! (input_string.equals("exit"))){
                if(input_string.equals("CL")){
                    ListingInput listing = new ListingInput();
                    listing.getListingInfo(UserID);
                }else if(input_string.equals("RL")){
                    Delete delete = new Delete();
                    delete.RemoveListing(UserID);
                }else if(input_string.equals("CB")){
                    Delete delete = new Delete();
                    delete.CancelBooking(UserID);
                }else if(input_string.equals("IA")){
                    AvailabilityInput availability = new AvailabilityInput();
                    availability.getAvailabilityInfo(UserID);
                }



                System.out.print("Please Enter your choice: ");
                input_string = input.nextLine();
            }
            
        }







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

        // Reports report = new Reports();
        // report.MostPopularNP();




    }
    
}
