package com.zuehlke.securesoftwaredevelopment.repository;

import com.zuehlke.securesoftwaredevelopment.config.AuditLogger;
import com.zuehlke.securesoftwaredevelopment.domain.Hotel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Repository
public class HotelRepository {

    private static final Logger LOG = LoggerFactory.getLogger(HotelRepository.class);
    private static final AuditLogger auditLogger = AuditLogger.getAuditLogger(HotelRepository.class);

    private DataSource dataSource;

    public HotelRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Hotel> getAllHotelFromCity(int cityId) {
        List<Hotel> hotelList = new ArrayList<>();
        String query = "SELECT id, name, description, address FROM hotel WHERE cityId = " + cityId;
        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(query)) {
            while (rs.next()) {
                Integer id = rs.getInt(1);
                String name = rs.getString(2);
                String description = rs.getString(3);
                String address = rs.getString(4);

                // Integer cityId, String name, String description, String address
                hotelList.add(new Hotel(id, cityId, name, description, address));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return hotelList;
    }

    public boolean existsById(int hotelId) {
        String query = "SELECT * FROM hotel WHERE hotel.id = " + hotelId;

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(query)) {
            return rs.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
