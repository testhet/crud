
package dao;

import config.DBconnection;
import model.Appointment;

import java.sql.*;

public class AppointmentDAO {

    public void addAppointment(Appointment appointment) {
        String sql = "INSERT INTO appointment (doctor_id, patient_id, appointment_date, appointment_time, status) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBconnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {


            stmt.setInt(1, appointment.getDoctor_id());
            stmt.setInt(2, appointment.getPatient_id());
            stmt.setDate(3, Date.valueOf(appointment.getAppointment_date()));
            stmt.setTime(4, Time.valueOf(appointment.getAppointment_time()));
            stmt.setString(5, appointment.getStatus());

            stmt.executeUpdate();
            System.out.println("Appointment inserted successfully.");

        } catch (SQLException e) {
            System.out.println("Error inserting appointment: " + e.getMessage());
        }
    }

    public void updateAppointment(Appointment appointment){
        String sql = "UPDATE appointment\n" +
                     "SET doctor_id = ?, patient_id = ?, appointment_date = ?, appointment_time = ?, status = ?\n" +
                     "WHERE id = ?;";
        try(Connection conn = DBconnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1,appointment.getDoctor_id());
            stmt.setInt(2,appointment.getPatient_id());
            stmt.setDate(3, Date.valueOf(appointment.getAppointment_date()));
            stmt.setTime(4, Time.valueOf(appointment.getAppointment_time()));
            stmt.setString(5,appointment.getStatus());
            stmt.setInt(6,appointment.getId());

            stmt.executeUpdate();
            System.out.println("appointment updated successfully");


        }catch (SQLException e) {
            System.out.println("Error Updating appointment: " + e.getMessage());
        }
    }

}
