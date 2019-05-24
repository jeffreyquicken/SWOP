package SQLQuery;

import Data.Column;
import Data.Table;
import Data.dataController;

import java.io.IOException;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;

/**
 * Class for parsing an sql query into its broken down attribute objects
 */
public class SQLParser extends StreamTokenizer {

    private String qRowID;
    private String qColID;
    private String qColName;
    private String qTable;


    private static HashMap<String, Integer> keywords = new HashMap<>();
    public static final int
            TT_IDENT = -9,
            TT_SELECT = -10,
            TT_OR = -11,
            TT_AND = -12,
            TT_TRUE = -13,
            TT_FALSE = -14,
            TT_AS = -15,
            TT_FROM = -16,
            TT_INNER = -17,
            TT_JOIN = -18,
            TT_ON = -19,
            TT_WHERE = -20;

    static {
        keywords.put("SELECT", TT_SELECT);
        keywords.put("OR", TT_OR);
        keywords.put("AND", TT_AND);
        keywords.put("TRUE", TT_TRUE);
        keywords.put("FALSE", TT_FALSE);
        keywords.put("AS", TT_AS);
        keywords.put("FROM", TT_FROM);
        keywords.put("INNER", TT_INNER);
        keywords.put("JOIN", TT_JOIN);
        keywords.put("ON", TT_ON);
        keywords.put("WHERE", TT_WHERE);
    }

    public static class ParseException extends RuntimeException {
    }

    public static Query parseQuery(String text) {
        return new SQLParser(text).parseQuery();
    }

    @Override
    public int nextToken() {
        try {
            super.nextToken();
            if (ttype == TT_WORD) {
                Integer kwd = keywords.get(sval);
                if (kwd != null)
                    ttype = kwd;
                else
                    ttype = TT_IDENT;
            }
            return ttype;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public SQLParser(String text) {
        super(new StringReader(text));
        ordinaryChar('.');
        wordChars('_', '_');
        nextToken();
    }

    public RuntimeException error() {
        return new ParseException();
    }

    public void expect(int ttype) {
        if (this.ttype != ttype)
            throw new RuntimeException("Expected " + ttype + ", found " + this.ttype);
        nextToken();
    }

    public String expectIdent() {
        if (ttype != TT_IDENT)
            throw error();
        String result = sval;
        nextToken();
        return result;
    }

    public String parseCellId() {
        String rowId = expectIdent();
        expect('.');
        String colName = expectIdent();
        return rowId + "." + colName;
    }

    public String parsePrimaryExpr() {
        switch (ttype) {
            case TT_TRUE:
                nextToken();
                return "TRUE";
            case TT_FALSE:
                nextToken();
                return "FALSE";
            case TT_NUMBER: {
                int value = (int) nval;
                nextToken();
                return String.valueOf(value);
            }
            case '"': {
                String value = sval;
                nextToken();
                return '"' + value + '"';
            }
            case TT_IDENT:
                return parseCellId();
            case '(': {
                nextToken();
                String result = parseExpr();
                expect(')');
                return "(" + result + ")";
            }
            default:
                throw error();
        }
    }

    public String parseSum() {
        String e = parsePrimaryExpr();
        for (; ; ) {
            switch (ttype) {
                case '+':
                    nextToken();
                    e = e + " + " + parsePrimaryExpr();
                    break;
                case '-':
                    nextToken();
                    e = e + " - " + parsePrimaryExpr();
                    break;
                default:
                    return e;
            }
        }
    }

    public String parseRelationalExpr() {
        String e = parseSum();
        switch (ttype) {
            case '=':
            case '<':
            case '>':
                char operator = (char) ttype;
                nextToken();
                return e + " " + operator + " " + parseSum();
            default:
                return e;
        }
    }

    public String parseConjunction() {
        String e = parseRelationalExpr();
        switch (ttype) {
            case TT_AND:
                nextToken();
                return e + " AND " + parseConjunction();
            default:
                return e;
        }
    }

    public String parseDisjunction() {
        String e = parseConjunction();
        switch (ttype) {
            case TT_OR:
                nextToken();
                return e + " OR " + parseDisjunction();
            default:
                return e;
        }
    }

    public String parseExpr() {
        return parseDisjunction();
    }

    public Query parseQuery() {

        //OUR QUERY
        Query query = new Query();


        StringBuilder result = new StringBuilder();
        expect(TT_SELECT);
        result.append("SELECT ");
        for (; ; ) {
            String e = parseExpr();
            expect(TT_AS);
            String colName = expectIdent();
            qColID = e;
            String tableName;
            String id;
            tableName = e.split("\\.")[0];
            id = e.split("\\.")[1];
            result.append(e + " AS " + colName);
            //ADD SELECT
            query.getSelectClause().addSelectItem(tableName, id, colName);


            if (ttype == ',') {
                nextToken();
                result.append(", ");
            } else
                break;
        }
        expect(TT_FROM);
        result.append(" FROM ");
        {
            String tableName = expectIdent();
            qTable = tableName;
            expect(TT_AS);
            String rowId = expectIdent();
            result.append(tableName + " AS " + rowId);
            //ADD FROM
            query.getFromClause().addFromClause(tableName, rowId);

        }
        while (ttype == TT_INNER) {
            nextToken();
            expect(TT_JOIN);
            String tableName = expectIdent();
            expect(TT_AS);
            String rowId = expectIdent();
            expect(TT_ON);
            String cell1 = parseCellId();
            expect('=');
            String cell2 = parseCellId();
            query.getJoinClause().addJoinItem(tableName, rowId, cell1, cell2);
            result.append(" INNER JOIN " + tableName + " AS " + rowId + " ON " + cell1 + " = " + cell2);
        }
        expect(TT_WHERE);
        String cond = parseExpr();
        String[] split;
        split = cond.split("\\s+");
        if (split.length == 1) {
            //EXPRESSION (WHERE TRUE)
            query.getWhereClause().addWhereClause("", "", "", split[0]);
        } else {
            //Expression with condition (WHERE movie.imdb_score > 7)
            String[] splitted;
            splitted = split[0].split("\\.");
            query.getWhereClause().addWhereClause(splitted[0], splitted[1], split[1], split[2]);
        }
        result.append(" WHERE " + cond);


        System.out.println(result);
        //return result.toString();
        return query;
    }

    public Table computeTable(String tableName, String columnName, String condition, dataController data) {
        Table table = getTableFromName(tableName, data);
        Integer colIndex = getColumnIndexFromName(table, columnName);
        return table;
    }

    public Table getTableFromName(String tableName, dataController data) {
        for (Table table : data.getTableList()) {
            if (table.getTableName().equals(tableName)) {
                return table;
            }
        }
        return null;
    }

    public Integer getColumnIndexFromName(Table table, String columnName) {
        for (Column col : table.getColumnNames()) {
            if (col.getName().equals(columnName)) {
                return table.getColumnNames().indexOf(col);
            }

        }
        return null;
    }
}

