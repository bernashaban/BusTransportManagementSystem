package project.dao.impl;

import project.dao.Repository;
import project.exception.EntityPersistenceException;
import project.model.Customer;
import project.model.Destination;
import project.model.Timetable;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TimetableRepository implements Repository<Integer, Timetable> {
    public static final String SELECT_ALL_TIMETABLES = "select * from `timetable`;";
    public static final String INSERT_NEW_TIMETABLE = "insert into `timetable` (`destionation_id`,`date_from`,`members_count`,`price`) values (?, ?,?,?);";
    public static final String SELECT_TIMETABLE_BY_ID = "select * from `timetable` where id_timetable= ?;";
    public static final String UPDATE_TIMETABLE_BY_ID = "update `timetable` set `destionation_id`=?,`date_from`=?,`members_count`=?,`price`=? where id_timetable = ?;";
    public static final String DELETE_TIMETABLE_BY_ID = "delete from `timetable` where id_timetable = ?;";
    private Connection connection;

    public TimetableRepository(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Collection<Timetable> findAll() {
        try (var stmt = connection.prepareStatement(SELECT_ALL_TIMETABLES)) {
            var rs = stmt.executeQuery();
            return toTimetable(rs);
        } catch (SQLException ex) {
            System.out.println("Error creating connection to DB");
            throw new EntityPersistenceException("Error executing SQL query: " + SELECT_ALL_TIMETABLES, ex);
        }
    }


    @Override
    public Timetable findById(Integer id) {
        var timetables = findAll();
        try (var stmt = connection.prepareStatement(SELECT_TIMETABLE_BY_ID)) {
            stmt.setInt(1, id);
            var rs = stmt.executeQuery();
            for (Timetable timetable : timetables) {
                Integer currentId = timetable.getId();
                if (currentId.equals(id)) {
                    return timetable;
                }
            }
            return null;
        } catch (SQLException ex) {
            System.out.println("Error creating connection to DB");
            throw new EntityPersistenceException("Error executing SQL query: " + SELECT_ALL_TIMETABLES, ex);
        }
    }

    @Override
    public Timetable create(Timetable entity) {

        try (var stmt = connection.prepareStatement(INSERT_NEW_TIMETABLE, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, entity.getDestination().getId());
            stmt.setTimestamp(2, entity.getDateFrom());
            stmt.setInt(3, entity.getMembersCount());
            stmt.setDouble(4, entity.getPrice());
            connection.setAutoCommit(false);
            var affectedRows = stmt.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);
            if (affectedRows == 0) {
                throw new EntityPersistenceException("Creating timetable failed, no rows affected.");
            }
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getInt(1));
                    return entity;
                } else {
                    throw new EntityPersistenceException("Creating timetable failed, no ID obtained.");
                }
            }
        } catch (SQLException ex) {
            try {
                connection.rollback();
            } catch (SQLException e) {
                throw new EntityPersistenceException("Error rolling back SQL query: " + SELECT_ALL_TIMETABLES, ex);
            }
            System.out.println("Error creating connection to DB");
            throw new EntityPersistenceException("Error executing SQL query: " + SELECT_ALL_TIMETABLES, ex);
        }
    }

    @Override
    public Timetable update(Timetable entity) {
        var old = findById(entity.getId());
        try {
            var stmt = connection.prepareStatement(UPDATE_TIMETABLE_BY_ID);
            stmt.setInt(1, entity.getDestination().getId());
            stmt.setTimestamp(2, entity.getDateFrom());
            stmt.setInt(3, entity.getMembersCount());
            stmt.setDouble(4, entity.getPrice());
            stmt.setInt(5, old.getId());
            connection.setAutoCommit(false);
            var affectedRows = stmt.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            System.out.println("Error creating connection to DB");
            throw new EntityPersistenceException("Error executing SQL query: " + SELECT_ALL_TIMETABLES, e);
        }
        return old;
    }

    @Override
    public Timetable deleteById(Integer id) {
        var timetable = findById(id);
        try (var stmt = connection.prepareStatement(DELETE_TIMETABLE_BY_ID)) {
            stmt.setInt(1, id);
            connection.setAutoCommit(false);
            var affectedRows = stmt.executeUpdate();
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException ex) {
            System.out.println("Error creating connection to DB");
            throw new EntityPersistenceException("Error executing SQL query: " + SELECT_ALL_TIMETABLES, ex);
        }
        return timetable;
    }
    public List<Timetable> toTimetable(ResultSet rs) throws SQLException {
        List<Timetable> results = new ArrayList<>();
        while (rs.next()) {
            results.add(new Timetable(
                    rs.getInt(1),
                    new Destination(),
                    rs.getTimestamp(3),
                    rs.getInt(4),
                    rs.getDouble(5)
            ));
        }
        return results;
    }
    //   private Timestamp dateFrom;
    //    private Destination destination;
    //    int membersCount;
    //    double price;
}
