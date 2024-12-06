package it2c.libradilla.cr;
import java.time.LocalDate;
import java.util.Scanner;

public class Reports {
    
    config conf = new config();

    public void Reports() {
        Scanner input = new Scanner(System.in);
        String response = "";

        int choice = 0;
        
        do {
            boolean validChoice = false;

            System.out.println("-------------------------------------");
            System.out.println("               Reports               ");
            System.out.println("-------------------------------------");
            System.out.println("1. General Report");
            System.out.println("2. Individual Report");
            System.out.println("3. Go back to Main Menu");

            while (!validChoice) {
                System.out.print("Enter action: ");
                String action = input.next().trim();

                try {
                    choice = Integer.parseInt(action);

                    if (choice >= 1 && choice <= 3) {
                        validChoice = true;
                    } else {
                        System.out.print("Invalid option. Please try agaim.\n");
                    }

                } catch (NumberFormatException e) {
                    System.out.print("Invalid input. Please enter a valid option.\n");
                }
            }

            Reports rep = new Reports();

            switch (choice) {
                case 1:

                    rep.GeneralReport();

                    break;

                case 2:

                    rep.IndividualReport();

                    break;

                case 3:
                    System.out.println("Returning to Main Menu...\n");
                    return;
            }

            input.nextLine();

            boolean validResponse = false;

            while (!validResponse) {
                System.out.print("Do you want to continue to Reports Menu? (yes/no): ");
                response = input.next().trim();

                if (response.isEmpty()) {
                    System.out.print("Input cannot be empty. Please input 'yes' or 'no'.");
                } else if (response.equalsIgnoreCase("yes") || response.equalsIgnoreCase("no")) {
                    break;
                } else {
                    System.out.println("Invalid input. Please input 'yes' or 'no'.");
                }
            }

        } while (response.equalsIgnoreCase("yes"));

    }
    
    private void GeneralReport() {

        config conf = new config();

        System.out.println("----------------------------------------------------");
        System.out.println("                  GENERAL REPORT                    ");
        System.out.println("----------------------------------------------------");

        String tQry = "SELECT * FROM customers WHERE c_status = 'Active'";
        String[] hdrs = {"ID", "First Name", "Last Name", "Email", "Contact Number", "Driver License"};
        String[] clmns = {"c_id", "c_fname", "c_lname", "c_email", "c_phonenumber", "c_driverlicense"};

        conf.viewRecords(tQry, hdrs, clmns);

        System.out.println("\n----------------------------------------------------");
        System.out.println("              Cars Report Summary                     ");
        System.out.println("------------------------------------------------------");

        String uQry = "SELECT * FROM cars WHERE cr_status = 'Rented'";
        String[] hdrs1 = {"Car ID", "Brand", "Model","Year","Fuel Type", "Rental Cost Perday"};
        String[] clmns1 = {"cr_id", "cr_brand", "cr_model", "cr_year", "cr_fueltype","cr_rentalcostperday"};

        LocalDate repGen = LocalDate.now();

        conf.viewRecords(uQry, hdrs1, clmns1);

        System.out.println("\n--------------------------------------------------------------");
        System.out.printf("              Date  %s                                  ", repGen);
        System.out.println("\n--------------------------------------------------------------");

    }
    
     private void IndividualReport() {
        Scanner input = new Scanner(System.in);
        Customers cus = new Customers();
        int c = 0;
        boolean validId = false;

        cus.viewCustomer();

        while (!validId) {

            System.out.print("Enter Customer ID: ");
            String uInput = input.next().trim();

            try {

                c = Integer.parseInt(uInput);

                if (c > 0) {
                    validId = true;
                } else {
                    System.out.println("Invalid Input. Please try again.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid input.");
            }

        }

        String sql = "SELECT c_id FROM customers WHERE c_id = ?";
        while (conf.getSingleValue(sql, c) == 0) {
            System.out.print("Customer does not exist. Please try again: ");
            c = input.nextInt();
        }

        while (!config.isCustomerEligible(c)) {

            System.out.print("The Customer is not currently renting a Car. Please try again.");
            c = input.nextInt();

            while (conf.getSingleValue(sql, c) == 0) {
                System.out.print("Customer ID does not exist. Please try again: ");
                c = input.nextInt();
            }

        }

        conf.generateIndividualReport(c);

    }
    
}
