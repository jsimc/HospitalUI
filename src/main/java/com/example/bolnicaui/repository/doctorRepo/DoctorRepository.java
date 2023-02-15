package com.example.bolnicaui.repository.doctorRepo;

import com.example.bolnicaui.domain.Doctor;
import com.example.bolnicaui.domain.certificate.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    @Query(nativeQuery = true,
    value = "select st.specialization_name as specialization, count(d.specialization_type_id)/totalNum as result from doctor d " +
            "join doctor_certificate dc on(dc.doctor_id = d.id) " +
            "join ( " +
            "select specialization_type_id, count(d1.id) as totalNum from doctor d1 " +
            "where specialization_type_id is not null " +
            "group by specialization_type_id " +
            ") a on (a.specialization_type_id = d.specialization_type_id) " +
            "join specialization_type st on(st.id = d.specialization_type_id) " +
            "where d.specialization_type_id is not null " +
            "group by d.specialization_type_id;")
    List<SpecializationResultI> findAverageNumberOfCertificatesPerSpecialization();
    @Query(nativeQuery = true,
    value = "select concat(d.first_name, ' ', d.last_name) as doctorName, count(pp.doctor_id)/pom2.totalWeekNum as avgProcedurePerWeek " +
            "from performed_procedure pp " +
            "join ( " +
            "select doctor_id, count(distinct week(performed_date)) as totalWeekNum from performed_procedure " +
            "group by doctor_id " +
            ") pom2 on(pom2.doctor_id = pp.doctor_id) " +
            "join doctor d on(d.id = pp.doctor_id) " +
            "group by pp.doctor_id;")
    List<DoctorNameAvgProcedurePerWeekI> findAverageNumberOfProceduresPerWeek();
    @Query(nativeQuery = true,
    value = "select concat(d.first_name, ' ', d.last_name) as doctorName," +
            "    d.free_time_days as freeTimeDays," +
            "    ifnull(d.free_time_days-sum(abs(datediff(dh.date_from, dh.date_to))+1), d.free_time_days) as daysLeft from doctor d " +
            "left join doctor_holiday dh on (d.id = dh.doctor_id) " +
            "where d.free_time_days is not null " +
            "group by d.id;")
    List<DoctorFreeDaysLeftI> findDoctorFreeDaysLeft();

    /**
     * select dep.name as departmentName, concat(first_name, ' ', last_name) as doctorName, " +
     * "d.free_time_days as freeTimeDays, " +
     * "d.free_time_days-sum(abs(datediff(dh.date_from, dh.date_to))+1) as daysLeft from doctor_holiday dh " +
     * "join doctor d on(d.id = dh.doctor_id) " +
     * "join doctor_department dd on(d.id = dd.doctor_id) " +
     * "join department dep on (dd.department_id = dep.id) " +
     * "where d.free_time_days is not null " +
     * "and dd.main_department is true " +
     * "group by dep.id, d.id;
     */

}