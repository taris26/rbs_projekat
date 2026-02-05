package com.zuehlke.securesoftwaredevelopment.controller;

import com.zuehlke.securesoftwaredevelopment.config.AuditLogger;
import com.zuehlke.securesoftwaredevelopment.domain.Hotel;
import com.zuehlke.securesoftwaredevelopment.domain.Reservation;
import com.zuehlke.securesoftwaredevelopment.domain.RoomType;
import com.zuehlke.securesoftwaredevelopment.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ReservationController {
    private static final Logger LOG = LoggerFactory.getLogger(ReservationController.class);
    private static final AuditLogger auditLogger = AuditLogger.getAuditLogger(ReservationController.class);

    private ReservationRepository reservationRepository;
    private HotelRepository hotelRepository;
    private RoomRepository roomRepository;

    public ReservationController(ReservationRepository reservationRepository, HotelRepository hotelRepository, RoomRepository roomRepository) {
        this.reservationRepository = reservationRepository;
        this.hotelRepository = hotelRepository;
        this.roomRepository = roomRepository;
    }

    @GetMapping("/reservations/new/{id}")
    public String showReservation(@PathVariable("id") int id, Model model) {
        model.addAttribute("id", id);

        List<Hotel> hotels = hotelRepository.getAllHotelFromCity(id);
        model.addAttribute("hotels", hotels);

        return "reserve-destination";
    }

    @PostMapping("/reservations/create")
    public String createReservation(
            @RequestParam Integer userId,
            @RequestParam Integer hotelId,
            @RequestParam Integer roomTypeId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam Integer roomsCount,
            @RequestParam Integer guestsCount
    ) {
        if (userId == null || userId <= 0) return "redirect:/reservations/new?createError=true";
        if (hotelId == null || hotelId <= 0) return "redirect:/reservations/new?createError=true";
        if (roomTypeId == null || roomTypeId <= 0) return "redirect:/reservations/new?createError=true";
        if (roomsCount == null || roomsCount <= 0) return "redirect:/reservations/new?createError=true";
        if (guestsCount == null || guestsCount <= 0) return "redirect:/reservations/new?createError=true";
        if (startDate == null || endDate == null || !endDate.isAfter(startDate)) {
            return "redirect:/reservations/new?dateError=true";
        }

        if (!hotelRepository.existsById(hotelId)) {
            return "redirect:/reservations/new?hotelError=true";
        }

        RoomType roomType = roomRepository.findByIdAndHotelId(roomTypeId, hotelId);
        if (roomType == null) {
            return "redirect:/reservations/new?roomTypeError=true";
        }

        long nights = ChronoUnit.DAYS.between(startDate, endDate);
        BigDecimal totalPrice = roomType.getPricePerNight()
                .multiply(BigDecimal.valueOf(nights))
                .multiply(BigDecimal.valueOf(roomsCount));

        int maxGuests = roomType.getCapacity() * roomsCount;
        if (guestsCount > maxGuests) {
            return "redirect:/reservations/new?createError=true";
        }

        Reservation r = new Reservation();
        r.setUserId(userId);
        r.setHotelId(hotelId);
        r.setRoomTypeId(roomTypeId);
        r.setStartDate(startDate);
        r.setEndDate(endDate);
        r.setRoomsCount(roomsCount);
        r.setGuestsCount(guestsCount);
        r.setTotalPrice(totalPrice);

        reservationRepository.save(r);

        return "redirect:/reservations/new?created=true";
    }

}
