package dao;

import config.DBconnection;
import model.Appointment;
import model.User;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO {

    public void addAppointment(Appointment appointment) throws SQLException {
        String sql = "INSERT INTO appointment (doctor_id, patient_id, appointment_date, appointment_time, status) " +
                "VALUES (?, ?, ?, ?, ?);";
        try (Connection connection = DBconnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, appointment.getDoctor_id());
            stmt.setInt(2, appointment.getPatient_id());
            stmt.setDate(3, Date.valueOf(appointment.getAppointment_date()));
            stmt.setString(4, appointment.getAppointment_time());
            stmt.setString(5, appointment.getStatus());
            stmt.executeUpdate();
        }
    }

    public int doctorIDFromAppointmentID(int id) throws  SQLException {
        String sql = "SELECT a.doctor_id AS dID FROM appointment a WHERE a.id = ? LIMIT 1;";
        int a = 0;
        try (Connection connection = DBconnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                a = rs.getInt("dID");
            }
        }
        return a;
    }

    public boolean appointmentExistPatient(int id, int patientID) throws SQLException {
        String sql = "SELECT 1 FROM appointment WHERE id = ? AND patient_id = ? LIMIT 1;";
        ResultSet rs;
        try (Connection connection = DBconnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.setInt(2,patientID);
            rs = stmt.executeQuery();
            return rs.next();
        }
    }

    public List<String> bookedTimeslots(int doctorId, String date) throws SQLException {
        List<String> bookedSlots = new ArrayList<>();
        String sql = """
                SELECT
                    a.appointment_time AS BookedSlots
                FROM appointment a
                WHERE a.doctor_id = ? AND a.appointment_date = ? AND a.status = "Scheduled";
                """;
        try (Connection connection = DBconnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, doctorId);
            stmt.setDate(2, Date.valueOf(date));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String slot = rs.getString("BookedSlots");
                bookedSlots.add(slot.trim());
            }
        }
        return bookedSlots;
    }


    public boolean appointmentExistDoctor(int id, int DoctorID) throws SQLException {
        String sql = "SELECT 1 FROM appointment WHERE id = ? AND doctor_id = ? LIMIT 1;";
        ResultSet rs;
        try (Connection connection = DBconnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.setInt(2,DoctorID);
            rs = stmt.executeQuery();
            return rs.next();
        }
    }

    public void updateAppointmentStatus(int appointmentId, String status) throws SQLException {
        String sql = "UPDATE appointment SET status = ? WHERE id = ? ";
        try (Connection connection = DBconnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setString(1, status);
            stmt.setInt(2, appointmentId);
            stmt.executeUpdate();
        }
    }

    public void rescheduleAppointment(int id, String date, String time) throws SQLException {
        String sql = "UPDATE appointment SET appointment_date = ? , appointment_time = ? WHERE id = ? ;";
        try (Connection connection = DBconnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(date));
            stmt.setString(2, time);
            stmt.setInt(3, id);
            stmt.executeUpdate();
        }
    }

    public boolean getAppointmentStatus(int id) throws SQLException {
        String sql = "SELECT 1 FROM appointment WHERE id = ? AND status = \"Scheduled\" LIMIT 1; ";
        try (Connection connection = DBconnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        }
    }


    public void getAppointmentList(User user) throws SQLException {
        String appointmentListForDoctor = """
                 SELECT
                      a.id as AppointmentID,
                      u.id AS PatientID,
                      u.user_name AS PatientName,
                      p.gender AS Gender,
                      p.phone AS Phone,
                      a.appointment_date AS Date,
                      a.appointment_time AS Time,
                      a.status AS "Appointment Status"
                  FROM appointment a
                  JOIN patients p ON p.patient_id = a.patient_id
                  JOIN users u ON u.id = p.patient_id
                  WHERE a.doctor_id = ? AND u.role = "PATIENT"
                  ORDER BY a.appointment_date, a.appointment_time;
                """;
        String appointmentListForPatient = """
                SELECT
                    a.id AS AppointmentID,
                    u.id AS DoctorID,
                    u.user_name AS DoctorName,
                    d.department AS Department,
                    a.appointment_date AS Date,
                    a.appointment_time AS Time,
                    a.status AS "Appointment Status"
                FROM appointment a
                JOIN doctors d ON d.doctor_id = a.doctor_id
                JOIN users u ON u.id = d.doctor_id
                WHERE a.patient_id = ? AND u.role = "DOCTOR"
                ORDER BY a.appointment_date,a.appointment_time;
                """;
        try (Connection connection = DBconnection.getConnection()) {
            PreparedStatement statement;
            try {
                if (user.getRole().equalsIgnoreCase("DOCTOR")) {
                    statement = connection.prepareStatement(appointmentListForDoctor);
                    statement.setInt(1, user.getId()); // Set doctor_id parameter
                    ResultSet rs = statement.executeQuery();
                    System.out.printf("%-13s %-13s %-25s %-18s %-22s %-22s %-20s %-22s%n", "AppointmentID", "PatientID", "PatientName", "Gender", "Phone", "AppointmentDate", "AppointmentTime", "Status");
                    System.out.println("-----------------------------------------------------------------------------------------");
                    while (rs.next()) {
                        int appointmentID = rs.getInt("AppointmentID");
                        int patientID = rs.getInt("PatientID");
                        String PatientName = rs.getString("PatientName");
                        String Gender = rs.getString("Gender");
                        String Phone = rs.getString("Phone");
                        java.util.Date date = rs.getDate("Date");
                        String time = rs.getString("Time");
                        String status = rs.getString("Appointment Status");
                        System.out.printf("%-13s %-13s %-25s %-18s %-22s %-22s %-20s %-22s%n", appointmentID, patientID, PatientName, Gender, Phone, date, time, status);
                    } rs.close();
                } else if (user.getRole().equalsIgnoreCase("PATIENT")) {
                    statement = connection.prepareStatement(appointmentListForPatient);
                    statement.setInt(1, user.getId()); // Set patient_id parameter
                    ResultSet rs = statement.executeQuery();
                    System.out.printf("%-13s %-13s %-25s %-22s %-22s %-20s %-22s%n", "AppointmentID", "PatientID", "DoctorName", "Department", "AppointmentDate", "AppointmentTime", "Status");
                    System.out.println("-----------------------------------------------------------------------------------------");
                    while (rs.next()) {
                        int appointmentID = rs.getInt("AppointmentID");
                        int doctorID = rs.getInt("DoctorID");
                        String DoctorName = rs.getString("DoctorName");
                        String Department = rs.getString("Department");
                        java.util.Date date = rs.getDate("Date");
                        String time = rs.getString("Time");
                        String status = rs.getString("Appointment Status");
                        System.out.printf("%-13s %-13s %-25s %-22s %-22s %-20s %-22s%n", appointmentID, doctorID, DoctorName, Department, date, time, status);
                    } rs.close();
                } else {
                    System.out.println("Invalid user role: " + user.getRole());
                }
            } catch (SQLException e) {
                System.err.println("Error retrieving appointment list: " + e.getMessage());
            }
        }
    }
}