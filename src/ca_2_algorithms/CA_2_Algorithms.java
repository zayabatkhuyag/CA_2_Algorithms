/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ca_2_algorithms;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**CA_2 Algorithms and Constructs
 * Buyanzaya Batkhuyag - 2025031 (Higher Diploma in Computing Feb2025)
 * 
 * @author zayab
 */
public class CA_2_Algorithms {
    
    //EMPLOYEE CLASS - shows a one employee record 
    // -------------------------------------------
    static class Employee{
        String firstName;     //first name of emloyee
        String lastName;      //last name of employee
        String department;    //department the employee works
        String managerType;   //employee`s manager type 
        
        //Constructor initializes all fields for an employee
        Employee (String firtName, String lastName, String department, String managerType){
            this.firstName = firstName;         //assign first name
            this.lastName = lastName;           //assign last name
            this.department = department;       //assign department
            this.managerType = managerType;     //assign manager type
        }
        
        //Showing how employee should be printed on the prompt
        @Override 
        public String toString(){
            return firstName + " " + lastName + " | " + managerType + " | " + department;
        }
    }

  //ENUM-MENU OPTION
    //--------------
    enum MenuOption {
        DISPLAY_FIRST_20(1, "Display first 20 employees (sorted by first name)"),
        SEARCH_BY_NAME(2, "Search employee by First Name"),
        ADD_EMPLOYEE(3, "Add new employee"),
        SHOW_HIERARCHY(4, "Show employee hierarchy (binary tree)"),
        EXIT(5, "Exit");
        
        final int code;                //numeric option code for menu 
        final String description;      //text describing the option
        
        // Constructor for each enum item
        MenuOption(int code, String description) {
            this.code = code;               // assign code
            this.description = description; // assign description
        }

        // Converts user numeric input into the corresponding enum option
        static MenuOption fromCode(int code) {
            for (MenuOption option : values()) { // Loop through all menu options
                if (option.code == code) return option; // Return match
            }
            return null; // If invalid option
        }
    }
    
    // File name to load employee data from
    private static final String FILE_NAME = "Applicants_Form - Sample data file for read.txt";

    
    // MAIN MENU SECTION
    // -----------------
    public static void main(String[] args) {

        List<Employee> employees = new ArrayList<>();      // Holds employees loaded from file
        List<Employee> newlyAdded = new ArrayList<>();     // Holds employees added during runtime
        Set<String> departments = new LinkedHashSet<>();   // Stores unique departments
        Set<String> managerTypes = new LinkedHashSet<>();  // Stores unique manager types
        
    // Load employees from file into memory
        if (!getEmployeesFromFile(FILE_NAME, employees, departments, managerTypes)) {
            System.out.println("Error: Could not load employees. Exiting.");
            return; // Stop program if file read fails
        }

        // Sort employees alphabetically using merge sort
        sortEmployees(employees);

        Scanner scanner = new Scanner(System.in); // Scanner for user input
        boolean running = true;                   // Loop control variable

        // MAIN MENU LOOP – continues until user selects Exit
        // --------------------------------------------------
        while (running) {
            System.out.println("***** MAIN MENU *****"); // Display header

            // Loop through enum to print menu lines
            for (MenuOption option : MenuOption.values()) {
                System.out.println(option.code + ". " + option.description); // Print lines like: 1. Display first 20 employees
            }
            System.out.print("Choose an option: ");

            int choice;
            try {
                choice = Integer.parseInt(scanner.nextLine()); // Convert user input to integer
            } catch (Exception e) {
                System.out.println("Invalid input."); // Detect non-numeric input
                continue; // Restart menu loop
            }

            MenuOption selected = MenuOption.fromCode(choice); // Match numeric input to enum
            if (selected == null) { // If invalid menu selection
                System.out.println("Invalid menu option.");
                continue;
            }

            // Execute appropriate action based on menu selection
            switch (selected) {
                case DISPLAY_FIRST_20:
                    displayFirst20(employees); // Display first 20 employees
                    break;

                case SEARCH_BY_NAME:
                    handleSearch(employees, scanner); // Bianry search
                    break;

                case ADD_EMPLOYEE:
                    handleAddEmployee(employees, newlyAdded, departments, managerTypes, scanner); // Add new employee
                    sortEmployees(employees); // Re-sort list after adding
                    break;

                case SHOW_HIERARCHY:
                    handleHierarchy(employees); // Display binary tree structure
                    break;

                case EXIT:
                    running = false; // End loop
                    System.out.println("Goodbye!"); // Exit message
                    break;
            }
        }

        scanner.close(); // Close scanner at end of program
    }
        
