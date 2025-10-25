package sk.ukf.employees.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sk.ukf.employees.dao.EmployeeRepository;
import sk.ukf.employees.entity.Employee;
import sk.ukf.employees.exception.EmailAlreadyExistsException;
import sk.ukf.employees.exception.ObjectNotFoundException;

import java.util.List;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee findById(int id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Zamestnanec", id));
    }

    @Transactional
    @Override
    public Employee save(Employee employee) {
        // Ak ide o nového zamestnanca (id == 0)
        if (employee.getId() == 0) {
            if (employeeRepository.existsByEmail(employee.getEmail())) {
                throw new EmailAlreadyExistsException(employee.getEmail());
            }
        } else {
            // Ak aktualizujeme existujúceho, kontrolujeme duplicitu emailu
            Employee existingWithEmail = employeeRepository.findByEmail(employee.getEmail()).orElse(null);
            if (existingWithEmail != null && existingWithEmail.getId() != employee.getId()) {
                throw new EmailAlreadyExistsException(employee.getEmail());
            }
        }

        return employeeRepository.save(employee);
    }

    @Transactional
    @Override
    public void deleteById(int id) {
        if (!employeeRepository.existsById(id)) {
            throw new ObjectNotFoundException("Zamestnanec", id);
        }
        employeeRepository.deleteById(id);
    }

    @Override
    public List<Employee> findByFirstNameAndLastName(String firstName, String lastName) {
        return employeeRepository.findByFirstNameAndLastName(firstName, lastName);
    }

    @Override
    public List<Employee> getSortedEmployees() {
        return employeeRepository.findAllByOrderByLastNameAsc();
    }
}
