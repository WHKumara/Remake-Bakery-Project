package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.OrderDAO;
import lk.ijse.entity.Order;
import lk.ijse.entity.Supplier;
import lk.ijse.view.tm.OrderDetailTm;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class OrderDAOImpl implements OrderDAO {

    @Override
    public ArrayList<OrderDetailTm> getData() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM orders";
        ResultSet result = SQLUtil.execute(sql);

        ArrayList<OrderDetailTm> orderData = new ArrayList<>();

        while (result.next()){
            orderData.add(new OrderDetailTm(
                    result.getString(1),
                    result.getString(2),
                    result.getDate(3),
                    result.getTime(4),
                    result.getDouble(5))
            );
        }
        return orderData;
    }

    @Override
    public boolean save(Order order) throws SQLException, ClassNotFoundException {
        String sql = "INSERT INTO orders VALUES(?, ?, now(), now(), ?)";
        return SQLUtil.execute(sql, order.getID(),order.getCustID(),order.getAmount());
    }

    @Override
    public int orderCount() throws SQLException, ClassNotFoundException {
        String sql = "SELECT COUNT(*) from orders";
        ResultSet result = SQLUtil.execute(sql);

        if (result.next())
            return result.getInt(1);
        return 0;
    }

    @Override
    public double getTotalAmount() throws SQLException, ClassNotFoundException {
        String sql = "SELECT SUM(Amount) FROM orders";

        ResultSet result = SQLUtil.execute(sql);

        if(result.next()){
            return result.getDouble(1);
        }
        return 0;
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
        ResultSet rst = SQLUtil.execute("SELECT id FROM Orders ORDER BY id DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("id");
            int newCustomerId = Integer.parseInt(id.replace("O-", "")) + 1;
            return String.format("O-%03d", newCustomerId);
        } else {
            return "O-001";
        }
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
