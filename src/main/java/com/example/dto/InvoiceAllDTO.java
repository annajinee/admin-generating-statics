package com.example.dto;


import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by annakim on 2017. 3. 5..
 */
@Entity
public class InvoiceAllDTO {

    @Id
    private int id;
    private String tier_code;
    private String invoice;
    private String com_code;

    public InvoiceAllDTO() {
    }

    public InvoiceAllDTO(String tier_code, String invoice) {
        this.tier_code = tier_code;
        this.invoice = invoice;
    }

    public InvoiceAllDTO(String tier_code, String invoice, String com_code) {
        this.tier_code = tier_code;
        this.invoice = invoice;
        this.com_code = com_code;
    }

    public InvoiceAllDTO(int id, String tier_code, String invoice, String com_code) {
        this.id = id;
        this.tier_code = tier_code;
        this.invoice = invoice;
        this.com_code = com_code;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCom_code() {
        return com_code;
    }

    public void setCom_code(String com_code) {
        this.com_code = com_code;
    }

    public String getTier_code() {
        return tier_code;
    }

    public void setTier_code(String tier_code) {
        this.tier_code = tier_code;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }
}
