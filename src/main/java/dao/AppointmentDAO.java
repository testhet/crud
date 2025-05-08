package dao;

import config.DBconnection;
import model.Appointment;

import javax.sound.midi.Soundbank;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AppointmentDAO {

    public void addAppointment(Appointment appointment) throws SQLException {
        String sql = "INSERT INTO appointment (doctor_id, patient_id, appointment_date, appointment_time, status) " +
                "VALUES (?, ?, ?, ?, ?)";

        Connection connection = DBconnection.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql);

        stmt.setInt(1, appointment.getDoctor_id());
        stmt.setInt(2, appointment.getPatient_id());
        stmt.setDate(3, Date.valueOf(appointment.getAppointment_date()));
        stmt.setTime(4, Time.valueOf(appointment.getAppointment_time()));
        stmt.setString(5, appointment.getStatus());

        stmt.executeUpdate();
        System.out.println("Appointment inserted successfully.");
    }

    public void updateAppointment(Appointment appointment) throws SQLException {
        String sql = "UPDATE appointment SET doctor_id = ?, patient_id = ?, appointment_date = ?, " +
                "appointment_time = ?, status = ? WHERE id = ?";

        Connection connection = DBconnection.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql);

        stmt.setInt(1, appointment.getDoctor_id());
        stmt.setInt(2, appointment.getPatient_id());
        stmt.setDate(3, Date.valueOf(appointment.getAppointment_date()));
        stmt.setTime(4, Time.valueOf(appointment.getAppointment_time()));
        stmt.setString(5, appointment.getStatus());
        stmt.setInt(6, appointment.getId());

        stmt.executeUpdate();
        System.out.println("Appointment updated successfully.");
    }

    public void updateAppointmentStatus(int appointmentId, String status) throws SQLException {
        String sql = "UPDATE appointment SET status = ? WHERE id = ?";

        Connection connection = DBconnection.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql);

        stmt.setString(1, status);
        stmt.setInt(2, appointmentId);

        stmt.executeUpdate();

    }

    public void rescheduleAppointment (int id, String date , String time) throws SQLException{
        String sql = "UPDATE appointment SET appointment_date = ? , appointment_time = ? WHERE id = ? ;";

        Connection connection = DBconnection.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql);

        stmt.setInt(1,id);
        stmt.setDate(2, Date.valueOf(date));
        stmt.setTime(3, Time.valueOf(time));

        stmt.executeUpdate();

    }


    public List<Appointment> getAppointmentsByDoctorId(int doctorId) throws SQLException {
        String sql = "SELECT * FROM appointment WHERE doctor_id = ?";
        Connection connection = DBconnection.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, doctorId);
        ResultSet rs = stmt.executeQuery();

        List<Appointment> appointments = new ArrayList<>();
        while (rs.next()) {
            Appointment appointment = new Appointment();
            appointment.setId(rs.getInt("id"));
            appointment.setDoctor_id(rs.getInt("doctor_id"));
            appointment.setPatient_id(rs.getInt("patient_id"));
            appointment.setAppointment_date(rs.getString("appointment_date"));
            appointment.setAppointment_time(rs.getString("appointment_time"));
            appointment.setStatus(rs.getString("status"));
            appointments.add(appointment);
        }

        return appointments;
    }

    public List<Appointment> getAppointmentsByPatientId(int patientId) throws SQLException {
        String sql = "SELECT * FROM appointment WHERE patient_id = ?";
        Connection connection = DBconnection.getConnection();
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setInt(1, patientId);
        ResultSet rs = stmt.executeQuery();

        List<Appointment> appointments = new ArrayList<>();
        while (rs.next()) {
            Appointment appointment = new Appointment();
            appointment.setId(rs.getInt("id"));
            appointment.setDoctor_id(rs.getInt("doctor_id"));
            appointment.setPatient_id(rs.getInt("patient_id"));
            appointment.setAppointment_date(rs.getString("appointment_date"));
            appointment.setAppointment_time(rs.getString("appointment_time"));
            appointment.setStatus(rs.getString("status"));
            appointments.add(appointment);
        }

        return appointments;
    }
}
