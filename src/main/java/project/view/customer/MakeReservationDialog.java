package project.view.customer;

import project.model.Customer;
import project.model.Reservation;
import project.model.Timetable;
import project.service.impl.EmployeeService;
import project.service.impl.TimetableService;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Scanner;

public class MakeReservationDialog {
    public static Scanner scanner = new Scanner(System.in);
    private Customer customer;
    private EmployeeService employeeService;
    private TimetableService timetableService;

    public MakeReservationDialog(Customer customer, EmployeeService employeeService, TimetableService timetableService) {
        this.customer = customer;
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
        if (reservation.getId() % 2 == 0) {
            reservation.setEmployee(employeeService.getById(4));
        } else {
            reservation.setEmployee(employeeService.getById(5));
        }
        reservation.setCustomer(customer);
        return reservation;
    }
}
