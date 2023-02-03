package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.SupplierBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.SupplierDAO;
import lk.ijse.dto.SupplierDTO;
import lk.ijse.entity.Supplier;

import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierBOImpl implements SupplierBO {

    SupplierDAO supplierDAO = (SupplierDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.SUPPLIER);

    @Override
    public ArrayList<SupplierDTO> getSupplierDetail() throws SQLException, ClassNotFoundException {
        ArrayList<SupplierDTO> detail = new ArrayList<>();
        ArrayList<Supplier> data = supplierDAO.getDetail();
        for(Supplier s : data){
            detail.add(new SupplierDTO(s.getSupID(),s.getSupName(),s.getSupContact(),s.getSupAddress(),s.getSupEmail()));
        }
        return detail;
    }

    @Override
    public boolean saveSupplier(SupplierDTO dto) throws SQLException, ClassNotFoundException {
        return supplierDAO.save(new Supplier(dto.getSupID(),dto.getSupName(),dto.getSupContact(),dto.getSupAddress(),dto.getSupEmail()));
    }

    @Override
    public boolean updateSupplierData(SupplierDTO dto) throws SQLException, ClassNotFoundException {
        return supplierDAO.updateData(new Supplier(dto.getSupID(),dto.getSupName(),dto.getSupContact(),dto.getSupAddress(),dto.getSupEmail()));
    }

    @Override
    public boolean deleteSupplier(String id) throws SQLException, ClassNotFoundException {
        return supplierDAO.delete(id);
    }

    @Override
    public String generateSupplierID() throws SQLException, ClassNotFoundException {
        return supplierDAO.generateNewID();
    }

    @Override
    public Supplier searchSupplier(String code) throws SQLException, ClassNotFoundException {
        return supplierDAO.search(code);
    }

    @Override
    public ArrayList<String> loadSupId() throws SQLException, ClassNotFoundException {
        return supplierDAO.loadSupId();
    }
}
