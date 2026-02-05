package com.zuehlke.securesoftwaredevelopment.repository;

import com.zuehlke.securesoftwaredevelopment.config.AuditLogger;
import com.zuehlke.securesoftwaredevelopment.domain.Destination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class DestinationRepository {

    private static final Logger LOG = LoggerFactory.getLogger(DestinationRepository.class);
    private static final AuditLogger auditLogger = AuditLogger.getAuditLogger(DestinationRepository.class);

    private DataSource dataSource;

    public DestinationRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Destination get(int id) {
        String query = "SELECT d.id, d.cityId, c.name, d.name, d.description FROM destination d, city c WHERE d.cityId = c.id AND d.id = " + id;
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(query)) {
            while (rs.next()) {
                Destination destination = crateDestinationFromResultSet(rs);
                return destination;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Destination> getAll() {
        List<Destination> destinationList = new ArrayList<>();
        String query = "SELECT destination.id, destination.cityId, city.Name, destination.name, destination.description FROM destination, city WHERE destination.cityId = city.id";
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(query)) {
            while (rs.next()) {
                Destination destination = crateDestinationFromResultSet(rs);
                destinationList.add(destination);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return destinationList;
    }

    public List<Destination> search(String searchTerm) throws SQLException {
        List<Destination> destinationList = new ArrayList<>();
        String query = "SELECT DISTINCT d.id, d.cityId, c.name, d.name, d.description FROM destination d, city c" +
                " WHERE d.cityId = c.id" +
                " AND (UPPER(d.name) like UPPER('%" + searchTerm + "%')" +
                " OR UPPER(c.name) like UPPER('%" + searchTerm + "%'))";
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(query)) {
            while (rs.next()) {
                destinationList.add(crateDestinationFromResultSet(rs));
            }
        }
        return destinationList;
    }

    private Destination crateDestinationFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt(1);
        int cityId = rs.getInt(2);
        String cityName = rs.getString(3);
        String name = rs.getString(4);
        String description = rs.getString(5);

        return new Destination(id, cityId, cityName, name, description);
    }

    public long create(Destination destination) {
        String query = "INSERT INTO destination(cityId, name, description) VALUES(?, ?, ?)";
        long id = 0;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        ) {
            statement.setInt(1, destination.getCityId());
            statement.setString(2, destination.getName());
            statement.setString(3, destination.getDescription());
            statement.executeUpdate();
            ResultSet generatedKeys = statement.getGeneratedKeys();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }
}
