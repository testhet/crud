package controller;

import dao.AppointmentDAO;
import model.Appointment;
import Validation_helper.InputValidator;

public class AppointmentController {

    private AppointmentDAO appointmentDAO = new AppointmentDAO();

    public void addAppointmentFromUser() {
        Appointment appointment = new Appointment();

        appointment.setDoctor_id(InputValidator.getValidatedInt("Enter doctor ID: "));
        appointment.setPatient_id(InputValidator.getValidatedInt("Enter patient ID: "));
        appointment.setAppointment_date(InputValidator.getValidatedDate("Enter appointment date (YYYY-MM-DD): "));
        appointment.setAppointment_time(InputValidator.getValidatedTime("Enter appointment time (HH:MM:SS): "));
        appointment.setStatus(InputValidator.getValidatedStatus("Enter status (Scheduled/Completed/Cancelled): "));

        appointmentDAO.addAppointment(appointment);
    }

    public void updateAppointmentFromUser() {
        Appointment appointment = new Appointment();

        appointment.setId(InputValidator.getValidatedInt("Enter appointment ID to update: "));
        appointment.setDoctor_id(InputValidator.getValidatedInt("Enter new doctor ID: "));
        appointment.setPatient_id(InputValidator.getValidatedInt("Enter new patient ID: "));
        appointment.setAppointment_date(InputValidator.getValidatedDate("Enter new appointment date (YYYY-MM-DD): "));
        appointment.setAppointment_time(InputValidator.getValidatedTime("Enter new appointment time (HH:MM:SS): "));
        appointment.setStatus(InputValidator.getValidatedStatus("Enter new status (Scheduled/Completed/Cancelled): "));

        appointmentDAO.updateAppointment(appointment);
    }
}
