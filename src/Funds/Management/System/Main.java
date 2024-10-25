package Funds.Management.System;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.time.LocalDate;

import static java.lang.String.format;

/*
 * @author: Divine Grace G. Garduque
 * @date: October 23, 2024
 *
 * // School Funds Management System //
 *
 * This is the entry point of the School Funds Management System application.
 * It manages the main user interface and controls the flow of the program,
 * allowing users to perform various actions such as:
 * - Adding treasurers and students.
 * - Logging in as a treasurer or student.
 * - Managing groups, payments, and messages.
 * - Displaying information about treasurers and students.
 * - Exiting the application.
 *
 */

public class Main {

    public static void main(String[] args) {

        ArrayList<Treasurer> treasurers = new ArrayList<>();
        ArrayList<Student> students = new ArrayList<>();
        LocalDate myDate = LocalDate.now();
        int currentYear = myDate.getYear();
        int senderId = -1;
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {

            instructions();

            try {

                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1: // Add Treasurer
                        System.out.print("Input the ID of the treasurer: ");
                        int inputID = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Input the name of the treasurer: ");
                        String inputName = sc.nextLine();

                        System.out.print("Input the surname of the treasurer: ");
                        String inputSurname = sc.nextLine();

                        String passwordT = getValidPassword(sc);

                        System.out.print("Input the year / grade level of the treasurer: ");
                        int inputYearOrGrade = sc.nextInt();
                        sc.nextLine();

                        while (inputYearOrGrade != 1 && inputYearOrGrade != 2 && inputYearOrGrade != 3 && inputYearOrGrade != 4 && inputYearOrGrade != 11 && inputYearOrGrade != 12) {
                            System.out.println("Invalid year / grade level");
                            inputYearOrGrade = sc.nextInt(); // Ask for input again
                        }

                        System.out.print("Input the program or strand of the treasurer: ");
                        String inputProgramOrStrand = sc.nextLine();

                        // Create and add the Treasurer to the list
                        Treasurer newTreasurer = new Treasurer(inputID, inputName, inputSurname, passwordT, inputYearOrGrade, inputProgramOrStrand);
                        treasurers.add(newTreasurer);
                        System.out.println("Treasurer added successfully!");
                        break;

                    case 2: // Add Student
                        System.out.print("Input the ID of the student: ");
                        int studentID = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Input the name of the student: ");
                        String studentName = sc.nextLine();

                        System.out.print("Input the surname of the student: ");
                        String studentSurname = sc.nextLine();

                        String passwordS = getValidPassword(sc);

                        System.out.print("Input the year / grade level of the student: ");
                        int studentYearGrade = sc.nextInt();
                        sc.nextLine();

                        while (studentYearGrade != 1 && studentYearGrade != 2 && studentYearGrade != 3 && studentYearGrade != 4 && studentYearGrade != 11 && studentYearGrade != 12) {
                            System.out.println("Invalid year / grade level");
                            studentYearGrade = sc.nextInt(); // Ask for input again
                        }

                        System.out.print("Input the program or strand of the student: ");
                        String studentProgramOrStrand = sc.nextLine();

                        // Create and add the Student to the list
                        Student newStudent = new Student(studentID, studentName, studentSurname, passwordS, studentYearGrade, studentProgramOrStrand);
                        students.add(newStudent);
                        System.out.println("Student added successfully!");
                        break;

                    case 3: // Log In (Treasurer)
                        System.out.print("Enter Treasurer ID: ");
                        int treasurerID = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter 4-digit password: ");
                        String treasurerPassword = sc.nextLine();

                        boolean treasurerLoggedIn = false;
                        for (Treasurer t : treasurers) {
                            if (t.getID() == treasurerID && t.getPassword().equals(treasurerPassword)) {
                                senderId = treasurerID;
                                System.out.println("Treasurer logged in successfully!");
                                treasurerLoggedIn = true;

                                // Treasurer management options loop
                                boolean managing = true;
                                while (managing) {

                                    treasurerInstructions();

                                    int actionChoice = sc.nextInt();
                                    sc.nextLine(); // Consume the newline

                                    switch (actionChoice) {
                                        case 1: // Create Student Group
                                            System.out.print("Enter group name: ");
                                            String groupName = sc.nextLine();
                                            System.out.print("Enter total funds to be paid by the group: ");
                                            double totalFunds = sc.nextDouble();
                                            t.createGroup(groupName, totalFunds);
                                            break;

                                        case 2: // Add Student to Group
                                            System.out.print("Enter the name of the group: ");
                                            String groupToAddTo = sc.nextLine();
                                            boolean groupFound = false;

                                            for (StudentGroup group : t.getStudentGroups()) {
                                                if (group.getGroupName().equalsIgnoreCase(groupToAddTo)) {
                                                    groupFound = true;
                                                    System.out.print("Enter Student ID to add: ");
                                                    int studentIDToAdd = sc.nextInt();
                                                    boolean studentExists = false;

                                                    for (Student s : students) {
                                                        if (s.getID() == studentIDToAdd) {
                                                            group.addStudent(s);
                                                            studentExists = true;
                                                            System.out.println("Student added to group!");
                                                            break;
                                                        }
                                                    }
                                                    if (!studentExists) {
                                                        System.out.println("Student ID not found.");
                                                    }
                                                    break;
                                                }
                                            }
                                            if (!groupFound) {
                                                System.out.println("Group not found.");
                                            }
                                            break;

                                        case 3: // View Total Remaining Balance of a Group
                                            System.out.print("Enter the name of the group to view total funds: ");
                                            String groupToView = sc.nextLine();
                                            boolean groupToViewFound = false;

                                            for (StudentGroup group : t.getStudentGroups()) {
                                                if (group.getGroupName().equalsIgnoreCase(groupToView)) {
                                                    groupToViewFound = true;
                                                    double remainingFunds = group.getRemainingFunds();
                                                    System.out.printf("Remaining funds for the group '" + group.getGroupName() + ": " +  String.format("%.2f", remainingFunds) + "\n");
                                                    break;
                                                }
                                            }
                                            if (!groupToViewFound) {
                                                System.out.println("Group not found.");
                                            }
                                            break;

                                        case 4: // View Members of a Group
                                            System.out.print("Enter the name of the group to view members: ");
                                            String groupNameToDisplay = sc.nextLine();
                                            boolean groupToDisplayFound = false;

                                            for (StudentGroup group : t.getStudentGroups()) {
                                                System.out.println(" ---------------------------------- ");
                                                System.out.println(" Members: ");
                                                if (group.getGroupName().equalsIgnoreCase(groupNameToDisplay)) {
                                                    groupToDisplayFound = true;
                                                    group.displayStudents(); // Display members of the group
                                                    break;
                                                }
                                                System.out.println(" ---------------------------------- ");
                                            }
                                            if (!groupToDisplayFound) {
                                                System.out.println("Group not found.");
                                            }
                                            break;

                                        case 5: // View Transactions of All Students
                                            System.out.println(" ---------------------------------- ");
                                            System.out.println(" Transactions of All Students:");
                                            System.out.println(" ---------------------------------- ");

                                            for (StudentGroup group : t.getStudentGroups()) {
                                                System.out.println(" Group Name: " + group.getGroupName());

                                                for (Student student : group.getStudents()) {
                                                    System.out.println(" Student ID: " + student.getID() + ", Name: " + student.getName());
                                                    System.out.println(" Payments:");

                                                    // Check if the student has made any payments
                                                    if (student.getPayments().isEmpty()) {
                                                        System.out.println("No payments made.");
                                                    } else {
                                                        for (PaymentDate payment : student.getPayments()) {
                                                            System.out.printf("\n Date: " + payment.getDate() + ", Amount Paid: " + format("%.2f", payment.getAmount()) +
                                                                    "\n Reference Number: " + payment.getReferenceNumber() + "\n"); // Display reference number
                                                            System.out.println(" ---------------------------------- ");
                                                        }
                                                    }
                                                    System.out.println(); // For better readability
                                                }
                                                System.out.println(); // For better readability between groups
                                            }
                                            break;

                                        case 6: // Update Treasurer Details
                                            System.out.print("Enter new name (or press Enter to skip): ");
                                            String newName = sc.nextLine();
                                            if (!newName.isEmpty()) {
                                                t.setName(newName);
                                            }

                                            System.out.print("Enter new surname (or press Enter to skip): ");
                                            String newSurname = sc.nextLine();
                                            if (!newSurname.isEmpty()) {
                                                t.setSurname(newSurname);
                                            }

                                            System.out.print("Enter new year/grade level (or press Enter to skip): ");
                                            String newYearGrade = sc.nextLine();
                                            if (!newYearGrade.isEmpty()) {
                                                t.setYearOrGrade(Integer.parseInt(newYearGrade));
                                            }

                                            System.out.print("Enter new program/strand (or press Enter to skip): ");
                                            String newProgramOrStrand = sc.nextLine();
                                            if (!newProgramOrStrand.isEmpty()) {
                                                t.setProgramOrStrand(newProgramOrStrand);
                                            }
                                            System.out.println(" ---------------------------------- ");
                                            System.out.println("Treasurer details updated successfully!");
                                            System.out.println(" ---------------------------------- ");
                                            break;

                                        case 7: // Remove Student from Group
                                            System.out.print("Enter the name of the group: ");
                                            String groupToRemoveFrom = sc.nextLine();
                                            boolean groupFoundToRemove = false;

                                            for (StudentGroup group : t.getStudentGroups()) {
                                                if (group.getGroupName().equalsIgnoreCase(groupToRemoveFrom)) {
                                                    groupFoundToRemove = true;
                                                    System.out.print("Enter Student ID to remove: ");
                                                    int studentIDToRemove = sc.nextInt();
                                                    boolean studentRemoved = group.removeStudent(studentIDToRemove);

                                                    if (studentRemoved) {
                                                        System.out.println("Student removed from group!");
                                                    } else {
                                                        System.out.println("Student ID not found in the group.");
                                                    }
                                                    break;
                                                }
                                            }
                                            if (!groupFoundToRemove) {
                                                System.out.println("*** Group not found.");
                                            }
                                            break;

                                        case 8: // Delete Group
                                            System.out.print("Enter the name of the group to delete: ");
                                            String groupToDelete = sc.nextLine();
                                            boolean groupDeleted = t.deleteGroup(groupToDelete);

                                            if (groupDeleted) {
                                                System.out.println("Group deleted successfully!");
                                            } else {
                                                System.out.println("*** Group not found.");
                                            }
                                            break;

                                        case 9: // View All Groups
                                            if (t.getStudentGroups().isEmpty()) {
                                                System.out.println("*** No groups available.");
                                            } else {
                                                t.viewAllGroups();
                                            }
                                            break;

                                        case 10: // Add Money Spent
                                            System.out.print("Enter a group to spend from: ");
                                            String groupNameToSpend = sc.nextLine();
                                            StudentGroup groupToSpend = null;

                                            // Find the group
                                            for (StudentGroup group : t.getStudentGroups()) {
                                                if (group.getGroupName().equalsIgnoreCase(groupNameToSpend)) {
                                                    groupToSpend = group;
                                                    break;
                                                }
                                            }

                                            if (groupToSpend == null) {
                                                System.out.println("*** Group not found.");
                                                break;
                                            }

                                            System.out.print("Enter amount to spend: ");
                                            double amountToSpend = sc.nextDouble();
                                            sc.nextLine();

                                            System.out.print("Enter details for the expenditure: ");
                                            String expenditureDetails = sc.nextLine();

                                            System.out.print("Enter payment day: ");
                                            int day = sc.nextInt();
                                            if (day < 1 || day > 31) {
                                                System.out.println("*** Invalid payment day.");
                                                break; // Exit if day is invalid
                                            }

                                            System.out.print("Enter payment month: ");
                                            int month = sc.nextInt();
                                            if (month < 1 || month > 12) {
                                                System.out.println("*** Invalid payment month.");
                                                break; // Exit if month is invalid
                                            }

                                            System.out.print("Enter payment year: ");
                                            int year = sc.nextInt();
                                            if (year > currentYear) {
                                                System.out.println("*** Invalid payment year.");
                                                break; // Exit if year is invalid
                                            }

                                            // Attempt to spend funds
                                            boolean transactionSuccess = groupToSpend.spendFunds(amountToSpend, expenditureDetails, day, month, year);
                                            if (transactionSuccess) {
                                                System.out.println("Expenditure recorded successfully.");
                                            } else {
                                                System.out.println("*** Failed to record expenditure.");
                                            }
                                            break;

                                        case 11: // Send Message (Treasurer)
                                            System.out.print("Enter recipient ID (Treasurer ID or Student ID): ");
                                            int recipientId = sc.nextInt();
                                            sc.nextLine(); // Consume newline

                                            System.out.print("Enter message: ");
                                            String messageContent = sc.nextLine();

                                            boolean messageSent = false;

                                            // Check if the sender is a treasurer
                                            for (Treasurer tr : treasurers) {
                                                if (tr.getID() == senderId) {
                                                    tr.sendMessage(recipientId, messageContent);
                                                    System.out.println("Message sent!");
                                                    messageSent = true;
                                                    break;
                                                }
                                            }

                                            // If not a treasurer, check if the sender is a student
                                            if (!messageSent) {
                                                for (Student s : students) {
                                                    if (s.getID() == senderId) {
                                                        s.sendMessage(recipientId, messageContent);
                                                        System.out.println("Message sent!");
                                                        break;
                                                    }
                                                }
                                            }
                                            break;

                                        case 12: // View Inbox (Treasurer)
                                            t.viewInbox();
                                            break;

                                        case 13: // Search Reference Number
                                            System.out.print("Enter Reference Number to search: ");
                                            String referenceNumberToSearch = sc.nextLine();
                                            t.searchReferenceNumber(referenceNumberToSearch);
                                            break;

                                        case 14: // Log Out
                                            managing = false;
                                            System.out.println("Treasurer logged out.");
                                            break;

                                        default:
                                            System.out.println("Invalid option. Please try again.");
                                            break;
                                    }
                                }
                                break; // Exit the treasurer login loop
                            }
                        }

                        if (!treasurerLoggedIn) {
                            System.out.println("Wrong Treasurer ID / Password!!");
                        }
                        break;

                    case 4: // Log In (Student)
                        System.out.print("Enter Student ID to log in: ");
                        int studentIDInput = sc.nextInt();
                        boolean studentFound = false;

                        for (Student s : students) {
                            if (s.getID() == studentIDInput) {
                                studentFound = true;
                                System.out.print("Enter 4-digit password: ");
                                String passwordInput = sc.nextLine(); // Consume newline
                                passwordInput = sc.nextLine(); // Read the password

                                if (s.getID() == studentIDInput && s.getPassword().equals(passwordInput)) {
                                    senderId = studentIDInput; // Set the senderId
                                    System.out.println("Student logged in successfully!");
                                    treasurerLoggedIn = true;
                                }

                                // Student management options loop
                                boolean studentManaging = true;
                                while (studentManaging) {

                                    System.out.println(" ---------------------------------- ");
                                    System.out.println(" ***           STUDENT          ***");
                                    System.out.println(" ---------------------------------- ");
                                    s.checkAndNotifyDebt();
                                    System.out.println(" ---------------------------------- ");
                                    System.out.println(" Select an option:");
                                    System.out.println(" 1. View Group Information");
                                    System.out.println(" 2. Pay Funds");
                                    System.out.println(" 3. View Payment History");
                                    System.out.println(" 4. View Expenditures");
                                    System.out.println(" 5. Update my Details");
                                    System.out.println(" 6. Send Inbox");
                                    System.out.println(" 7. View Inbox");
                                    System.out.println(" 8. Log Out");
                                    System.out.println(" ---------------------------------- ");

                                    int studentActionChoice = sc.nextInt();
                                    sc.nextLine(); // Consume the newline

                                    switch (studentActionChoice) {
                                        case 1: // View Group Information
                                            StudentGroup studentGroup = s.getGroup();
                                            if (studentGroup != null) {
                                                System.out.println(" ---------------------------------- ");
                                                System.out.println(" Group Name: " + studentGroup.getGroupName());
                                                System.out.printf(" Total Funds Required: %s%n", String.format("%.2f", studentGroup.getTotalFunds()));
                                                System.out.printf(" Total Paid by Group: " + String.format("%.2f", studentGroup.getTotalPaid()) + "\n");
                                                System.out.printf(" Remaining Funds: " + String.format("%.2f", studentGroup.getRemainingFunds()) + "\n");
                                                System.out.println(" ---------------------------------- ");
                                                System.out.println(" Members:");
                                                for (Student member : studentGroup.getStudents()) {
                                                    System.out.println(" ID: " + member.getID() + ", Name: " + member.getName() +
                                                            "\n Year / Grade: " + member.getYearOrGrade() + ", Program / Strand: " + member.getProgramOrStrand());
                                                }
                                                System.out.println(" ---------------------------------- ");
                                            } else {
                                                System.out.println("You are not part of any group.");
                                            }
                                            break;

                                        case 2: // Pay Funds
                                            System.out.print("Enter the amount to pay: ");
                                            double amountToPay = sc.nextDouble();
                                            sc.nextLine(); // Consume the newline

                                            System.out.print("Enter payment day: ");
                                            int day = sc.nextInt();
                                            if (day < 1 || day > 31) {
                                                System.out.println("Invalid payment day.");
                                                break; // Exit if day is invalid
                                            }

                                            System.out.print("Enter payment month: ");
                                            int month = sc.nextInt();
                                            if (month < 1 || month > 12) {
                                                System.out.println("Invalid payment month.");
                                                break; // Exit if month is invalid
                                            }

                                            System.out.print("Enter payment year: ");
                                            int year = sc.nextInt();
                                            if (year > currentYear) {
                                                System.out.println("Invalid payment year.");
                                                break; // Exit if year is invalid
                                            }

                                            // Set the payment date
                                            LocalDate paymentDate = LocalDate.of(year, month, day); // Create LocalDate from input

                                            // Now call payFunds using paymentDate
                                            s.payFunds(amountToPay, paymentDate.getDayOfMonth(), paymentDate.getMonthValue(), paymentDate.getYear());
                                            System.out.println("Payment successful!");
                                            break;

                                        case 3: // View Payment History
                                            System.out.println(" ---------------------------------- ");
                                            System.out.println(" Payment History for " + s.getName() + ":");
                                            System.out.println(" ---------------------------------- ");
                                            for (PaymentDate payment : s.getPayments()) {
                                                System.out.printf("\n Date: " + payment.getDate() + ", Amount Paid: " + String.format("%.2f", payment.getAmount()) +
                                                        "\n Reference Number: " + payment.getReferenceNumber() + "\n");
                                            }
                                            System.out.println(" ---------------------------------- ");
                                            break;

                                        case 4: // View Expenditures
                                            StudentGroup studentGroupForExpenditures = s.getGroup(); // Avoid name conflict
                                            if (studentGroupForExpenditures != null) {
                                                System.out.println("Expenditures for the group '" + studentGroupForExpenditures.getGroupName() + "':");
                                                ArrayList<Expenditure> expenditures = studentGroupForExpenditures.getExpenditures();

                                                if (expenditures.isEmpty()) {
                                                    System.out.println("No expenditures recorded for this group.");
                                                } else {
                                                    for (Expenditure expenditure : expenditures) {
                                                        System.out.printf("Date: " + expenditure.getDate() + ", Amount Spent: " + String.format("%.2f", expenditure.getAmount()) + ", Details: " + expenditure.getDetails() + "\n");
                                                    }
                                                }
                                            } else {
                                                System.out.println("You are not part of any group.");
                                            }
                                            break;

                                        case 5: // Update Student Details
                                            System.out.print("Enter new name (or press Enter to skip): ");
                                            String studentNewName = sc.nextLine();
                                            if (!studentNewName.isEmpty()) {
                                                s.setName(studentNewName);
                                            }

                                            System.out.print("Enter new surname (or press Enter to skip): ");
                                            String studentNewSurname = sc.nextLine();
                                            if (!studentNewSurname.isEmpty()) {
                                                s.setSurname(studentNewSurname);
                                            }

                                            System.out.print("Enter new year/grade level (or press Enter to skip): ");
                                            String studentNewYearGrade = sc.nextLine();

                                            if (!studentNewYearGrade.isEmpty()) {
                                                s.setYearOrGrade(Integer.parseInt(studentNewYearGrade));
                                            }

                                            System.out.print("Enter new program/strand (or press Enter to skip): ");
                                            String studentNewProgramOrStrand = sc.nextLine();
                                            if (!studentNewProgramOrStrand.isEmpty()) {
                                                s.setProgramOrStrand(studentNewProgramOrStrand);
                                            }
                                            System.out.println("Student details updated successfully!");
                                            break;

                                        case 6: // Send Message (Student)
                                            System.out.print("Enter the recipient's ID: ");
                                            int recipientStudentId = sc.nextInt();
                                            sc.nextLine();
                                            System.out.print("Enter your message: ");
                                            String messageStudent = sc.nextLine();
                                            s.sendMessage(recipientStudentId, messageStudent);
                                            System.out.println("Message sent!");
                                            break;

                                        case 7: // View Inbox (Student)
                                            s.viewInbox();
                                            break;

                                        case 8: // Log Out
                                            studentManaging = false;
                                            System.out.println("Student logged out.");
                                            break;

                                        default:
                                            System.out.println("Invalid option. Please try again.");
                                            break;
                                    }
                                }
                                break; // Exit the student login loop
                            }
                        }

                        if (!studentFound) {
                            System.out.println("Wrong Student ID / Password!");
                        }
                        break;


                    case 5: // Display Treasurers
                        System.out.println(" ---------------------------------- ");
                        System.out.println(" Treasurers: ");
                        for (Treasurer t : treasurers) {
                            System.out.println(" Treasurer ID: " + t.getID() + " Full Name: " + t.getName());
                        }
                        System.out.println(" ---------------------------------- ");
                        break;

                    case 6: // Display Students
                        System.out.println(" ---------------------------------- ");
                        System.out.println(" Students: ");
                        for (Student s : students) {
                            System.out.println(" Student ID: " + s.getID() + " Full Name: " + s.getName());
                        }
                        System.out.println(" ---------------------------------- ");
                        break;

                    case 7: // Exit
                        running = false;
                        System.out.println("Exiting the program. Thank you for using the system!");
                        break;

                    default:
                        System.out.println("Invalid option. Please try again.");
                        break;

                }

            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                sc.nextLine(); // Clear the invalid input
            }

        }

