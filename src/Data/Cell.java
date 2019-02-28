package Data;

public class Cell {
    private String cellType; // number, bool, email or text



    private Boolean bool;
    private String email;
    private String text;
    private int number;

    public String getCellType() {
        return cellType;
    }

    public void setCellType(String cellType) {
        this.cellType = cellType;
    }

    public Boolean getBool() {
        return bool;
    }

    public void setBool(Boolean bool) {
        this.bool = bool;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setValue(int value){
        if (this.cellType == "number"){
            setNumber(value);
        }
    }

    public void setValue(Boolean value){
        if (this.cellType == "bool"){
            setBool(value);
        }
    }

    public void setValue(String value){
        if (this.cellType == "email" && isValidEmailAddress(value)){
            setEmail(value);
        }
        else if (this.cellType == "text"){
            setText(value);
        }
    }


    public boolean isValidEmailAddress(String text) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(text);
        return m.matches();
    }
}
