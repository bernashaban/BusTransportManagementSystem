package project.view.employee.reservation;

import project.model.Customer;
import project.model.Employee;
import project.model.Reservation;
import project.model.Timetable;
import project.service.impl.CustomerService;
import project.service.impl.EmployeeService;
import project.service.impl.TimetableService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Scanner;

public class AddReservationDialog {
    public static Scanner scanner = new Scanner(System.in);
    private CustomerService customerService;
    private EmployeeService employeeService;
    private TimetableService timetableService;

    public AddReservationDialog(CustomerService customerService, EmployeeService employeeService, TimetableService timetableService) {
        this.customerService = customerService;
        this.employeeService = employeeService;
        this.timetableService = timetableService;
    }

    public Reservation input() {
        var reservation = new Reservation();
        Timetable timetable = null;
        while (reservation.getTimetable() == null) {
            timetableService.getAll().forEach(System.out::println);
            System.out.println("Моля изберете ID на разписание от наличните разписания: ");
            var answer = scanner.nextLine();
            Integer timetableId = 0;
            try {
                timetableId = Integer.parseInt(answer);
            } catch (NumberFormatException e) {
                System.out.println("Невалиден формат на ID. Опитайте с цифри.");
            }
            try {
                timetable = timetableService.getById(timetableId);
            } catch (NullPointerException e) {
                System.out.println("Разписание с ID = " + timetable + " не съществува.");
            }
            reservation.setTimetable(timetable);
        }
        Customer customer = null;
        while (reservation.getCustomer() == null) {
            System.out.println("ID потребител: ");
            var answer = scanner.nextLine();
            Integer id = 0;
            try {
                id = Integer.parseInt(answer);
            } catch (NumberFormatException e) {
                System.out.println("Невалиден формат на ID. Опитайте с цифри.");
            }
            try {
                customer = customerService.getById(id);
            } catch (NullPointerException e) {
                System.out.println("Потребител с ID = " + id + " не съществува.");
            }
            reservation.setCustomer(customer);
        }
        Employee employee = null;
        while (reservation.getEmployee() == null) {
            System.out.println("ID служител: ");
            var answer = scanner.nextLine();
            Integer id = 0;
            try {
                id = Integer.parseInt(answer);
            } catch (NumberFormatException e) {
                System.out.println("Невалиден формат на ID. Опитайте с цифри.");
            }
            try {
                customer = customerService.getById(id);
            } catch (NullPointerException e) {
                System.out.println("Служител с ID = " + id + " не съществува.");
            }
            reservation.setEmployee(employee);
        }
        while (reservation.getTicketCount() == 0) {
            System.out.println("Наличен брой билети: " + timetable.getMembersCount());
            System.out.println("Брой билети, които искате да резервирате: ");
            var answer = scanner.nextLine();
            int ticketCount = 0;
            try {
                ticketCount = Integer.parseInt(answer);
            } catch (NumberFormatException e) {
                System.out.println("Невалиден формат на брой. Опитайте с цифри.");
            }
            if (ticketCount > timetable.getMembersCount()) {
                System.out.println("Невалиден брой! Опитайте отново.");
            } else {
                int currentMemberCount = timetable.getMembersCount();
                timetable.setMembersCount(currentMemberCount - ticketCount);
                reservation.setTicketCount(ticketCount);
            }
        }
        reservation.setTravelDate(timetable.getDateFrom());
        reservation.setReservationDate(Timestamp.valueOf(LocalDateTime.now()));
        return reservation;
    }
}
