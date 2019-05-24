package Data;

/**
 * Cell Class for email adresses. Inherits from Cell Superclass
 *
 * @invar The value of this class must always be a valid email.
 * | this.getValue() instanceof Integer
 */
public class CellEmail implements CellEditable<String> {
    protected String value;
    private String type = "Email";

    /**
     * Initialize CellInteger with given value.
     *
     * @param arg The value contained by the newly created CellInteger Object.
     * @effect A CellInteger is initialized with the given arg as its value if it is an email.
     * | this.setValue(value)
     */
    public CellEmail(String arg) {
        this.setValue(arg);
    }

    public void setEmail(String email) {
        this.value = email;
    }


    public String getValue() {

        return value;
    }


    /**
     * Sets the value of the object to the given value if it's a valid email
     *
     * @param value The value to override the current value with
     * @effect The value is changed to the given value if it's a valid email, else nothing happens
     * |if(isValidEmailAdress(value))
     * |		new.value == value
     */
    public void setValue(String value) {
        if (isValidEmailAddress(value)) {
            this.value = value;
        }
    }


    public String getType() {
        return this.type;
    }


    /**
     * Method that adds a character to the value
     *
     * @param keyChar the character to be added
     * @effect The value the character is appended to the end of the value
     * | new.value = value + keyChar
     */
    public void addChar(char keyChar) {
        this.setValue(this.getValue() + keyChar);
    }

    /**
     * Method that deletes the last character from the value
     *
     * @effect The value is treated as a string and the last character is deleted
     * | new.value == value.substring(value.length() -1)
     */
    public void delChar() {
        this.setValue(this.getValue().substring(0, this.getValue().length() - 1));
    }

    /**
     * Returns a boolean value that states if the text string is a valid email,
     * that is if it follows a classic email address pattern
     *
     * @param text The string to be tested for email validity
     * @return whether the string is a valid email
     * | if(email matches pattern)
     * | 	result == true
     * | else result == false
     */
    public boolean isValidEmailAddress(String text) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(text);
        return m.matches();
    }

    /**
     * Method that returns the value as string
     *
     * @return the value as a string
     * | result == value
     */
    public String getString() {
        return value;
    }
}
