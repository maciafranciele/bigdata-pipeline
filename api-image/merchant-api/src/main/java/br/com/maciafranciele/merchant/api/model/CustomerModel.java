package br.com.maciafranciele.merchant.api.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tbl_dim_customer")
public class CustomerModel {

    private String customer_id;
    private String language;
    private LocalDateTime created_at;
    private boolean active;
    private String customer_name;
    private int phone_are;
    private int phone_number;

    CustomerModel(){}

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }

    @Column(name = "language", nullable = false)
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Column(name = "created_at", nullable = false)
    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    @Column(name = "active", nullable = false)
    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Column(name = "customer_name", nullable = false)
    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    @Column(name = "phone_are", nullable = false)
    public int getPhone_are() {
        return phone_are;
    }

    public void setPhone_are(int phone_are) {
        this.phone_are = phone_are;
    }

    @Column(name = "phone_number", nullable = false)
    public int getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(int phone_number) {
        this.phone_number = phone_number;
    }

    @Override
    public String toString() {
        return "CustomerModel{" +
                "customer_id='" + customer_id + '\'' +
                ", language='" + language + '\'' +
                ", created_at=" + created_at +
                ", active=" + active +
                ", customer_name='" + customer_name + '\'' +
                ", phone_are=" + phone_are +
                ", phone_number=" + phone_number +
                '}';
    }
}
