package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.ItemDTO;
import lk.ijse.dto.OrderDetailDTO;
import lk.ijse.entity.Item;
import lk.ijse.entity.OrderDetail;

import java.sql.SQLException;
import java.util.ArrayList;

public interface ItemBO extends SuperBO {
    ArrayList<ItemDTO> getItemDetail() throws SQLException, ClassNotFoundException;
    boolean saveItem(ItemDTO dto) throws SQLException, ClassNotFoundException;
    boolean updateItem(ItemDTO dto) throws SQLException, ClassNotFoundException;
    boolean deleteItem(String id) throws SQLException, ClassNotFoundException;
    String generateItemID() throws SQLException, ClassNotFoundException;
    ItemDTO searchItem(String code) throws SQLException, ClassNotFoundException;
    ArrayList<String> loadItemId() throws SQLException, ClassNotFoundException;
    public ArrayList<String> loadItemName() throws SQLException, ClassNotFoundException;
    boolean updateItemQty(ArrayList<OrderDetailDTO> orderDetails) throws SQLException, ClassNotFoundException;
}
