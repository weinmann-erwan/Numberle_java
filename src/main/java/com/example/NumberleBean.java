package com.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

@Named
@SessionScoped
public class NumberleBean implements Serializable{

    private String message = "";
    private String randomNumber;
    private char[] charArray;
    private String[] numbersInput = new String[6];
    private String userInput = "";
    private String comparisonResult = "";

    public String randomNumber() {
        // Get the InputStream for the file
        InputStream filePath = getClass().getClassLoader().getResourceAsStream("numbers.txt");

        List<String> numbers = new ArrayList<>();
        
        // Check if the file was found
        if (filePath == null) {
            System.out.println("File not found!");
            return message = "File not found";  // Return null or a message in case the file is not found
        }
        // Try-with-resources block to handle the reading
        try (BufferedReader br = new BufferedReader(new InputStreamReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                numbers.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        Random random = new Random();
        int randomIndex = random.nextInt(numbers.size()); // Get a random index
        message = numbers.get(randomIndex); // Get the random number from the list
        // Convert the message into a character array
        charArray = message.toCharArray(); // Convert the string to a char array
        // For debugging: print the character array
        System.out.println("Character array: " + Arrays.toString(charArray));
        System.out.println(numbersInput[0]);

        return message;
    }

    public void compareUserInput() {
        // Debugging output to check inputs
        System.out.println("numbersInput: " + Arrays.toString(numbersInput)); // Proper way to print an array
        System.out.println("charArray: " + Arrays.toString(charArray));       // Proper way to print a char array
    
        // Check if the lengths match (assuming each element of numbersInput should correspond to one charArray element)
        if (numbersInput.length != charArray.length) {
            comparisonResult = "Incorrect number of characters!";
            System.out.println("Input length mismatch.");
            return;
        }
    
        StringBuilder result = new StringBuilder();
        
        // Loop through each character and compare
        // TESTTTT
        for (int i = 0; i < charArray.length; i++) {
            if (numbersInput[i] != null && !numbersInput[i].isEmpty()) {
                char inputChar = numbersInput[i].charAt(0);  // Assuming each numbersInput[i] is a single character string
                if (inputChar == charArray[i]) {
                    // Correct character in correct position
                    result.append(inputChar).append(" is correct. ");
                } else if (new String(charArray).indexOf(inputChar) != -1) {
                    // Character exists but is in the wrong position
                    result.append(inputChar).append(" is in the wrong position. ");
                } else {
                    // Character does not exist
                    result.append(inputChar).append(" does not exist. Try again ");
                }
            } else {
                // Handle case where numbersInput[i] is empty or null
                result.append("No input at position ").append(i + 1).append(". ");
            }
        }
    
        // Store the result to display to the user
        comparisonResult = result.toString();
        System.out.println(comparisonResult);  // For debugging purposes
    }

    public void processInput() {
        userInput = numbersInput[0];  // Assuming you're using the first input field
        System.out.println("User input: " + userInput);
        
        // Compare the user input with the random number
        compareUserInput();
    }

    @PostConstruct
    public void init() {
        randomNumber = randomNumber();
    }

    public String getRandomNumber() {
        return randomNumber;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String[] getNumbers() {
        return numbersInput;
    }

    public char[] getCharArray() {
        return charArray;
    }
   
    public String[] getNumbersInput() {
        return numbersInput;
    }

    public void setNumbersInput(String[] numbersInput) {
        this.numbersInput = numbersInput; // Assign the passed array to the class variable
    }

    public String getUserInput() {
        return userInput;
    }

    public void setUserInput(String userInput) {
        this.userInput = userInput;
    }

    public String getComparisonResult() {
        return comparisonResult;
    }
}