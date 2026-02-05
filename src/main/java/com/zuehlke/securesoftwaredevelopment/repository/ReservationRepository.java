package com.zuehlke.securesoftwaredevelopment.repository;

import com.zuehlke.securesoftwaredevelopment.config.AuditLogger;
import com.zuehlke.securesoftwaredevelopment.domain.Reservation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Repository
public class ReservationRepository {
    private static final Logger LOG = LoggerFactory.getLogger(DestinationRepository.class);
    private static final AuditLogger auditLogger = AuditLogger.getAuditLogger(DestinationRepository.class);

    private DataSource dataSource;

    public ReservationRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void save(Reservation r) {
        String query = "INSERT INTO reservation(userId, hotelId, roomTypeId, startDate, endDate, roomsCount, guestsCount, totalPrice) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?," + r.getTotalPrice() + ")";
        long id = 0;
        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
        ) {
            statement.setInt(1, r.getUserId());
            statement.setInt(2, r.getHotelId());
            statement.setInt(3, r.getRoomTypeId());
            statement.setDate(4, java.sql.Date.valueOf(r.getStartDate()));
            statement.setDate(5, java.sql.Date.valueOf(r.getEndDate()));
            statement.setInt(6, r.getRoomsCount());
            statement.setInt(7, r.getGuestsCount());
            statement.setBigDecimal(8, r.getTotalPrice());

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

