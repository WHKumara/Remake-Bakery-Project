package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.ItemDAO;
import lk.ijse.entity.Item;
import lk.ijse.entity.OrderDetail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {
    @Override
    public ArrayList<Item> getDetail() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM item ";

        ResultSet result = SQLUtil.execute(sql);
        ArrayList<Item> itemList = new ArrayList<>();

        while (result.next()) {
            itemList.add(new Item(
                    result.getString(1),
                    result.getInt(2),
                    result.getString(3),
                    result.getDouble(4)
            ));
        }
        return itemList;
    }

    @Override
        public boolean save(Item obj) throws SQLException, ClassNotFoundException {
        String sql ="INSERT INTO item VALUES (?,?,?,?)";

        return SQLUtil.execute(sql,obj.getItemID(),obj.getQty(),obj.getDetail(),obj.getUnitPrice());
    }

    @Override
    public boolean updateData(Item obj) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE item SET Qty=?,UnitPrice=? WHERE ItemID=?";

        return SQLUtil.execute(sql,obj.getQty(),obj.getUnitPrice(),obj.getItemID());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM item WHERE ItemID=?";

        return SQLUtil.execute(sql,id);
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT ItemID FROM Item ORDER BY ItemID DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("ItemID");
            int newItemId = Integer.parseInt(id.replace("I-", "")) + 1;
            return String.format("I-%03d", newItemId);
        } else {
            return "I-001";
        }
    }

    @Override
    public Item search(String code) throws SQLException, ClassNotFoundException {
        String sql = "SELECT  * FROM Item WHERE ItemID = ? OR Detail= ?";
        ResultSet result = SQLUtil.execute(sql, code, code);

        if (result.next()) {
            return new Item(
                    result.getString(1),
                    result.getInt(2),
                    result.getString(3),
                    result.getDouble(4)
            );
        }
        return null;
    }

    @Override
    public ArrayList<String> loadItemId() throws SQLException, ClassNotFoundException {
        String sql = "SELECT ItemID FROM item";
        ResultSet result = SQLUtil.execute(sql);
        ArrayList<String> data = new ArrayList<>();

        while(result.next()){
            data.add(result.getString(1));
        }
        return data;
    }

    @Override
    public ArrayList<String> loadItemName() throws SQLException, ClassNotFoundException {
        String sql = "SELECT Detail FROM item";
        ResultSet result = SQLUtil.execute(sql);
        ArrayList<String> data = new ArrayList<>();

        while(result.next()){
            data.add(result.getString(1));
        }
        return data;
    }

    @Override
    public boolean updateQty(ArrayList<OrderDetail> orderDetails) throws SQLException, ClassNotFoundException {
        for (OrderDetail orderdetail : orderDetails) {
            if (!updateQty(new Item(orderdetail.getItemID(), (int) orderdetail.getQty(),"",0.0))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean updateQty(Item item) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE item SET qty = qty - ? WHERE ItemID = ?";
        return SQLUtil.execute(sql, item.getQty(),item.getItemID());
    }
}
