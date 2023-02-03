package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.CustomerBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.CustomerDAO;
import lk.ijse.dto.CustomerDTO;
import lk.ijse.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {
    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.CUSTOMER);
    @Override
    public ArrayList<CustomerDTO> getCustomerDetail() throws SQLException, ClassNotFoundException {
        ArrayList<CustomerDTO> dto = new ArrayList<>();
        ArrayList<Customer> customers = customerDAO.getDetail();

        for (Customer c: customers) {
            dto.add(new CustomerDTO(c.getCustID(),c.getCustName(),c.getCustContact(),c.getCustAddress()));
        }
        return dto;
    }

    @Override
    public boolean saveCustomer(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        return customerDAO.save(new Customer(dto.getCustID(),dto.getCustName(),dto.getCustContact(),dto.getCustAddress()));
    }

    @Override
    public boolean updateData(CustomerDTO dto) throws SQLException, ClassNotFoundException {
        return customerDAO.updateData(new Customer(dto.getCustID(),dto.getCustName(),dto.getCustContact(),dto.getCustAddress()));
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(id);
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return customerDAO.generateNewID();
    }

    @Override
    public CustomerDTO searchCustomer(String code) throws SQLException, ClassNotFoundException {
        Customer c = customerDAO.search(code);
        return new CustomerDTO(c.getCustID(),c.getCustName(),c.getCustContact(),c.getCustAddress());
    }

    @Override
    public int customerCount() throws SQLException, ClassNotFoundException {
        return customerDAO.customerCount();
    }

    @Override
    public ArrayList<String> loadCustomerId() throws SQLException, ClassNotFoundException {
        return customerDAO.loadCustId();
    }
}
