import java.util.Scanner;
import java.util.UUID;

public class UserMain {
    public static void main(String[] args) throws ClassNotFoundException
    {
        boolean success = true;
        Scanner inputUserType = new Scanner(System.in);
        System.out.print("Become a Host or Renter: ");
        String user_type = inputUserType.nextLine();

        if(!(user_type.equals("Host") || user_type.equals("Renter"))){
            success = false;
            System.out.println("Error! Please enter Host or Renter correctly");
        }

        Scanner inputFirstName = new Scanner(System.in);
        System.out.print("Enter your first name: ");
        String first_name = inputFirstName.nextLine();
        if(first_name.equals(" ")){
            success = false;
            System.out.println("Error! Please enter your first name");
        }

        Scanner inputLastName = new Scanner(System.in);
        System.out.print("Enter your last name: ");
        String last_name = inputLastName.nextLine();
        if(last_name.equals(" ")){
            success = false;
            System.out.println("Error! Please enter your last name");
        }

        Scanner inputAddress= new Scanner(System.in);
        System.out.print("Enter your address: ");
        String address = inputAddress.nextLine();
        if(address.equals(" ")){
            success = false;
            System.out.println("Error! Please enter your address");
        }

        Scanner inputAge= new Scanner(System.in);
        System.out.print("Enter your age: ");
        int age = inputAge.nextInt();
        if(age < 18){
            success = false;
            System.out.println("User under 18 cannot create an account in AirBnB");
        }

        Scanner inputBirth = new Scanner(System.in);
        System.out.print("Enter your date of birth in the form dd/mm/yyyy: ");
        String date_of_birth= inputBirth.nextLine();
        String[] tokens = date_of_birth.split("/");
        for(String s : tokens){
            if(s.equals(" ")){
                success = false;
                System.out.println("Error! Please enter a valid date");
            }
        }

        Scanner inputOccupation = new Scanner(System.in);
        System.out.print("Enter your occupation: ");
        String occupation = inputOccupation.nextLine();
        if(occupation.equals(" ")){
            success = false;
            System.out.println("Error! Please enter your occupation");
        }

        Scanner inputSIN= new Scanner(System.in);
        System.out.print("Enter your social insurance number: ");
        int SIN = 0;
        if(inputSIN.hasNextInt()) {
            SIN = inputSIN.nextInt();
        }
        if(String.valueOf(SIN).length() != 9){
            success = false;
            System.out.println("Error! Please enter your valid 9-digit social insurance number");
        }

        int CreditCard_Num = 0;
        String ExpDate = null;
        String Postal_Code = null;
        int CVC = 0;

        if(user_type.equals("Renter")){
            Scanner inputCreditCard_Num = new Scanner(System.in);
            System.out.print("Enter your credit card number: ");
            CreditCard_Num = inputCreditCard_Num.nextInt();

            Scanner inputExpDate = new Scanner(System.in);
            System.out.print("Enter the expiry date in the form MM/YY: ");
            ExpDate = inputExpDate.nextLine();

            Scanner inputPostal = new Scanner(System.in);
            System.out.print("Enter your postal code: ");
            Postal_Code = inputPostal.nextLine();

            Scanner inputCVC = new Scanner(System.in);
            System.out.print("Enter the cvc number: ");
            CVC = inputCVC.nextInt();

        }

        String UserID;
        UUID uuid = UUID.randomUUID();
        if(user_type.equals("Host")){
            UserID = "Host"+ "-" + uuid;
        }else{
            UserID = "Renter"+ "-" + uuid;
        }

        boolean result = true;
        boolean result2 = true;

        inputUserType.close();
        inputFirstName.close();
        inputLastName.close();
        inputAddress.close();
        inputBirth.close();
        inputOccupation.close();
        inputSIN.close();
        inputAge.close();

        if(success == true && user_type.equals("Host")){
            InsertUser myUser = new InsertUser(UserID, first_name, last_name, address, date_of_birth, occupation, SIN);
            result = myUser.createUser();
            result2 = result;
        }else if(success != true){
            System.out.println("User was not successfully created!");
            return;
        }

        if(success == true && user_type.equals("Renter")){
            InsertUser myUser = new InsertUser(UserID, first_name, last_name, address, date_of_birth, occupation, SIN);
            result = myUser.createUser();

            PaymentInfo myPayment = new PaymentInfo(UserID, CreditCard_Num, ExpDate, Postal_Code, CVC);
            result2 = myPayment.recordPaymentInfo();
        }


        if(!result && !result2){
            System.out.println("User was successfully created!");
        }else{
            System.out.println("User was not successfully created!");
            return;
        }

    }
}