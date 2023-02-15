package com.example.bolnicaui.repository.roomRepo;

import com.example.bolnicaui.domain.rooms.Department;
import com.example.bolnicaui.dto.DepartmentDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {
    @Query(nativeQuery = true,
            value = "select dep.* from performed_procedure pp " +
                    "join doctor d on(d.id = pp.doctor_id) " +
                    "join doctor_department dd on(dd.doctor_id = d.id) " +
                    "join department dep on (dep.id = dd.department_id) " +
                    "where dd.date_to is null or (pp.performed_date >= dd.date_from and pp.performed_date <= dd.date_to) " +
                    "group by department_id " +
                    "order by count(dd.department_id) desc")
    List<Department> getMostProceduresDepartment();
}
