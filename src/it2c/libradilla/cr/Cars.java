package it2c.libradilla.cr;

import java.util.Scanner;

public class Cars {
    
    private void addCars() {
        Scanner input = new Scanner(System.in);
        config conf = new config();

        System.out.print("Enter Car Brand(e.g., Toyota/Honda/Ford/BMW/Nissan): ");
        String brand = input.nextLine();

        System.out.print("Enter Car Model(e.g., Corolla/Civic/X5): ");
        String model = input.nextLine();
        
        String year;
        while (true){
        System.out.print("Enter Car Year (e.g., 2XXX): ");
         year = input.next();
         
         if (isValidYear(year)) {
             break;
         } else {
             System.out.println("Invalid Input. Please Try Again: ");
         }
        }
        
        String fuel;
        while (true){
        System.out.print("Enter Car Fuel Type (e.g., Petrol/Diesel/Electric/Hybrid): ");
         fuel = input.next();
         
         if (isValidFuelType(fuel)) {
             break;
         } else {
             System.out.println("Invalid Input. Please Try Again: ");
         }
        }              
        
        String condition;
        while (true){
        System.out.print("Enter Car Condition (e.g., Excellent/Good/Fair/Poor): ");
         condition = input.next().trim();
         
         if (isValidCondition(condition)) {
             break;
         } else {
             System.out.println("Invalid Input. Please Try Again: ");
         }
        }           
        
        String status;
        while (true){
        System.out.println("Enter Status (e.g., Available/Rented/Under Maintenance/Reserved): ");
         status  = input.nextLine();
         
         if (isValidStatus (status)) {
             break;
         } else {
             System.out.println("Invalid Input. Please Try Again: ");
         }
        }         

        System.out.print("Enter Rental Cost Perday: ");
        String cost = input.next();

        String sql = "INSERT INTO cars (cr_brand , cr_model, cr_year, cr_fueltype, cr_condition, cr_status, cr_rentalcostperday) VALUES (?, ?, ?, ?, ?, ?, ?)";
        conf.addCustomer(sql, brand, model, year, fuel, condition, status, cost);

    }
    
    public void viewCars() {
        String tqry = "SELECT * FROM cars";
        String[] hrds = {"Car ID", "Brand", "Model", "Year", "Fuel Type", "Condition", "Status","Rental Cost Perday"};
        String[] clmns = {"cr_id", "cr_brand", "cr_model", "cr_year", "cr_fueltype", "cr_condition", "cr_status", "cr_rentalcostperday"};

        config conf = new config();

        conf.viewRecords(tqry, hrds, clmns);
    }
    
    private void updateCars(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Customer ID ");
        int id = sc.nextInt();
        
        System.out.println("Enter the new Condition");
        String condition = sc.next();
        
        System.out.println("Enter the new Status");
        String sts = sc.next();
        
        String qry = "UPDATE cars SET cr_condition = ?, cr_status = ? WHERE cr_id = ?";
        
        config conf = new config();
        conf.updateRecord(qry,condition, sts, id);
    }
    
    private void deleteCars(){
        
        Scanner sc= new Scanner(System.in);
        System.out.print("Enter the ID to Delete: ");
        int id = sc.nextInt();
        
        String qry = "DELETE FROM cars WHERE cr_id = ?";
        
        config conf = new config();
        conf.deleteRecord(qry, id);
    
    }
    
    public void Carsp() {
        Cars cts = new Cars();
        Scanner input = new Scanner(System.in);
        String response;
        int choice = 0;

        do {
            
            boolean validChoice = false;

            System.out.println("-------------------------------------");
            System.out.println("             Cars Menu               ");
            System.out.println("-------------------------------------");
            System.out.println("1. Add Cars");
            System.out.println("2. Update Cars");
            System.out.println("3. View Cars");
            System.out.println("4. Delete Cars");
            System.out.println("5. Go back to Main Menu");

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
                    cts.addCars();
                    break;

                case 2:
                    cts.viewCars();
                    cts.updateCars();
                    cts.viewCars();
                    break;

                case 3:
                    cts.viewCars();
                    break;

                case 4:
                    cts.viewCars();
                    cts.deleteCars();
                    cts.viewCars();
                    break;

                case 5:
                    System.out.println("\nGoing Back to Main Menu.");
                    return;

                default:
                    System.out.println("Invalid action. Try Again.");
                    break;
            }
            System.out.print("Go back to Cars Menu (yes/no): ");
            response = input.next();

        } while (response.equalsIgnoreCase("yes"));

    }
    
    public static boolean isValidFuelType(String fuel) {
         return
               fuel.equals("Diesel") ||
                 fuel.equals("Electric") ||
                 fuel.equals("Petrol") ||
                 fuel.equals("Hybrid");
     }
    
    public static boolean isValidCondition(String condition) {
    return
        condition.equals("Excellent") || 
        condition.equals("Good") ||
        condition.equals("Poor") ||
        condition.equals("Fair");
}
    public static boolean isValidYear(String year) {
         return
               year.matches("\\d{4}");
     }
    
    public static boolean isValidStatus(String status) {
         return
               status.equals("Available") ||
               status.equals("Rented") ||
               status.equals("Under Maintenance") ||
               status.equals("Reserved");
     }
    
}
