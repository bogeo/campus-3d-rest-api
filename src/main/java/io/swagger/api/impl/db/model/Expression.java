package io.swagger.api.impl.db.model;

import org.json.JSONObject;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



public class Expression {

    private static final String TABLENAME = "Expression";
    private int id;
    private String kind;
    private int pointDenseMin;
    private int pointDenseMax;

    public Expression(String kind, int pointDenseMin, int pointDenseMax) {
        this.kind = kind;
        this.pointDenseMin = pointDenseMin;
        this.pointDenseMax = pointDenseMax;
    }

    public Expression(int id, String kind, int pointDenseMin, int pointDenseMax) {
        this.id = id;
        this.kind = kind;
        this.pointDenseMin = pointDenseMin;
        this.pointDenseMax = pointDenseMax;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKind() {
        return kind;
    }

    public int getPointDenseMin() {
        return pointDenseMin;
    }

    public int getPointDenseMax() {
        return pointDenseMax;
    }

    @Override
    public boolean equals (Object object) {
        boolean result = false;
        if (object == null || object.getClass() != getClass()) {
            result = false;
        } else {
            Expression expression = (Expression) object;
            if (this.kind.equals(expression.getKind()) && this.pointDenseMin == expression.getPointDenseMin() && this.pointDenseMax == expression.getPointDenseMax()) {
                result = true;
            }
        }
        return result;
    }

    public static List<Expression> execSelectAllStatement(Connection conn, String whereClause) {

        List<Expression> resultList = new ArrayList<>();
        String query = "select * from public.\"" + TABLENAME + "\"" + whereClause;

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query);
        ) {
            while (rs.next()) {
                //Display values
                Integer pointDense[] = (Integer[]) rs.getArray(3).getArray();
                Expression expression = new Expression(rs.getInt(1), rs.getString(2), pointDense[0], pointDense[1]);
                resultList.add(expression);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultList;
    }

    public static List<Expression> execSelectAllStatement(Connection conn) {

        return execSelectAllStatement(conn, "");
    }

    public static JSONObject getExpressionAsJSON(Connection conn, Integer id){
        JSONObject result = new JSONObject();

        List<Expression> expressions = execSelectAllStatement(conn, " where id = " + id.toString());
        Expression expression = expressions.get(0);

        result.put("kind", expression.getKind());

        JSONObject pointDense = new JSONObject();
        pointDense.put("min", expression.getPointDenseMin());
        pointDense.put("max", expression.getPointDenseMax());

        result.put("pointdense", pointDense);

        return result;
    }

    public static void execDeleteStatement(Connection conn, Integer id) {

        String query = "delete from public.\"" + TABLENAME + "\" where id = " + id.toString();

        Statement stmt = null;
        try {
            stmt = conn.createStatement();
            stmt.execute(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
