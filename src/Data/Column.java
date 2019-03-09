package Data;

public class Column {
    private String name;
    public String getName() {
        return name;
    }
    public int width;

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }



    public Column(String name){
        this.name = name;
    }



}
