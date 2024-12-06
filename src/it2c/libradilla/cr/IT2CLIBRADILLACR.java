package it2c.libradilla.cr;
import java.util.Scanner;

public class IT2CLIBRADILLACR {        
  
    public static void main(String[] args) {
        
        Scanner input = new Scanner(System.in);
        String response;
        int choice = 0;
        
        do {
            
            boolean validChoice = false;

            System.out.println("-------------------------------------");
            System.out.println("        CAR RENTAL SYSTEM           ");
            System.out.println("-------------------------------------");
            System.out.println("1. Customer");
            System.out.println("2. Cars");
            System.out.println("3. Rent a Car");
            System.out.println("4. Report");
            System.out.println("5. Exit");
        
            
            
            while (!validChoice) {
                System.out.print("Enter action: ");
                String action = input.next().trim();

                try {
                    choice = Integer.parseInt(action);

                    if (choice >= 1 && choice <= 5) {
                        validChoice = true;
                    } else {
                        System.out.print("Invalid option. Please try agaim.\n");
                    }

                } catch (NumberFormatException e) {
                    System.out.print("Invalid input. Please enter a valid option.\n");
                }
            }

            switch (choice) {

                case 1:
                    Customers cts = new Customers();

                    cts.Cusomterp();

                    break;

                case 2:
                    Cars cr = new Cars();

                    cr.Carsp();

                    break;

                case 3:
                    Rental rent = new Rental();

                    rent.rentCar();

                    break;

                case 4:
                    
                    Reports reps = new Reports();
                    
                    reps.Reports();
                                                         
                    break;

                case 5:
                    System.out.println("Exiting the program. Goodbye !");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
            System.out.print("Do you want to continue? (yes/no): ");
            response = input.next();

        } while (response.equalsIgnoreCase("yes"));
        System.out.println("Thank you. Goodbye!");
        
    }
    
}
