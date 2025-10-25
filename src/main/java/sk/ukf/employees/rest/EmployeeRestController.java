package sk.ukf.employees.rest;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sk.ukf.employees.dto.ApiResponse;
import sk.ukf.employees.entity.Employee;
import sk.ukf.employees.service.EmployeeService;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class EmployeeRestController {

    private final EmployeeService employeeService;

    @Autowired
    public EmployeeRestController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    // 🔹 1. findAll - vráti všetkých zamestnancov v jednotnom formáte
    @GetMapping("/employees")
    public ResponseEntity<ApiResponse<List<Employee>>> findAll() {
        List<Employee> employees = employeeService.findAll();
        return ResponseEntity.ok(ApiResponse.success(employees, "Zoznam všetkých zamestnancov"));
    }

    // 🔹 2. getEmployee - vráti zamestnanca podľa ID (ak neexistuje, vráti správu, nie výnimku)
    @GetMapping("/employees/{id}")
    public ResponseEntity<ApiResponse<Employee>> getEmployee(@PathVariable int id) {
        Employee employee = employeeService.findById(id);

        if (employee == null) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(ApiResponse.success(null, "Zamestnanec s ID " + id + " nebol nájdený"));
        }

        return ResponseEntity.ok(ApiResponse.success(employee, "Zamestnanec úspešne načítaný"));
    }

    // 🔹 3. createEmployee - vytvorí nového zamestnanca
    @PostMapping("/employees")
    public ResponseEntity<ApiResponse<Employee>> createEmployee(@Valid @RequestBody Employee employee) {
        employee.setId(0); // zabezpečí, že sa vytvorí nový záznam
        Employee savedEmployee = employeeService.save(employee);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(savedEmployee, "Zamestnanec úspešne vytvorený"));
    }

    // 🔹 4. updateEmployee - aktualizuje zamestnanca podľa ID
    @PutMapping("/employees/{id}")
    public ResponseEntity<ApiResponse<Employee>> updateEmployee(@PathVariable int id,
                                                                @Valid @RequestBody Employee employee) {

        employeeService.findById(id); // ak neexistuje, metóda v service to vyrieši

        employee.setId(id);
        Employee updatedEmployee = employeeService.save(employee);

        return ResponseEntity.ok(ApiResponse.success(updatedEmployee, "Zamestnanec úspešne aktualizovaný"));
    }

    // 🔹 5. deleteEmployee - zmaže zamestnanca podľa ID (nevyhadzuje výnimku)
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteEmployee(@PathVariable int id) {
        Employee employee = employeeService.findById(id);

        if (employee == null) {
            // stále vrátime success, len s textom, že neexistuje
            return ResponseEntity.ok(ApiResponse.success(null, "Zamestnanec s ID " + id + " neexistuje, nič sa nezmazalo"));
        }

        employeeService.deleteById(id);
        return ResponseEntity.ok(ApiResponse.success(null, "Zamestnanec úspešne zmazaný"));
    }

    // 🔹 6. Hľadanie podľa mena (firstName alebo lastName)
    @GetMapping("/employees/search")
    public ResponseEntity<ApiResponse<List<Employee>>> searchEmployees(@RequestParam("name") String name) {
        List<Employee> employees = employeeService.findAll();

        List<Employee> filtered = employees.stream()
                .filter(e -> e.getFirstName().toLowerCase().contains(name.toLowerCase())
                        || e.getLastName().toLowerCase().contains(name.toLowerCase()))
                .collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.success(filtered, "Výsledky vyhľadávania pre: " + name));
    }

    // 🔹 7. Zoradenie podľa mena (ASC/DESC)
    @GetMapping("/employees/sort/name")
    public ResponseEntity<ApiResponse<List<Employee>>> sortByName(
            @RequestParam(defaultValue = "asc") String order) {

        List<Employee> employees = employeeService.findAll();

        Comparator<Employee> comparator = Comparator.comparing(Employee::getLastName)
                .thenComparing(Employee::getFirstName);

        if (order.equalsIgnoreCase("desc")) {
            comparator = comparator.reversed();
        }

        employees = employees.stream().sorted(comparator).collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.success(employees,
                "Zamestnanci zoradení podľa mena (" + order.toUpperCase() + ")"));
    }

    // 🔹 8. Zoradenie podľa platu (ASC/DESC)
    @GetMapping("/employees/sort/salary")
    public ResponseEntity<ApiResponse<List<Employee>>> sortBySalary(
            @RequestParam(defaultValue = "asc") String order) {

        List<Employee> employees = employeeService.findAll();

        Comparator<Employee> comparator = Comparator.comparing(Employee::getSalary);
        if (order.equalsIgnoreCase("desc")) {
            comparator = comparator.reversed();
        }

        employees = employees.stream().sorted(comparator).collect(Collectors.toList());

        return ResponseEntity.ok(ApiResponse.success(employees,
                "Zamestnanci zoradení podľa platu (" + order.toUpperCase() + ")"));
    }
}
