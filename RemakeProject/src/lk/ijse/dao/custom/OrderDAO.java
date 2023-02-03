package lk.ijse.dao.custom;

import lk.ijse.entity.Order;
import lk.ijse.view.tm.OrderDetailTm;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDAO extends SupplierDAO {

    ArrayList<OrderDetailTm> getData() throws SQLException, ClassNotFoundException;
    boolean save(Order order) throws SQLException, ClassNotFoundException;
    int orderCount() throws SQLException, ClassNotFoundException;
    double getTotalAmount() throws SQLException, ClassNotFoundException;
    String generateNewID() throws SQLException, ClassNotFoundException;
}
