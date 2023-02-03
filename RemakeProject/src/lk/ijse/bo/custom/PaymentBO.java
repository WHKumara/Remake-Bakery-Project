package lk.ijse.bo.custom;

import lk.ijse.bo.SuperBO;
import lk.ijse.dto.PaymentDTO;
import lk.ijse.entity.Payment;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PaymentBO extends SuperBO {
    ArrayList<PaymentDTO> getPaymentDetail() throws SQLException, ClassNotFoundException;
    boolean savePayment(PaymentDTO dto) throws SQLException, ClassNotFoundException;
    boolean updateData(PaymentDTO dto) throws SQLException, ClassNotFoundException;
    boolean deletePayment(String id) throws SQLException, ClassNotFoundException;
    String generatePaymentID() throws SQLException, ClassNotFoundException;
    Payment search(String code) throws SQLException, ClassNotFoundException;
}
