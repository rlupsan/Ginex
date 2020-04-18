package bll.validators.employee;


import bll.validators.Validator;
import model.Employee;

import java.util.regex.Pattern;

public class UsernameValidator implements Validator<Employee> {
    /**
     * Username Validator assures that the username is chosen as follows:
     * in order from the code:
     * username is 3-20 characters long
     * no _ or . at the beginning not at the end
     * no __ or _. or ._ or .. inside
     * [a-zA-Z0-9._] represent the allowed characters
     * <p>
     * source: https://stackoverflow.com/questions/12018245/regular-expression-to-validate-username
     */
    private static final String usernameRegex = "^(?=.{3,20}$)(?![_.])(?!.*[_.]{2})[a-zA-Z0-9._]+(?<![_.])$";

    public void validate(Employee employee) {
        Pattern pattern = Pattern.compile(usernameRegex);
        if (!pattern.matcher(employee.getUsername()).matches()) {
            throw new IllegalArgumentException("Username is not well chosen!");
        }
    }
}
