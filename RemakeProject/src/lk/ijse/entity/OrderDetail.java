package lk.ijse.entity;

public class OrderDetail {
    private String OrderID;
    private String ItemID;
    private String Detail;
    private double UnitPrice;
    private double Qty;

    public OrderDetail() {
    }

    public OrderDetail(String orderID, String itemID, String detail, double unitPrice, double qty) {
        this.OrderID = orderID;
        this.ItemID = itemID;
        this.Detail = detail;
        this.UnitPrice = unitPrice;
        this.Qty = qty;
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String orderID) {
        OrderID = orderID;
    }

    public String getItemID() {
        return ItemID;
    }

    public void setItemID(String itemID) {
        ItemID = itemID;
    }

    public String getDetail() {
        return Detail;
    }

    public void setDetail(String detail) {
        Detail = detail;
    }

    public double getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        UnitPrice = unitPrice;
    }

    public double getQty() {
        return Qty;
    }

    public void setQty(double qty) {
        Qty = qty;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "OrderID='" + OrderID + '\'' +
                ", ItemID='" + ItemID + '\'' +
                ", Detail='" + Detail + '\'' +
                ", UnitPrice=" + UnitPrice +
                ", Qty=" + Qty +
                '}';
    }
}
