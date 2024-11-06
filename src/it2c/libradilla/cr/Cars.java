package it2c.libradilla.cr;

import java.util.Scanner;

public class Cars {
    
    private void addCars() {
        Scanner input = new Scanner(System.in);
        config conf = new config();

        System.out.print("Enter Car Brand: ");
        String brand = input.nextLine();

        System.out.print("Enter Car Model: ");
        String model = input.nextLine();

        System.out.print("Enter Car Year: ");
        String year = input.nextLine();

        System.out.print("Enter Car Fuel Type: ");
        String fuel = input.nextLine();

        System.out.print("Enter Car Condition: ");
        String condition = input.nextLine();

        System.out.print("Enter Status ");
        String status = input.nextLine();

        System.out.print("Enter Rental Cost Perday: ");
        String cost = input.next();

        String sql = "INSERT INTO cars (cr_brand , cr_model, cr_year, cr_fueltype, cr_condition, cr_status, cr_rentalcostperday) VALUES (?, ?, ?, ?, ?, ?, ?)";
        conf.addTenants(sql, brand, model, year, fuel, condition, status, cost);

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

        do {

            System.out.println("-------------------------------------");
            System.out.println("             Cars                    ");
            System.out.println("-------------------------------------");
            System.out.println("1. Add Cars");
            System.out.println("2. Update Cars");
            System.out.println("3. View Cars");
            System.out.println("4. Delete Cars");
            System.out.println("5. Go back to Main Menu");

            System.out.print("Enter action: ");
            int action = input.nextInt();

            switch (action) {
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
    
    
}
