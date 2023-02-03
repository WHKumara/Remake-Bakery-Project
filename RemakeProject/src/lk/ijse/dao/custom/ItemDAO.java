package lk.ijse.dao.custom;

import lk.ijse.dao.CrudDAO;
import lk.ijse.entity.Item;
import lk.ijse.entity.OrderDetail;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemDAO extends CrudDAO<Item> {
    ArrayList<String> loadItemId() throws SQLException, ClassNotFoundException;
    ArrayList<String> loadItemName() throws SQLException, ClassNotFoundException;
    boolean updateQty(ArrayList<OrderDetail> orderDetails) throws SQLException, ClassNotFoundException;
    boolean updateQty(Item item) throws SQLException, ClassNotFoundException;
}
