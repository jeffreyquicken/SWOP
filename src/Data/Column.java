package Data;

import java.util.ArrayList;
import java.util.List;

public class Column {
    private String name;
    private String defaultV;
    private String type;
    private Boolean blanksAllowed;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }



    /**
     *
     * @param name
     */
    public Column(String name, String defaultValue, String Type, Boolean blanks){
        this.name = name;
        defaultV= defaultValue;
        type = Type;
        blanksAllowed = blanks;
    }

    /**
     * Method to get basic column info
     * @return list with name,deafault value, type and if blanks are allowed
     */
    public List<String>  getInfo(){
        List<String> info = new ArrayList<>();
        info.add(name);
        info.add(defaultV);
        info.add(type);
        info.add(blanksAllowed.toString());
        return info;
    }


    /**
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     *
     */
    public int width;
    List<String> colInfo = new ArrayList<>();

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    public String getDefaultV() {
        return this.defaultV;
    }

    public Boolean getBlanksAllowed() {
        return blanksAllowed;
    }

    public void setBlanksAllowed(Boolean blanksAllowed) {
        this.blanksAllowed = blanksAllowed;
    }







}
