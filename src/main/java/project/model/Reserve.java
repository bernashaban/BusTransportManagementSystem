package project.model;

import java.sql.Timestamp;

public class Reserve {
    private int id;
    private Timestamp reservationDate;
    private Employee employee;
    private Customer customer;
    private Timetable timetable;
    private Timestamp travelDate;
    private int ticketCount;

}
