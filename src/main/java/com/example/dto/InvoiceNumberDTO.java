package com.example.dto;

import javax.persistence.*;

/**
 * Created by annakim on 2017. 3. 5..
 * 미배송건수 통계
 */

@Entity
public class InvoiceNumberDTO {

    @Id
    private int id; // key값 자동증가, int(number(11)

    private String tier_code;

    private String com_code; // 배송사

    public InvoiceNumberDTO() {
    }

    public InvoiceNumberDTO(int id, String tier_code, String com_code) {
        this.id = id;
        this.tier_code = tier_code;
        this.com_code = com_code;
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

    public String getCom_code() {
        return com_code;
    }

    public void setCom_code(String com_code) {
        this.com_code = com_code;
    }
}
