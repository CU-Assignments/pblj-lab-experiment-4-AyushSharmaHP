import java.util.ArrayList;
import java.util.Scanner;

class Employee {
    int id;
    String name;
    double salary;

    Employee(int id, String name, double salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Salary: " + salary;
    }
}

public class Main {
    static ArrayList<Employee> employees = new ArrayList<>();

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        displayAllEmployees();

        addEmployee(101, "Anish", 50000);
        addEmployee(102, "Bobby", 60000);

        displayAllEmployees();
    }

    public static void addEmployee(int id, String name, double salary) {
        employees.add(new Employee(id, name, salary));
        System.out.println("Employee added: " + name);
    }

    public static void updateEmployee(int id, String newName, double newSalary) {
        Employee emp = findEmployeeById(id);
        if (emp != null) {
            emp.name = newName;
            emp.salary = newSalary;
            System.out.println("Employee updated: " + newName);
        } else {
            System.out.println("Employee not found.");
        }
    }

    public static void removeEmployee(int id) {
        Employee emp = findEmployeeById(id);
        if (emp != null) {
            employees.remove(emp);
            System.out.println("Employee removed: " + emp.name);
        } else {
            System.out.println("Employee not found.");
        }
    }

    public static void searchEmployee(String query) {
        Employee emp = findEmployeeById(Integer.parseInt(query));
        if (emp != null) {
            System.out.println("Employee found: " + emp);
        } else {
            emp = findEmployeeByName(query);
            if (emp != null) {
                System.out.println("Employee found: " + emp);
            } else {
                System.out.println("Employee not found.");
            }
        }
    }

    public static void displayAllEmployees() {
        if (employees.isEmpty()) {
            System.out.println("No employees found.");
        } else {
            System.out.println("Employee List:");
            for (Employee emp : employees) {
                System.out.println(emp);
            }
        }
    }

    public static Employee findEmployeeById(int id) {
        for (Employee emp : employees) {
            if (emp.id == id) {
                return emp;
            }
        }
        return null;
    }

    public static Employee findEmployeeByName(String name) {
        for (Employee emp : employees) {
            if (emp.name.equalsIgnoreCase(name)) {
                return emp;
            }
        }
        return null;
    }
}
