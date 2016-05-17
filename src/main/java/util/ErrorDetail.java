package util;

/**
 * Created by ginger on 16.05.16.
 */
public enum ErrorDetail {
    EMAIL_ADDRESS_MUST_CONTAIN_SYMBOL("An email address must contain a single @"),
    EMAIL_ENTERED_INVALID("The e-mail you entered is invalid"),
    ENTER_EMAIL("Please enter your email"),
    FILE_NAME_DOT_NOT_ALLOWED("The filenames dot (.) and dot dot (..) are not allowed."),
    FOLLOWING_CHARACTERS_ARE_NOT_ALLOWED("The following characters are not allowed: angle brackets \\ / : ? * \" |"),
    INVALID_EMAIL_OR_PASSWORD("Invalid email or password"),
    MAX_FILE_NAME_LENGTH_IS("The max filename length is 255 characters."),
    PROVIDE_NAME_FOR_NEW_FOLDER("Please provide a name for the new folder."),
    USERNAME_PORTION_OF_EMAIL_INVALID("The username portion of the email address is invalid (the portion before the @: )");

    private String error;

    ErrorDetail(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return error;
    }

}
