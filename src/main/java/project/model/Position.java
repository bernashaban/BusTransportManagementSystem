package project.model;

public class Position {
    private int id;
    private String position;

    public Position() {
    }

    public Position(String position) {
        this.position = position;
    }

    public Position(int id, String position) {
        this.id = id;
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "Позиция ID: " + id + " | Име: " + position + "\n";
    }
}
