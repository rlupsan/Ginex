package bll.validators.employee;

import bll.validators.Validator;
import model.Employee;

import java.util.regex.Pattern;

public class PasswordValidator implements Validator<Employee> {
    /**
     * Password Validator:
     * At least one upper case English letter, (?=.*?[A-Z])
     * At least one lower case English letter, (?=.*?[a-z])
     * At least one digit, (?=.*?[0-9])
     * Minimum four in length .{4,} (with the anchors)
     * source: https://stackoverflow.com/questions/19605150/regex-for-password-must-contain-at-least-eight-characters-at-least-one-number-a
     */
    private static final String passString = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9]).{4,}$";

    public void validate(Employee employee) {
        Pattern pattern = Pattern.compile(passString);
        if (!pattern.matcher(employee.getPassword()).matches()) {
            throw new IllegalArgumentException("Password is not good enough!");
        }
    }
}
