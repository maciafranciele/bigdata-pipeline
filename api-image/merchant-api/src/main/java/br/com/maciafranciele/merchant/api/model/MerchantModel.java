package br.com.maciafranciele.merchant.api.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_dim_merchant")
public class MerchantModel {

    private String id;
    private LocalDateTime created_at;
    private boolean enabled;
    private int price_range;
    private double average_ticket;
    private int takeout_time;
    private int delivery_time;
    private float minimum_order_value;
    private int merchant_zip_code;
    private String city;
    private char state;
    private String country;

    MerchantModel(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "created_at", nullable = false)
    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    @Column(name = "enabled", nullable = false)
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Column(name = "price_range", nullable = false)
    public int getPrice_range() {
        return price_range;
    }

    public void setPrice_range(int price_range) {
        this.price_range = price_range;
    }

    @Column(name = "average_ticket", nullable = false)
    public double getAverage_ticket() {
        return average_ticket;
    }

    public void setAverage_ticket(double average_ticket) {
        this.average_ticket = average_ticket;
    }

    @Column(name = "takeout_time", nullable = false)
    public int getTakeout_time() {
        return takeout_time;
    }

    public void setTakeout_time(int takeout_time) {
        this.takeout_time = takeout_time;
    }

    @Column(name = "delivery_time", nullable = false)
    public int getDelivery_time() {
        return delivery_time;
    }

    public void setDelivery_time(int delivery_time) {
        this.delivery_time = delivery_time;
    }

    @Column(name = "minimum_order_value", nullable = false)
    public float getMinimum_order_value() {
        return minimum_order_value;
    }

    public void setMinimum_order_value(float minimum_order_value) {
        this.minimum_order_value = minimum_order_value;
    }

    @Column(name = "merchant_zip_code", nullable = false)
    public int getMerchant_zip_code() {
        return merchant_zip_code;
    }

    public void setMerchant_zip_code(int merchant_zip_code) {
        this.merchant_zip_code = merchant_zip_code;
    }

    @Column(name = "city", nullable = false)
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "state", nullable = false)
    public char getState() {
        return state;
    }

    public void setState(char state) {
        this.state = state;
    }

    @Column(name = "country", nullable = false)
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "MerchantModel{" +
                "id='" + id + '\'' +
                ", created_at=" + created_at +
                ", enabled=" + enabled +
                ", price_range=" + price_range +
                ", average_ticket=" + average_ticket +
                ", takeout_time=" + takeout_time +
                ", delivery_time=" + delivery_time +
                ", minimum_order_value=" + minimum_order_value +
                ", merchant_zip_code=" + merchant_zip_code +
                ", city='" + city + '\'' +
                ", state=" + state +
                ", country='" + country + '\'' +
                '}';
    }
}
