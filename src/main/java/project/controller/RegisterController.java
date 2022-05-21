package project.controller;

import project.service.impl.CustomerService;
import project.service.impl.EmployeeService;
import project.service.impl.PositionService;
import project.view.Menu;
import project.view.register.RegisterDialogCustomer;
import project.view.register.RegisterDialogEmployee;

import java.util.List;
import java.util.Scanner;

public class RegisterController {
    public static Scanner scanner = new Scanner(System.in);
    private final CustomerService customerService;
    private final EmployeeService employeeService;
    private final PositionService positionService;

    public RegisterController(CustomerService customerService, EmployeeService employeeService, PositionService positionService) {
        this.customerService = customerService;
        this.employeeService = employeeService;
        this.positionService = positionService;
    }

    public void init() {
        var menu = new Menu("Регистрация", List.of(
                new Menu.Option("Регистрация като потребител", () -> {
                    var customer = new RegisterDialogCustomer().input();
                    var created =  customerService.add(customer);
                    return String.format("Потребител с ID:'%s', потребителско име: '%s' се регистрира успешно.", created.getId(), created.getUsername());

                }),
                new Menu.Option("Регистрация като служител", () -> {
                    var employee = new RegisterDialogEmployee(positionService).input();
                    var created =  employeeService.add(employee);
                    return String.format("Служител с ID:'%s', потребителско име: '%s' се регистрира успешно.", created.getId(), created.getUsername());
                })
        ));
        menu.show();
    }
}
