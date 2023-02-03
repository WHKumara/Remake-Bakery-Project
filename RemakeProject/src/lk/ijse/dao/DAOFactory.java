package lk.ijse.dao;

import lk.ijse.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {
    }

    public static DAOFactory getDaoFactory() {
        return (daoFactory == null) ? daoFactory = new DAOFactory() : daoFactory;
    }

    public enum DAOTypes {
        CUSTOMER,ITEM,ORDER,ORDER_DETAILS,PAYMENT,RAW_MATERIAL,SUPPLIER,EMPLOYEE
    }

    public SuperDAO getDAO(DAOTypes types){
        switch (types) {
            case CUSTOMER:
                return new CustomerDAOImpl();
            case ITEM:
                return new ItemDAOImpl();
            case ORDER:
                return new OrderDAOImpl();
            case ORDER_DETAILS:
                return new OrderDetailDAOImpl();
            case PAYMENT:
                return new PaymentDAOImpl();
            case RAW_MATERIAL:
                return new RawMaterialDAOImpl();
            case SUPPLIER:
                return new SupplierDAOImpl();
            case EMPLOYEE:
                return new EmployeeDAOImpl();
            default:
                return null;
        }
    }
}
