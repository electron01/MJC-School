package by.epam.esm.dto.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
/**
 * class UserDto
 * base abstract entity data transfer object class
 * @see EntityDto
 * @author Aliaksei Tkachuk
 * @version 1.0
 */
public class UserDto extends EntityDto{
    @Pattern(regexp = "(?=.*([a-zA-Z\\.]\\s*){3,})(?=(^[\\s\\S]{0,25}$)).*",
            message = "User login must be longer than 3 characters and shorter than 25 characters" +
                    " and consist only of letters, numbers and an underscore ")
    @NotNull(message = "login can't be null")
    private String login;

    @Pattern(regexp = "^[\\w]{5,25}",message = "User password must be longer than 5 characters and shorter than 25 characters")
    @NotNull(message = "password can't be null")
    private String password;

    @Pattern(regexp = "^[\\w\\.-]+@[_a-zA-Z]+\\.[a-z]{2,3}$",message = "Plz enter correct email address")
    @NotNull(message = "email can't be null")
    private String email;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

