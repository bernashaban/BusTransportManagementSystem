package project.dao.impl;

import project.dao.Repository;
import project.exception.EntityPersistenceException;
import project.model.Customer;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomerRepository implements Repository<Integer, Customer> {

    public static final String SELECT_ALL_CUSTOMERS = "select * from `customer`;";
    public static final String INSERT_NEW_CUSTOMER = "insert into `customer` (`name`,`phone`,`username`,`password`) values (?, ?);";
    public static final String SELECT_CUSTOMER_BY_ID = "select * from `customer` where id_customer= ?;";
    public static final String UPDATE_CUSTOMER_BY_ID = "update `customer` set `name`=?, `phone`=?,`username`=?,`password`=? where id_customer = ?;";
    public static final String DELETE_CUSTOMER_BY_ID = "delete from `customer` where id_customer = ?;";
    private Connection connection;

    public CustomerRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Collection<Customer> findAll() {
        try (var stmt = connection.prepareStatement(SELECT_ALL_CUSTOMERS)) {
            var rs = stmt.executeQuery();
            return toCustomer(rs);
        } catch (SQLException ex) {
            System.out.println("Error creating connection to DB");
            throw new EntityPersistenceException("Error executing SQL query: " + SELECT_ALL_CUSTOMERS, ex);
        }
    }

    @Override
    public Customer findById(Integer id) {
        var customers = findAll();
        try (var stmt = connection.prepareStatement(SELECT_CUSTOMER_BY_ID)) {
            stmt.setInt(1, id);
            var rs = stmt.executeQuery();
            for (Customer customer : customers) {
                Integer currentId = customer.getId();
                if (currentId.equals(id)) {
                    return customer;
                }
            }
            return null;
        } catch (SQLException ex) {
            System.out.println("Error creating connection to DB");
            throw new EntityPersistenceException("Error executing SQL query: " + SELECT_ALL_CUSTOMERS, ex);
        }
    }

    @Override
    public Customer create(Customer entity) {
        try (var stmt = connection.prepareStatement(INSERT_NEW_CUSTOMER, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, entity.getName());
            stmt.setString(2, entity.getPhone());
            stmt.setString(3, entity.getUsername());
            stmt.setString(4, entity.getPassword());
            connection.setAutoCommit(false);
            var affectedRows = stmt.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);
            if (affectedRows == 0) {
                throw new EntityPersistenceException("Creating customer failed, no rows affected.");
            }
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getInt(1));
                    return entity;
                } else {
                    throw new EntityPersistenceException("Creating customer failed, no ID obtained.");
                }
            }
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                throw new EntityPersistenceException("Error rolling back SQL query: " + SELECT_ALL_CUSTOMERS, ex);
            }
            System.out.println("Error creating connection to DB");
            throw new EntityPersistenceException("Error executing SQL query: " + SELECT_ALL_CUSTOMERS, ex);
        }
    }

    @Override
    public Customer update(Customer entity) {
        var old = findById(entity.getId());
        try {
            var stmt = connection.prepareStatement(UPDATE_CUSTOMER_BY_ID);
            stmt.setString(1, entity.getName());
            stmt.setString(2, entity.getPhone());
            stmt.setString(4, entity.getUsername());
            stmt.setString(5, entity.getPassword());
            stmt.setInt(6, old.getId());
            connection.setAutoCommit(false);
            var affectedRows = stmt.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            System.out.println("Error creating connection to DB");
            throw new EntityPersistenceException("Error executing SQL query: " + SELECT_ALL_CUSTOMERS, e);
        }
        return old;
    }

    @Override
    public Customer deleteById(Integer id) {
        var customer = findById(id);
        try (var stmt = connection.prepareStatement(DELETE_CUSTOMER_BY_ID)) {
            stmt.setInt(1, id);
            connection.setAutoCommit(false);
            var affectedRows = stmt.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException ex) {
            System.out.println("Error creating connection to DB");
            throw new EntityPersistenceException("Error executing SQL query: " + SELECT_ALL_CUSTOMERS, ex);
        }
        return customer;
    }

    public List<Customer> toCustomer(ResultSet rs) throws SQLException {
        List<Customer> results = new ArrayList<>();
        while (rs.next()) {
            results.add(new Customer(
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getString(5)
            ));
        }
        return results;
    }
}
