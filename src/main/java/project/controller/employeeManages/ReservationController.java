package project.controller.employeeManages;

import project.service.impl.CustomerService;
import project.service.impl.EmployeeService;
import project.service.impl.ReservationService;
import project.service.impl.TimetableService;
import project.view.Menu;
import project.view.employee.reservation.AddReservationDialog;
import project.view.employee.reservation.SearchReservationByIdDialog;
import project.view.employee.reservation.UpdateReservationDialog;

import java.util.List;
import java.util.Scanner;

public class ReservationController {
    public static Scanner scanner = new Scanner(System.in);
    private EmployeeService employeeService;
    private CustomerService customerService;
    private TimetableService timetableService;
    private ReservationService reservationService;

    public ReservationController(EmployeeService employeeService, CustomerService customerService, TimetableService timetableService, ReservationService reservationService) {
        this.employeeService = employeeService;
        this.customerService = customerService;
        this.timetableService = timetableService;
        this.reservationService = reservationService;
    }

    public void init() {
        var menu = new Menu("Резервации", List.of(
                new Menu.Option("Виж всички резервации", () -> {
                    reservationService.getAll().forEach(System.out::println);
                    return "";
                }),
                new Menu.Option("Добави резервация", () -> {
                    var created = new AddReservationDialog(customerService, employeeService, timetableService).input();
                    reservationService.add(created);
                    return "Резервацията е добавена успешно!";
                }),
                new Menu.Option("Актуализирай резервация", () -> {
                    var updating = new UpdateReservationDialog(customerService, employeeService, timetableService, reservationService).input();
                    reservationService.update(updating);
                    return "Резервацията е актуализирана успшено!";
                }),
                new Menu.Option("Търси резервация по ID", () -> {
                    var reservation = new SearchReservationByIdDialog(reservationService).input();
                    System.out.println(reservation);
                    return "";
                }),
                new Menu.Option("Изтрий резервация", () -> {
                    var deleted = new SearchReservationByIdDialog(reservationService).input();
                    reservationService.delete(deleted.getId());
                    return "Резервацията е изтрита успешно!";
                })
        ));
        menu.show();
    }
}
