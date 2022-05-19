package project.dao.impl;

import project.dao.Repository;
import project.exception.EntityPersistenceException;
import project.model.Position;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PositionRepository implements Repository<Integer, Position> {
    public static final String SELECT_ALL_POSITIONS = "select * from `position`;";
    public static final String INSERT_NEW_POSITION = "insert into `position` (`position_name`) values (?);";
    public static final String SELECT_POSITION_BY_ID = "select * from `position` where id_position= ?;";
    public static final String UPDATE_POSITION_BY_ID = "update `position` set `position_name`=? where id_position = ?;";
    public static final String DELETE_POSITION_BY_ID = "delete from `position` where id_position = ?;";
    private Connection connection;

    public PositionRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Collection<Position> findAll() {
        try (var stmt = connection.prepareStatement(SELECT_ALL_POSITIONS)) {
            var rs = stmt.executeQuery();
            return toPosition(rs);
        } catch (SQLException ex) {
            System.out.println("Error creating connection to DB");
            throw new EntityPersistenceException("Error executing SQL query: " + SELECT_ALL_POSITIONS, ex);
        }
    }

    @Override
    public Position findById(Integer id) {
        var positions = findAll();
        try (var stmt = connection.prepareStatement(SELECT_POSITION_BY_ID)) {
            stmt.setInt(1, id);
            var rs = stmt.executeQuery();
            for (Position position : positions) {
                Integer currentId = position.getId();
                if (currentId.equals(id)) {
                    return position;
                }
            }
            return null;
        } catch (SQLException ex) {
            System.out.println("Error creating connection to DB");
            throw new EntityPersistenceException("Error executing SQL query: " + SELECT_ALL_POSITIONS, ex);
        }
    }

    @Override
    public Position create(Position entity) {
        try (var stmt = connection.prepareStatement(INSERT_NEW_POSITION, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, entity.getPosition());
            connection.setAutoCommit(false);
            var affectedRows = stmt.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);
            if (affectedRows == 0) {
                throw new EntityPersistenceException("Creating position failed, no rows affected.");
            }
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getInt(1));
                    return entity;
                } else {
                    throw new EntityPersistenceException("Creating position failed, no ID obtained.");
                }
            }
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                throw new EntityPersistenceException("Error rolling back SQL query: " + SELECT_ALL_POSITIONS, ex);
            }
            System.out.println("Error creating connection to DB");
            throw new EntityPersistenceException("Error executing SQL query: " + SELECT_ALL_POSITIONS, ex);
        }
    }

    @Override
    public Position update(Position entity) {
        var old = findById(entity.getId());
        try {
            var stmt = connection.prepareStatement(UPDATE_POSITION_BY_ID);
            stmt.setString(1, entity.getPosition());
            stmt.setInt(2, old.getId());
            connection.setAutoCommit(false);
            var affectedRows = stmt.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            System.out.println("Error creating connection to DB");
            throw new EntityPersistenceException("Error executing SQL query: " + SELECT_ALL_POSITIONS, e);
        }
        return old;
    }

    @Override
    public Position deleteById(Integer id) {
        var position = findById(id);
        try (var stmt = connection.prepareStatement(DELETE_POSITION_BY_ID)) {
            stmt.setInt(1, id);
            connection.setAutoCommit(false);
            var affectedRows = stmt.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException ex) {
            System.out.println("Error creating connection to DB");
            throw new EntityPersistenceException("Error executing SQL query: " + SELECT_ALL_POSITIONS, ex);
        }
        return position;
    }
    public List<Position> toPosition(ResultSet rs) throws SQLException {
        List<Position> results = new ArrayList<>();
        while (rs.next()) {
            results.add(new Position(
                    rs.getInt(1),
                    rs.getString(2)
            ));
        }
        return results;
    }
}
