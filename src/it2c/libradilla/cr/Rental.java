package it2c.libradilla.cr;

import java.util.Scanner;


public class Rental {
    
    int id;
    config conf = new config();
    
    public void rentCar() {
        Scanner input = new Scanner(System.in);

        Cars cr = new Cars();
        Rental rent = new Rental();

        System.out.println("-------------------------------------");
        System.out.println("              Rent Car               ");
        System.out.println("-------------------------------------");
        cr.viewCars();

        System.out.print("Enter Cars ID: ");
        id = input.nextInt();

        String sql1 = "SELECT cr_id FROM cars WHERE cr_id = ?";
        while (conf.getSingleValue(sql1, id) == 0) {
            System.out.print("Car does not exist. Please try again: ");
            id = input.nextInt();
        }


    }
    
    
    
}
