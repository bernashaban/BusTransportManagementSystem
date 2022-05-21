package project.controller;

import project.service.impl.*;
import project.view.Menu;
import project.view.logIn.LogInDialogCustomer;
import project.view.logIn.LogInDialogEmployee;

import java.util.List;

public class LogInController {
    private final CustomerService customerService;
    private final EmployeeService employeeService;
    private final DestinationService destinationService;
    private final PositionService positionService;
    private final ReservationService reserveService;
    private final TimetableService timetableService;

    public LogInController(CustomerService customerService, EmployeeService employeeService, DestinationService destinationService, PositionService positionService, ReservationService reserveService, TimetableService timetableService) {
        this.customerService = customerService;
        this.employeeService = employeeService;
        this.destinationService = destinationService;
        this.positionService = positionService;
        this.reserveService = reserveService;
        this.timetableService = timetableService;
    }

    public void init() {
        var menu = new Menu("Вход", List.of(
                new Menu.Option("Вход като потребител", () -> {
                    var user = new LogInDialogCustomer(customerService).input();
                    if (user == null) {
                        System.out.println("Неуспешен вход. Опитайте пак.");
                    } else {
                        CustomerController customerController = new CustomerController(user, customerService, employeeService, destinationService, reserveService, timetableService);
                        customerController.init();
                    }
                    return "Успешен вход.";
                }),
                new Menu.Option("Вход като служител", () -> {
                    var user = new LogInDialogEmployee(employeeService).input();
                    if (user == null) {
                        System.out.println("Неуспешен вход. Опитайте пак.");
                    } else {
                        EmployeeController employeeController = new EmployeeController(user, employeeService, customerService, positionService, destinationService, reserveService, timetableService);
                        employeeController.init();
                    }
                    return "Успешен вход.";
                })
        ));
        menu.show();
    }
}
