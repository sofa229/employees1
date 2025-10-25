package sk.ukf.employees.service;

import sk.ukf.employees.entity.Employee;

import java.util.List;

public interface EmployeeService {

    List<Employee> findAll();

    Employee findById(int id);

    Employee save(Employee employee);

    void deleteById(int id);

    List<Employee> findByFirstNameAndLastName(String firstName, String lastName);

    List<Employee> getSortedEmployees();
}
