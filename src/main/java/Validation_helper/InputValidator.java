package Validation_helper;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class InputValidator {
    
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<String> validStatuses = Arrays.asList("SCHEDULED", "COMPLETED", "CANCELLED");
    private static final List<String> validRoles  = Arrays.asList("PATIENT", "DOCTOR");
    
    public static int getValidatedInt(String s) {
        while (true) {
            System.out.print(s);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please try again.");
            }
        }
    }

    public static String getValidatedDate(String s) {
        while (true) {
            System.out.print(s);
            String input = scanner.nextLine().trim();
            try {
               LocalDate parsedDate =  LocalDate.parse(input);
                if(parsedDate.isAfter(LocalDate.now())){
                return input;}
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Use YYYY-MM-DD.");
            }
        }
    }

    public static String getValidatedTime(String s) {
        while (true) {
            System.out.print(s);
            String input = scanner.nextLine().trim();
            try {
                LocalTime.parse(input);
                return input;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid time format. Use HH:MM:SS.");
            }
        }
    }

    public static String getValidatedStatus(String s) {
        while (true) {
            System.out.print(s);
            String input = scanner.nextLine().trim();
            if (validStatuses.contains(input.toUpperCase())) {
                return input;
            } else {
                System.out.println("Invalid status. Choose from: Scheduled, Completed, Cancelled.");
            }
        }
    }
    
    public static String getValidatedRole(String s){
        while(true) {
            System.out.println(s);
            String input = scanner.nextLine().trim();
            if(validRoles.contains(input.toUpperCase())){
                return input;
            }
            else {
                System.out.println("Invalid Role. Choose From: Doctor & Patient");
            }
        }
    }
}
