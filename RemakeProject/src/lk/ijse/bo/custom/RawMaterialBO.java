package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.RawMaterialDTO;
import lk.ijse.entity.RawMaterial;

import java.sql.SQLException;
import java.util.ArrayList;

public interface RawMaterialBO extends SuperBO {
    ArrayList<RawMaterialDTO> getMaterialDetail() throws SQLException, ClassNotFoundException;
    boolean saveMaterial(RawMaterialDTO dto) throws SQLException, ClassNotFoundException;
    boolean updateMaterialData(RawMaterialDTO dto) throws SQLException, ClassNotFoundException;
    boolean deleteMaterial(String id) throws SQLException, ClassNotFoundException;
    String generateMaterialID() throws SQLException, ClassNotFoundException;
    RawMaterial searchMaterial(String code) throws SQLException, ClassNotFoundException;
}
