package SQLQuery;

/**
 * Class with query attributes
 */
public class Query {
    private asClause asClause = new asClause();
    private fromClause fromClause = new fromClause();
    private selectClause selectClause = new selectClause();
    private whereClause whereClause = new whereClause();



    public SQLQuery.asClause getAsClause() {
        return asClause;
    }

    public void setAsClause(SQLQuery.asClause asClause) {
        this.asClause = asClause;
    }

    public SQLQuery.fromClause getFromClause() {
        return fromClause;
    }

    public void setFromClause(SQLQuery.fromClause fromClause) {
        this.fromClause = fromClause;
    }

    public SQLQuery.selectClause getSelectClause() {
        return selectClause;
    }

    public void setSelectClause(SQLQuery.selectClause selectClause) {
        this.selectClause = selectClause;
    }

    public SQLQuery.whereClause getWhereClause() {
        return whereClause;
    }

    public void setWhereClause(SQLQuery.whereClause whereClause) {
        this.whereClause = whereClause;
    }






}
