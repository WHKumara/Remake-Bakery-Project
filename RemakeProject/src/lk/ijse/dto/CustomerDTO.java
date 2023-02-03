package lk.ijse.dto;

import java.io.Serializable;

public class CustomerDTO implements Serializable {
    private String CustID;
    private String CustName;
    private int CustContact;
    private String CustAddress;

    public CustomerDTO() {
    }

    public CustomerDTO(String custID, String custName, int custContact, String custAddress) {
        this.CustID = custID;
        this.CustName = custName;
        this.CustContact = custContact;
        this.CustAddress = custAddress;
    }

    public String getCustID() {
        return CustID;
    }

    public void setCustID(String custID) {
        this.CustID = custID;
    }

    public String getCustName() {
        return CustName;
    }

    public void setCustName(String custName) {
        this.CustName = custName;
    }

    public int getCustContact() {
        return CustContact;
    }

    public void setCustContact(int custContact) {
        this.CustContact = custContact;
    }

    public String getCustAddress() {
        return CustAddress;
    }

    public void setCustAddress(String custAddress) {
        this.CustAddress = custAddress;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "CustID='" + CustID + '\'' +
                ", CustName='" + CustName + '\'' +
                ", CustContact=" + CustContact +
                ", CustAddress='" + CustAddress + '\'' +
                '}';
    }

}
