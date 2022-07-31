import java.sql.Date;
import java.util.Scanner;
import java.util.UUID;
import java.sql.*;

public class UserInput {
    private static final String CONNECTION = "jdbc:mysql://localhost:3306/mybnb"; 

    public void getUserInfo(int year) throws ClassNotFoundException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        final String USER = "root";
        final String PASS = "giselle";
        try{
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
    
            Scanner inputBirth = new Scanner(System.in);
            System.out.print("Enter your date of birth in the form YYYY-MM-DD: ");
            String date_of_birth_s = inputBirth.nextLine();
            Date date_of_birth = Date.valueOf(date_of_birth_s);
    
            String[] tokens = date_of_birth_s.split("-");
            int age_year = Integer.valueOf(tokens[0]);
            if(year - age_year < 18){
                success = false;
                System.out.println("User under 18 cannot create an account in AirBnB");
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
    
            String CreditCard_Num = null;
            String ExpDate = null;
            String Postal_Code = null;
            int CVC = 0;
    
            if(user_type.equals("Renter")){
                Scanner inputCreditCard_Num = new Scanner(System.in);
                System.out.print("Enter your credit card number: ");
                CreditCard_Num = inputCreditCard_Num.nextLine();
    
                Scanner inputExpDate = new Scanner(System.in);
                System.out.print("Enter the expiry date in the form MM/YY: ");
                ExpDate = inputExpDate.nextLine();
    
                Scanner inputPostal = new Scanner(System.in);
                System.out.print("Enter your postal code: ");
                Postal_Code = inputPostal.nextLine();
    
                Scanner inputCVC = new Scanner(System.in);
                System.out.print("Enter the cvc number: ");
                CVC = inputCVC.nextInt();
    
                inputCreditCard_Num.close();
                inputExpDate.close();
                inputPostal.close();
                inputCVC.close();
    
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
    
            // inputUserType.close();
            // inputFirstName.close();
            // inputLastName.close();
            // inputAddress.close();
            // inputBirth.close();
            // inputOccupation.close();
            // inputSIN.close();
    
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
    
            String query2 = "SELECT UserID FROM Users WHERE SIN = '" + SIN + "'";
            Connection con2 = null;
            ResultSet rs2 = null;
            con2 = DriverManager.getConnection(CONNECTION,USER,PASS);
            Statement stmt2 = con2.createStatement();
            rs2 = stmt2.executeQuery(query2);
    
            System.out.println();
    
            if(rs2.next()){
                System.out.println("The User ID is: " + rs2.getString(1));
            }
    
    
            if(!result && !result2){
                System.out.println("User was successfully created!");
                return;
            }else{
                System.out.println("User was not successfully created!");
                return;
            }
    
        }
        catch(SQLException e){
            e.printStackTrace();
        }
       
    }
}