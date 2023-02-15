package com.example.bolnicaui.controller;

import com.example.bolnicaui.domain.certificate.PerformedProcedure;
import com.example.bolnicaui.domain.rooms.Department;
import com.example.bolnicaui.dto.DepartmentDto;
import com.example.bolnicaui.dto.DoctorDto;
import com.example.bolnicaui.exception.HospitalException;
import com.example.bolnicaui.service.RoomService;
import com.sun.net.httpserver.HttpServer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/room")
public class RoomController {
    RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }
    @GetMapping("/numberOfIntensiveCareRooms")
    public ResponseEntity<String> numberOfIntensiveCareRooms() {
        return new ResponseEntity<>("There is " +
                roomService.getNumberOfIntensiveCareRooms() +
                " intensive care rooms.", HttpStatus.OK);
    }

    @GetMapping("/mostProceduresDepartment")
    public ResponseEntity<DepartmentDto> mostProceduresDepartment() {
        return new ResponseEntity<>(roomService.getMostProceduresDepartment(), HttpStatus.OK);
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<DoctorDto>> getHeadDoctorsForDepartment(@PathVariable Long departmentId,
                                                                       @RequestParam(name = "dateFrom", required = false) LocalDate dateFrom,
                                                                       @RequestParam(name = "dateTo", required = false) LocalDate dateTo) {
        try {
            return new ResponseEntity<>(roomService.getHeadDoctorsForDepartment(departmentId, dateFrom, dateTo), HttpStatus.OK);
        } catch (HospitalException hospitalException) {
            System.out.println(hospitalException.getMessage());
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
}
