package lk.ijse.dao;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CrudDAO <T> extends SuperDAO {
    ArrayList<T> getDetail() throws SQLException, ClassNotFoundException;
    boolean save(T obj) throws SQLException, ClassNotFoundException;
    boolean updateData(T obj) throws SQLException, ClassNotFoundException;
    boolean delete(String id) throws SQLException, ClassNotFoundException;
    String generateNewID() throws SQLException, ClassNotFoundException;
    T search(String code) throws SQLException, ClassNotFoundException;
}
