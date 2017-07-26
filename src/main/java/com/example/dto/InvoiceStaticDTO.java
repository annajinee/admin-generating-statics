package com.example.dto;


import javax.persistence.*;

/**
 * Created by annakim on 2017. 3. 5..
 */
@Entity
public class InvoiceStaticDTO {

    @Id
    private String cnt; // 중복되지 않은 송장 유효갯수

    public InvoiceStaticDTO(String cnt) {
        this.cnt = cnt;
    }


    public InvoiceStaticDTO() {
    }


    public String getCnt() {
        return cnt;
    }

    public void setCnt(String cnt) {
        this.cnt = cnt;
    }

}
