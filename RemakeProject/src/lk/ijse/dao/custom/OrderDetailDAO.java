package lk.ijse.dao.custom;

import lk.ijse.entity.OrderDetail;

import java.sql.SQLException;
import java.util.ArrayList;

public interface OrderDetailDAO extends SupplierDAO{
    boolean saveOrderDetails(ArrayList<OrderDetail> orderDetails) throws SQLException, ClassNotFoundException;
    boolean save(OrderDetail orderDetail) throws SQLException, ClassNotFoundException;
}