        sc.close();

    }

    private static void instructions() {

        System.out.println(" ------------------------------------------ ");
        System.out.println(" Welcome to School Funds Management System!");
        System.out.println(" ------------------------------------------ ");
        System.out.println(" Select an option:");
        System.out.println(" 1. Add Treasurer");
        System.out.println(" 2. Add Student");
        System.out.println(" 3. Log In (Treasurer)");
        System.out.println(" 4. Log In (Student)");
        System.out.println(" 5. Display Treasurers");
        System.out.println(" 6. Display Students");
        System.out.println(" 7. Exit");
        System.out.println(" ---------------------------------- ");

    }

    private static void treasurerInstructions() {

        System.out.println(" ---------------------------------- ");
        System.out.println(" ***          TREASURER         ***");
        System.out.println(" ---------------------------------- ");
        System.out.println(" Select an option:");
        System.out.println(" 1. Create Student Group");
        System.out.println(" 2. Add Student to Group");
        System.out.println(" 3. Total Remaining Balance of a Group");
        System.out.println(" 4. View Members of a Group");
        System.out.println(" 5. View Transactions of All Students");
        System.out.println(" 6. Update My Details");
        System.out.println(" 7. Remove Student from Group");
        System.out.println(" 8. Delete Group");
        System.out.println(" 9. View All Groups");
        System.out.println(" 10. Add Money Spent");
        System.out.println(" 11. Send Inbox");
        System.out.println(" 12. View Inbox");
        System.out.println(" 13. Search Reference Number");
        System.out.println(" 14. Log Out");
        System.out.println(" ---------------------------------- ");

    }

    private static String getValidPassword(Scanner sc) {

        String password;
        while (true) {
            System.out.print("Enter a 4-digit password: ");
            password = sc.nextLine();
            if (password.length() == 4 && password.chars().allMatch(Character::isDigit)) {
                break;
            } else {
                System.out.println("Invalid password. It must be exactly 4 digits.");
            }
        }
        return password;

    }

}
