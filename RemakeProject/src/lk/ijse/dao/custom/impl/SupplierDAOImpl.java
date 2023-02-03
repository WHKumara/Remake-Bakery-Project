package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.SupplierDAO;
import lk.ijse.entity.Supplier;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierDAOImpl implements SupplierDAO {
    @Override
    public ArrayList<Supplier> getDetail() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM supplier";
        ResultSet result = SQLUtil.execute(sql);

        ArrayList<Supplier> suppList = new ArrayList<>();

        while (result.next()) {
            suppList.add(new Supplier(
                    result.getString(1),
                    result.getString(2),
                    result.getInt(3),
                    result.getString(4),
                    result.getString(5)
            ));
        }
        return suppList;
    }

    @Override
    public boolean save(Supplier obj) throws SQLException, ClassNotFoundException {
        String sql ="INSERT INTO supplier VALUES (?,?,?,?,?)";

        return SQLUtil.execute(sql,obj.getSupID(),obj.getSupName(),obj.getSupContact(),obj.getSupAddress(),
                obj.getSupEmail());
    }

    @Override
    public boolean updateData(Supplier obj) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE supplier SET SupName=?,SupContact=?,SupAddress=? WHERE SupID=?";

        return SQLUtil.execute(sql,obj.getSupName(),obj.getSupContact(),obj.getSupAddress(),
                obj.getSupID());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM supplier WHERE SupID=?";

        return SQLUtil.execute(sql,id);
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT SupID FROM supplier ORDER BY SupID DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("SupID");
            int newItemId = Integer.parseInt(id.replace("S-", "")) + 1;
            return String.format("S-%03d", newItemId);
        } else {
            return "S-001";
        }
    }

    @Override
    public Supplier search(String code) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public ArrayList<String> loadSupId() throws SQLException, ClassNotFoundException {
        String sql = "SELECT SupID FROM supplier";
        ResultSet result = SQLUtil.execute(sql);
        ArrayList<String> data = new ArrayList<>();

        while(result.next()){
            data.add(result.getString(1));
        }
        return data;
    }
}
