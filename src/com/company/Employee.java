package com.company;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;

public class Employee {

    // declare the hashmap to hold the employee data
    static HashMap<String, List<Double>> employee = new HashMap<>();

    public static void main(String[] args) throws IOException {

        // declare variable
	    double empNum, anualSal;
	    String name = "";
	    boolean flag = true;

	    // declare scanner
        Scanner ac = new Scanner(System.in);

        transferData();

        while (!(name.equals("ex"))){
            System.out.print("Enter the employee name, type \"ex\" to exit: ");
            name = ac.nextLine();
            if (name.equals("ex")){
                break;
            }
            System.out.print("Enter the employee number: ");
            empNum = ac.nextDouble();

            flag = true;
            while (flag){
                try {
                    System.out.print("Enter the employee salary: ");
                    anualSal = ac.nextDouble();
                    if (anualSal < 0 || anualSal == 0){
                        throw new MyError("Salary must be greater than 0.");
                    }
                    flag = false;
                    employee.put(name, new ArrayList<>(Arrays.asList(empNum, anualSal)));
                    ac.nextLine();
                } catch (InputMismatchException e){
                    System.out.println("You should input a number.");
                    ac.nextLine();
                } catch (MyError message){
                    System.out.println(message.getMessage());
                }
            }
        }

        display();

        saveFile();

        System.out.println("Data is saved in text file.");

    }

    public static void transferData(){

        // creating a file object
        File tempFile = new File("MyEmployeeList.txt");

        // checking if file exist
        boolean exists = tempFile.exists();

        // input data read from file
        String[] input;

        // try - catch to known if file is exist
        try {

            // if file is exist
            if (exists){
                Scanner ac1 = new Scanner(tempFile);
                while (ac1.hasNextLine()){
                    String data = ac1.nextLine();
                    input = data.split("\\s");
                    double empNum = Double.parseDouble(input[1]);
                    double empSal = Double.parseDouble(input[2]);
                    employee.put(input[0], new ArrayList<>(Arrays.asList(empNum, empSal)));
                }
            }

          // catch error if file is not exist
        } catch (FileNotFoundException e){
            // print error to screen
            System.out.println("The file is not exist.");
        }
    }

    public static void display(){
        int count = employee.size();
        System.out.println("There are currently " + count + " of employees in you records.\n");
        for (Map.Entry<String,List<Double>> entry : employee.entrySet()){
            System.out.println("Employee name:   \t" + entry.getKey());
            System.out.println("Employee number: \t" + employee.get(entry.getKey()).get(0));
            System.out.println("Employee number: \t" + employee.get(entry.getKey()).get(1));
        }
    }

    public static void saveFile() throws IOException {
        Path file = Paths.get("MyEmployeeList.txt");

        OutputStream output = new BufferedOutputStream(Files.newOutputStream(file, APPEND, CREATE));

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(output));

        String data = "";

        try {
            for (Map.Entry<String,List<Double>> entry : employee.entrySet()){
                data = entry.getKey() + " " + employee.get(entry.getKey()).get(0) + " " + employee.get(entry.getKey()).get(1) + "\n";
                writer.write(data);
            }
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        writer.close();
    }
}
