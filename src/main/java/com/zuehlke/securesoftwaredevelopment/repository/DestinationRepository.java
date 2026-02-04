package com.zuehlke.securesoftwaredevelopment.repository;

import com.zuehlke.securesoftwaredevelopment.config.AuditLogger;
import com.zuehlke.securesoftwaredevelopment.domain.Book;
import com.zuehlke.securesoftwaredevelopment.domain.Destination;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DestinationRepository {

    private static final Logger LOG = LoggerFactory.getLogger(BookRepository.class);
    private static final AuditLogger auditLogger = AuditLogger.getAuditLogger(BookRepository.class);

    private DataSource dataSource;

    public DestinationRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Destination> getAll() {
        List<Destination> destinationList = new ArrayList<>();
        String query = "SELECT id, cityId, name, description FROM destination";
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

    private Destination crateDestinationFromResultSet(ResultSet rs) throws SQLException {
        int id = rs.getInt(1);
        int cityId = rs.getInt(2);
        String name = rs.getString(3);
        String description = rs.getString(4);

        return new Destination(id, cityId, name, description);
    }
}
