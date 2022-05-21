package project.controller;

import project.model.Customer;
import project.service.impl.*;
import project.view.Menu;
import project.view.customer.MakeReservationDialog;
import project.view.customer.SearchByDestinationDialog;
import project.view.customer.UpdateDataDialog;

import java.util.List;
import java.util.Scanner;

public class CustomerController {
    public static Scanner scanner = new Scanner(System.in);
    private Customer customer;
    private CustomerService customerService;
    private EmployeeService employeeService;
    private DestinationService destinationService;
    private ReservationService reservationService;
    private TimetableService timetableService;

    public CustomerController(Customer customer, CustomerService customerService, EmployeeService employeeService, DestinationService destinationService, ReservationService reservationService, TimetableService timetableService) {
        this.customer = customer;
        this.customerService = customerService;
        this.employeeService = employeeService;
        this.destinationService = destinationService;
        this.reservationService = reservationService;
        this.timetableService = timetableService;
    }

    public void init() {
        var menu = new Menu("Потребител", List.of(
                new Menu.Option("Лична информация", () -> {
                    System.out.println(customer);
                    return "";
                }),
                new Menu.Option("Редактирай лична информация", () -> {
                    var updating = new UpdateDataDialog(customerService, customer).input();
                    customerService.update(updating);
                    return "";
                }),
                new Menu.Option("Виж всички резервации", () -> {
                    var reservations = reservationService.getCustomerReservations(customer);
                    reservations.forEach(System.out::println);
                    return "";
                }),
                new Menu.Option("Резервирай билет", () -> {
                    var reservation = new MakeReservationDialog( customer, employeeService, timetableService).input();
                    reservationService.add(reservation);
                    return "";
                }),
                new Menu.Option("Търсене на разписание по дестинация", () -> {
                    var timetables = new SearchByDestinationDialog(timetableService,destinationService).input();
                    if(timetables.size()==0){
                        System.out.println("Няма налични разписания!");
                    }else{
                        timetables.forEach(System.out::println);
                    }
                    return "";
                })

        ));
        menu.show();
    }
}
