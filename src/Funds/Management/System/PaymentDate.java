package Funds.Management.System;

/*
 * @author: Divine Grace G. Garduque
 * @date: October 23 - 25, 2024
 *
 * This class represents a payment made by a student, including:
 * - Amount: The total amount paid.
 * - Date: The date when the payment was made.
 * - Reference Number: A unique identifier for tracking the payment.
 */

import java.time.LocalDate;
import java.util.Scanner;

public class PaymentDate {
    private int day; // Day of the payment
    private int month; // Month of the payment
    private int year; // Year of the payment
    private double amount; // Amount paid in the transaction
    private String referenceNumber; // Unique reference number for the transaction

    // Constructor to initialize a PaymentDate object with the specified details
    public PaymentDate(int day, int month, int year, double amount, String referenceNumber) {
        this.day = day; // Set the day of the payment
        this.month = month; // Set the month of the payment
        this.year = year; // Set the year of the payment
        this.amount = amount; // Set the amount paid
        this.referenceNumber = referenceNumber; // Set the unique reference number
    }

    // Get the unique reference number for the payment
    public String getReferenceNumber() {
        return referenceNumber;
    }

    // Get the date of the payment formatted as "dd/mm/yyyy"
    public String getDate() {
        return day + "/" + month + "/" + year;
    }

    // Get the amount paid in the transaction
    public double getAmount() {
        return amount;
    }

}
