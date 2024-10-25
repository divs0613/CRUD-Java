package Funds.Management.System;

/*
 * @author: Divine Grace G. Garduque
 * @date: October 23 - 25, 2024
 *
 * This class represents an expenditure made by the treasurer, including:
 * - Amount: The total amount spent.
 * - Date: The date when the expenditure was recorded.
 * - Details: Description of the expenditure.
 */

public class Expenditure {
    private double amount; // The amount of money spent
    private String details; // Description or details of the expenditure
    private String date; // Date of the expenditure in dd/mm/yyyy format

    // Constructor to initialize an Expenditure object
    public Expenditure(double amount, String details, int day, int month, int year) {
        this.amount = amount; // Set the expenditure amount
        this.details = details; // Set the expenditure details
        // Format the date as "dd/mm/yyyy"
        this.date = String.format("%02d/%02d/%04d", day, month, year);
    }

    // Getters:

    // Get the amount of the expenditure
    public double getAmount() {
        return amount;
    }

    // Get the details of the expenditure
    public String getDetails() {
        return details;
    }

    // Get the date of the expenditure
    public String getDate() {
        return date;
    }
}
