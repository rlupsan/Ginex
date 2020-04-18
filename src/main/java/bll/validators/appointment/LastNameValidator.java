package bll.validators.appointment;

import bll.validators.Validator;
import model.Appointment;

import java.util.regex.Pattern;

public class LastNameValidator implements Validator<Appointment> {
    private static final String NAME_PATTERN = "^[a-zA-Z]+(([',. -][a-zA-Z ])?[a-zA-Z]*)*$";

    /**
     * source: https://www.regextester.com/93648
     * applies to all possible names
     */
    public void validate(Appointment appointment) {
        Pattern pattern = Pattern.compile(NAME_PATTERN);
        if (!pattern.matcher(appointment.getLastName()).matches()) {
            throw new IllegalArgumentException("Last Name is not well written!");
        }
    }
}
