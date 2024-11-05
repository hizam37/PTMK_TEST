//package org.example.service;
//
//
//import lombok.RequiredArgsConstructor;
//import org.example.entity.Employee;
//import org.example.repository.EmployeeRepository;
//
//
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.time.Period;
//import java.util.List;
//
//@Service
//@RequiredArgsConstructor
//public class EmployeeService {
//
//
//    private final EmployeeRepository employeeRepository;
//
//    public void registerEmployee(Employee employee) {
//        employeeRepository.save(employee);
//    }
//
//    public int CalculateAge(LocalDate employeeDoB) {
//       return Period.between(employeeDoB, LocalDate.now()).getYears();
//    }
//
//
//    public List<Employee> getAllEmployees()
//    {
//        return employeeRepository.findAll();
//    }
//
//    public List<Employee> getAllEmployeesByGenderAndName()
//    {
//        return employeeRepository.findEmployeeByGenderAndAndName("Male","F");
//    }
//
//}
//
