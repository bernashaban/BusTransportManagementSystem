package project.view;


import java.sql.SQLException;

public interface Command {
    String execute() throws SQLException;
}
