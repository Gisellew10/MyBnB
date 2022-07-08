import java.sql.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException
    {
        Scanner inputtype = new Scanner(System.in);
        System.out.print("Enter the type: ");
        String type = inputtype.nextLine();

        Scanner inputlatitude = new Scanner(System.in);
        System.out.print("Enter the latitude: ");
        float latitude = inputtype.nextFloat();

        Scanner inputlongtitude = new Scanner(System.in);
        System.out.print("Enter the longtitude: ");
        float longtitude = inputlongtitude.nextFloat();

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


        InsertListing myListing = new InsertListing(type, latitude, longtitude, address, city, country, postal_code, amenities);
        boolean result = myListing.createListing();

        if(!result){
            System.out.println("Listing was successfully added!");
        }else{
            return;
        }

    }
}