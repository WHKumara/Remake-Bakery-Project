package lk.ijse.entity;

public class Item {
    private String ItemID;
    private int Qty;
    private String Detail;
    private double UnitPrice;

    public Item() {
    }

    public Item(String itemID, int qty, String detail, double unitPrice) {
        this.ItemID = itemID;
        this.Qty = qty;
        this.Detail = detail;
        this.UnitPrice = unitPrice;
    }

    public String getItemID() {
        return ItemID;
    }

    public void setItemID(String itemID) {
        this.ItemID = itemID;
    }

    public int getQty() {
        return Qty;
    }

    public void setQty(int qty) {
        this.Qty = qty;
    }

    public String getDetail() {
        return Detail;
    }

    public void setDetail(String detail) {
        this.Detail = detail;
    }

    public double getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.UnitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "Item{" +
                "ItemID='" + ItemID + '\'' +
                ", Qty=" + Qty +
                ", Detail='" + Detail + '\'' +
                ", UnitPrice=" + UnitPrice +
                '}';
    }
}
