package com.example.bolnicaui.domain.rooms;

import com.example.bolnicaui.domain.duty.DutyNurse;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "block")
public class Block {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "code", nullable = false, unique = true)
    private String code;
    @Column(name = "block_name")
    private String blockName;
    @Column(name = "floor_number")
    private int floorNumber;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;
    @OneToMany(mappedBy = "block")
    private List<DutyNurse> dutyNurses;

}
