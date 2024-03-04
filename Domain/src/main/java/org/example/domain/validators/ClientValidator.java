package org.example.domain.validators;

import org.example.domain.Client;

import java.util.regex.Pattern;

public class ClientValidator implements Validator<Client> {
    @Override
    public void validate(Client entity) throws ValidationException {
        String messages = "";
        String phone = entity.getPhone();
        String firstName = entity.getFirstName();
        String lastName = entity.getLastName();

        if (phone.length() != 10)
            messages = messages + "Phone number must have 10 numbers!\n";

        if (firstName.length() < 3)
            messages = messages + "First name must have at least 3 characters!\n";

        if (lastName.length() < 3)
            messages = messages + "Last name must have at least 3 characters!\n";

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

        if(!messages.equals("")){
            throw new ValidationException(messages);
        }
    }
}
