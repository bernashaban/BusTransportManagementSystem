package project.model;

import java.sql.Timestamp;

public class Reservation {
    private int id;
    private Employee employee;
    private Customer customer;
    private Timetable timetable;
    private Timestamp reservationDate;
    private Timestamp travelDate;
    private int ticketCount;

    public Reservation() {
    }

    public Reservation(Employee employee, Customer customer, Timetable timetable, Timestamp reservationDate, Timestamp travelDate, int ticketCount) {
        this.employee = employee;
        this.customer = customer;
        this.timetable = timetable;
        this.reservationDate = reservationDate;
        this.travelDate = travelDate;
        this.ticketCount = ticketCount;
    }

    public Reservation(int id, Employee employee, Customer customer, Timetable timetable, Timestamp reservationDate, Timestamp travelDate, int ticketCount) {
        this.id = id;
        this.employee = employee;
        this.customer = customer;
        this.timetable = timetable;
        this.reservationDate = reservationDate;
        this.travelDate = travelDate;
        this.ticketCount = ticketCount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Timestamp reservationDate) {
        this.reservationDate = reservationDate;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Timetable getTimetable() {
        return timetable;
    }

    public void setTimetable(Timetable timetable) {
        this.timetable = timetable;
    }

    public Timestamp getTravelDate() {
        return travelDate;
    }

    public void setTravelDate(Timestamp travelDate) {
        this.travelDate = travelDate;
    }

    public int getTicketCount() {
        return ticketCount;
    }

    public void setTicketCount(int ticketCount) {
        this.ticketCount = ticketCount;
    }

    @Override
    public String toString() {
        return "Резервация ID: " + id + "\n" +
                "Дата на резервация: " + reservationDate + "\n" +
                "Служител: " + employee.getName() + "\n" +
                "Клиент: " + customer.getName() + "\n" +
                "Разписание ID: " + timetable.getId() + "\n" +
                "Дата на пътуване: " + travelDate + "\n" +
                "Брой билети: " + ticketCount + "\n";
    }
}
