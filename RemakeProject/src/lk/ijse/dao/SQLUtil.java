package lk.ijse.dao;


import lk.ijse.db.DBConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLUtil {
    public static <T>T execute(String sql,Object...args) throws SQLException, ClassNotFoundException {
        PreparedStatement pstm = DBConnection.getDBConnection().getConnection().prepareStatement(sql);

        for (int i= 0;i<args.length;i++){
            pstm.setObject((i+1),args[i]);
        }

        if(sql.startsWith("SELECT") || sql.startsWith("select")) {
            //result set ekk enne methanin
            return (T) pstm.executeQuery();
        }

        return (T)(Boolean)(pstm.executeUpdate() > 0);
        }
}
