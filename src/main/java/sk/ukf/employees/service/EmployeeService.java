package sk.ukf.employees.service;

import org.springframework.transaction.annotation.Transactional;
import sk.ukf.employees.entity.Employee;

import java.util.List;

public interface EmployeeService {
    List<Employee> findAll();

    Employee findById(int id);

    @Transactional
    Employee save(Employee employee);

    @Transactional
    void deleteById(int id);
}
