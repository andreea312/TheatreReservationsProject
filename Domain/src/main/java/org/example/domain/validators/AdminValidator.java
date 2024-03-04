package org.example.domain.validators;

import org.example.domain.Admin;

import java.util.regex.Pattern;

public class AdminValidator implements Validator<Admin> {
    /**
     * Validates the given User object
     * @param entity User - the user that has to be validated
     * @throws ValidationException exception is thrown if user is not valid, meaning
     * its first/last name has an inappropriate length,
     * email is null or its structure does not respect the specified regex
     */
    @Override
    public void validate(Admin entity) throws ValidationException {
        String messages = "";
        String email = entity.getEmail();

        if (email.equals(""))
            messages = messages + "Email cannot be null!\n";
        else{
            String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+ "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z" + "A-Z]{2,7}$";
            Pattern pat = Pattern.compile(emailRegex);
            if(!pat.matcher(email).matches()) {
                messages = messages + "Invalid email structure!\n";
            }
        }

        String password = entity.getPassword();

        if(password.length() < 8 || password.length() > 20)
            messages = messages + "Password length should be between 8 and 20 characters!\n";

        String upperCaseChars = "(.*[A-Z].*)";
        if (!password.matches(upperCaseChars ))
            messages = messages + "Password must have at least one uppercase character!\n";

        String lowerCaseChars = "(.*[a-z].*)";
        if (!password.matches(lowerCaseChars ))
            messages = messages + "Password must have at least one lowercase character!\n";

        String numbers = "(.*[0-9].*)";
        if (!password.matches(numbers ))
            messages = messages + "Password must have at least one number!\n";

        String specialChars = "(.*[@,#,$,%].*$)";
        if (!password.matches(specialChars ))
            messages = messages + "Password must have at least one special character among @#$%!\n";

        String validationCode = entity.getTheatre().getValidationCode();

        if(!validationCode.equals("0A1B2C3D"))
            messages = messages + "Invalid validation code!\n";

        if(!messages.equals("")){
            throw new ValidationException(messages);
        }
    }
}
