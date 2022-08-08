import java.util.Scanner;
import java.sql.*;

public class ListingInput {
    private static final String CONNECTION = "jdbc:mysql://localhost:3306/mybnb"; 

    public void getListingInfo(String HostID) throws ClassNotFoundException
    {
        Class.forName("com.mysql.cj.jdbc.Driver");
        final String USER = "root";
        final String PASS = "giselle";
        try{
            Scanner inputtitle = new Scanner(System.in);
            System.out.print("Enter the title: ");
            String title = inputtitle.nextLine();

            Scanner inputtype = new Scanner(System.in);
            System.out.print("Enter the type: ");
            String type = inputtype.nextLine();

            Scanner inputlatitude = new Scanner(System.in);
            System.out.print("Enter the latitude: ");
            double latitude = inputtype.nextDouble();

            Scanner inputlongitude = new Scanner(System.in);
            System.out.print("Enter the longitude: ");
            double longitude = inputlongitude.nextDouble();

            Scanner inputaddress = new Scanner(System.in);
            System.out.print("Enter the address: ");
            String address = inputaddress.nextLine();

            Scanner inputcity = new Scanner(System.in);
            System.out.print("Enter the city: ");
            String city = inputcity.nextLine();

            Scanner inputcountry = new Scanner(System.in);
            System.out.print("Enter the country: ");
            String country = inputcountry.nextLine();

            Scanner inputpostal = new Scanner(System.in);
            System.out.print("Enter the postal code: ");
            String postal_code = inputpostal.nextLine();

            HostToolkit suggest = new HostToolkit();
            suggest.SuggestAmenities(type, "Insert", 0);
            Scanner inputamenities = new Scanner(System.in);
            System.out.print("Enter the amenities: ");
            String amenities = inputamenities.nextLine();

            Scanner inputBedroom = new Scanner(System.in);
            System.out.print("Enter the number of bedrooms: ");
            int bedroom = inputBedroom.nextInt();

            Scanner inputBathroom = new Scanner(System.in);
            System.out.print("Enter the number of bathrooms: ");
            int bathroom = inputBathroom.nextInt();

            Scanner inputBed = new Scanner(System.in);
            System.out.print("Enter the number of beds: ");
            int bed = inputBed.nextInt();

            Scanner inputPrice= new Scanner(System.in);
            System.out.print("Enter the approximately price: ");
            int price = inputPrice.nextInt();


            InsertListing myListing = new InsertListing(HostID, title,type, latitude, longitude, address, city, country, postal_code, amenities, bedroom, bathroom, bed, price);
            boolean result = myListing.createListing();


            String query = "SELECT LID FROM Listings WHERE HostID = '" + HostID + "' AND address = '" + address + "'";
            Connection con = null;
            ResultSet rs = null;
            con = DriverManager.getConnection(CONNECTION,USER,PASS);
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(query);

            System.out.println();

            if(rs.next()){
                System.out.println("The listing ID is: " + rs.getInt(1));
            }
            System.out.println();
            if(!result){
                System.out.println("Listing was successfully added!");
                return;
            }else{
                return;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }

    }
}