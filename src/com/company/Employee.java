package com.company;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;


//practice for try and catch
//hashmap

public class Employee {


    //declare the hashmap to hold the employee data.
    static HashMap<String, List<Double>> Employee = new HashMap<>();


    public static void main(String[] args) throws IOException {

        double empNum;
        double annualSalary = 0;
        String name = "";
        boolean flag = true;
        Scanner ac = new Scanner(System.in);
        Transferdata();


        while(!(name.equals("ex"))) {
            //asks for input
            System.out.println("Enter the employee name, type ex to exit");
            name = ac.nextLine();
            if (name.equals("ex")){
                break;
            }
            System.out.println("Enter the employee number");
            empNum = ac.nextDouble();
            flag = true;
            while(flag){
                try{
                    System.out.println("Emter the employee salary:");
                    //convert to Double the input
                    annualSalary = ac.nextDouble();
                    if(annualSalary <0 || annualSalary == 0){
                        throw new MyError("salary should be greater than zero");
                    }
                    // to get out of the loop
                    flag = false;
                    Employee.put(name, new ArrayList<>(Arrays.asList(empNum, empNum)));
                    ac.nextLine();

                }
                catch(InputMismatchException a) {
                    System.out.println("your input should be a number!");
                    ac.nextLine();
                }

                catch (MyError message1){
                    System.out.println(message1.getMessage());
                }

            }

        }

        Display();
        SaveFile();
        System.out.println("data is saved in the text file");
    }

    public static void Transferdata(){
        //creating a relative file
        File tempFile = new File("myEmployeeList.txt");
        //ask a boolean, checking if file exists
        boolean exists = tempFile.exists();
        //this is where they the input will be temporarily stored; it will be a list
        String input[];
        // try and catch to see if file exists
        try {
            // if the file exists it will read the file
            if (exists) {

                //declaring a new scanner object to read from the file
                Scanner acl = new Scanner(tempFile);
                while(acl.hasNextLine()){
                    //placed in the string data
                    String data = acl.nextLine();
                    //splitting the input
                    input = data.split("\\s");
                    //placing the data inside the hashmap
                    //the key is the student name, employee number and salary

                    double EmpNum = Double.parseDouble(input[1]);
                    double EmpSal = Double.parseDouble(input[2]);

                    Employee.put(input[0], new ArrayList<>(Arrays.asList(EmpNum, EmpSal)));


                }
            }
        }
        // solution for the scanner acl
        catch (FileNotFoundException e){
            System.out.println("This file does not exists");
        }

    }

    public static void Display(){
        int count = Employee.size();
        System.out.println("there are currently" + count + "employees in your records \n");
        for(Map.Entry<String,List<Double>> entry:Employee.entrySet()){
            System.out.print("Employee name" + "\t" + entry.getKey());
            System.out.print("Employee number" + "\t" + Employee.get(entry.getKey()).get(0));
            System.out.print("Employee salary" + "\t" + Employee.get(entry.getKey()).get(1));
        }
    }

    public static void SaveFile() throws IOException {
        Path file = Paths.get("MyEmployeeList.txt");
        OutputStream output = new BufferedOutputStream(Files.newOutputStream(file,APPEND,CREATE));
        BufferedWriter Writer = new BufferedWriter(new OutputStreamWriter(output));
        //temporary string data to be created before being saved
        String data;
        try{
            for (Map.Entry<String,List<Double>> entry:Employee.entrySet()){
                data = entry.getKey() + " " + Employee.get(entry.getKey()).get(0) + " " + Employee.get(entry.getKey()).get(1) + "\n";
                Writer.write(data);
            }
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        Writer.close();


    }



}
