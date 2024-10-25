package Funds.Management.System;

import java.util.ArrayList;

/*
 * @author: Divine Grace G. Garduque
 * @date: October 23 - 25, 2024
 *
 * This class represents a student group in the school funds management system, including:
 * - Group Name: Name of the student group.
 * - Total Funds: The total amount required from the group members.
 * - Members: List of students belonging to the group.
 * - Expenditures: Tracking spending by the group.
 * - Methods for adding/removing students and managing financial transactions.
 */

public class StudentGroup {
    private String groupName; // Name of the student group
    private double totalFunds; // Total funds required for the group
    private double totalPaid; // Total amount paid by the group
    private ArrayList<Student> students; // List of students in the group
    private ArrayList<PaymentDate> payments; // List of payment records
    private ArrayList<Expenditure> expenditures; // List of expenditures made by the group

    // Constructor to initialize a StudentGroup with a name and total funds
    public StudentGroup(String groupName, double totalFunds) {
        this.groupName = groupName;
        this.totalFunds = totalFunds;
        this.totalPaid = 0.0; // Initialize total paid to zero
        this.students = new ArrayList<>(); // Initialize the list of students
        this.payments = new ArrayList<>(); // Initialize the list of payments
        this.expenditures = new ArrayList<>(); // Initialize the list of expenditures
    }

    // Get the total amount paid by the group
    public double getTotalPaid() {
        return totalPaid;
    }

    // Check if the group is in debt
    public boolean hasDebt() {
        return totalPaid < 0; // Indicates if the group is in debt
    }

    // Get the list of payment records for the group
    public ArrayList<PaymentDate> getPayments() {
        return payments;
    }

    // Spend funds from the group's total and record the expenditure
    public boolean spendFunds(double amount, String details, int day, int month, int year) {
        // Check if spending exceeds total funds
        if (amount > (totalFunds - totalPaid)) {
            double debtAmount = amount - (totalFunds - totalPaid);
            expenditures.add(new Expenditure(amount, details, day, month, year)); // Record the expenditure
            System.out.printf("Warning: This group is now in debt by %.2f", debtAmount);

        }
        // Record the expenditure regardless of debt
        expenditures.add(new Expenditure(amount, details, day, month, year));
        totalPaid -= amount; // Update total paid
        return true; // Transaction successful
    }

    // Get the list of expenditures made by the group
    public ArrayList<Expenditure> getExpenditures() {
        return expenditures;
    }

    // Process a payment and update the total paid
    public void processPayment(double amount) {
        totalPaid += amount; // Update total paid
        payments.add(new PaymentDate(0, 0, 0, amount, "Payment processed!")); // Record payment with placeholder date
    }

    // Get the list of students in the group
    public ArrayList<Student> getStudents() {
        return students;
    }

    // Get the name of the student group
    public String getGroupName() {
        return groupName;
    }

    // Add a student to the group and set the group association for the student
    public void addStudent(Student student) {
        students.add(student);
        student.setGroup(this); // Set group for the student
    }

    // Display the details of all students in the group
    public void displayStudents() {
        System.out.println(" ---------------------------------- ");
        System.out.println(" Group: " + groupName);
        for (Student student : students) {
            System.out.println(" Student ID: " + student.getID() + " Full Name: " + student.getName() +
                    "\n Year / Grade: " + student.getYearOrGrade() + " Program / Strand: " + student.getProgramOrStrand());
        }
        System.out.println(" ---------------------------------- ");
    }

    // Get the total funds required for the group
    public double getTotalFunds() {
        return totalFunds;
    }

    // Calculate and return the remaining funds for the group
    public double getRemainingFunds() {
        return totalFunds - totalPaid; // Calculate remaining funds
    }

    // Remove a student from the group by their ID
    public boolean removeStudent(int studentID) {
        for (Student student : students) {
            if (student.getID() == studentID) {
                students.remove(student); // Remove the student from the list
                student.setGroup(null); // Remove group association from the student
                return true; // Student removed successfully
            }
        }
        return false; // Student not found
    }
}
