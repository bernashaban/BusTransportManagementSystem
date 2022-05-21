package project.dao.impl;

import project.dao.Repository;
import project.exception.EntityPersistenceException;
import project.model.Customer;
import project.model.Destination;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class DestinationRepository implements Repository<Integer, Destination> {
    public static final String SELECT_ALL_DESTINATIONS = "select * from `destination`;";
    public static final String INSERT_NEW_DESTINATION = "insert into `destination` (`name`) values (?);";
    public static final String SELECT_DESTINATION_BY_ID = "select * from `destination` where id_destination= ?;";
    public static final String UPDATE_DESTINATION_BY_ID = "update `destination` set `name`=? where id_destination = ?;";
    public static final String DELETE_DESTINATION_BY_ID = "delete from `destination` where id_destination = ?;";
    private Connection connection;

    public DestinationRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Collection<Destination> findAll() {
        try (var stmt = connection.prepareStatement(SELECT_ALL_DESTINATIONS)) {
            var rs = stmt.executeQuery();
            return toDestination(rs);
        } catch (SQLException ex) {
            System.out.println("Error creating connection to DB");
            throw new EntityPersistenceException("Error executing SQL query: " + SELECT_ALL_DESTINATIONS, ex);
        }
    }

    @Override
    public Destination findById(Integer id) {
        var destinations = findAll();
        try (var stmt = connection.prepareStatement(SELECT_DESTINATION_BY_ID)) {
            stmt.setInt(1, id);
            var rs = stmt.executeQuery();
            for (Destination destination : destinations) {
                Integer currentId = destination.getId();
                if (currentId.equals(id)) {
                    return destination;
                }
            }
            return null;
        } catch (SQLException ex) {
            System.out.println("Error creating connection to DB");
            throw new EntityPersistenceException("Error executing SQL query: " + SELECT_ALL_DESTINATIONS, ex);
        }
    }

    @Override
    public Destination create(Destination entity) {
        try (var stmt = connection.prepareStatement(INSERT_NEW_DESTINATION, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, entity.getDestination());
            connection.setAutoCommit(false);
            var affectedRows = stmt.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);
            if (affectedRows == 0) {
                throw new EntityPersistenceException("Creating destination failed, no rows affected.");
            }
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getInt(1));
                    return entity;
                } else {
                    throw new EntityPersistenceException("Creating destination failed, no ID obtained.");
                }
            }
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                throw new EntityPersistenceException("Error rolling back SQL query: " + SELECT_ALL_DESTINATIONS, ex);
            }
            System.out.println("Error creating connection to DB");
            throw new EntityPersistenceException("Error executing SQL query: " + SELECT_ALL_DESTINATIONS, ex);
        }
    }

    @Override
    public Destination update(Destination entity) {
        var old = findById(entity.getId());
        try {
            var stmt = connection.prepareStatement(UPDATE_DESTINATION_BY_ID);
            stmt.setString(1, entity.getDestination());
            stmt.setInt(2, old.getId());
            connection.setAutoCommit(false);
            var affectedRows = stmt.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            System.out.println("Error creating connection to DB");
            throw new EntityPersistenceException("Error executing SQL query: " + SELECT_ALL_DESTINATIONS, e);
        }
        return old;
    }

    @Override
    public Destination deleteById(Integer id) {
        var destination = findById(id);
        try (var stmt = connection.prepareStatement(DELETE_DESTINATION_BY_ID)) {
            stmt.setInt(1, id);
            connection.setAutoCommit(false);
            var affectedRows = stmt.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException ex) {
            System.out.println("Error creating connection to DB");
            throw new EntityPersistenceException("Error executing SQL query: " + SELECT_ALL_DESTINATIONS, ex);
        }
        return destination;
    }
    public List<Destination> toDestination(ResultSet rs) throws SQLException {
        List<Destination> results = new ArrayList<>();
        while (rs.next()) {
            results.add(new Destination(
                    rs.getInt(1),
                    rs.getString(2)
            ));
        }
        return results;
    }
    public Destination findByName(String name){
        var destinations = findAll();
        for (Destination destination : destinations) {
            if(destination.getDestination().equals(name)){
                return destination;
            }
        }
        return null;
    }
}
