package org.example.entity;

import lombok.*;

import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

    private String name;

    private String middleName;

    private String lastName;

    private LocalDate dateOfBirth;

    private String gender;

    public int calculateAge(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }

    public void saveToData(Connection con, String name, String middleName, String lastName, LocalDate dateOfBirth, String gender) throws SQLException {
        String sql = "INSERT INTO employees (name,middleName,lastName,dateOfBirth,gender) values(?,?,?,?,?)";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, middleName);
            stmt.setString(3, lastName);
            stmt.setDate(4, Date.valueOf(dateOfBirth));
            stmt.setString(5, gender);
            stmt.executeUpdate();
            con.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void saveDataInBatches(Connection con,List<Employee>employees) throws SQLException {
        con.setAutoCommit(false);
        String sql = "INSERT INTO employees (name,middleName,lastName,dateOfBirth,gender) values(?,?,?,?,?)";
        try (PreparedStatement stmt = con.prepareStatement(sql)) {
            for (int i=0;i<employees.size(); i++) {
                stmt.setString(1, employees.get(i).getName());
                stmt.setString(2, employees.get(i).getMiddleName());
                stmt.setString(3, employees.get(i).getLastName());
                stmt.setDate(4, Date.valueOf(employees.get(i).getDateOfBirth()));
                stmt.setString(5, employees.get(i).getGender());
                stmt.addBatch();
                if (i % 100 == 0) {
                    stmt.executeBatch();
                    con.commit();
                }

            }
            stmt.executeBatch();
            con.commit();
            con.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }


    public List<Employee> generateEmployees()
    {
        List<Employee> employees = new ArrayList<>();
        String[] firstNameOfMale = {
                "ADAM", "BORIS", "CEMYON", "DANYA",
                "EAGER", "FIDOR", "GREGOR", "HIDER",
                "ILYA", "JEMMY", "KEM", "LIAV",
                "MICK", "NICK", "OLIVER", "PETER",
                "QIMMY", "RAMSAY", "SERGEY", "TOM",
                "URAL", "VITALY", "WILLY", "XAVIAR",
                "ZAFIER"
        };
        String[] middleNameOfMale = {
                "ADAMOVICH", "BORISOVICH", "CEMYONOVICH", "DANILLOVICH",
                "EAGEROVICH", "FIDOROVICH", "GREGOROVICH", "HIDEROVICH",
                "ILYAVICH", "JEMMIVOCH", "KEMOVICH", "LIAVOVICH",
                "MICKOVICH", "NICKOVICH", "OLIVEROVICH", "PETROVICH",
                "QIMMYOVICH", "RAMSOVICH", "SERGEYOVICH", "TOMOVICH",
                "URALOVICH", "VITALYVICH", "WILLYVICH", "XAVIARVICH",
                "ZAFIEROVICH"
        };

        String[] lastNameOfMale = {
                "ADAMOV", "BORISOV", "CEMYONOV", "DANILLOV",
                "EAGEROV", "FIDOROV", "GREGOROV", "HIDEROV",
                "ILYAV", "JEMMIV", "KEMOV", "LIAVOV",
                "MICKOV", "NICKOV", "OLIVEROV", "PETROV",
                "QIMMYOV", "RAMSOV", "SERGEYOV", "TOMOV",
                "URALOV", "VITALYV", "WILLYV", "XAVIARV",
                "ZAFIEROV"
        };
        String[] firstNameOfFemale = {
                "ADA", "BORISA", "CELLINE", "DARYA",
                "EMILY", "FIDORA", "GREGORIA", "HILLY",
                "IMA", "JENNY", "KEMLY", "LEONA",
                "MARIA", "NARA", "OLIVIA", "PAMILA",
                "QIMMY", "RAMSAY", "SARAH", "TIMOUGHY",
                "URIEL", "VITALIA", "WILLY", "XENIA",
                "ZARA"
        };
        String[] middleNameOfFemale = {
                "ADAMOVNA", "BORISOVNA", "CEMYONOVNA", "DANILLOVNA",
                "EAGEROVNA", "FIDOROVNA", "GREGOROVNA", "HIDEROVNA",
                "ILYAVNA", "JEMMIVNA", "KEMOVNA", "LIAVOVNA",
                "MICKOVNA", "NICKOVNA", "OLIVEROVNA", "PETROVNA",
                "QIMMYOVNA", "RAMSOVNA", "SERGEYOVNA", "TOMOVNA",
                "URALOVNA", "VITALVNA", "WILLYVA", "XAVIARVNA",
                "ZAFIEROVNA"
        };

        String[] lastNameOfFemale = {
                "ADAMOVA", "BORISOVA", "CEMYONOVA", "DANILLOVA",
                "EAGEROVA", "FIDOROVA", "GREGOROV", "HIDEROVA",
                "ILYAVA", "JEMMIVA", "KEMOVA", "LIAVOVA",
                "MICKOVA", "NICKOV", "OLIVEROVA", "PETROVA",
                "QIMMYOVA", "RAMSOVA", "SERGEYOVA", "TOMOVA",
                "URALOVA", "VITALYVA", "WILLYVA", "XAVIARVA",
                "ZAFIEROVA"
        };


        LocalDate startDays = LocalDate.of(1970,1,1);
        LocalDate currenday = LocalDate.now();
        long days = ChronoUnit.DAYS.between(startDays,currenday);
        for(int i=1;i<=10000;i++)
        {
           String gender = i % 2 == 0 ? "MALE" : "FEMALE";
           String firstName = gender.equals("MALE") ? firstNameOfMale[i%firstNameOfMale.length] : firstNameOfFemale[i%firstNameOfFemale.length];
           String middleName = gender.equals("MALE") ? middleNameOfMale[i% middleNameOfMale.length] : middleNameOfFemale[i%middleNameOfFemale.length];
           String lastName = gender.equals("MALE") ? lastNameOfMale[i%lastNameOfMale.length] : lastNameOfFemale[i%lastNameOfFemale.length];
           int randomDays = (int) (Math.random()*days);
           LocalDate dateOfBirth = startDays.plusDays(randomDays);
           if(gender.equals("MALE") && lastName.contains("F"))
            {
                for(int ii=0;ii<100;ii++)
                {
                employees.add(new Employee(firstName,middleName,lastName,dateOfBirth,gender));
                }
            }
           employees.add(new Employee(firstName,middleName,lastName,dateOfBirth,gender));
        }
        return employees;
    }
}
