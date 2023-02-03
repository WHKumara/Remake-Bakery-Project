package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.PurchaseOrderBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.CustomerDAO;
import lk.ijse.dao.custom.ItemDAO;
import lk.ijse.dao.custom.OrderDAO;
import lk.ijse.dao.custom.OrderDetailDAO;
import lk.ijse.dto.OrderDTO;
import lk.ijse.dto.OrderDetailDTO;
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
    public ArrayList<OrderDetailTm> getData() throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public boolean save(OrderDTO order) throws SQLException, ClassNotFoundException {
        return orderDAO.save(new Order(order.getID(),order.getCustID(),order.getDate(),order.getTime(),order.getAmount(),order.getOrderDetailDTO()));
    }

    @Override
    public int orderCount() throws SQLException, ClassNotFoundException {
        return 0;
    }

    @Override
    public double getTotalAmount() throws SQLException, ClassNotFoundException {
        return 0;
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
    public boolean save(OrderDetail orderDetail) throws SQLException, ClassNotFoundException {
        return false;
    }
}
