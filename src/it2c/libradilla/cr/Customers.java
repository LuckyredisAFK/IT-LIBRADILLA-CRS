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
        
        String email;
        while (true) {
            System.out.print("Enter Email(e.g., John.Doe@gmail.com): ");
            email = input.next();

            if (isValidEmail(email)) {
                break;
            } else {
                System.out.print("Invalid Email format. Please try again: ");
            }
        }

        String pno;
        while (true) {
            System.out.print("Enter Contact Number(e.g., 09123456789): ");
            pno = input.next();

            if (isValidPNum(pno)) {
                break;
            } else {
                System.out.print("Invalid Contact Number. Please try again: ");
            }
        }

        
        String license;
        while (true){
        System.out.print("Enter Driver License(e.g., NXX-XX-XXXXXX): ");
         license = input.next();
         
         if (isValidDriverLicense(license)) {
             break;
         } else {
             System.out.println("Invalid Driver Lincense Format. Please Try Again: ");
         }
        }
    
        
        System.out.print("Enter Status(e.g., Active/Inactive/Suspended): ");
        String sts = input.next();
        while (!sts.equalsIgnoreCase("Active") && !sts.equalsIgnoreCase("Inactive") && !sts.equalsIgnoreCase("Suspended")) {
            System.out.println("Invalid Input. Please enter 'Active' 'Inactive' 'Suspended'.");
            sts = input.nextLine();
        }

        String sql = "INSERT INTO customers (c_fname, c_lname, c_email, c_phonenumber, c_driverlicense, c_status) VALUES (?, ?, ?, ?, ?, ?)";
        conf.addCustomer(sql, fname, lname, email, pno, license, sts);

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
        int choice = 0;

        do {
            
             boolean validChoice = false;

            System.out.println("-------------------------------------");
            System.out.println("            Customer's Menu          ");
            System.out.println("-------------------------------------");
            System.out.println("1. Add Customer");
            System.out.println("2. Update Customer");
            System.out.println("3. View Customer");
            System.out.println("4. Delete Customer");
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
                    System.out.println("\nGoing Back to Main Menu");
                    return;

                default:
                    System.out.println("Invalid action. Try Again.");
                    break;
            }
            System.out.print("Go back to Customer Menu? (yes/no): ");
            response = input.next();

        } while (response.equalsIgnoreCase("yes"));

    }
    
     public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailRegex);
    }
     
     public static boolean isValidPNum(String phoneNumber) {
        String phoneRegex = "^[0-9]{11}$";
        return phoneNumber.matches(phoneRegex);
    }
     
     public static boolean isValidDriverLicense(String license) {
         String licenseregex = "^[A-Z]\\d{2}-\\d{2}-\\d{6}$";
         return license.matches(licenseregex);
     }

}
    
    
    
    
