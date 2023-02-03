package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.PaymentDAO;
import lk.ijse.entity.Payment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentDAOImpl implements PaymentDAO {
    @Override
    public ArrayList<Payment> getDetail() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM payment";
        ResultSet result = SQLUtil.execute(sql);

        ArrayList<Payment> payList = new ArrayList<>();

        while (result.next()) {
            payList.add(new Payment(
                    result.getString(1),
                    result.getDouble(2),
                    result.getDate(3),
                    result.getTime(4),
                    result.getString(5)
            ));
        }
        return payList;
    }

    @Override
    public boolean save(Payment obj) throws SQLException, ClassNotFoundException {
        String sql ="INSERT INTO payment VALUES (?,?,now(),now(),?)";

        return SQLUtil.execute(sql,obj.getPaymentID(),obj.getPaymentAmount(),obj.getSupID());
    }

    @Override
    public boolean updateData(Payment obj) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE payment SET PaymentAmount=?,SupID=? WHERE PaymentID=?";

        return SQLUtil.execute(sql,obj.getPaymentAmount(),obj.getSupID(),obj.getPaymentID());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT PaymentID FROM payment ORDER BY PaymentID DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("PaymentID");
            int newItemId = Integer.parseInt(id.replace("P-", "")) + 1;
            return String.format("P-%03d", newItemId);
        } else {
            return "P-001";
        }
    }

    @Override
    public Payment search(String code) throws SQLException, ClassNotFoundException {
        return null;
    }
}
