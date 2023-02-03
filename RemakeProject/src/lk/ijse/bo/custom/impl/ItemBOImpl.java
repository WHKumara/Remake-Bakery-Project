package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.ItemBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.SuperDAO;
import lk.ijse.dao.custom.ItemDAO;
import lk.ijse.dto.CustomerDTO;
import lk.ijse.dto.ItemDTO;
import lk.ijse.dto.OrderDetailDTO;
import lk.ijse.entity.Item;
import lk.ijse.entity.OrderDetail;

import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBOImpl implements ItemBO {
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);

    @Override
    public ArrayList<ItemDTO> getItemDetail() throws SQLException, ClassNotFoundException {
        ArrayList<ItemDTO> set = new ArrayList<>();
        ArrayList<Item> detail = itemDAO.getDetail();

        for(Item i : detail){
            set.add(new ItemDTO(i.getItemID(),i.getQty(),i.getDetail(),i.getUnitPrice()));
        }
        return set;
    }

    @Override
    public boolean saveItem(ItemDTO dto) throws SQLException, ClassNotFoundException {
        return itemDAO.save(new Item(dto.getItemID(),dto.getQty(),dto.getDetail(),dto.getUnitPrice()));
    }

    @Override
    public boolean updateItem(ItemDTO dto) throws SQLException, ClassNotFoundException {
        return itemDAO.updateData(new Item(dto.getItemID(),dto.getQty(),dto.getDetail(),dto.getUnitPrice()));
    }

    @Override
    public boolean deleteItem(String id) throws SQLException, ClassNotFoundException {
        return itemDAO.delete(id);
    }

    @Override
    public String generateItemID() throws SQLException, ClassNotFoundException {
        return itemDAO.generateNewID();
    }

    @Override
    public ItemDTO searchItem(String code) throws SQLException, ClassNotFoundException {
        Item i = itemDAO.search(code);
        return new ItemDTO(i.getItemID(),i.getQty(),i.getDetail(),i.getUnitPrice());
    }

    @Override
    public ArrayList<String> loadItemId() throws SQLException, ClassNotFoundException {
        return itemDAO.loadItemId();
    }

    @Override
    public ArrayList<String> loadItemName() throws SQLException, ClassNotFoundException {
        return itemDAO.loadItemName();
    }

    @Override
    public boolean updateItemQty(ArrayList<OrderDetailDTO> orderDetails) throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetail> detail = new ArrayList<>();
        for (OrderDetailDTO o : orderDetails){
            detail.add(new OrderDetail(o.getOrderID(),o.getItemID(),o.getDetail(),o.getUnitPrice(),o.getQty()));
        }
        return itemDAO.updateQty(detail);
    }
}
