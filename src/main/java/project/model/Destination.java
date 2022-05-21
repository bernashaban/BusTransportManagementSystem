package project.model;

public class Destination {
    private int id;
    private String destination;

    public Destination() {
    }

    public Destination(String destination) {
        this.destination = destination;
    }

    public Destination(int id, String destination) {
        this.id = id;
        this.destination = destination;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    @Override
    public String toString() {
        return "Дестинация ID: " + id + " | Име: " + destination + "\n";
    }
}
