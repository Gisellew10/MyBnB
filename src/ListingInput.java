import java.util.Scanner;

public class ListingInput {
    public void getListingInfo() throws ClassNotFoundException
    {
        Scanner inputHostID = new Scanner(System.in);
        System.out.print("Enter your host ID: ");
        String HostID = inputHostID.nextLine();

        Scanner inputtype = new Scanner(System.in);
        System.out.print("Enter the type: ");
        String type = inputtype.nextLine();

        Scanner inputlatitude = new Scanner(System.in);
        System.out.print("Enter the latitude: ");
        double latitude = inputtype.nextDouble();

        Scanner inputlongtitude = new Scanner(System.in);
        System.out.print("Enter the longtitude: ");
        double longtitude = inputlongtitude.nextDouble();

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

        Scanner inputamenities = new Scanner(System.in);
        System.out.print("Enter the amenities: ");
        String amenities = inputamenities.nextLine();

        Scanner inputAreaSize = new Scanner(System.in);
        System.out.print("Enter the area size of the listing in square foot: ");
        int area_size = inputamenities.nextInt();

        Scanner inputBedroom = new Scanner(System.in);
        System.out.print("Enter the number of bedrooms: ");
        int bedroom = inputBedroom.nextInt();

        Scanner inputBathroom = new Scanner(System.in);
        System.out.print("Enter the number of bathrooms: ");
        int bathroom = inputBathroom.nextInt();

        Scanner inputBed = new Scanner(System.in);
        System.out.print("Enter the number of beds: ");
        int bed = inputBed.nextInt();


        InsertListing myListing = new InsertListing(HostID, type, latitude, longtitude, address, city, country, postal_code, amenities, area_size, bedroom, bathroom, bed);
        boolean result = myListing.createListing();

        inputHostID.close();
        inputtype.close();
        inputaddress.close();
        inputlatitude.close();
        inputlongtitude.close();
        inputcity.close();
        inputcountry.close();
        inputpostal.close();
        inputamenities.close();


        if(!result){
            System.out.println("Listing was successfully added!");
        }else{
            return;
        }

    }
}