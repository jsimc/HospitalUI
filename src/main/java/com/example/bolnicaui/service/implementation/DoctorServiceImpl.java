package com.example.bolnicaui.service.implementation;

import com.example.bolnicaui.domain.Bill;
import com.example.bolnicaui.domain.Doctor;
import com.example.bolnicaui.domain.Patient;
import com.example.bolnicaui.domain.certificate.PerformedProcedure;
import com.example.bolnicaui.domain.drug.Drug;
import com.example.bolnicaui.domain.drug.Receipt;
import com.example.bolnicaui.domain.exam.Exam;
import com.example.bolnicaui.domain.overtime.DoctorOvertime;
import com.example.bolnicaui.domain.rooms.Discharge;
import com.example.bolnicaui.domain.rooms.HospitalStay;
import com.example.bolnicaui.domain.rooms.Room;
import com.example.bolnicaui.dto.*;
import com.example.bolnicaui.exception.HospitalException;
import com.example.bolnicaui.exception.NoSuchIdException;
import com.example.bolnicaui.exception.WrongExamIdException;
import com.example.bolnicaui.mapper.*;
import com.example.bolnicaui.repository.doctorRepo.*;
import com.example.bolnicaui.repository.drugRepo.DrugRepository;
import com.example.bolnicaui.repository.drugRepo.ReceiptRepository;
import com.example.bolnicaui.repository.examRepo.ExamRepository;
import com.example.bolnicaui.repository.patientRepo.BillRepository;
import com.example.bolnicaui.repository.patientRepo.PatientRepository;
import com.example.bolnicaui.repository.roomRepo.DischargeRepository;
import com.example.bolnicaui.repository.roomRepo.HospitalStayRepository;
import com.example.bolnicaui.repository.roomRepo.RoomRepository;
import com.example.bolnicaui.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorServiceImpl implements DoctorService {
    public final double HIGH_BILL_VALUE = 200_000;
    public final double PRICE_PER_DAY_HOSPITAL_STAY = 1_000;
    private DoctorRepository doctorRepository;
    private MedicalProcedureRepository medicalProcedureRepository;
    private MedicalProcedureMapper medicalProcedureMapper;
    private DoctorMapper doctorMapper;
    private final NurseRepository nurseRepository;
    private final PatientRepository patientRepository;
    private final ExamRepository examRepository;
    private final DrugRepository drugRepository;
    private final ReceiptRepository receiptRepository;
    private final ReceiptMapper receiptMapper;
    private HospitalStayMapper hospitalStayMapper;
    private DischargeMapper dischargeMapper;
    private final RoomRepository roomRepository;
    private final HospitalStayRepository hospitalStayRepository;
    private final DischargeRepository dischargeRepository;
    private final BillRepository billRepository;
    private final SpecializationTypeRepository specializationTypeRepository;

    @Autowired
    public DoctorServiceImpl(DoctorRepository doctorRepository,
                             MedicalProcedureRepository medicalProcedureRepository,
                             MedicalProcedureMapper medicalProcedureMapper,
                             NurseRepository nurseRepository,
                             PatientRepository patientRepository,
                             ExamRepository examRepository,
                             DrugRepository drugRepository,
                             ReceiptRepository receiptRepository,
                             ReceiptMapper receiptMapper,
                             RoomRepository roomRepository,
                             HospitalStayRepository hospitalStayRepository,
                             HospitalStayMapper hospitalStayMapper,
                             DischargeMapper dischargeMapper,
                             DischargeRepository dischargeRepository,
                             BillRepository billRepository,
                             DoctorMapper doctorMapper,
                             SpecializationTypeRepository specializationTypeRepository) {
        this.doctorRepository = doctorRepository;
        this.medicalProcedureRepository = medicalProcedureRepository;
        this.medicalProcedureMapper = medicalProcedureMapper;
        this.nurseRepository = nurseRepository;
        this.patientRepository = patientRepository;
        this.examRepository = examRepository;
        this.drugRepository = drugRepository;
        this.receiptRepository = receiptRepository;
        this.receiptMapper = receiptMapper;
        this.roomRepository = roomRepository;
        this.hospitalStayRepository = hospitalStayRepository;
        this.hospitalStayMapper = hospitalStayMapper;
        this.dischargeMapper = dischargeMapper;
        this.dischargeRepository = dischargeRepository;
        this.billRepository = billRepository;
        this.doctorMapper = doctorMapper;
        this.specializationTypeRepository = specializationTypeRepository;
    }

    @Override
    public List<SpecializationResultI> findAverageNumberOfCertificatesPerSpecialization() {
        return doctorRepository.findAverageNumberOfCertificatesPerSpecialization();
    }

    @Override
    public List<DoctorNameAvgProcedurePerWeekI> findAverageNumberOfProceduresPerWeek() {
        return doctorRepository.findAverageNumberOfProceduresPerWeek();
    }

    @Override
    public List<MedicalProcedureDto> getAllProcedures() {
        return medicalProcedureRepository.findAll(Sort.by(new Sort.Order(Sort.Direction.DESC, "price")))
                .stream().map(medicalProcedureMapper::medicalProcedureToMedicalProcedureDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<DoctorFreeDaysLeftI> getDoctorFreeDaysLeft() {
        return doctorRepository.findDoctorFreeDaysLeft();
    }

    @Override
    public List<NurseFreeDaysLeftI> getNurseFreeDaysLeft() {
        return nurseRepository.findNurseFreeDaysLeft();
    }

    @Override
    public ReceiptDto addReceiptToThePatient(Long doctorId, Long patientId, ReceiptCreateDto receiptCreateDto) throws HospitalException {
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(()->new NoSuchIdException("No doctor with Id = " + doctorId));
        Patient patient = patientRepository.findById(patientId).orElseThrow(()->new NoSuchIdException("No patient with Id = " + patientId));
        Drug drug = drugRepository.findById(receiptCreateDto.getDrugId()).orElseThrow(()->new NoSuchIdException("No drug with Id = " + receiptCreateDto.getDrugId()));
        Receipt receipt = new Receipt();
        receipt.setDosage(receiptCreateDto.getDosage());
        receipt.setExpireDate(receiptCreateDto.getExpireDate());
        receipt.setIssueDate(LocalDate.now());
        receipt.setSelfRenewing(receiptCreateDto.isSelfRenewing());
        doctor.addReceipt(receipt);
        patient.addReceipt(receipt);
        drug.addReceipt(receipt);
        if(receiptCreateDto.getExamId() != null) {
            Exam exam = examRepository.findById(receiptCreateDto.getExamId())
                    .orElseThrow(()->new NoSuchIdException("No exam with Id = " + receiptCreateDto.getExamId()));
            if(exam.getPatient().getId() != patientId) {
                throw new WrongExamIdException(String.format("%s %s", receiptCreateDto.getExamId(), patientId));
            }
            exam.addReceipt(receipt);
            examRepository.save(exam);
        }
        doctorRepository.save(doctor);
        patientRepository.save(patient);
        drugRepository.save(drug);
        receiptRepository.save(receipt);
        return receiptMapper.mapToReceiptDto(receipt);
    }

    @Override
    public ReceiptDto stopRenewingTheReceipt(Long receiptId) throws HospitalException {
        Receipt receipt = receiptRepository.findById(receiptId)
                .orElseThrow(()->new NoSuchIdException("No receipt with Id = " + receiptId));
        receipt.setSelfRenewing(false);
        receiptRepository.save(receipt);
        return receiptMapper.mapToReceiptDto(receipt);
    }

    @Override
    public HospitalStayDto putPatientInHospitalStay(Long patientId, Long roomId) throws HospitalException{
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(()->new NoSuchIdException("No patient with Id = " + patientId));
        Room room = roomRepository.findById(roomId)
                .orElseThrow(()->new NoSuchIdException("No room with Id = " + roomId));
        HospitalStay hospitalStay = new HospitalStay();
        patient.addHospitalStay(hospitalStay);
        room.addHospitalStay(hospitalStay);
        hospitalStayRepository.save(hospitalStay);
//        patientRepository.save(patient);
//        roomRepository.save(room);
        return hospitalStayMapper.mapToHospitalStayDto(hospitalStay);
    }

    @Override
    public DischargeDto dischargePatient(Long doctorId, Long hospitalStayId) throws HospitalException {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(()->new NoSuchIdException("No doctor with Id = " + doctorId));
        HospitalStay hospitalStay = hospitalStayRepository.findById(hospitalStayId)
                .orElseThrow(()->new NoSuchIdException("No hospital stay with Id = " + hospitalStayId));
        // Pravicemo i KONACNI RACUN, KOJI TREBA DA IZRACUNA CENNU ZA CEO BORAVAK PO DANIMA I DA U TO UBACI
        // PROCEDURE KOJE SU SE OBAVILE NAD PACIJENTOM TOKOM BORAVKA.
        Bill bill = new Bill();

        Discharge discharge = new Discharge();
        discharge.setHospitalStay(hospitalStay); // u setHospitalStay se postavlja LocalDate.now i currentlyActive = false.
        doctor.addDischarge(discharge); // discharge.setDoctor

        ////////////////////// CENE PO PROCEDURAMA ///////////////////////////////////
        Patient patient = patientRepository.findById(hospitalStay.getPatient().getId())
                .orElseThrow(()->new NoSuchIdException("No patient with Id = " + hospitalStay.getPatient().getId()));
        List<PerformedProcedure> performedProcedures = patient.getPerformedProcedures()
                .stream().filter(performedProcedure -> performedProcedure.getPerformedDate().isAfter(hospitalStay.getDateFrom()) &&
                        performedProcedure.getPerformedDate().isBefore(hospitalStay.getDateTo())
                ).toList();
        System.out.println(performedProcedures);
        for(PerformedProcedure performedProcedure : performedProcedures) {
            bill.addPerformedProcedure(performedProcedure);
            bill.setPrice(bill.getPrice()+performedProcedure.getMedicalProcedure().getPrice());
        }
        long daysInHospital = ChronoUnit.DAYS.between(hospitalStay.getDateFrom(), hospitalStay.getDateTo());
        bill.setPrice(bill.getPrice()+PRICE_PER_DAY_HOSPITAL_STAY*daysInHospital); //Cena sa danima boravka
        System.out.println(bill.getPrice());
        bill.setHighBill(bill.getPrice()>=HIGH_BILL_VALUE);
        ////////////////////////////////////////////////////////////////////////////////
        discharge.setBill(bill);

        dischargeRepository.save(discharge);
        doctorRepository.save(doctor);
        hospitalStayRepository.save(hospitalStay);
        billRepository.save(bill);

        return dischargeMapper.mapToDischargeDto(discharge);
    }

    @Override
    public DoctorDto getDoctorById(Long doctorId) throws HospitalException{
        return doctorMapper.doctorToDoctorDto(doctorRepository.findById(doctorId)
                .orElseThrow(()->new NoSuchIdException("No doctor with Id = " + doctorId)));
    }
    //TODO ISPITAJ OVO SVE
    //ZA DOKTOR IMAO KOVID
    //ZA BROJ DOZVOLJENIH SATI
    @Override
    public DoctorOvertimeDto putDoctorForOvertime(Long doctorId, LocalDateTime dateFrom, LocalDateTime dateTo) throws HospitalException {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(()->new NoSuchIdException("No doctor with Id = " + doctorId));
        ///////////////////////////// DOKTOR IMAO KOVID ///////////////////////////////////////////////////////////
        if(!doctor.getCovidReports().stream()
                .filter(covidReport -> covidReport.getDateTo().isAfter(ChronoLocalDate.from(dateFrom.minusMonths(3)))
                        || covidReport.getDateFrom().isAfter(ChronoLocalDate.from(dateFrom.minusMonths(3))))
                .toList().isEmpty()) {
            throw new HospitalException("Doktor: " + doctor + " je bio bolestan od kovida u protekla 3 meseca.");
        }
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////

        ///////////////////////////// DOKTOR IMA PREVISE SATI OVERTIME ///////////////////////////////////////////////////////////
        long hoursOvertime = doctor.getDoctorOvertimes().stream()
                .map(doctorOvertime -> ChronoUnit.HOURS.between(doctorOvertime.getDateFrom(), doctorOvertime.getDateTo()))
                .reduce(0L, Long::sum);
        long dodatniSati = ChronoUnit.HOURS.between(dateFrom, dateTo);
        long brojDozvoljenihSati =
                doctor.getSpecializationType().getId() == 7L || doctor.getSpecializationType().getSpecializationName().equals("hirurgija")
                ? 8L : 12L;

//        if (doctor.getSpecializationType().getId() == 7L || doctor.getSpecializationType().getSpecializationName().equals("hirurgija")) {
//            brojDozvoljenihSati = 8L;
//        } else {
//            brojDozvoljenihSati = 12L;
//        }
        if(brojDozvoljenihSati < hoursOvertime+dodatniSati) {
            throw new HospitalException("Doktor " + doctor + " je vec popunio broj prekovremenih sati");
        }
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        DoctorOvertime doctorOvertime = new DoctorOvertime();
        doctorOvertime.setDateFrom(dateFrom);
        doctorOvertime.setDateTo(dateTo);
        doctor.addDoctorOvertime(doctorOvertime);
        doctorRepository.save(doctor);
        return new DoctorOvertimeDto(doctorOvertime.getId(), doctorOvertime.getDateFrom(),
                doctorOvertime.getDateTo(), doctorOvertime.getDoctor().getId());
    }

    @Override
    public DoctorDto saveDoctor(DoctorCreateDto doctorCreateDto) {
        Doctor doctor = doctorMapper.doctorCreateDtoToDoctor(doctorCreateDto);
        doctorRepository.save(doctor);
        return doctorMapper.doctorToDoctorDto(doctor);
    }

    @Override
    public List<DoctorDto> getAllDoctors(){
        return doctorRepository.findAll().stream().map(doctorMapper::doctorToDoctorDto).toList();
    }

}
