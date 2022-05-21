package project.view.logIn;

import project.model.Employee;
import project.service.impl.EmployeeService;

import java.util.Scanner;

public class LogInDialogEmployee {
    public static Scanner scanner = new Scanner(System.in);
    public EmployeeService employeeService;

    public LogInDialogEmployee(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public Employee input() {
        Employee employee = null;
        while (employee == null) {
            System.out.println("Потребителско име: ");
            var answer = scanner.nextLine();
            try {
                employee = employeeService.findEmployeeByUsername(answer);
            } catch (NullPointerException e) {
                System.out.println("Служителят не съществува.");
            }
            if (employee != null) {
                System.out.println("Парола:");
                var pass = scanner.nextLine();
                while (true) {
                    if (pass.equals(employee.getPassword())) {
                        return employee;
                    }
                    System.out.println("Грешна парола. Опитайте отново.");
                    pass = scanner.nextLine();
                }
            } else {
                System.out.println("Грешка: Служител с потребителско име: '" + answer + "' не съществува.");
            }
        }
        System.out.println("Неуспешен вход.");
        return null;
    }
}
