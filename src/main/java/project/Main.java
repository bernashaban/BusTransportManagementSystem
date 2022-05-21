package project;

import project.controller.MainController;
import project.dao.impl.*;
import project.service.impl.*;
import project.util.Jdbc;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import static project.util.Jdbc.closeConnection;
import static project.util.Jdbc.createDbConnection;

public class Main {
    public static void main(String[] args) {
        Properties props = new Properties();
        String dbConfigPath = Jdbc.class.getClassLoader().getResource("jdbc.properties").getPath();
        try {
            props.load(new FileInputStream(dbConfigPath));
        } catch (IOException e) {
            System.out.println("Error loading files.");
        }
        Connection conn = null;
        try {
            conn = createDbConnection(props);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error creating connection with the database.");
        }


        CustomerRepository customerRepository = new CustomerRepository(conn);
        CustomerService customerService = new CustomerService(customerRepository);

        PositionRepository positionRepository = new PositionRepository(conn);
        PositionService positionService = new PositionService(positionRepository);

        EmployeeRepository employeeRepository = new EmployeeRepository(conn, positionRepository);
        EmployeeService employeeService = new EmployeeService(employeeRepository);

        DestinationRepository destinationRepository = new DestinationRepository(conn);
        DestinationService destinationService = new DestinationService(destinationRepository);

        TimetableRepository timetableRepository = new TimetableRepository(conn, destinationRepository);
        TimetableService timetableService = new TimetableService(timetableRepository);

        ReservationRepository reservationRepository = new ReservationRepository(conn, employeeRepository, customerRepository, timetableRepository);
        ReservationService reservationService = new ReservationService(reservationRepository);

        var mainController = new MainController(customerService, destinationService,employeeService, positionService,reservationService, timetableService);
        mainController.init();

        try {
            closeConnection(conn);
        } catch (SQLException e) {
            System.out.println("Error while closing the connection with database.");
        }

    }
}
