package sk.ukf.employees.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NotBlank(message = "Meno je povinné")
    @Size(min = 2, max = 50, message = "Meno musí mať 2-50 znakov")
    @Column(name = "first_name")
    private String firstName;

    @NotBlank(message = "Priezvisko je povinné")
    @Size(min = 2, max = 50, message = "Priezvisko musí mať 2-50 znakov")
    @Column(name = "last_name")
    private String lastName;

    @Past(message = "Dátum narodenia musí byť v minulosti")
    @Column(name = "birth_date")
    private LocalDate birthDate;

    @NotBlank(message = "Email je povinný")
    @Email(message = "Neplatný email")
    @Column(name = "email", unique = true)
    private String email;

    @Pattern(regexp = "^\\+?[0-9\\s-]{9,15}$", message = "Neplatné telefónne číslo")
    @Column(name = "phone")
    private String phone;

    @NotBlank(message = "Pracovná pozícia je povinná")
    @Size(min = 2, max = 100, message = "Pracovná pozícia musí mať 2-100 znakov")
    @Column(name = "job_title")
    private String jobTitle;

    @NotNull(message = "Plat je povinný")
    @DecimalMin(value = "0.0", inclusive = false, message = "Plat musí byť kladné číslo")
    @Column(name = "salary")
    private Double salary;

    @NotNull(message = "Typ úväzku je povinný (true/false)")
    @Column(name = "full_time")
    private Boolean fullTime;

    public Employee() {
    }

    public Employee(String firstName, String lastName, LocalDate birthDate, String email,
                    String phone, String jobTitle, Double salary, Boolean fullTime) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.email = email;
        this.phone = phone;
        this.jobTitle = jobTitle;
        this.salary = salary;
        this.fullTime = fullTime;
    }

    public Employee(int id, String firstName, String lastName, LocalDate birthDate, String email,
                    String phone, String jobTitle, Double salary, Boolean fullTime) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.email = email;
        this.phone = phone;
        this.jobTitle = jobTitle;
        this.salary = salary;
        this.fullTime = fullTime;
    }

    // Gettre a settre

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public Boolean getFullTime() {
        return fullTime;
    }

    public void setFullTime(Boolean fullTime) {
        this.fullTime = fullTime;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", jobTitle='" + jobTitle + '\'' +
                ", salary=" + salary +
                ", fullTime=" + fullTime +
                '}';
    }
}
