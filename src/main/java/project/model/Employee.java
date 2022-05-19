package project.model;

public class Employee {
    private int id;
    private String name;
    private String phone;
    private Position position;
    private String username;
    private String password;

    public Employee() {
    }

    public Employee(String name, String phone, Position position, String username, String password) {
        this.name = name;
        this.phone = phone;
        this.position = position;
        this.username = username;
        this.password = password;
    }

    public Employee(int id, String name, String phone, Position position, String username, String password) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.position = position;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Служител ID: " + id + " | Име: " + name +" | Телефон: "+ phone + " | Позиция: "+ position.getPosition() + '\'';
    }
}
