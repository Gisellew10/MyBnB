import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException{
        int year = 2022;

        System.out.println();
        System.out.println("Hello and Welcome to Airbnb!");
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
            System.out.println("------Enter exit if you want to sign out------");

            Scanner input = new Scanner(System.in);
            System.out.print("Please Enter your choice: ");
            String input_string = input.nextLine();


            while(! (input_string.equals("exit"))){
                if(input_string.equals("TB")){
                    Reports report = new Reports();
                    report.TotalBookingsReport();
                }else if(input_string.equals("TL")){
                    Reports report = new Reports();
                    report.TotalListingsReport();
                }else if(input_string.equals("RH")){
                    Reports report = new Reports();
                    report.RankTheHosts();
                }else if(input_string.equals("IC")){
                    Reports report = new Reports();
                    report.IdentifyCommercialHosts();
                }else if(input_string.equals("RR")){
                    Reports report = new Reports();
                    report.RankTheRenters(year);
                }else if(input_string.equals("C")){
                    Reports report = new Reports();
                    report.LargestCancellationReport(year);
                }else if(input_string.equals("NP")){
                    Reports report = new Reports();
                    report.MostPopularNP();
                }

                System.out.print("Please Enter your choice: ");
                input_string = input.nextLine();
            }

            System.out.println("Bye!");
            System.out.println();
        }

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
            System.out.println("------Menu------");
            System.out.println("1. Create a new listing(CL)");
            System.out.println("2. Remove a listing(RL)");
            System.out.println("3. Cancel a booking(CB)");
            System.out.println("4. Insert new availability(IA)");
            System.out.println("5. Update availability(UAV)");
            System.out.println("6. Update listing price(UP)");
            System.out.println("7. Update listing amenities(UA)");
            System.out.println("8. Comment and rate a renter(C)");
            System.out.println("9. Rental History(R)");
            System.out.println("10. List of listings(L)");
            System.out.println("11. Delete your account(D)");
            System.out.println("------Enter exit if you want to sign out------");

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
                }else if(input_string.equals("UAV")){
                    Update update = new Update();
                    update.UpdateAvailability(UserID);
                }else if(input_string.equals("UP")){
                    Update update = new Update();
                    update.UpdatePrice(UserID);
                }else if(input_string.equals("UA")){
                    Update update = new Update();
                    update.UpdateAmenities(UserID);
                }else if(input_string.equals("C")){
                    CommentInput comment = new CommentInput();
                    comment.getCommentInfo(UserID);
                }else if(input_string.equals("R")){
                    Retrieve retrieve = new Retrieve();
                    retrieve.ShowRentalHistory(UserID);
                }else if(input_string.equals("L")){
                    Retrieve retrieve = new Retrieve();
                    retrieve.ShowListings(UserID);
                }else if(input_string.equals("D")){
                    Delete delete = new Delete();
                    delete.DeleteUser(UserID);
                }

                System.out.print("Please Enter your choice: ");
                input_string = input.nextLine();
            }

            System.out.println("Bye!");
            System.out.println();
        }else if(tokens[0].equals("Renter")){
            System.out.println("------Menu------");
            System.out.println("1. Book a listing(BL)");
            System.out.println("2. Search for listings by latitude and longtitude(SL)");
            System.out.println("3. Search for listings by postal code(SP)");
            System.out.println("4. Exact search by address(SA)");
            System.out.println("5. Cancel a booking(CP)");
            System.out.println("6. Comment and rate a listing and host(C)");
            System.out.println("7. Rental History(R)");
            System.out.println("8. Delete your account(D)");
            System.out.println("------Enter exit if you want to sign out------");

            Scanner input = new Scanner(System.in);
            System.out.print("Please Enter your choice: ");
            String input_string = input.nextLine();


            while(! (input_string.equals("exit"))){
                if(input_string.equals("BL")){
                    RentalHistoryInput rental_history = new RentalHistoryInput();
                    rental_history.getRentingInfo(UserID);
                }else if(input_string.equals("SL")){
                    Queries search = new Queries();
                    search.SearchByLatAndLon();
                }else if(input_string.equals("SP")){
                    Queries search = new Queries();
                    search.SearchByPostalCode();
                }else if(input_string.equals("SA")){
                    Queries search = new Queries();
                    search.SearchByAddress();
                }else if(input_string.equals("CB")){
                    Delete delete = new Delete();
                    delete.CancelBooking(UserID);
                }else if(input_string.equals("C")){
                    CommentInput comment = new CommentInput();
                    comment.getCommentInfo(UserID);
                }else if(input_string.equals("R")){
                    Retrieve retrieve = new Retrieve();
                    retrieve.ShowRentalHistory(UserID);
                }else if(input_string.equals("D")){
                    Delete delete = new Delete();
                    delete.DeleteUser(UserID);
                }

                System.out.print("Please Enter your choice: ");
                input_string = input.nextLine();
            }

            System.out.println("Bye!");
            System.out.println();
        }








        // Reports report = new Reports();
        // report.RankTheRenters(year);

        // Reports report = new Reports();
        // report.LargestCancellationReport(year);

        // Reports report = new Reports();
        // report.MostPopularNP();




    }
    
}
