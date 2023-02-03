package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.SupplierDTO;
import lk.ijse.entity.Supplier;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SupplierBO extends SuperBO {
    ArrayList<SupplierDTO> getSupplierDetail() throws SQLException, ClassNotFoundException;
    boolean saveSupplier(SupplierDTO dto) throws SQLException, ClassNotFoundException;
    boolean updateSupplierData(SupplierDTO dto) throws SQLException, ClassNotFoundException;
    boolean deleteSupplier(String id) throws SQLException, ClassNotFoundException;
    String generateSupplierID() throws SQLException, ClassNotFoundException;
    Supplier searchSupplier(String code) throws SQLException, ClassNotFoundException;
    ArrayList<String> loadSupId() throws SQLException, ClassNotFoundException;

}
