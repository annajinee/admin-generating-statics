package com.example.dto;



import javax.persistence.*;

/**
 * Created by annakim on 2017. 3. 5..
 */
@Entity
public class InvoiceDTO {

    @Id
    private int id;
    private String tier_code;
    private String invoice;
//    private String com_code;

    public InvoiceDTO() {
    }

    public InvoiceDTO(String tier_code, String invoice) {
        this.tier_code = tier_code;
        this.invoice = invoice;
    }

    public InvoiceDTO(int id, String tier_code, String invoice) {
        this.id = id;
        this.tier_code = tier_code;
        this.invoice = invoice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
