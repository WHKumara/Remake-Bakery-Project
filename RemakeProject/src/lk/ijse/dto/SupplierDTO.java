package lk.ijse.dto;

import java.io.Serializable;

public class SupplierDTO implements Serializable {
    private String SupID;
    private String SupName;
    private int SupContact;
    private String SupAddress;
    private String SupEmail;

    public SupplierDTO() {
    }

    public SupplierDTO(String supID, String supName, int supContact, String supAddress, String supEmail) {
        this.SupID = supID;
        this.SupName = supName;
        this.SupContact = supContact;
        this.SupAddress = supAddress;
        this.SupEmail = supEmail;
    }

    public String getSupID() {
        return SupID;
    }

    public void setSupID(String supID) {
        SupID = supID;
    }

    public String getSupName() {
        return SupName;
    }

    public void setSupName(String supName) {
        SupName = supName;
    }

    public int getSupContact() {
        return SupContact;
    }

    public void setSupContact(int supContact) {
        SupContact = supContact;
    }

    public String getSupAddress() {
        return SupAddress;
    }

    public void setSupAddress(String supAddress) {
        SupAddress = supAddress;
    }

    public String getSupEmail() {
        return SupEmail;
    }

    public void setSupEmail(String supEmail) {
        SupEmail = supEmail;
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "SupID='" + SupID + '\'' +
                ", SupName='" + SupName + '\'' +
                ", SupContact='" + SupContact + '\'' +
                ", SupAddress='" + SupAddress + '\'' +
                ", SupEmail='" + SupEmail + '\'' +
                '}';
    }
}
