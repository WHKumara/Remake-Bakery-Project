package lk.ijse.dto;

import java.io.Serializable;

public class RawMaterialDTO implements Serializable {
    private String MaterialID;
    private String type;
    private int Qty;
    private String SupID;

    public RawMaterialDTO() {
    }

    public RawMaterialDTO(String materialID, String type, int qty, String supID) {
        this.MaterialID = materialID;
        this.type = type;
        this.Qty = qty;
        this.SupID = supID;
    }

    public String getMaterialID() {
        return MaterialID;
    }

    public void setMaterialID(String materialID) {
        MaterialID = materialID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getQty() {
        return Qty;
    }

    public void setQty(int qty) {
        Qty = qty;
    }

    public String getSupID() {
        return SupID;
    }

    public void setSupID(String supID) {
        SupID = supID;
    }

    @Override
    public String toString() {
        return "RawMaterial{" +
                "MaterialID='" + MaterialID + '\'' +
                ", type='" + type + '\'' +
                ", Qty=" + Qty +
                ", SupID='" + SupID + '\'' +
                '}';
    }
}
