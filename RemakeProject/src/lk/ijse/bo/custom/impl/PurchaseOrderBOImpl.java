package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.PurchaseOrderBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.CustomerDAO;
import lk.ijse.dao.custom.ItemDAO;
import lk.ijse.dao.custom.OrderDAO;
import lk.ijse.dao.custom.OrderDetailDAO;
import lk.ijse.dto.CustomerDTO;
import lk.ijse.dto.ItemDTO;
import lk.ijse.dto.OrderDTO;
import lk.ijse.dto.OrderDetailDTO;
import lk.ijse.entity.Customer;
import lk.ijse.entity.Item;
import lk.ijse.entity.Order;
import lk.ijse.entity.OrderDetail;
import lk.ijse.view.tm.OrderDetailTm;

import java.sql.SQLException;
import java.util.ArrayList;

public class PurchaseOrderBOImpl implements PurchaseOrderBO {
    OrderDAO orderDAO = (OrderDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER);
    OrderDetailDAO orderDetailDAO = (OrderDetailDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ORDER_DETAILS);
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    ItemDAO itemDAO = (ItemDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.ITEM);



    @Override
    public boolean save(OrderDTO order) throws SQLException, ClassNotFoundException {
        return orderDAO.save(new Order(order.getID(),order.getCustID(),order.getDate(),order.getTime(),order.getAmount(),order.getOrderDetailDTO()));
    }

    @Override
    public String generateNextOrderID() throws SQLException, ClassNotFoundException {
        return orderDAO.generateNewID();
    }

    @Override
    public boolean saveOrderDetails(ArrayList<OrderDetailDTO> orderDetails) throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetail> details = new ArrayList<>();
        for (OrderDetailDTO o : orderDetails){
            details.add(new OrderDetail(o.getOrderID(),o.getItemID(),o.getDetail(),o.getUnitPrice(),o.getQty()));
        }
        return orderDetailDAO.saveOrderDetails(details);
    }

    @Override
    public CustomerDTO searchCustomer(String code) throws SQLException, ClassNotFoundException {
        Customer c = customerDAO.search(code);
        return new CustomerDTO(c.getCustID(),c.getCustName(),c.getCustContact(),c.getCustAddress());
    }

    @Override
    public ArrayList<String> loadCustomerId() throws SQLException, ClassNotFoundException {
        return customerDAO.loadCustId();
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
    public boolean updateItemQty(ArrayList<OrderDetailDTO> orderDetails) throws SQLException, ClassNotFoundException {
        ArrayList<OrderDetail> detail = new ArrayList<>();
        for (OrderDetailDTO o : orderDetails){
            detail.add(new OrderDetail(o.getOrderID(),o.getItemID(),o.getDetail(),o.getUnitPrice(),o.getQty()));
        }
        return itemDAO.updateQty(detail);
    }

    @Override
    public ArrayList<String> loadItemName() throws SQLException, ClassNotFoundException {
        return itemDAO.loadItemName();
    }

    @Override
    public double getTotalAmount() throws SQLException, ClassNotFoundException {
        return orderDAO.getTotalAmount();
    }

    @Override
    public ArrayList<OrderDetailTm> getData() throws SQLException, ClassNotFoundException {
        return orderDAO.getData();
    }

}
