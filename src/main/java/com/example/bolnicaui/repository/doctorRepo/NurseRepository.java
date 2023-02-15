package com.example.bolnicaui.repository.doctorRepo;

import com.example.bolnicaui.domain.Nurse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface NurseRepository extends JpaRepository<Nurse, Long> {
    @Query(nativeQuery = true,
            value = "select dep.name as departmentName, concat(first_name, ' ', last_name) as nurseName, " +
                    "n.free_time_days as freeTimeDays, " +
                    "n.free_time_days-sum(abs(datediff(nh.date_from, nh.date_to))+1) as daysLeft from nurse_holiday nh " +
                    "join nurse n on(n.id = nh.nurse_id) " +
                    "join nurse_department nd on(n.id = nd.nurse_id) " +
                    "join department dep on (nd.department_id = dep.id) " +
                    "where n.free_time_days is not null " +
                    "group by n.id;")
    List<NurseFreeDaysLeftI> findNurseFreeDaysLeft();
}
