package Funds.Management.System;

import java.util.ArrayList;
import java.util.UUID;

/*
 * @author: Divine Grace G. Garduque
 * @date: October 23 - 25, 2024
 *
 * This class represents a student in the school funds management system, including:
 * - ID: Unique identifier for the student.
 * - Name: First name of the student.
 * - Surname: Last name of the student.
 * - Year or Grade: The year or grade level of the student.
 * - Program or Strand: The educational program or strand the student is enrolled in.
 * - Group: The student’s associated group for fund management.
 * - Payment records: Tracking payments made by the student and managing their financial contributions.
 */

public class Student {
    private int ID; // Unique identifier for the student
    private String name; // Student's first name
    private String surname; // Student's last name
    private int yearOrGrade; // Student's year or grade level
    private String programOrStrand; // Program or strand the student is enrolled in
    private double fundPayment; // Total amount paid by the student
    private ArrayList<PaymentDate> payments; // List to track all payment records
    private StudentGroup group; // Reference to the group the student belongs to
    private ArrayList<Message> inbox = new ArrayList<>(); // For messages
    private static ArrayList<Student> students = new ArrayList<Student>(); // For this class to be got
    private String passwordS;


    // Constructor to initialize a new Student object
    public Student(int ID, String name, String surname, String passwordS, int yearOrGrade, String programOrStrand) {
        this.ID = ID;
        this.name = name;
        this.surname = surname;
        this.yearOrGrade = yearOrGrade;
        this.programOrStrand = programOrStrand;
        this.fundPayment = 0; // Initialize fund payment to zero
        this.payments = new ArrayList<>(); // Initialize payment records
        inbox = new ArrayList<>();
        this.name = name + " " + surname;
        students.add(this);
        this.passwordS = passwordS;
    }

    // Set the group for the student
    public void setGroup(StudentGroup group) {
        this.group = group;
    }

    // Get the group associated with the student
    public StudentGroup getGroup() {
        return group;
    }

    // Set the student's first name
    public void setName(String name) {
        this.name = name;
    }

    // Set the student's last name
    public void setSurname(String surname) {
        this.surname = surname;
    }

    // Set the student's year or grade level
    public void setYearOrGrade(int yearOrGrade) {
        this.yearOrGrade = yearOrGrade;
    }

    // Set the student's program or strand
    public void setProgramOrStrand(String programOrStrand) {
        this.programOrStrand = programOrStrand;
    }

    public String getPassword() {
        return passwordS;
    }

    public boolean checkPassword(String inputPassword) {
        return passwordS.equals(inputPassword);
    }

    public void setPassword(String password) {
        if (isValidPassword(password)) {
            this.passwordS = password;
        } else {
            throw new IllegalArgumentException("*** Password must be exactly 4 digits.");
        }
    }

    private boolean isValidPassword(String password) {
        return password.length() == 4 && password.chars().allMatch(Character::isDigit);
    }

    public static ArrayList<Student> getStudents() {
        return students;
    }

    public void receiveMessage(Message message) {
        inbox.add(message);
    }

    public void viewInbox() {
        System.out.println(" ---------------------------------- ");
        System.out.println("Inbox for " + this.getName() + " (ID " + this.getID() + "):");
        if (inbox.isEmpty()) {
            System.out.println("No messages in inbox.");
            return;
        }
        for (Message message : inbox) {
            System.out.println("From: " + message.getSenderName() + " (ID: " + message.getSenderId() + ") \n - " + message.getContent());
        }
        System.out.println(" ---------------------------------- ");
    }

    public void sendMessage(int recipientId, String content) {
        Message message = new Message(this.getID(), this.getName(), recipientId, this.getName(), content);

        // Check if the recipient is a Treasurer
        for (Treasurer t : Treasurer.getTreasurers()) {
            if (t.getID() == recipientId) {
                t.receiveMessage(message);
                System.out.println("Message sent to Treasurer ID: " + recipientId + ", Name: " + t.getName());
                return; // Exit after sending the message
            }
        }

        // Check if the recipient is a Student
        for (Student s : Student.getStudents()) {
            System.out.println("Checking Student ID: " + s.getID()); // Debug print
            if (s.getID() == recipientId) {
                s.receiveMessage(message);
                System.out.println("Message sent to Student ID: " + recipientId);
                return; // Exit after sending the message
            }
        }

        System.out.println("*** Recipient not found.");
    }

    // Check if the group is in debt and notify the student
    public void checkAndNotifyDebt() {
        if (group != null && group.hasDebt()) {
            System.out.println("Notification: Your group is currently in debt. Please settle your payments.");
        }
    }

    // Process payment made by the student
    public void payFunds(double amount, int day, int month, int year) {
        // Generate a unique reference number for the payment
        String referenceNum = UUID.randomUUID().toString();
        fundPayment += amount; // Update the total fund payment

        // Create a new PaymentDate object to record the payment
        PaymentDate paymentDate = new PaymentDate(day, month, year, amount, referenceNum);
        payments.add(paymentDate); // Add the payment to the payment records

        // Print confirmation message
        System.out.println("Reference Number: " + referenceNum);
        System.out.println("Payment of " + amount + " on " + day + "/" + month + "/" + year + " has been made successfully.");

        // Process the payment in the associated group
        if (group != null) {
            group.processPayment(amount); // Update the group's total paid amount
        }
    }

    // Getters for student properties
    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getYearOrGrade() {
        return yearOrGrade;
    }

    public String getProgramOrStrand() {
        return programOrStrand;
    }

    public ArrayList<PaymentDate> getPayments() {
        return payments; // Return the list of payment records
    }
}
