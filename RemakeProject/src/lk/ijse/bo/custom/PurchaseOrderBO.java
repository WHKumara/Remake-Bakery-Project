package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.OrderDTO;
import lk.ijse.dto.OrderDetailDTO;
import lk.ijse.entity.Order;
import lk.ijse.entity.OrderDetail;
import lk.ijse.view.tm.OrderDetailTm;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PurchaseOrderBO extends SuperBO {
    ArrayList<OrderDetailTm> getData() throws SQLException, ClassNotFoundException;
    boolean save(OrderDTO order) throws SQLException, ClassNotFoundException;
    int orderCount() throws SQLException, ClassNotFoundException;
    double getTotalAmount() throws SQLException, ClassNotFoundException;
    String generateNextOrderID() throws SQLException, ClassNotFoundException;
    boolean saveOrderDetails(ArrayList<OrderDetailDTO> orderDetails) throws SQLException, ClassNotFoundException;
    boolean save(OrderDetail orderDetail) throws SQLException, ClassNotFoundException;
}
