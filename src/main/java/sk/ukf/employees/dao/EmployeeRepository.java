package sk.ukf.employees.dao;
import org.springframework.data.jpa.repository.JpaRepository;
import sk.ukf.employees.entity.Employee;
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
    //nie je potrebné písať žiaden kód
}