    //READING FILE - from .txt file (downloaded from Moodle)
    //-----------------------------------------------------
    private static boolean getEmployeesFromFile(String FILE_NAME, List<Employee> employees, Set<String> departments, Set<String> managerTypes){
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) { // Open reader

            String line = br.readLine(); // Read header line and ignore

            // Read each line until file ends
            while ((line = br.readLine()) != null) {

                if (line.trim().isEmpty()) continue; // Skip blank lines

                String[] parts = line.split(","); // Split values

                if (parts.length < 9) continue; // Making sure 9 rows exist

                // Extract specific data fields
                String firstName = parts[0].trim();
                String lastName = parts[1].trim();
                String department = parts[5].trim();
                String managerType = parts[6].trim();

                // Create employee object and add to list
                Employee e = new Employee(firstName, lastName, department, managerType);
                employees.add(e);

                // Track unique departments and manager types
                departments.add(department);
                managerTypes.add(managerType);
            }

            System.out.println("Loaded " + employees.size() + " employees from file.");
            return true; // File read successfully

        } catch (IOException e) {
            System.out.println("File Load Error: " + e.getMessage()); // Print reason for failure
            return false; // File read failed
        }    
}
 
    // MERGE SORT – sorted by firstName
    // ----------------------------------------------
    private static void sortEmployees(List<Employee> employees) {
        Employee[] arr = employees.toArray(new Employee[0]);   // Convert list to array
        mergeSort(arr, 0, arr.length - 1);                     // Call recursive merge sort
        employees.clear();                                     // Clear original list
        employees.addAll(Arrays.asList(arr));                  // Add sorted items back
    }

    //  Merge sort
    private static void mergeSort(Employee[] arr, int left, int right) {
        if (left < right) {   // Condition
            int mid = (left + right) / 2;    // Find middle

            mergeSort(arr, left, mid);       // Sorting left side
            mergeSort(arr, mid + 1, right);  // Sorting right side

            merge(arr, left, mid, right);    // Merge both sides
        }
    }

    // Merge function combines two sorted sides
    private static void merge(Employee[] arr, int left, int mid, int right) {

        int n1 = mid - left + 1;  // Size of left side
        int n2 = right - mid;     // Size of right side

        Employee[] L = new Employee[n1];   // Temporary array for left side
        Employee[] R = new Employee[n2];   // Temporary array for right side

        // Copy data into temporary arrays
        for (int i = 0; i < n1; i++) L[i] = arr[left + i];
        for (int j = 0; j < n2; j++) R[j] = arr[mid + 1 + j];

        int i = 0, j = 0, k = left; // Index variables

        // Merge elements in correct alphabetical order
        while (i < n1 && j < n2) {
            if (L[i].firstName.compareToIgnoreCase(R[j].firstName) <= 0)
                arr[k++] = L[i++]; // Left item comes first
            else
                arr[k++] = R[j++]; // Right item comes first
        }

        // Copy remaining elements
        while (i < n1) arr[k++] = L[i++];
        while (j < n2) arr[k++] = R[j++];
    }

    // DISPLAY FIRST 20 EMPLOYEES (Sorted)- Task 1 of Assignment
    // ---------------------------------------------------------
    private static void displayFirst20(List<Employee> employees) {
        System.out.println("***** FIRST 20 EMPLOYEES *****");

        int limit = Math.min(20, employees.size()); // Minimum 20 employees
        for (int i = 0; i < limit; i++) {
            System.out.println((i + 1) + ". " + employees.get(i)); // Print numbered list
        }
    }  
}