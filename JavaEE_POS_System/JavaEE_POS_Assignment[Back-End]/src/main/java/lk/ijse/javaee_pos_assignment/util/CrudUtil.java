package lk.ijse.javaee_pos_assignment.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CrudUtil {
    Connection connection;
    public static <T> T execute(String sql,Connection connection,Object...args) throws SQLException {

        PreparedStatement pstm = connection.prepareStatement(sql);
        for (int i = 0; i < args.length; i++) {
            pstm.setObject((i + 1), args[i]);
        }

        if (sql.startsWith("select") || sql.startsWith("SELECT")) {
            return (T) pstm.executeQuery();
        }

        return (T) (Boolean) (pstm.executeUpdate() > 0);
    }
}
