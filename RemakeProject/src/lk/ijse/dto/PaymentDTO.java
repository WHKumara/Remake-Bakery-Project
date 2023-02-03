package lk.ijse.dto;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

public class PaymentDTO implements Serializable {
    private String PaymentID;
    private double PaymentAmount;
    private java.sql.Date Date;
    private java.sql.Time Time;
    private String SupID;

    public PaymentDTO() {
    }

    public PaymentDTO(String paymentID, double paymentAmount, java.sql.Date date, java.sql.Time time, String supID) {
        this.PaymentID = paymentID;
        this.PaymentAmount = paymentAmount;
        this.Date = date;
        this.Time = time;
        this.SupID = supID;
    }

    public String getPaymentID() {
        return PaymentID;
    }

    public void setPaymentID(String paymentID) {
        PaymentID = paymentID;
    }

    public double getPaymentAmount() {
        return PaymentAmount;
    }

    public void setPaymentAmount(double paymentAmount) {
        PaymentAmount = paymentAmount;
    }

    public java.sql.Date getDate() {
        return Date;
    }

    public void setDate(java.sql.Date date) {
        Date = date;
    }

    public java.sql.Time getTime() {
        return Time;
    }

    public void setTime(java.sql.Time time) {
        Time = time;
    }

    public String getSupID() {
        return SupID;
    }

    public void setSupID(String supID) {
        SupID = supID;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "PaymentID='" + PaymentID + '\'' +
                ", PaymentAmount='" + PaymentAmount + '\'' +
                ", Date=" + Date +
                ", Time=" + Time +
                ", SupID='" + SupID + '\'' +
                '}';
    }

}
