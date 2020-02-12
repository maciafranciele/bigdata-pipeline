package br.com.maciafranciele.merchant.api.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "tbl_dim_order")
public class OrderModel {

    private String id;
    private String customer_id;
    private LocalDateTime created_at;
    private boolean scheduled;
    private String delivery_address_city;
    private String delivery_address_state;
    private String delivery_address_country;
    private String delivery_address_district;
    private String merchant_id;
    private String delivery_address_zip_code;
    private double total_amount;

    @OneToMany(targetEntity=ItemsModel.class, mappedBy="tbl_dim_order_items", fetch=FetchType.EAGER)
    private List<ItemsModel> items;

    OrderModel(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "customer_id", nullable = false)
    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    @Column(name = "created_at", nullable = false)
    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    @Column(name = "scheduled", nullable = false)
    public boolean isScheduled() {
        return scheduled;
    }

    public void setScheduled(boolean scheduled) {
        this.scheduled = scheduled;
    }

    @Column(name = "delivery_address_city", nullable = false)
    public String getDelivery_address_city() {
        return delivery_address_city;
    }

    public void setDelivery_address_city(String delivery_address_city) {
        this.delivery_address_city = delivery_address_city;
    }

    @Column(name = "delivery_address_state", nullable = false)
    public String getDelivery_address_state() {
        return delivery_address_state;
    }

    public void setDelivery_address_state(String delivery_address_state) {
        this.delivery_address_state = delivery_address_state;
    }

    @Column(name = "delivery_address_country", nullable = false)
    public String getDelivery_address_country() {
        return delivery_address_country;
    }

    public void setDelivery_address_country(String delivery_address_country) {
        this.delivery_address_country = delivery_address_country;
    }

    @Column(name = "delivery_address_district", nullable = false)
    public String getDelivery_address_district() {
        return delivery_address_district;
    }

    public void setDelivery_address_district(String delivery_address_district) {
        this.delivery_address_district = delivery_address_district;
    }

    @Column(name = "merchant_id", nullable = false)
    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    @Column(name = "delivery_address_zip_code", nullable = false)
    public String getDelivery_address_zip_code() {
        return delivery_address_zip_code;
    }

    public void setDelivery_address_zip_code(String delivery_address_zip_code) {
        this.delivery_address_zip_code = delivery_address_zip_code;
    }

    @Column(name = "total_amount", nullable = false)
    public double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }

    public List<ItemsModel> getItems() {
        return items;
    }

    public void setItems(List<ItemsModel> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "OrderModel{" +
                "id='" + id + '\'' +
                ", customer_id='" + customer_id + '\'' +
                ", created_at=" + created_at +
                ", scheduled=" + scheduled +
                ", delivery_address_city='" + delivery_address_city + '\'' +
                ", delivery_address_state='" + delivery_address_state + '\'' +
                ", delivery_address_country='" + delivery_address_country + '\'' +
                ", delivery_address_district='" + delivery_address_district + '\'' +
                ", merchant_id='" + merchant_id + '\'' +
                ", delivery_address_zip_code='" + delivery_address_zip_code + '\'' +
                ", total_amount=" + total_amount +
                ", items=" + items +
                '}';
    }
}
