package project.view.employee.empEmployee;

import project.model.Employee;
import project.service.impl.EmployeeService;

import java.util.Scanner;

public class SearchEmployeeByIdDialog {
    public static Scanner scanner = new Scanner(System.in);
    private EmployeeService employeeService;

    public SearchEmployeeByIdDialog(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public Employee input() {
        Employee employee = null;
        while (employee == null) {
            System.out.println("ID на служител: ");
            var answer = scanner.nextLine();
            int id = 0;
            try {
                id = Integer.parseInt(answer);
            } catch (NumberFormatException e) {
                System.out.println("Невалиден формат на ID. Моля въведете число.");
            }
            employee = employeeService.getById(id);
            if (employee == null) {
                System.out.println("Служител с такова ID не съществува!");
            }
        }
        return employee;
    }
}
