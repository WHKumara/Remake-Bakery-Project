package lk.ijse.dao.custom.impl;

import lk.ijse.dao.SQLUtil;
import lk.ijse.dao.custom.RawMaterialDAO;
import lk.ijse.entity.RawMaterial;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class RawMaterialDAOImpl implements RawMaterialDAO {

    @Override
    public ArrayList<RawMaterial> getDetail() throws SQLException, ClassNotFoundException {
        String sql = "SELECT * FROM raw_material";
        ResultSet result = SQLUtil.execute(sql);

        ArrayList<RawMaterial> materialList = new ArrayList<>();

        while (result.next()) {
            materialList.add(new RawMaterial(
                    result.getString(1),
                    result.getString(2),
                    result.getInt(3),
                    result.getString(4)
            ));
        }
        return materialList;
    }

    @Override
    public boolean save(RawMaterial obj) throws SQLException, ClassNotFoundException {
        String sql ="INSERT INTO raw_material VALUES (?,?,?,?)";

        return SQLUtil.execute(sql,obj.getMaterialID(),obj.getType(),obj.getQty(),obj.getSupID());
    }

    @Override
    public boolean updateData(RawMaterial obj) throws SQLException, ClassNotFoundException {
        String sql = "UPDATE raw_material SET Qty=?,SupID=? WHERE MaterialID=?";

        return SQLUtil.execute(sql,obj.getQty(),obj.getSupID(),obj.getMaterialID());
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        String sql = "DELETE FROM raw_material WHERE MaterialID=?";

        return SQLUtil.execute(sql,id);
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT MaterialID FROM raw_material ORDER BY MaterialID DESC LIMIT 1;");
        if (rst.next()) {
            String id = rst.getString("MaterialID");
            int newItemId = Integer.parseInt(id.replace("R-", "")) + 1;
            return String.format("R-%03d", newItemId);
        } else {
            return "R-001";
        }
    }

    @Override
    public RawMaterial search(String code) throws SQLException, ClassNotFoundException {
        return null;
    }
}
