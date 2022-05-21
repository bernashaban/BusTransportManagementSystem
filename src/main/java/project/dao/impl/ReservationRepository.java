package project.dao.impl;

import project.dao.Repository;
import project.exception.EntityPersistenceException;
import project.model.Customer;
import project.model.Reservation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ReservationRepository implements Repository<Integer, Reservation> {
    public static final String SELECT_ALL_RESERVES = "select * from `reserve`;";
    public static final String INSERT_NEW_RESERVE = "insert into `reserve` (`employee_id`,`customer_id`,`timetable_id`,`reservation_date`,`travel_date`,`ticket_count`) values (?, ?, ?, ?, ?, ?);";
    public static final String SELECT_RESERVE_BY_ID = "select * from `reserve` where id_reserve= ?;";
    public static final String UPDATE_RESERVE_BY_ID = "update `reserve` set `employee_id`=?,`customer_id`=?,`timetable_id`=?,`reservation_date`=?,`travel_date`=?,`ticket_count`=? where id_reserve = ?;";
    public static final String DELETE_RESERVE_BY_ID = "delete from `reserve` where id_reserve = ?;";
    private Connection connection;
    private EmployeeRepository employeeRepository;
    private CustomerRepository customerRepository;
    private TimetableRepository timetableRepository;

    public ReservationRepository(Connection connection, EmployeeRepository employeeRepository, CustomerRepository customerRepository, TimetableRepository timetableRepository) {
        this.connection = connection;
        this.employeeRepository = employeeRepository;
        this.customerRepository = customerRepository;
        this.timetableRepository = timetableRepository;
    }

    @Override
    public Collection<Reservation> findAll() {
        try (var stmt = connection.prepareStatement(SELECT_ALL_RESERVES)) {
            var rs = stmt.executeQuery();
            return toReserve(rs);
        } catch (SQLException ex) {
            System.out.println("Error creating connection to DB");
            throw new EntityPersistenceException("Error executing SQL query: " + SELECT_ALL_RESERVES, ex);
        }
    }

    @Override
    public Reservation findById(Integer id) {
        var reserves = findAll();
        try (var stmt = connection.prepareStatement(SELECT_RESERVE_BY_ID)) {
            stmt.setInt(1, id);
            var rs = stmt.executeQuery();
            for (Reservation reserve : reserves) {
                Integer currentId = reserve.getId();
                if (currentId.equals(id)) {
                    return reserve;
                }
            }
            return null;
        } catch (SQLException ex) {
            System.out.println("Error creating connection to DB");
            throw new EntityPersistenceException("Error executing SQL query: " + SELECT_ALL_RESERVES, ex);
        }
    }

    @Override
    public Reservation create(Reservation entity) {
        try (var stmt = connection.prepareStatement(INSERT_NEW_RESERVE, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, entity.getEmployee().getId());
            stmt.setInt(2, entity.getCustomer().getId());
            stmt.setInt(3, entity.getTimetable().getId());
            stmt.setTimestamp(4, entity.getReservationDate());
            stmt.setTimestamp(5, entity.getTravelDate());
            stmt.setInt(6, entity.getTicketCount());
            connection.setAutoCommit(false);
            var affectedRows = stmt.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);
            if (affectedRows == 0) {
                throw new EntityPersistenceException("Creating reservation failed, no rows affected.");
            }
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getInt(1));
                    return entity;
                } else {
                    throw new EntityPersistenceException("Creating reservation failed, no ID obtained.");
                }
            }
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                throw new EntityPersistenceException("Error rolling back SQL query: " + SELECT_ALL_RESERVES, ex);
            }
            System.out.println("Error creating connection to DB");
            throw new EntityPersistenceException("Error executing SQL query: " + SELECT_ALL_RESERVES, ex);
        }
    }

    @Override
    public Reservation update(Reservation entity) {
        var old = findById(entity.getId());
        try {
            var stmt = connection.prepareStatement(UPDATE_RESERVE_BY_ID);
            stmt.setInt(1, entity.getEmployee().getId());
            stmt.setInt(2, entity.getCustomer().getId());
            stmt.setInt(3, entity.getTimetable().getId());
            stmt.setTimestamp(4, entity.getReservationDate());
            stmt.setTimestamp(5, entity.getTravelDate());
            stmt.setInt(6, entity.getTicketCount());
            stmt.setInt(7, old.getId());
            connection.setAutoCommit(false);
            var affectedRows = stmt.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            System.out.println("Error creating connection to DB");
            throw new EntityPersistenceException("Error executing SQL query: " + SELECT_ALL_RESERVES, e);
        }
        return old;
    }

    @Override
    public Reservation deleteById(Integer id) {
        var customer = findById(id);
        try (var stmt = connection.prepareStatement(DELETE_RESERVE_BY_ID)) {
            stmt.setInt(1, id);
            connection.setAutoCommit(false);
            var affectedRows = stmt.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException ex) {
            System.out.println("Error creating connection to DB");
            throw new EntityPersistenceException("Error executing SQL query: " + SELECT_ALL_RESERVES, ex);
        }
        return customer;
    }
    public List<Reservation> toReserve(ResultSet rs) throws SQLException {
        List<Reservation> results = new ArrayList<>();
        while (rs.next()) {
            results.add(new Reservation(
                    rs.getInt(1),
                    employeeRepository.findById(rs.getInt(2)),
                    customerRepository.findById(rs.getInt(3)),
                    timetableRepository.findById(rs.getInt(4)),
                    rs.getTimestamp(5),
                    rs.getTimestamp(6),
                    rs.getInt(7)
            ));
        }
        return results;
    }
    public List<Reservation> customerReservations(Customer customer){
        var allReservations = findAll();
        List<Reservation> customerReservations = new ArrayList<>();
        for (Reservation reservation : allReservations) {
            Integer currentCustomerId = reservation.getCustomer().getId();
            if (currentCustomerId.equals(customer.getId())) {
                customerReservations.add(reservation);
            }
        }
        return customerReservations;
    }
}
