package model;

public class Appointment {
    private int id;
    private int doctor_id;
    private int patient_id;
    private String appointment_date;
    private String getAppointment_time;
    private String reason;
    private String status;
    private String created_at;

//    public Appointment(int id, int doctor_id, int patient_id, String appointment_date, String getAppointment_time, String reason, String status, String created_at) {
//        this.id = id;
//        this.doctor_id = doctor_id;
//        this.patient_id = patient_id;
//        this.appointment_date = appointment_date;
//        this.getAppointment_time = getAppointment_time;
//        this.reason = reason;
//        this.status = status;
//        this.created_at = created_at;
//    }
//    public Appointment(int doctor_id, int patient_id, String appointment_date, String getAppointment_time, String reason, String status, String created_at) {
//
//        this.doctor_id = doctor_id;
//        this.patient_id = patient_id;
//        this.appointment_date = appointment_date;
//        this.getAppointment_time = getAppointment_time;
//        this.reason = reason;
//        this.status = status;
//        this.created_at = created_at;
//    }
//    public Appointment() {
//    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public String getAppointment_date() {
        return appointment_date;
    }

    public void setAppointment_date(String appointment_date) {
        this.appointment_date = appointment_date;
    }

    public String getAppointment_time() {
        return getAppointment_time;
    }

    public void setAppointment_time(String getAppointment_time) {
        this.getAppointment_time = getAppointment_time;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
