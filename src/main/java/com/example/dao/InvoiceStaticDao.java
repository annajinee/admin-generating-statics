package com.example.dao;

import com.example.model.InvoiceStatic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by annakim on 2017. 3. 3..
 */
@Transactional
public interface InvoiceStaticDao extends JpaRepository<InvoiceStatic, Long> {

}
