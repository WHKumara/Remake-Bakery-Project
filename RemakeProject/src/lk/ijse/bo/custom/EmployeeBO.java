package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.EmployeeDTO;
import lk.ijse.entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeBO extends SuperBO {
    ArrayList<EmployeeDTO> getEmployeeDetail() throws SQLException, ClassNotFoundException;
    boolean saveEmployee(EmployeeDTO dto) throws SQLException, ClassNotFoundException;
    boolean updateEmployee(EmployeeDTO dto) throws SQLException, ClassNotFoundException;
    boolean deleteEmployee(String id) throws SQLException, ClassNotFoundException;
    String generateEmployeeID() throws SQLException, ClassNotFoundException;
    Employee search(String code) throws SQLException, ClassNotFoundException;
}
