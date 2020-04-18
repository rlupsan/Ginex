package bll;

import bll.validators.Validator;
import bll.validators.appointment.FirstNameValidator;
import bll.validators.appointment.LastNameValidator;
import dao.AppointmentDAO;
import model.Appointment;

import java.util.ArrayList;
import java.util.List;

public class AppointmentBLL {
    private static List<Validator<Appointment>> validators;

    public AppointmentBLL() {
        validators = new ArrayList<>();
        validators.add(new FirstNameValidator());
        validators.add(new LastNameValidator());
    }

    public static int insertAppointment(Appointment appointment) {
        for (Validator<Appointment> v : validators) {
            v.validate(appointment);
        }
        return AppointmentDAO.insert(appointment);
    }

    public static int updateAppointment(Appointment appointment) {
        for (Validator<Appointment> v : validators) {
            v.validate(appointment);
        }
        return AppointmentDAO.update(appointment);
    }


}
