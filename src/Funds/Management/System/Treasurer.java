package Funds.Management.System;

/*
 * @author: Divine Grace G. Garduque
 * @date: October 23 - 25, 2024
 *
 * This class represents a treasurer in the school funds management system, including:
 * - ID: Unique identifier for the treasurer.
 * - Name: First name of the treasurer.
 * - Surname: Last name of the treasurer.
 * - Year or Grade: The year or grade level the treasurer is associated with.
 * - Program or Strand: The educational program or strand the treasurer belongs to.
 * - Management of student groups: Creating and managing groups for monitoring funds.
 * - Sending and receiving messages related to financial transactions.
 */

import java.util.ArrayList;

import static java.lang.String.format;

public class Treasurer {

    private int ID; // Unique identifier for the treasurer
    private String name; // First name of the treasurer
    private String surname; // Last name of the treasurer
    private int yearOrGrade; // Year or grade level of the treasurer's class
    private String programOrStrand; // Program or strand of study
    private int day; // Day of the money spent (used for records)
    private int month; // Month of the money spent (used for records)
    private int year; // Year of the money spent (used for records)
    private String dateOfMoneySpent; // Date when money was spent (formatted string)
    private double moneySpent; // Total amount of money spent by the treasurer
    private double fundsOfTheClass; // Total funds available for the class
    private ArrayList<Message> inbox = new ArrayList<>();
    private static ArrayList<Treasurer> treasurers = new ArrayList<>();
    private String passwordT;


    private ArrayList<StudentGroup> studentGroups; // List of student groups managed by the treasurer

    // Constructor to initialize a Treasurer with basic information
    public Treasurer(int ID, String name, String surname, String passwordT, int yearOrGrade, String programOrStrand) {
        this.ID = ID; // Set the treasurer's ID
        this.name = name; // Set the treasurer's first name
        this.surname = surname; // Set the treasurer's last name
        this.yearOrGrade = yearOrGrade; // Set the treasurer's year/grade
        this.programOrStrand = programOrStrand; // Set the treasurer's program/strand
        day = 0; // Initialize day to zero
        month = 0; // Initialize month to zero
        year = 0; // Initialize year to zero
        moneySpent = 0; // Initialize money spent to zero
        fundsOfTheClass = 0; // Initialize class funds to zero
        this.studentGroups = new ArrayList<>(); // Initialize the list of student groups
        inbox = new ArrayList<>(); // Initialize the inbox
        this.name = name + " " + surname; // Format full name
        treasurers.add(this); // Add this treasurer to the static list
        this.passwordT = passwordT;
    }

    public String getPassword() {
        return passwordT;
    }

    public void sendMessage(int recipientId, String content) {
        Message message = new Message(this.getID(), this.getName(), recipientId, this.getName(), content);

        // Check if the recipient is a Student
        boolean found = false;
        for (Student s : Student.getStudents()) {
            if (s.getID() == recipientId) {
                s.receiveMessage(message);
                System.out.println("Message sent to Student ID: " + recipientId + ", Name: " + s.getName());
                found = true;
                break; // Exit after sending the message
            }
        }

        if (!found) {
            for (Treasurer t : Treasurer.getTreasurers()) {
                System.out.println("Checking Treasurer ID: " + t.getID()); // Debug print
                if (t.getID() == recipientId) {
                    t.receiveMessage(message);
                    System.out.println("Message sent to Treasurer ID: " + recipientId);
                    found = true;
                    break; // Exit after sending the message
                }
            }
        }

        if (!found) {
            System.out.println("Recipient not found.");
        }
    }

    public void searchReferenceNumber(String referenceNumber) {
        boolean found = false;

        for (StudentGroup group : studentGroups) {
            for (Student student : group.getStudents()) {
                for (PaymentDate payment : student.getPayments()) {
                    if (payment.getReferenceNumber().equals(referenceNumber)) {
                        found = true;
                        System.out.println(" ---------------------------------- ");
                        System.out.println(" Group Name: " + group.getGroupName());
                        System.out.println(" Student Name: " + student.getName() + " " + student.getSurname());
                        System.out.printf(" Amount Paid: %.2f", payment.getAmount());
                        System.out.println(" Reference Number: " + payment.getReferenceNumber());
                        System.out.println(" Payment Date: " + payment.getDate());
                        System.out.println(" ---------------------------------- ");
                    }
                }
            }
        }

        if (!found) {
            System.out.println("*** No payment found with the reference number: " + referenceNumber);
        }
    }


