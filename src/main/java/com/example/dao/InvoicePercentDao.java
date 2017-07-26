package com.example.dao;

import com.example.model.InvoicePercent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by annakim on 2017. 3. 3..
 * 배송율 통계
 */
@Transactional
public interface InvoicePercentDao extends JpaRepository<InvoicePercent, Long> {

}
