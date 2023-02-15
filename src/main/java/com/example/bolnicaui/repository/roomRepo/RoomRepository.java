package com.example.bolnicaui.repository.roomRepo;


import com.example.bolnicaui.domain.rooms.Room;
import com.example.bolnicaui.dto.DepartmentDto;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    /**
     * Jedan mali FUN FACT: Ako ti je upit Update, Insert, Delete...
     * Onda ti treba @Modifying
     * ako ti samo vraca neki rezultat NE STAVLJAJ @Modifying !!!
     */
    @Query(nativeQuery = true,
            value = "select count(r.id) as number from room r " +
                    "where r.room_type_id = 1;")
    Integer findNumberOfIntensiveCareRooms();


}
