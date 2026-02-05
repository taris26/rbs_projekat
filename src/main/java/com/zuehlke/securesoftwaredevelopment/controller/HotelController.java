package com.zuehlke.securesoftwaredevelopment.controller;

import com.zuehlke.securesoftwaredevelopment.config.AuditLogger;
import com.zuehlke.securesoftwaredevelopment.domain.RoomType;
import com.zuehlke.securesoftwaredevelopment.repository.RoomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class HotelController {
    private static final Logger LOG = LoggerFactory.getLogger(HotelController.class);
    private static final AuditLogger auditLogger = AuditLogger.getAuditLogger(HotelController.class);

    private RoomRepository roomRepository;

    public HotelController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @GetMapping("/new-hotel")
    public String newHotel() {
        return "new-hotel";
    }

    @GetMapping("/api/hotels/{hotelId}/room-types")
    public ResponseEntity<List<RoomType>> getRoomTypesForHotel(@PathVariable Integer hotelId) {
        List<RoomType> result = roomRepository.getAllRoomTypes(hotelId);
        return ResponseEntity.ok(result);
    }
}
