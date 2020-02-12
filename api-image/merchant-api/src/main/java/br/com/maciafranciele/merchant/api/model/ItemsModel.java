package br.com.maciafranciele.merchant.api.model;

import javax.persistence.*;

@Entity
@Table(name = "tbl_dim_order_items")
public class ItemsModel {

    private String id;
    private String name;
    private int quantity;
    private double unitPrice;
    private int sequence;
    private String garnishItems;
    private double addition;
    private double totalAddition;
    private double discount;
    private double totalDiscount;
    private String id_order;

    ItemsModel(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "name", nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "quantity", nullable = false)
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Column(name = "unitPrice", nullable = false)
    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Column(name = "sequence", nullable = false)
    public int getSequence() {
        return sequence;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    @Column(name = "garnishItems", nullable = false)
    public String getGarnishItems() {
        return garnishItems;
    }

    public void setGarnishItems(String garnishItems) {
        this.garnishItems = garnishItems;
    }

    @Column(name = "addition", nullable = false)
    public double getAddition() {
        return addition;
    }

    public void setAddition(double addition) {
        this.addition = addition;
    }

    @Column(name = "totalAddition", nullable = false)
    public double getTotalAddition() {
        return totalAddition;
    }

    public void setTotalAddition(double totalAddition) {
        this.totalAddition = totalAddition;
    }

    @Column(name = "discount", nullable = false)
    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    @Column(name = "totalDiscount", nullable = false)
    public double getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(double totalDiscount) {
        this.totalDiscount = totalDiscount;
    }


    public String getId_order() {
        return id_order;
    }

    public void setId_order(String id_order) {
        this.id_order = id_order;
    }

    @Override
    public String toString() {
        return "ItemsModel{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", quantity=" + quantity +
                ", unitPrice=" + unitPrice +
                ", sequence=" + sequence +
                ", garnishItems='" + garnishItems + '\'' +
                ", addition=" + addition +
                ", totalAddition=" + totalAddition +
                ", discount=" + discount +
                ", totalDiscount=" + totalDiscount +
                ", id_order='" + id_order + '\'' +
                '}';
    }
}
