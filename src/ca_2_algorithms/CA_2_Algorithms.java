/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ca_2_algorithms;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
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
    //----------------
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
    // ---------
    public static void main(String[] args) {

        List<Employee> employees = new ArrayList<>();      // Holds employees loaded from file
        List<Employee> newlyAdded = new ArrayList<>();     // Holds employees added during runtime
        Set<String> departments = new LinkedHashSet<>();   // Stores unique departments
        Set<String> managerTypes = new LinkedHashSet<>();  // Stores unique manager types
        
    
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
}