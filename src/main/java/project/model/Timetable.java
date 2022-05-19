package project.model;

import java.sql.Timestamp;

public class Timetable {
    private int id;
    private Timestamp dateFrom;
    private Destination destination;
    int membersCount;
    double price;

    public Timetable() {
    }

    public Timetable(Timestamp dateFrom, Destination destination, int membersCount, double price) {
        this.dateFrom = dateFrom;
        this.destination = destination;
        this.membersCount = membersCount;
        this.price = price;
    }

    public Timetable(int id, Timestamp dateFrom, Destination destination, int membersCount, double price) {
        this.id = id;
        this.dateFrom = dateFrom;
        this.destination = destination;
        this.membersCount = membersCount;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Timestamp getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Timestamp dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public int getMembersCount() {
        return membersCount;
    }

    public void setMembersCount(int membersCount) {
        this.membersCount = membersCount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Timetable{" +
                "id=" + id +
                ", dateFrom=" + dateFrom +
                ", destination=" + destination +
                ", membersCount=" + membersCount +
                ", price=" + price +
                '}';
    }
}
