package SQLQuery;

/**
 * Class for asItems (movies.title AS title). It stores the id and the alias of the item
 */
public class asItem {

    private String id;


    private String alias;
    public asItem(String id, String as){
        this.id = id;
        this.alias = as;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String as) {
        this.alias = as;
    }

}
