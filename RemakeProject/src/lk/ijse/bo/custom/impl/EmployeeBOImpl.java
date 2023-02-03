package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.EmployeeBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.EmployeeDAO;
import lk.ijse.dto.EmployeeDTO;
import lk.ijse.entity.Employee;

import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeBOImpl implements EmployeeBO {
    EmployeeDAO emp = (EmployeeDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.EMPLOYEE);
    @Override
    public ArrayList<EmployeeDTO> getEmployeeDetail() throws SQLException, ClassNotFoundException {
        ArrayList<EmployeeDTO> detail = new ArrayList<>();
        ArrayList<Employee> empDetail = emp.getDetail();

        for(Employee e : empDetail){
            detail.add(new EmployeeDTO(e.getEmpID(),e.getEmpName(),e.getEmpContact(),e.getEmpAddress(),e.getEmpEmail()));
        }
        return detail;
    }

    @Override
    public boolean saveEmployee(EmployeeDTO dto) throws SQLException, ClassNotFoundException {
        return emp.save(new Employee(dto.getEmpID(),dto.getEmpName(),dto.getEmpContact(),dto.getEmpAddress(),dto.getEmpEmail()));
    }

    @Override
    public boolean updateEmployee(EmployeeDTO dto) throws SQLException, ClassNotFoundException {
        return emp.updateData(new Employee(dto.getEmpID(),dto.getEmpName(),dto.getEmpContact(),dto.getEmpAddress(),dto.getEmpEmail()));
    }

    @Override
    public boolean deleteEmployee(String id) throws SQLException, ClassNotFoundException {
        return emp.delete(id);
    }

    @Override
    public String generateEmployeeID() throws SQLException, ClassNotFoundException {
        return emp.generateNewID();
    }

    @Override
    public Employee search(String code) throws SQLException, ClassNotFoundException {
        return emp.search(code);
    }
}
