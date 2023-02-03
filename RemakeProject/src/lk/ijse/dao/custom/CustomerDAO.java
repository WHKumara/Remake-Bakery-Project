package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public interface CustomerDAO extends CrudDAO<Customer> {
    int customerCount() throws SQLException, ClassNotFoundException;
    ArrayList<String> loadCustId() throws SQLException, ClassNotFoundException;
}
