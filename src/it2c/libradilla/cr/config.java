package it2c.libradilla.cr;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.time.LocalDate;

class config {
    
    //Connection Method to SQLITE

    public static Connection connectDB() {
        Connection con = null;
        try {
            Class.forName("org.sqlite.JDBC"); // Load the SQLite JDBC driver
            con = DriverManager.getConnection("jdbc:sqlite:database.db"); // Establish connection
            System.out.println("");
        } catch (Exception e) {
            System.out.println("Connection Failed: " + e);
        }
        return con;
    }
    
    public void addCustomer(String sql, String... values) {
        try (Connection conn = this.connectDB();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (int i = 0; i < values.length; i++) {
                pstmt.setString(i + 1, values[i]);
            }

            pstmt.executeUpdate();
            System.out.println("Record added successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding record: " + e.getMessage());
        }
    }
    
    // Dynamic view method to display records from any table
    public void viewRecords(String sqlQuery, String[] columnHeaders, String[] columnNames) {
        // Check that columnHeaders and columnNames arrays are the same length
        if (columnHeaders.length != columnNames.length) {
            System.out.println("Error: Mismatch between column headers and column names.");
            return;
        }

        try (Connection conn = this.connectDB();
                PreparedStatement pstmt = conn.prepareStatement(sqlQuery);
                ResultSet rs = pstmt.executeQuery()) {

            // Print the headers dynamically
            StringBuilder headerLine = new StringBuilder();
            headerLine.append("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n| ");
            for (String header : columnHeaders) {
                headerLine.append(String.format("%-25s | ", header)); // Adjust formatting as needed
            }
            headerLine.append("\n--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

            System.out.println(headerLine.toString());

            // Print the rows dynamically based on the provided column names
            while (rs.next()) {
                StringBuilder row = new StringBuilder("| ");
                for (String colName : columnNames) {
                    String value = rs.getString(colName);
                    row.append(String.format("%-25s | ", value != null ? value : "")); // Adjust formatting
                }
                System.out.println(row.toString());
            }
            System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

        } catch (SQLException e) {
            System.out.println("Error retrieving records: " + e.getMessage());
        }
    }
    
    //-----------------------------------------------
    // UPDATE METHOD
    //-----------------------------------------------
    public void updateRecord(String sql, Object... values) {
        try (Connection conn = this.connectDB(); // Use the connectDB method
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Loop through the values and set them in the prepared statement dynamically
            for (int i = 0; i < values.length; i++) {
                if (values[i] instanceof Integer) {
                    pstmt.setInt(i + 1, (Integer) values[i]); // If the value is Integer
                } else if (values[i] instanceof Double) {
                    pstmt.setDouble(i + 1, (Double) values[i]); // If the value is Double
                } else if (values[i] instanceof Float) {
                    pstmt.setFloat(i + 1, (Float) values[i]); // If the value is Float
                } else if (values[i] instanceof Long) {
                    pstmt.setLong(i + 1, (Long) values[i]); // If the value is Long
                } else if (values[i] instanceof Boolean) {
                    pstmt.setBoolean(i + 1, (Boolean) values[i]); // If the value is Boolean
                } else if (values[i] instanceof java.util.Date) {
                    pstmt.setDate(i + 1, new java.sql.Date(((java.util.Date) values[i]).getTime())); // If the value is Date
                } else if (values[i] instanceof java.sql.Date) {
                    pstmt.setDate(i + 1, (java.sql.Date) values[i]); // If it's already a SQL Date
                } else if (values[i] instanceof java.sql.Timestamp) {
                    pstmt.setTimestamp(i + 1, (java.sql.Timestamp) values[i]); // If the value is Timestamp
                } else {
                    pstmt.setString(i + 1, values[i].toString()); // Default to String for other types
                }
            }

            pstmt.executeUpdate();
            System.out.println("Record updated successfully!");
        } catch (SQLException e) {
            System.out.println("Error updating record: " + e.getMessage());
        }
    }
    
    // Add this method in the config class
public void deleteRecord(String sql, Object... values) {
    try (Connection conn = this.connectDB();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        // Loop through the values and set them in the prepared statement dynamically
        for (int i = 0; i < values.length; i++) {
            if (values[i] instanceof Integer) {
                pstmt.setInt(i + 1, (Integer) values[i]); // If the value is Integer
            } else {
                pstmt.setString(i + 1, values[i].toString()); // Default to String for other types
            }
        }

        pstmt.executeUpdate();
        System.out.println("Record deleted successfully!");
    } catch (SQLException e) {
        System.out.println("Error deleting record: " + e.getMessage());
    }
}

//-----------------------------------------------
    // Helper Method for Setting PreparedStatement Values
    //-----------------------------------------------
    private void setPreparedStatementValues(PreparedStatement pstmt, Object... values) throws SQLException {
        for (int i = 0; i < values.length; i++) {
            if (values[i] instanceof Integer) {
                pstmt.setInt(i + 1, (Integer) values[i]);
            } else if (values[i] instanceof Double) {
                pstmt.setDouble(i + 1, (Double) values[i]);
            } else if (values[i] instanceof Float) {
                pstmt.setFloat(i + 1, (Float) values[i]);
            } else if (values[i] instanceof Long) {
                pstmt.setLong(i + 1, (Long) values[i]);
            } else if (values[i] instanceof Boolean) {
                pstmt.setBoolean(i + 1, (Boolean) values[i]);
            } else if (values[i] instanceof java.util.Date) {
                pstmt.setDate(i + 1, new java.sql.Date(((java.util.Date) values[i]).getTime()));
            } else if (values[i] instanceof java.sql.Date) {
                pstmt.setDate(i + 1, (java.sql.Date) values[i]);
            } else if (values[i] instanceof java.sql.Timestamp) {
                pstmt.setTimestamp(i + 1, (java.sql.Timestamp) values[i]);
            } else {
                pstmt.setString(i + 1, values[i].toString());
            }
        }
    }
    
    //-----------------------------------------------
    // GET SINGLE VALUE METHOD
    //-----------------------------------------------
    public double getSingleValue(String sql, Object... params) {
        double result = 0.0;
        try (Connection conn = connectDB();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            setPreparedStatementValues(pstmt, params);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                result = rs.getDouble(1);
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving single value: " + e.getMessage());
        }
        return result;
    }
    
    public void fetchCarsDetails(int unitId) {
        String sql = "SELECT * FROM cars WHERE cr_id = ?";

        try (Connection conn = connectDB();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, unitId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {

                    String brand = rs.getString("cr_brand");
                    String model = rs.getString("cr_model");
                    String rentalcost = rs.getString("cr_rentalcostperday");
                    String status = rs.getString("cr_status");

                    System.out.println("-------------------------------------");
                    System.out.println("             CAR DETAILS             ");
                    System.out.println("-------------------------------------");
                    System.out.printf("\nUnit ID: %s", unitId);
                    System.out.printf("\nBrand: %s", brand);
                    System.out.printf("\nModel: %s", model);
                    System.out.printf("\nRental Cost Perday: P%s", rentalcost);
                    System.out.printf("\nStatus: %s\n", status);
                } else {
                    System.out.printf("No Car Found with this ID: %s", unitId);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error fetching Car details: " + e.getMessage());
        }
    }
    
    
    
    public void payment(int unitId) {
        Scanner input = new Scanner(System.in);
        String sql = "SELECT cr_rentalcostperday FROM cars WHERE cr_id = ?";

        try (Connection conn = connectDB();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, unitId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {

                String monthlyRental = rs.getString("cr_rentalcostperday");
                System.out.println("-------------------------------------");
                System.out.println("           PAYMENT PROCESS           ");
                System.out.println("-------------------------------------");

                System.out.printf("Amount needed to pay: %s\n", monthlyRental);

                System.out.print("Enter amount to pay: ");
                int pay = input.nextInt();

                System.out.printf("You have successfully rented Car ID. %s\n", unitId);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching Car details for payment: " + e.getMessage());
        }
    }
    
    public static boolean isCustomerEligible(int customerID) {
        String sql = "SELECT c_status FROM customers WHERE c_id = ?";
        try (Connection conn = config.connectDB();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, customerID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next() && rs.getString("c_status").equalsIgnoreCase("Active")) {
                    return true;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error checking Customer eligibility: " + e.getMessage());
        }
        return false;
    }
    
    public void generateIndividualReport(int CustomerID) {
    config conf = new config();

    System.out.println("\n-------------------------------------");
    System.out.println("|      INDIVIDUAL RENTAL REPORT      |");
    System.out.println("-------------------------------------");

    String query = "SELECT c.c_id AS c_id, c.c_fname AS c_fname, c.c_lname AS c_lname, "
            + "c.c_email, c.c_phonenumber, cr.cr_id AS cr_id, cr.cr_model AS car_model, "
            + "cr.cr_rentalcostperday AS cr_rentalcostperday "
            + "FROM customers c "
            + "LEFT JOIN rentals r ON c.c_id = r.c_id "
            + "LEFT JOIN cars cr ON r.cr_id = cr.cr_id "
            + "WHERE c.c_id = ?";

    try (Connection conn = config.connectDB()) {
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, CustomerID);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    
                    // Customer Details
                    int cid = rs.getInt("c_id");
                    String firstName = rs.getString("c_fname");
                    String lastName = rs.getString("c_lname");
                    String email = rs.getString("c_email");
                    String contact = rs.getString("c_phonenumber");
                    
                    // Car Details
                    int carId = rs.getInt("cr_id");
                    String carModel = rs.getString("car_model");
                    String rentalCostPerDay = rs.getString("cr_rentalcostperday");
                    System.out.printf("\n - Customer ID: %d", cid);
                    System.out.printf("\n - First Name: %s", firstName);
                    System.out.printf("\n - Last Name: %s", lastName);
                    System.out.printf("\n - Email: %s", email);
                    System.out.printf("\n - Contact: %s", contact);
                    System.out.printf("\n - Car ID: %d", carId);
                    System.out.printf("\n - Car Model: %s", carModel);
                    System.out.printf("\n - Rental Cost Per Day: %s", rentalCostPerDay);
                    System.out.println("\n-------------------------------------");

                } else {
                    System.out.println("Customer ID not found.");
                }
            }
        }

        System.out.println("-------------------------------------\n");
    } catch (SQLException e) {
        System.out.println("Error generating individual report: " + e.getMessage());
    }
}
    
    public void addRental(int customerId, int carId) {

    
    String sql = "INSERT INTO rentals (c_id, cr_id, r_start, r_returndate) VALUES (?, ?, ?, ?)";

    config conf = new config();

    
    LocalDate start = LocalDate.now();
    LocalDate returnDate = start.plusYears(1);
    
    System.out.println("Customer ID: " + customerId);
    System.out.println("Car ID: " + carId);

    try (Connection conn = connectDB();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

       
        pstmt.setInt(1, customerId);  
        pstmt.setInt(2, carId);  
        pstmt.setDate(3, Date.valueOf(start));  
        pstmt.setDate(4, Date.valueOf(returnDate));  

        
        int rowsAffected = pstmt.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Rental record added successfully.");
        } else {
            System.out.println("Failed to add rental record.");
        }

    } catch (SQLException e) {
        System.out.println("Error adding rental: " + e.getMessage());
    }
}
    
    
    
    
}
