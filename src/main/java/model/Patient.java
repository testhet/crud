package model;

public class Patient {


    private int patient_id;
    private String date_of_birth;
    private String gender;
    private String address;
    private int phone;
    private int emergency_contact_number;
    private String insuranceID;
    private String insurance_provider;

    public Patient(int patient_id, String date_of_birth, String gender, String address, int phone, int emergency_contact_number, String insuranceID, String insurance_provider) {

        this.patient_id = patient_id;
        this.date_of_birth = date_of_birth;
        this.gender = gender;
        this.address = address;
        this.phone = phone;
        this.emergency_contact_number = emergency_contact_number;
        this.insuranceID = insuranceID;
        this.insurance_provider = insurance_provider;
    }
    public Patient(String date_of_birth, String gender, String address, int phone, int emergency_contact_number, String insuranceID, String insurance_provider) {

        this.date_of_birth = date_of_birth;
        this.gender = gender;
        this.address = address;
        this.phone = phone;
        this.emergency_contact_number = emergency_contact_number;
        this.insuranceID = insuranceID;
        this.insurance_provider = insurance_provider;
    }

    public int getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(int patient_id) {
        this.patient_id = patient_id;
    }

    public String getDate_of_birth() {
        return date_of_birth;
    }

    public void setDate_of_birth(String date_of_birth) {
        this.date_of_birth = date_of_birth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public int getEmergency_contact_number() {
        return emergency_contact_number;
    }

    public void setEmergency_contact_number(int emergency_contact_number) {
        this.emergency_contact_number = emergency_contact_number;
    }

    public String getInsuranceID() {
        return insuranceID;
    }

    public void setInsuranceID(String insuranceID) {
        this.insuranceID = insuranceID;
    }

    public String getInsurance_provider() {
        return insurance_provider;
    }

    public void setInsurance_provider(String insurance_provider) {
        this.insurance_provider = insurance_provider;
    }
}


