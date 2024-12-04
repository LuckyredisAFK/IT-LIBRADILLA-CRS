package it2c.libradilla.cr;

import java.util.Scanner;


public class Rental {
    
    int id;
    config conf = new config();
    
    public void rentCar() {
        Scanner input = new Scanner(System.in);

        Rental r = new Rental();
        Customers c = new Customers();

        System.out.println("-------------------------------------");
        System.out.println("              Rent a Car             ");
        System.out.println("-------------------------------------");
        c.viewCustomer();

        System.out.print("Enter Customer ID: ");
        id = input.nextInt();

        String sql1 = "SELECT c_id FROM customers WHERE c_id = ?";
        while (conf.getSingleValue(sql1, id) == 0) {
            System.out.print("Customer does not exist. Please try again: ");
            id = input.nextInt();
        }

        r.viewAvailableUnits();

       r.rentalProcess();
    }
    
    private void viewAvailableUnits() {

        String tqry = "SELECT * FROM cars WHERE cr_status = 'Available'";
        String[] hrds = {"Car ID", "Brand", "Model", "Condition", "Status", "Rental Cost Perday"};
        String[] clmns = {"cr_id", "cr_brand", "cr_model", "cr_condition", "cr_status", "cr_rentalcostperday"};

        conf.viewRecords(tqry, hrds, clmns);
    }
    
    private void rentalProcess() {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter the Car ID you want to rent: ");
        int unum = input.nextInt();

        conf.fetchCarsDetails(unum);
        
        System.out.print("\nWould you like to proceed to payment? (yes/no): ");
        String response = input.next();

        if (response.equalsIgnoreCase("yes")) {
            String sql = "UPDATE cars SET cr_status = 'Rented' WHERE cr_id = ?";
            
            conf.updateRecord(sql, unum);
            conf.addRental(id, unum);
            conf.payment(unum);
        } else {
            System.out.println("You chose not to rent this unit.");
        }

    }

    }   
   
   
    

