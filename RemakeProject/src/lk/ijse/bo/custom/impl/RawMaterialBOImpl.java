package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.RawMaterialBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.RawMaterialDAO;
import lk.ijse.dto.RawMaterialDTO;
import lk.ijse.entity.RawMaterial;

import java.sql.SQLException;
import java.util.ArrayList;

public class RawMaterialBOImpl implements RawMaterialBO {
    RawMaterialDAO materialDAO = (RawMaterialDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.RAW_MATERIAL);
    @Override
    public ArrayList<RawMaterialDTO> getMaterialDetail() throws SQLException, ClassNotFoundException {
        ArrayList<RawMaterialDTO> detail = new ArrayList<>();
        ArrayList<RawMaterial> data = materialDAO.getDetail();

        for(RawMaterial r : data){
            detail.add(new RawMaterialDTO(r.getMaterialID(),r.getType(),r.getQty(),r.getSupID()));
        }
                return detail;
    }

    @Override
    public boolean saveMaterial(RawMaterialDTO dto) throws SQLException, ClassNotFoundException {

        return materialDAO.save(new RawMaterial(dto.getMaterialID(),dto.getType(),dto.getQty(),dto.getSupID()));
    }

    @Override
    public boolean updateMaterialData(RawMaterialDTO dto) throws SQLException, ClassNotFoundException {
        return materialDAO.updateData(new RawMaterial(dto.getMaterialID(),dto.getType(),dto.getQty(),dto.getSupID()));
    }

    @Override
    public boolean deleteMaterial(String id) throws SQLException, ClassNotFoundException {
        return materialDAO.delete(id);
    }

    @Override
    public String generateMaterialID() throws SQLException, ClassNotFoundException {
        return materialDAO.generateNewID();
    }

    @Override
    public RawMaterial searchMaterial(String code) throws SQLException, ClassNotFoundException {
        return materialDAO.search(code);
    }
}
