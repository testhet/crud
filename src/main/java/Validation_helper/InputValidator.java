package Validation_helper;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class InputValidator {

    private static final Scanner scanner = new Scanner(System.in);
    private static final List<String> validStatuses = Arrays.asList("SCHEDULED", "COMPLETED", "CANCELLED");
    private static final List<String> validRoles = Arrays.asList("PATIENT", "DOCTOR");
    private static final List<String> validGender = Arrays.asList("MALE", "FEMALE", "OTHER");

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
                LocalDate parsedDate = LocalDate.parse(input);
                if (parsedDate.isAfter(LocalDate.now())) {
                    return input;
                }
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

    public static String getValidatedRole(String s) {
        while (true) {
            System.out.println(s);
            String input = scanner.nextLine().trim();
            if (validRoles.contains(input.toUpperCase())) {
                return input;
            } else {
                System.out.println("Invalid Role. Choose From: Doctor & Patient");
            }
        }
    }

    public static String getValidatedEmail(String s) {
        Scanner scanner = new Scanner(System.in);
        String emailPattern = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9_.-]+\\.[a-zA-Z]{2,5}$";

        while (true) {
            System.out.print(s);
            String input = scanner.nextLine().trim();

            if (Pattern.matches(emailPattern, input)) {
                return input;
            } else {
                System.out.println("Invalid Email, Please Enter Correct Email.");
            }
        }
    }

    public static String getValidatedPassword(String s) {
        while (true) {
            System.out.println(s);
            String input = scanner.nextLine();

            if(input.isEmpty()){
                System.out.println("Password Can't Be empty");
            } else if (input.length() < 6) {
                System.out.println("Password must be at least 6 characters long.");
            } else if (!input.matches(".*[A-Za-z].*")) {
                System.out.println("Password must contain at least one letter.");
            } else if (!input.matches(".*\\d.*")) {
                System.out.println("Password must contain at least one number.");
            } else if (!input.matches(".*[!@#$%^&*()_+=\\-].*")) {
                System.out.println("Password must contain at least one special character (!@#$%^&*()_+=-).");
            } else {
                return input;
            }
        }
    }


    public static String getValidatedName(String s)  {
    Scanner scanner = new Scanner(System.in);
    String namePattern = "^[a-zA-Z\\s'-]+$";

        while (true) {
        System.out.print(s);
        String input = scanner.nextLine().trim();

        if (Pattern.matches(namePattern, input)) {
            return input;
        } else {
            System.out.println("Invalid Name Formate, Please Enter Correct name.");
        }
    }
}

    public static String getValidatedGender(String s) {
        while (true) {
            System.out.print(s);
            String input = scanner.nextLine().trim();
            if (validGender.contains(input.toUpperCase())) {
                return input;
            } else {
                System.out.println("Invalid Gender. Choose from: Male, Female, Other.");
            }
        }
    }

    public static String getValidatedTextField(String s) {
        Scanner scanner = new Scanner(System.in);
        String textPattern = "^[a-zA-Z0-9,-]+$";

        while (true) {
            System.out.print(s);
            String input = scanner.nextLine().trim();

            if (Pattern.matches(textPattern, input)) {
                return input;
            } else {
                System.out.println("Invalid Input, Please Enter Correct Input.");
            }
        }
    }

    public static long getValidatedPhone(String prompt) {
        Scanner scanner = new Scanner(System.in);
        String phonePattern = "^[0-9]{10}$";

        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();

            if (Pattern.matches(phonePattern, input)) {
                return Long.parseLong(input);
            } else {
                System.out.println("Invalid Input. Please enter a 10-digit phone number.");
            }
        }
    }


    public static String getValidatedInsuranceId(String s) {
        Scanner scanner = new Scanner(System.in);
        String IDPattern = "^[A-Z0-9]+$";

        while (true) {
            System.out.print(s);
            String input = scanner.nextLine().trim();

            if (Pattern.matches(IDPattern, input)) {
                return input;
            } else {
                System.out.println("Invalid InsuranceID, Please Enter Correct ID Ex.R49835J4");
            }
        }
    }
}
