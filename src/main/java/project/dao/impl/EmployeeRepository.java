package project.dao.impl;

import project.dao.Repository;
import project.exception.EntityPersistenceException;
import project.model.Employee;
import project.model.Position;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EmployeeRepository implements Repository<Integer, Employee> {
    public static final String SELECT_ALL_EMPLOYEES = "select * from `employee`;";
    public static final String INSERT_NEW_EMPLOYEE = "insert into `employee` (`name`,`phone`,`position_id`,`username`,`password`) values (?, ?, ?);";
    public static final String SELECT_EMPLOYEE_BY_ID = "select * from `employee` where id_employee= ?;";
    public static final String UPDATE_EMPLOYEE_BY_ID = "update `employee` set `name`=?, `phone`=?,`position_id`=?,`username`=?,`password`=? where id_employee = ?;";
    public static final String DELETE_EMPLOYEE_BY_ID = "delete from `employee` where id_employee = ?;";
    private Connection connection;
    private PositionRepository positionRepository;

    public EmployeeRepository(Connection connection, PositionRepository positionRepository) {
        this.connection = connection;
        this.positionRepository = positionRepository;
    }

    @Override
    public Collection<Employee> findAll() {
        try (var stmt = connection.prepareStatement(SELECT_ALL_EMPLOYEES)) {
            var rs = stmt.executeQuery();
            return toEmployee(rs);
        } catch (SQLException ex) {
            System.out.println("Error creating connection to DB");
            throw new EntityPersistenceException("Error executing SQL query: " + SELECT_ALL_EMPLOYEES, ex);
        }
    }

    @Override
    public Employee findById(Integer id) {
        var employees = findAll();
        try (var stmt = connection.prepareStatement(SELECT_EMPLOYEE_BY_ID)) {
            stmt.setInt(1, id);
            var rs = stmt.executeQuery();
            for (Employee employee : employees) {
                Integer currentId = employee.getId();
                if (currentId.equals(id)) {
                    return employee;
                }
            }
            return null;
        } catch (SQLException ex) {
            System.out.println("Error creating connection to DB");
            throw new EntityPersistenceException("Error executing SQL query: " + SELECT_ALL_EMPLOYEES, ex);
        }
    }

    @Override
    public Employee create(Employee entity) {
        try (var stmt = connection.prepareStatement(INSERT_NEW_EMPLOYEE, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, entity.getName());
            stmt.setString(2, entity.getPhone());
            stmt.setInt(3, entity.getPosition().getId());
            stmt.setString(4, entity.getUsername());
            stmt.setString(5, entity.getPassword());
            connection.setAutoCommit(false);
            var affectedRows = stmt.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);
            if (affectedRows == 0) {
                throw new EntityPersistenceException("Creating employee failed, no rows affected.");
            }
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getInt(1));
                    return entity;
                } else {
                    throw new EntityPersistenceException("Creating employee failed, no ID obtained.");
                }
            }
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                throw new EntityPersistenceException("Error rolling back SQL query: " + SELECT_ALL_EMPLOYEES, ex);
            }
            System.out.println("Error creating connection to DB");
            throw new EntityPersistenceException("Error executing SQL query: " + SELECT_ALL_EMPLOYEES, ex);
        }
    }

    @Override
    public Employee update(Employee entity) {
        var old = findById(entity.getId());
        try {
            var stmt = connection.prepareStatement(UPDATE_EMPLOYEE_BY_ID);
            stmt.setString(1, entity.getName());
            stmt.setString(2, entity.getPhone());
            stmt.setInt(3, entity.getPosition().getId());
            stmt.setString(4, entity.getUsername());
            stmt.setString(5, entity.getPassword());
            stmt.setInt(6, old.getId());
            connection.setAutoCommit(false);
            var affectedRows = stmt.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            System.out.println("Error creating connection to DB");
            throw new EntityPersistenceException("Error executing SQL query: " + SELECT_ALL_EMPLOYEES, e);
        }
        return old;
    }

    @Override
    public Employee deleteById(Integer id) {
        var employee = findById(id);
        try (var stmt = connection.prepareStatement(DELETE_EMPLOYEE_BY_ID)) {
            stmt.setInt(1, id);
            connection.setAutoCommit(false);
            var affectedRows = stmt.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException ex) {
            System.out.println("Error creating connection to DB");
            throw new EntityPersistenceException("Error executing SQL query: " + SELECT_ALL_EMPLOYEES, ex);
        }
        return employee;
    }
    public List<Employee> toEmployee(ResultSet rs) throws SQLException {
        List<Employee> results = new ArrayList<>();
        while (rs.next()) {
            results.add(new Employee(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    positionRepository.findById(rs.getInt(4)),
                    rs.getString(5),
                    rs.getString(6)
            ));
        }
        return results;
    }
    public Employee findByUsername(String username) {
        var employees = findAll();
        for (Employee employee : employees) {
            String currentUsername = employee.getUsername();
            if(currentUsername.equals(username)){
                return employee;
            }
        }
        return null;
    }
}
