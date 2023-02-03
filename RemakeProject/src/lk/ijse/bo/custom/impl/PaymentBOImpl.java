package lk.ijse.bo.custom.impl;

import lk.ijse.bo.custom.PaymentBO;
import lk.ijse.dao.DAOFactory;
import lk.ijse.dao.custom.PaymentDAO;
import lk.ijse.dto.PaymentDTO;
import lk.ijse.entity.Payment;

import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentBOImpl implements PaymentBO {
    PaymentDAO paymentDAO = (PaymentDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.PAYMENT);

    @Override
    public ArrayList<PaymentDTO> getPaymentDetail() throws SQLException, ClassNotFoundException {
        ArrayList<PaymentDTO> detail = new ArrayList<>();
        ArrayList<Payment> data = paymentDAO.getDetail();
        for (Payment p : data){
            detail.add(new PaymentDTO(p.getPaymentID(),p.getPaymentAmount(),p.getDate(),p.getTime(),p.getSupID()));
        }
        return detail;
    }

    @Override
    public boolean savePayment(PaymentDTO dto) throws SQLException, ClassNotFoundException {
        return paymentDAO.save(new Payment(dto.getPaymentID(),dto.getPaymentAmount(),dto.getDate(),dto.getTime(),dto.getSupID()));
    }

    @Override
    public boolean updateData(PaymentDTO dto) throws SQLException, ClassNotFoundException {
        return paymentDAO.updateData(new Payment(dto.getPaymentID(),dto.getPaymentAmount(),dto.getDate(),dto.getTime(),dto.getSupID()));
    }

    @Override
    public boolean deletePayment(String id) throws SQLException, ClassNotFoundException {
        return paymentDAO.delete(id);
    }

    @Override
    public String generatePaymentID() throws SQLException, ClassNotFoundException {
        return paymentDAO.generateNewID();
    }

    @Override
    public Payment search(String code) throws SQLException, ClassNotFoundException {
        return paymentDAO.search(code);
    }
}
