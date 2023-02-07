package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.CustomerDTO;
import lk.ijse.dto.ItemDTO;
import lk.ijse.dto.OrderDTO;
import lk.ijse.dto.OrderDetailDTO;
import lk.ijse.entity.Order;
import lk.ijse.entity.OrderDetail;
import lk.ijse.view.tm.OrderDetailTm;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PurchaseOrderBO extends SuperBO {
    boolean save(OrderDTO order) throws SQLException, ClassNotFoundException;
    String generateNextOrderID() throws SQLException, ClassNotFoundException;
    boolean saveOrderDetails(ArrayList<OrderDetailDTO> orderDetails) throws SQLException, ClassNotFoundException;
    CustomerDTO searchCustomer(String code) throws SQLException, ClassNotFoundException;
    ArrayList<String> loadCustomerId() throws SQLException, ClassNotFoundException;
    ItemDTO searchItem(String code) throws SQLException, ClassNotFoundException;
    ArrayList<String> loadItemId() throws SQLException, ClassNotFoundException;
    boolean updateItemQty(ArrayList<OrderDetailDTO> orderDetails) throws SQLException, ClassNotFoundException;
    ArrayList<String> loadItemName() throws SQLException, ClassNotFoundException;
    double getTotalAmount() throws SQLException, ClassNotFoundException;
    ArrayList<OrderDetailTm> getData() throws SQLException, ClassNotFoundException;
}
