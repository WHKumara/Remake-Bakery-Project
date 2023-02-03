package lk.ijse.dto;

import java.io.Serializable;
import java.util.ArrayList;

public class OrderDTO implements Serializable {
    private String ID;
    private String CustID;
    private java.sql.Date Date;
    private java.sql.Time Time;
    private double Amount;

    private ArrayList<OrderDetailDTO> orderDetailDTO;

    public OrderDTO() {
    }

    public OrderDTO(String ID, String custID, java.sql.Date date, java.sql.Time time, double amount,ArrayList<OrderDetailDTO> orderDetailDTO) {
        this.ID = ID;
        this.CustID = custID;
        this.Date = date;
        this.Time = time;
        this.Amount = amount;
        this.setOrderDetailDTO(orderDetailDTO);
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getCustID() {
        return CustID;
    }

    public void setCustID(String custID) {
        this.CustID = custID;
    }

    public java.sql.Date getDate() {
        return Date;
    }

    public void setDate(java.sql.Date date) {
        this.Date = date;
    }

    public java.sql.Time getTime() {
        return Time;
    }

    public void setTime(java.sql.Time time) {
        this.Time = time;
    }

    public double getAmount() {
        return Amount;
    }

    public void setAmount(double amount) {
        this.Amount = amount;
    }

    @Override
    public String toString() {
        return "Order{" +
                "ID='" + ID + '\'' +
                ", CustID='" + CustID + '\'' +
                ", Date=" + Date +
                ", Time=" + Time +
                ", Amount=" + Amount +
                '}';
    }

    public ArrayList<OrderDetailDTO> getOrderDetailDTO() {
        return orderDetailDTO;
    }

    public void setOrderDetailDTO(ArrayList<OrderDetailDTO> orderDetailDTO) {
        this.orderDetailDTO = orderDetailDTO;
    }
}
