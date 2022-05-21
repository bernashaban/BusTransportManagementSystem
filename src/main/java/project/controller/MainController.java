package project.controller;

import project.service.impl.*;
import project.view.Menu;

import java.util.List;

public class MainController {
    private final CustomerService customerService;
    private final DestinationService destinationService;
    private final EmployeeService employeeService;
    private final PositionService positionService;
    private final ReservationService reserveService;
    private final TimetableService timetableService;

    public MainController(CustomerService customerService, DestinationService destinationService, EmployeeService employeeService, PositionService positionService, ReservationService reserveService, TimetableService timetableService) {
        this.customerService = customerService;
        this.destinationService = destinationService;
        this.employeeService = employeeService;
        this.positionService = positionService;
        this.reserveService = reserveService;
        this.timetableService = timetableService;
    }

    public void init() {
        var menu = new Menu("Главно меню", List.of(
                new Menu.Option("Вход", () -> {
                    LogInController logInController = new LogInController(customerService, employeeService, destinationService, positionService, reserveService, timetableService);
                    logInController.init();
                    return ("");
                }),
                new Menu.Option("Регистрация", () -> {
                    RegisterController registerController = new RegisterController(customerService, employeeService, positionService);
                    registerController.init();
                    return ("");
                })
        ));
        menu.show();
    }
}
