import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, IOException{

        System.out.println();
        System.out.println("Hello and Welcome to Airbnb!");
        System.out.println();
        
        Scanner yearInput = new Scanner(System.in);
        System.out.print("Please Enter the year: ");
        int year = yearInput.nextInt();
        System.out.println();

        System.out.println("1. Create an Account");
        System.out.println("2. Login to Existing Account");
        System.out.println("3. Run reports");
        
        Scanner create = new Scanner(System.in);
        System.out.print("Please Enter your choice: ");
        int create_input = create.nextInt();

        if(create_input == 3){
            System.out.println("------Menu------");
            System.out.println("1. Total number of bookings in a specific date range");
            System.out.println("2. Total number of listings");
            System.out.println("3. Rank the hosts by the total number of listings");
            System.out.println("4. Hosts that have a number of listings that is more than 10% of the numer of listings in that city and country");
            System.out.println("5. Rank the renters by the number of bookings in a specific time period");
            System.out.println("6. Hosts and renters with the largest number of cancellations within a year");
            System.out.println("7. Most popular noun phrases associated with each listing");
            System.out.println("------Enter 0 if you want to sign out------");

            Scanner input = new Scanner(System.in);
            System.out.print("Please Enter your choice: ");
            int input_string = input.nextInt();
            System.out.println();


            while(input_string != 0){
                if(input_string == 1){
                    Reports report = new Reports();
                    report.TotalBookingsReport();
                }else if(input_string == 2){
                    Reports report = new Reports();
                    report.TotalListingsReport();
                }else if(input_string == 3){
                    Reports report = new Reports();
                    report.RankTheHosts();
                }else if(input_string == 4){
                    Reports report = new Reports();
                    report.IdentifyCommercialHosts();
                }else if(input_string == 5){
                    Reports report = new Reports();
                    report.RankTheRenters(year);
                }else if(input_string == 6){
                    Reports report = new Reports();
                    report.LargestCancellationReport(year);
                }else if(input_string == 7){
                    Reports report = new Reports();
                    report.MostPopularNP();
                }

                System.out.print("Please Enter your choice: ");
                if(input.hasNextLine()){
                    input_string = input.nextInt();
                }
            }

            System.out.println("Bye!");
            System.out.println();
            return;
        }

        if(create_input == 1){
            UserInput user = new UserInput();
            user.getUserInfo(year);
        }

        System.out.println();
        System.out.println("-----Login-----");
        String UserID = null;
        Scanner ID = new Scanner(System.in);
        System.out.print("Please Enter your User ID: ");
        if(ID.hasNextLine()){
            UserID = ID.nextLine();
        }

        String copy = UserID;
        String[] tokens = copy.split("-");

        if(tokens[0].equals("Host")){
            System.out.println();
            System.out.println("------Menu------");
            System.out.println("1. Create a new listing");
            System.out.println("2. Remove a listing");
            System.out.println("3. Cancel a booking");
            System.out.println("4. Insert new availability");
            System.out.println("5. Update availability");
            System.out.println("6. Update listing price");
            System.out.println("7. Update listing amenities");
            System.out.println("8. Comment and rate a renter");
            System.out.println("9. Rental History");
            System.out.println("10. List of listings");
            System.out.println("11. Delete your account");
            System.out.println("------Enter 0 if you want to sign out------");

            Scanner input = new Scanner(System.in);
            System.out.print("Please Enter your choice: ");
            int input_string = input.nextInt();
            System.out.println();


            while(input_string != 0){
                if(input_string == 1){
                    ListingInput listing = new ListingInput();
                    listing.getListingInfo(UserID);

                    AvailabilityInput availability = new AvailabilityInput();
                    availability.getAvailabilityInfo(UserID);
                }else if(input_string == 2){
                    Scanner inputLID = new Scanner(System.in);
                    System.out.print("Enter the Listing ID that you would like to remove: ");
                    String LID = inputLID.nextLine();

                    Delete delete = new Delete();
                    delete.RemoveListing(UserID, LID);
                }else if(input_string == 3){
                    Scanner inputBooking = new Scanner(System.in);
                    System.out.print("Enter the Booking ID that you would like to cancel: ");
                    String Booking_ID = inputBooking.nextLine();

                    Scanner inputdate = new Scanner(System.in);
                    System.out.print("Enter today's date: ");
                    String cancellation_date_s = inputdate.nextLine();

                    Delete delete = new Delete();
                    delete.CancelBooking(UserID, Booking_ID, cancellation_date_s);
                }else if(input_string == 4){
                    AvailabilityInput availability = new AvailabilityInput();
                    availability.getAvailabilityInfo(UserID);
                }else if(input_string == 5){
                    Update update = new Update();
                    update.UpdateAvailability(UserID);
                }else if(input_string == 6){
                    Update update = new Update();
                    update.UpdatePrice(UserID);
                }else if(input_string == 7){
                    Update update = new Update();
                    update.UpdateAmenities(UserID);
                }else if(input_string == 8){
                    CommentInput comment = new CommentInput();
                    comment.getCommentInfo(UserID);
                }else if(input_string == 9){
                    Retrieve retrieve = new Retrieve();
                    retrieve.ShowRentalHistory(UserID);
                }else if(input_string == 10){
                    Retrieve retrieve = new Retrieve();
                    retrieve.ShowListings(UserID);
                }else if(input_string == 11){
                    Scanner inputdate = new Scanner(System.in);
                    System.out.print("Enter today's date: ");
                    String delete_date = inputdate.nextLine();

                    Delete delete = new Delete();
                    delete.DeleteUser(UserID, delete_date);
                }

                System.out.println();
                System.out.print("Please Enter your choice: ");
                if(input.hasNextLine()){
                    input_string = input.nextInt();
                }
            }

            System.out.println("Bye!");
            System.out.println();
        }else if(tokens[0].equals("Renter")){
            System.out.println();
            System.out.println("------Menu------");
            System.out.println("1. Book a listing");
            System.out.println("2. Search for listings by latitude and longtitude");
            System.out.println("3. Search for listings by postal code");
            System.out.println("4. Exact search by address");
            System.out.println("5. Cancel a booking");
            System.out.println("6. Comment and rate a listing and host");
            System.out.println("7. Rental History");
            System.out.println("8. Delete your account");
            System.out.println("------Enter exit if you want to sign out------");

            Scanner input = new Scanner(System.in);
            System.out.print("Please Enter your choice: ");
            int input_string = input.nextInt();
            System.out.println();


            while(input_string != 0){
                if(input_string == 1){
                    RentalHistoryInput rental_history = new RentalHistoryInput();
                    rental_history.getRentingInfo(UserID);
                }else if(input_string == 2){
                    Queries search = new Queries();
                    search.SearchByLatAndLon();
                }else if(input_string == 3){
                    Queries search = new Queries();
                    search.SearchByPostalCode();
                }else if(input_string == 4){
                    Queries search = new Queries();
                    search.SearchByAddress();
                }else if(input_string == 5){
                    Scanner inputBooking = new Scanner(System.in);
                    System.out.print("Enter the Booking ID that you would like to cancel: ");
                    String Booking_ID = inputBooking.nextLine();

                    Scanner inputdate = new Scanner(System.in);
                    System.out.print("Enter today's date: ");
                    String cancellation_date_s = inputdate.nextLine();

                    Delete delete = new Delete();
                    delete.CancelBooking(UserID, Booking_ID, cancellation_date_s);

                }else if(input_string == 6){
                    CommentInput comment = new CommentInput();
                    comment.getCommentInfo(UserID);
                }else if(input_string == 7){
                    Retrieve retrieve = new Retrieve();
                    retrieve.ShowRentalHistory(UserID);
                }else if(input_string == 8){
                    Scanner inputdate = new Scanner(System.in);
                    System.out.print("Enter today's date: ");
                    String delete_date = inputdate.nextLine();

                    Delete delete = new Delete();
                    delete.DeleteUser(UserID, delete_date);
                }

                System.out.println();
                System.out.print("Please Enter your choice: ");
                if(input.hasNextLine()){
                    input_string = input.nextInt();
                }
            }

            System.out.println("Bye!");
            System.out.println();
        }







    }
    
}
