package org.example;

import org.example.entity.Employee;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class EmployeeRecordsApp {
    public static void main(String[] args) {

        Connection connection;

        Employee employee = new Employee();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Create table by typing 1");

        String[] inputs = scanner.nextLine().split(" ");

        if (inputs[0].equals("1")) {

            try {

                connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/ptmk_db", "postgres", "1");

                System.out.println("table created with columns name,middleName,lastName,date,gender");

                Statement statement = connection.createStatement();

                String sql = "CREATE TABLE IF NOT EXISTS employees (id SERIAL PRIMARY KEY,"
                        + "name varchar(200),"
                        + "middleName varchar(200),"
                        + "lastName varchar(200),"
                        + "dateOfBirth date,"
                        + "gender varchar(200))";

                statement.executeUpdate(sql);
                System.out.println("Congratulation table has been created)");
                System.out.println("""
                        Choose mode for registering employee type 2\
                        
                        for displaying list of employees type 3\
                        
                        for generating employees and adding them in the database type 4\
                        
                        for displaying list of male employees with lastName that starts with letter f type 5\
                        
                        for (optimized version) displaying list of male employees with lastName that starts with letter f type 6\
                        
                        for deleting the table type 7\
                        
                        for existing type 8""");

                while (true) {

                    inputs = scanner.nextLine().split(" ");

                    if (inputs[0].equals("2")) {

                        System.out.println("Enter details");

                        inputs = scanner.nextLine().split(" ");

                        employee.saveToData(connection, inputs[0], inputs[1], inputs[2], LocalDate.parse(inputs[3]), inputs[4]);

                        System.out.println("Employee details registered successfully");
                    }

                    if (inputs[0].equals("3")) {

                        String listOfEmployees = "SELECT  * FROM employees order by name,middleName,lastName";

                        ResultSet resultSet = statement.executeQuery(listOfEmployees);

                        while (resultSet.next()) {

                            String name = resultSet.getString("name");

                            String middleName = resultSet.getString("middleName");

                            String lastName = resultSet.getString("lastName");

                            Date dateOfBirth = resultSet.getDate("dateOfBirth");

                            String gender = resultSet.getString("gender");

                            String age = String.valueOf(employee.calculateAge(dateOfBirth.toLocalDate()));

                            System.out.println(name + " " + middleName + " " + lastName + " " + dateOfBirth + " " + gender + " " + age);

                        }
                    }

                    if (inputs[0].equals("4")) {

                        List<Employee> employeeList = employee.generateEmployees();

                        employee.saveDataInBatches(connection, employeeList);
                        System.out.println("Employees added successfully");
                    }


                    if (inputs[0].equals("5")) {

                        long start = System.currentTimeMillis();

                        String getMaleEmployeesWithNameThatStartsWithF = "SELECT * FROM employees WHERE gender = 'MALE' AND lastname LIKE 'F%'";

                        ResultSet resultSet = statement.executeQuery(getMaleEmployeesWithNameThatStartsWithF);
                        while (resultSet.next()) {

                            String name = resultSet.getString("name");

                            String middleName = resultSet.getString("middleName");

                            String lastName = resultSet.getString("lastName");

                            Date dateOfBirth = resultSet.getDate("dateOfBirth");

                            String gender = resultSet.getString("gender");

                            String age = String.valueOf(employee.calculateAge(dateOfBirth.toLocalDate()));

                            System.out.println(name + " " + middleName + " " + lastName + " " + dateOfBirth + " " + gender + " " + age);
                        }

                        System.out.println("Duration " + (System.currentTimeMillis() - start));

                        continue;
                    }

                    if (inputs[0].equals("6")) {

                        String indexedSql = "CREATE INDEX IF NOT EXISTS idx_name ON employees (name,middleName,lastName,dateOfBirth,gender)";

                        statement.executeUpdate(indexedSql);

                        long start = System.currentTimeMillis();

                        String getMaleEmployeesWithNameThatStartsWithF = "SELECT * FROM employees WHERE gender = 'MALE' AND lastname LIKE 'F%'";

                        ResultSet resultSet = statement.executeQuery(getMaleEmployeesWithNameThatStartsWithF);
                        while (resultSet.next()) {

                            String name = resultSet.getString("name");

                            String middleName = resultSet.getString("middleName");

                            String lastName = resultSet.getString("lastName");

                            Date dateOfBirth = resultSet.getDate("dateOfBirth");

                            String gender = resultSet.getString("gender");

                            String age = String.valueOf(employee.calculateAge(dateOfBirth.toLocalDate()));

                            System.out.println(name + " " + middleName + " " + lastName + " " + dateOfBirth + " " + gender + " " + age);
                        }

                        System.out.println("Duration " + (System.currentTimeMillis() - start));

                    }

                    if (inputs[0].equals("7")) {
                       String deleteTable = "DROP TABLE employees";
                       statement.executeUpdate(deleteTable);
                        System.out.println("Employees table successfully deleted");
                    }

                    if (inputs[0].equals("8")) {
                        scanner.close();
                        connection.close();
                        break;
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


        }
    }
}