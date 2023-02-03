package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.EmployeeDAO;
import lk.ijse.entity.Employee;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeDAOImpl implements EmployeeDAO {
    @Override
    public ArrayList<Employee> getDetail() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM employee";
        ResultSet resultSet = SQLUtil.execute(sql);

        ArrayList<Employee> employeeList =new ArrayList<>();

        while (resultSet.next()){
            employeeList.add(new Employee(
                    resultSet.getString(1),
                    resultSet.getString(2),
                    resultSet.getInt(3),
                    resultSet.getString(4),
                    resultSet.getString(5)
            ));
        }
        return employeeList;
    }

    @Override
    public boolean save(Employee obj) throws SQLException, ClassNotFoundException {
        String sql ="INSERT INTO employee VALUES (?,?,?,?,?)";

        return SQLUtil.execute(sql,obj.getEmpID(),obj.getEmpName(),obj.getEmpContact(),obj.getEmpAddress()
                ,obj.getEmpEmail());
    }

    @Override
    public boolean updateData(Employee obj) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE employee SET EmpName=?,EmpContact=?,EmpAddress=? WHERE EmpID=?";

        return SQLUtil.execute(sql,obj.getEmpName(),obj.getEmpContact(),obj.getEmpAddress(),obj.getEmpID());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM employee WHERE EmpID=?";

        return SQLUtil.execute(sql,id);
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT EmpID FROM employee ORDER BY EmpID DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("EmpID");
            int newItemId = Integer.parseInt(id.replace("E-", "")) + 1;
            return String.format("E-%03d", newItemId);
        } else {
            return "E-001";
        }
    }

    @Override
    public Employee search(String code) throws SQLException, ClassNotFoundException {
        return null;
    }
}
