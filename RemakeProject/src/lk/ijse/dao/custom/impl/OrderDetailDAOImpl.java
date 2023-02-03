package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.OrderDetailDAO;
import lk.ijse.entity.OrderDetail;
import lk.ijse.entity.Supplier;

import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDetailDAOImpl implements OrderDetailDAO {
    @Override
    public boolean saveOrderDetails(ArrayList<OrderDetail> orderDetails) throws SQLException, ClassNotFoundException {
        for (OrderDetail orderDetail : orderDetails) {
            if (!save(orderDetail)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean save(OrderDetail orderDetail) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO orderdetail VALUES(?, ?, ?, ?, ?)";
        return SQLUtil.execute(sql, orderDetail.getOrderID(),orderDetail.getItemID(),orderDetail.getDetail(),orderDetail.getUnitPrice(),orderDetail.getQty());
    }

    @Override
    public ArrayList<Supplier> getDetail() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(Supplier obj) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean updateData(Supplier obj) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public Supplier search(String code) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<String> loadSupId() throws SQLException, ClassNotFoundException {
        return null;
    }
}
