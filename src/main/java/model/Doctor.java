package model;

import java.util.TimeZone;

public class Doctor {

    private User user;
    private int doctor_id;
    private String specialization;
    private String department;


    public Doctor() {
    }


    public User getUser() {
        return user;
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

    public void setUser(User user) {
        this.user = user;
    }
}

