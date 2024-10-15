package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

@Named
@ViewScoped
public class NumberleBean implements Serializable{

    private String message = "";
    private String randomNumber;
    private char[] charArray;
    private String[] numbersInput = new String[6];
    private String comparisonResult = "";
    private String[] boxColors = new String[6];
    private boolean showResultButton = true;
    private boolean showResult = false;
    private int counter = 0;
    private List<String> previousResults = new ArrayList<>();

    // Function that choose a number randomly in the numbers.txt and transform it in list of char
    public String randomNumber() {
       
        InputStream filePath = getClass().getClassLoader().getResourceAsStream("numbers.txt");
        List<String> numbers = new ArrayList<>();
        
        if (filePath == null) {
            System.out.println("File not found!");
            return message = "File not found";
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                numbers.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        Random random = new Random();
        int randomIndex = random.nextInt(numbers.size()); 
        message = numbers.get(randomIndex); 
        
        charArray = message.toCharArray();
    
        System.out.println("Character array: " + Arrays.toString(charArray));
        System.out.println(numbersInput[0]);

        return message;
    }

    // Function that look what the users wrote on the page and compare it to the numbers choosed in the randomNumber() function 
    public void compareUserInput() {
        boxColors = new String[numbersInput.length];
        StringBuilder result = new StringBuilder();
        boolean isCorrect = true;
        showResult = true;
    
        boolean[] guessed = new boolean[charArray.length];
    
        for (int i = 0; i < charArray.length; i++) {
            char inputChar = numbersInput[i].charAt(0);
    
            if (inputChar == charArray[i]) {
                guessed[i] = true; 
                boxColors[i] = "correct"; 
                result.append(inputChar).append(" is correct. ");

            } 
            
            else if (new String(charArray).contains(String.valueOf(inputChar))) {
                int correctIndex = new String(charArray).indexOf(inputChar);
    
                if (guessed[correctIndex]) {
                    boxColors[i] = "incorrect";
                    result.append(inputChar).append(" was already guessed correctly. ");
                    isCorrect = false;
                } 
                
                else {

                    if (correctIndex < i) {
                        boxColors[i] = "wrong-left"; 
                        result.append(inputChar).append(" should be at the left. ");
                    } 
                    
                    else {
                        boxColors[i] = "wrong-right";
                        result.append(inputChar).append(" should be at the right. ");
                    }

                    isCorrect = false;
                }
            } 
        
            else {
                boxColors[i] = "incorrect";
                result.append(inputChar).append(" doesn't exist. ");
                isCorrect = false;
            }
        }
    
        counter++;
        
        if (isCorrect) {
            comparisonResult = "Congrats, you discovered the number!";
            showResultButton = false;
        } 
        
        else if (counter >= 6) {
            comparisonResult = "You lose. The correct number was: " + new String(charArray);
            showResultButton = false;
        } 
        
        else {
            comparisonResult = result.toString();   
        }

        showResult = true;
        previousResults.add("Attempt " + counter + ": " + comparisonResult);
    
        System.out.println(comparisonResult);
    }
    
    // GETTERS

    @PostConstruct
    public void init() {
        randomNumber = randomNumber();
    }

    public String[] getBoxColors() {
            return boxColors;
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
        this.numbersInput = numbersInput; 
    }

    public String getComparisonResult() {
        return comparisonResult;
    }

    public boolean isShowResult() {
        return showResult;
    }

    public List<String> getPreviousResults() {
        return previousResults;
    }


    public boolean isShowResultButton() {
        return showResultButton;
    }
}