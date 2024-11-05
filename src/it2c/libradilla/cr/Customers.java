package it2c.libradilla.cr;

import java.util.Scanner;

public class Customers {
    
    private void addCustomer() {
        Scanner input = new Scanner(System.in);
        config conf = new config();

        System.out.print("Enter First Name: ");
        String fname = input.next();

        System.out.print("Enter Last Name: ");
        String lname = input.next();

        System.out.print("Enter Email: ");
        String email = input.next();

        System.out.print("Enter Contact Number: ");
        String pno = input.next();
        
        System.out.print("Enter Driver License: ");
        String license = input.next();
        
        System.out.print("Enter Status: ");
        String sts = input.next();

        String sql = "INSERT INTO customers (c_fname, c_lname, c_email, c_phonenumber, c_driverlicense, c_status) VALUES (?, ?, ?, ?, ?, ?)";
        conf.addTenants(sql, fname, lname, email, pno, license, sts);

    }
    
    public void viewCustomer() {
        String tqry = "SELECT * FROM customers";
        String[] hrds = {"ID", "First Name", "Last Name", "Email", "Contact No.","Driver's License", "Status"};
        String[] clmns = {"c_id", "c_fname", "c_lname", "c_email", "c_phonenumber","c_driverlicense", "c_status"};

        config conf = new config();

        conf.viewRecords(tqry, hrds, clmns);
    }
    
    private void updateCustomer(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Customer ID ");
        int id = sc.nextInt();
        
        System.out.println("Enter the new Status");
        String sts = sc.next();
        
        String qry = "UPDATE customers SET c_status = ? WHERE c_id = ?";
        
        config conf = new config();
        conf.updateRecord(qry, sts, id);
    }
    
    private void deleteCustomer(){
        
        Scanner sc= new Scanner(System.in);
        System.out.print("Enter the ID to Delete: ");
        int id = sc.nextInt();
        
        String qry = "DELETE FROM customers WHERE c_id = ?";
        
        config conf = new config();
        conf.deleteRecord(qry, id);
    
    }
    
    public void Cusomterp() {
        Customers cts = new Customers();
        Scanner input = new Scanner(System.in);
        String response;

        do {

            System.out.println("-------------------------------------");
            System.out.println("            Customer                 ");
            System.out.println("-------------------------------------");
            System.out.println("1. Add Customer");
            System.out.println("2. Update Customer");
            System.out.println("3. View Customer");
            System.out.println("4. Delete Customer");
            System.out.println("5. Go back to Main Menu");

            System.out.print("Enter action: ");
            int action = input.nextInt();

            switch (action) {
                case 1:
                    cts.addCustomer();
                    break;

                case 2:
                    cts.viewCustomer();
                    cts.updateCustomer();
                    cts.viewCustomer();
                    break;

                case 3:
                    cts.viewCustomer();
                    break;

                case 4:
                    cts.viewCustomer();
                    cts.deleteCustomer();
                    cts.viewCustomer();
                    break;

                case 5:
                    System.out.println("\nReturning to Main Menu...");
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
            System.out.print("Do you want to continue? (yes/no): ");
            response = input.next();

        } while (response.equalsIgnoreCase("yes"));

    }
    
    
    
    
}