    public void receiveMessage(Message message) {
        inbox.add(message);
    }

    public void viewInbox() {
        System.out.println(" ---------------------------------- ");
        System.out.println(" Inbox for " + this.getName() + " (ID " + this.getID() + "):");
        if (inbox.isEmpty()) {
            System.out.println(" No messages in inbox.");
            return;
        }
        for (Message message : inbox) {
            System.out.println(" From: " + message.getSenderName() + " (ID: " + message.getSenderId() + ") \n - " + message.getContent());
        }
        System.out.println(" ---------------------------------- ");
    }

    public static ArrayList<Treasurer> getTreasurers() {
        return treasurers;
    }

    // Create a new student group and add it to the treasurer's list
    public void createGroup(String groupName, double fundsOfTheClass) {
        StudentGroup newGroup = new StudentGroup(groupName, fundsOfTheClass); // Create a new group
        studentGroups.add(newGroup); // Add the new group to the list
        System.out.println("Student group '" + groupName + "' created successfully!"); // Confirmation message
    }


    // Get the list of student groups managed by this treasurer
    public ArrayList<StudentGroup> getStudentGroups() {
        return studentGroups;
    }

    // Get the treasurer's ID
    public int getID() {
        return ID;
    }

    // Get the treasurer's first name
    public String getName() {
        return name;
    }

    // Get the treasurer's last name
    public String getSurname() {
        return surname;
    }

    // Get the date when money was spent (formatted as a string)
    public String getDateOfMoneySpent() {
        return dateOfMoneySpent;
    }

    // Update the treasurer's first name
    public void setName(String newName) {
        this.name = newName;
    }

    // Update the treasurer's last name
    public void setSurname(String newSurname) {
        this.surname = newSurname;
    }

    // Update the treasurer's year or grade
    public void setYearOrGrade(int newYearOrGrade) {
        this.yearOrGrade = newYearOrGrade;
    }

    // Update the treasurer's program or strand
    public void setProgramOrStrand(String newProgramOrStrand) {
        this.programOrStrand = newProgramOrStrand;
    }

    // Delete a group by name from the treasurer's list
    public boolean deleteGroup(String groupName) {
        return studentGroups.removeIf(group -> group.getGroupName().equalsIgnoreCase(groupName));
    }

    // View all student groups managed by the treasurer and their financial status
    public void viewAllGroups() {
        ArrayList<StudentGroup> groupsWithDebt = new ArrayList<>(); // List for groups in debt
        ArrayList<StudentGroup> groupsWithoutDebt = new ArrayList<>(); // List for groups without debt

        // Separate groups based on debt status
        for (StudentGroup group : studentGroups) {
            if (group.hasDebt()) {
                groupsWithDebt.add(group); // Add to groups with debt
            } else {
                groupsWithoutDebt.add(group); // Add to groups without debt
            }
        }

        // Combine lists, with groups with debt first
        ArrayList<StudentGroup> sortedGroups = new ArrayList<>();
        sortedGroups.addAll(groupsWithDebt); // Add groups with debt
        sortedGroups.addAll(groupsWithoutDebt); // Add groups without debt

        // Display the financial status of all groups
        System.out.println(" ---------------------------------- ");
        for (StudentGroup group : sortedGroups) {
            System.out.println(" Groups Overview:");
            // Display the current status of each group
            if (group.hasDebt()) {
                System.out.println(" Status: In Debt");
            } else {
                System.out.println(" Status: No Debt");
            }
            System.out.println(" Group Name: " + group.getGroupName());
            System.out.printf(" Total Funds Required: " + String.format("%.2f", group.getTotalFunds()) + "\n");
            System.out.printf(" Total Paid by Group: " + String.format("%.2f", group.getTotalPaid()) + "\n");
            System.out.printf(" Remaining Funds: " + String.format("%.2f", group.getRemainingFunds()) + "\n");
            System.out.println(" ---------------------------------- ");
        }
    }
}
