package model;

public class Doctor {

    private int doctor_id;
    private String specialization;
    private String department;


    public Doctor(int doctor_id, String specialization, String department) {
        this.doctor_id = doctor_id;
        this.specialization = specialization;
        this.department = department;
    }
    public Doctor(String specialization, String department) {

        this.specialization = specialization;
        this.department = department;
    }

    public int getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(int doctor_id) {
        this.doctor_id = doctor_id;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
