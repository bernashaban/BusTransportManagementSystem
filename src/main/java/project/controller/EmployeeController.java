package project.controller;

import project.controller.employeeManages.*;
import project.model.Employee;
import project.service.impl.*;
import project.view.Menu;
import project.view.employee.UpdateDataEmpDialog;

import java.util.List;
import java.util.Scanner;

public class EmployeeController {
    public static Scanner scanner = new Scanner(System.in);
    private Employee employee;
    private EmployeeService employeeService;
    private CustomerService customerService;
    private PositionService positionService;
    private DestinationService destinationService;
    private ReservationService reserveService;
    private TimetableService timetableService;


    public EmployeeController(Employee employee, EmployeeService employeeService, CustomerService customerService, PositionService positionService, DestinationService destinationService, ReservationService reserveService, TimetableService timetableService) {
        this.employee = employee;
        this.employeeService = employeeService;
        this.customerService = customerService;
        this.positionService = positionService;
        this.destinationService = destinationService;
        this.reserveService = reserveService;
        this.timetableService = timetableService;
    }

    public void init() {
        var menu = new Menu("Служител", List.of(
                new Menu.Option("Лична информация", () -> {
                    System.out.println(employee);
                    return "";
                }),
                new Menu.Option("Редактирай лична информация", () -> {
                    var updating = new UpdateDataEmpDialog(employeeService, employee).input();
                    employeeService.update(updating);
                    return "";
                }),
                new Menu.Option("Управление на потребители", () -> {
                    EmpCustomerController empCustomerController = new EmpCustomerController(customerService);
                    empCustomerController.init();
                    return "";
                }),
                new Menu.Option("Управление на служители", () -> {
                    EmpEmployeeController empEmployeeController = new EmpEmployeeController(employeeService, positionService);
                    empEmployeeController.init();
                    return "";
                }),
                new Menu.Option("Управление на позиции", () -> {
                    PositionController positionController = new PositionController(positionService);
                    positionController.init();
                    return "";
                }),
                new Menu.Option("Управление на дестинации", () -> {
                    DestinationController destinationController = new DestinationController(destinationService);
                    destinationController.init();
                    return "";
                }),
                new Menu.Option("Управление на резервации", () -> {
                    ReservationController reservationController = new ReservationController(employeeService, customerService, timetableService, reserveService);
                    reservationController.init();
                    return "";
                }),
                new Menu.Option("Управление на разписания", () -> {
                    TimetableController timetableController = new TimetableController(timetableService, destinationService);
                    timetableController.init();
                    return "";
                })
        ));
        menu.show();
    }
}
