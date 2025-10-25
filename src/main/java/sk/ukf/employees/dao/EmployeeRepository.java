package sk.ukf.employees.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.ukf.employees.entity.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    boolean existsByEmail(String email);

    Optional<Employee> findByEmail(String email);

    List<Employee> findByFirstNameAndLastName(String firstName, String lastName);

    List<Employee> findAllByOrderByLastNameAsc();
}
