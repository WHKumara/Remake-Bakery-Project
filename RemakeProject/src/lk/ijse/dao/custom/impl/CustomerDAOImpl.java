package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.CustomerDAO;
import lk.ijse.entity.Customer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public ArrayList<Customer> getDetail() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM customer";
        ResultSet result = SQLUtil.execute(sql);

        ArrayList<Customer> customerList = new ArrayList<>();

        while (result.next()) {
            customerList.add(new Customer(
                    result.getString(1),
                    result.getString(2),
                    result.getInt(3),
                    result.getString(4)
            ));
        }
        return customerList;
    }

    @Override
    public boolean save(Customer obj) throws SQLException, ClassNotFoundException {
        String sql ="INSERT INTO customer VALUES (?,?,?,?)";

        return SQLUtil.execute(sql,obj.getCustID(),obj.getCustName(),obj.getCustContact(),obj.getCustAddress());
    }

    @Override
    public boolean updateData(Customer obj) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE customer SET CustName=?,CustContact=?,CustAddress=? WHERE CustID=?";

        return SQLUtil.execute(sql,obj.getCustName(),obj.getCustContact(),obj.getCustAddress(),obj.getCustID());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM customer WHERE CustID=?";

        return SQLUtil.execute(sql,id);
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT CustID FROM Customer ORDER BY CustID DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("CustID");
            int newCustomerId = Integer.parseInt(id.replace("C-", "")) + 1;
            return String.format("C-%03d", newCustomerId);
        } else {
            return "C-001";
        }
    }

    @Override
    public Customer search(String code) throws SQLException, ClassNotFoundException {
        String sql = "SELECT  * FROM customer WHERE CustID = ? OR CustContact = ?";
        ResultSet result = SQLUtil.execute(sql, code ,code);

        if (result.next()) {
            return new Customer(
                    result.getString(1),
                    result.getString(2),
                    result.getInt(3),
                    result.getString(4)
            );
        }
        return null;
    }

    @Override
    public int customerCount() throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) from customer";
        ResultSet result = SQLUtil.execute(sql);

        if (result.next())
            return result.getInt(1);
        return 0;
    }

    @Override
    public ArrayList<String> loadCustId() throws SQLException, ClassNotFoundException {
        String sql = "SELECT CustID FROM customer";
        ResultSet result = SQLUtil.execute(sql);
        ArrayList<String> data = new ArrayList<>();

        while(result.next()){
            data.add(result.getString(1));
        }
        return data;
    }
}
