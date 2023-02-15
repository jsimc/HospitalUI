package com.example.bolnicaui.domain.rooms;

import com.example.bolnicaui.domain.ChosenDoctor;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "room_number")
    private int roomNumber;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "block_id")
    private Block block;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_type_id")
    private RoomType roomType;
    @OneToMany(mappedBy = "room", cascade = {CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.MERGE, CascadeType.DETACH})
    private List<HospitalStay> hospitalStays;

    public void addHospitalStay(HospitalStay hospitalStay) {
        if(hospitalStay == null)
            return;
        if(hospitalStays == null)
            hospitalStays = new ArrayList<>();
        hospitalStays.add(hospitalStay);
        hospitalStay.setRoom(this);
        hospitalStay.setDateFrom(LocalDate.now());
        hospitalStay.setCurrentlyActive(true);
    }
}
