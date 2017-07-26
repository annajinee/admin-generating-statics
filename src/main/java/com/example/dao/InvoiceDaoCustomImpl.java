package com.example.dao;

import com.example.dto.*;
import com.example.model.*;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by annakim on 2017. 3. 3..
 */
@Service
@Transactional
public class InvoiceDaoCustomImpl implements InvoiceDaoCustom {

    @PersistenceContext
    private EntityManager em;

    protected Session getCurrentSession() {
        return em.unwrap(Session.class);
    }

    @Override
    public List<InvoiceDTO> findTierCodeAndInvoice(String date_base) {
        Session session = getCurrentSession();

        // 당일 Tier별 운송장 갯수
        String sqlQueryString = "select row_number() over (order by tier_code) id, count(invoice) as invoice, tier_code FROM INVOICE " +
                "where REG_DATE BETWEEN :date_base_from AND :date_base_to group by tier_code ";
        SQLQuery sqlQuery = session.createSQLQuery(sqlQueryString).addEntity(InvoiceDTO.class);
        sqlQuery.setString("date_base_from", date_base + " 00:00:00");
        sqlQuery.setString("date_base_to", date_base + " 23:59:59");

        List<InvoiceDTO> invoiceDTOS = (List<InvoiceDTO>) sqlQuery.list();
        return invoiceDTOS;

    }

    @Override
    public InvoiceStaticDTO findStatic(String date_base, String date_base_before, String tier_code, String date_threemonth) {
        Session session = getCurrentSession();

        String sqlQueryString = "select COUNT(DISTINCT INVOICE) as cnt from INVOICE where INVOICE NOT IN " +
                "(select INVOICE from INVOICE where REG_DATE BETWEEN :date_base_before_from AND :date_base_before_to " +
                "and TIER_CODE =:tier_code)and REG_DATE BETWEEN :date_base_from AND :date_base_to " +
                "and TIER_CODE =:tier_code";

        SQLQuery sqlQuery = session.createSQLQuery(sqlQueryString).addEntity(InvoiceStaticDTO.class);
        sqlQuery.setString("date_base_from", date_base + " 00:00:00");
        sqlQuery.setString("date_base_to", date_base + " 23:59:59");
        sqlQuery.setString("date_base_before_from", date_threemonth + " 00:00:00");
        sqlQuery.setString("date_base_before_to", date_base_before + " 23:59:59");
        sqlQuery.setString("tier_code", tier_code);

        InvoiceStaticDTO aStatic = (InvoiceStaticDTO) sqlQuery.uniqueResult();

        return aStatic;
    }


    // 배송사 별
    @Override
    public List<InvoiceAllDTO> findTierCodeAndInvoiceAndComcode(String date_base) {

        System.out.println("배송사별 왔음");

        Session session = getCurrentSession();

        // 당일 Tier별 운송장 갯수
        String sqlQueryString = "select row_number() over (order by tier_code) id, count(DISTINCT invoice) as invoice, com_code, tier_code FROM INVOICE " +
                "where REG_DATE BETWEEN :date_base_from AND :date_base_to group by com_code, tier_code ";

        SQLQuery sqlQuery = session.createSQLQuery(sqlQueryString).addEntity(InvoiceAllDTO.class);
        sqlQuery.setString("date_base_from", date_base + " 00:00:00");
        sqlQuery.setString("date_base_to", date_base + " 23:59:59");

        List<InvoiceAllDTO> invoiceAllDTOList = (List<InvoiceAllDTO>) sqlQuery.list();

        return invoiceAllDTOList;
    }

    @Override
    public InvoiceStaticDTO findStaticComcode(String date_base, String date_base_before, String tier_code, String com_code, String date_threemonth) {

        Session session = getCurrentSession();

        String sqlQueryString = "select COUNT(DISTINCT INVOICE) as cnt from INVOICE where INVOICE NOT IN " +
                "(select INVOICE from INVOICE where REG_DATE BETWEEN :date_base_before_from AND :date_base_before_to " +
                "and TIER_CODE =:tier_code and COM_CODE =:com_code)and REG_DATE BETWEEN :date_base_from AND :date_base_to " +
                "and TIER_CODE =:tier_code and COM_CODE =:com_code";

        SQLQuery sqlQuery = session.createSQLQuery(sqlQueryString).addEntity(InvoiceStaticDTO.class);
        sqlQuery.setString("date_base_from", date_base + " 00:00:00");
        sqlQuery.setString("date_base_to", date_base + " 23:59:59");
        sqlQuery.setString("date_base_before_from", date_threemonth + " 00:00:00");
        sqlQuery.setString("date_base_before_to", date_base_before + " 23:59:59");
        sqlQuery.setString("tier_code", tier_code);
        sqlQuery.setString("com_code", com_code);


        InvoiceStaticDTO aStatic = (InvoiceStaticDTO) sqlQuery.uniqueResult();

        return aStatic;
    }

    // 미배송건수 통계 데이터 (5일 기준 배송완료 되지 않는것 level !=6)
    // date_base: 5일전, date_base_before: 6일전 (중복되지 않도록)
    // cnt : 중복되지 않은 송장 유효갯수 (총 갯수)
    // invoice : 미배송건수 <<= count(distinct invoice) as cnt
    // ----> 전체 배송사 (com_code: 00으로 들어감)
    @Override
    public InvoiceStaticDTO findNotDeliveredStatic(String date_base, String date_base_before, String tier_code, String date_threemonth) {
        Session session = getCurrentSession();

        String sqlQueryString = "select COUNT(DISTINCT INVOICE) as cnt from INVOICE where INVOICE NOT IN " +
                "(select INVOICE from INVOICE where REG_DATE BETWEEN :date_base_before_from AND :date_base_before_to " +
                "and TIER_CODE =:tier_code and LEVEL !='6')and REG_DATE BETWEEN :date_base_from AND :date_base_to " +
                "and TIER_CODE =:tier_code and LEVEL !='6'";

        SQLQuery sqlQuery = session.createSQLQuery(sqlQueryString).addEntity(InvoiceStaticDTO.class);
        sqlQuery.setString("date_base_from", date_base + " 00:00:00");
        sqlQuery.setString("date_base_to", date_base + " 23:59:59");
        sqlQuery.setString("date_base_before_from", date_threemonth + " 00:00:00");
        sqlQuery.setString("date_base_before_to", date_base_before + " 23:59:59");
        sqlQuery.setString("tier_code", tier_code);

        InvoiceStaticDTO aStatic = (InvoiceStaticDTO) sqlQuery.uniqueResult();

        return aStatic;
    }

    // 미배송건수 통계 데이터 (5일 기준 배송완료 되지 않는것 level !=6)
    // date_base: 5일전, date_base_before: 6일전 (중복되지 않도록)
    // cnt : 중복되지 않은 송장 유효갯수 (총 갯수)
    // invoice : 미배송건수 <<= count(distinct invoice) as cnt
    // ----> 배송사별 분류
    @Override
    public InvoiceStaticDTO findNotDeliveredStaticComcode(String date_base, String date_base_before, String tier_code, String com_code, String date_threemonth) {
        Session session = getCurrentSession();

        System.out.println("date_base_before: "+date_base_before);
        System.out.println("date_base: "+date_base);

        String sqlQueryString = "select COUNT(DISTINCT INVOICE) as cnt from INVOICE where INVOICE NOT IN " +
                "(select INVOICE from INVOICE where REG_DATE BETWEEN :date_base_before_from AND :date_base_before_to " +
                "and TIER_CODE =:tier_code and COM_CODE =:com_code and LEVEL !='6')and REG_DATE BETWEEN :date_base_from AND :date_base_to " +
                "and TIER_CODE =:tier_code and COM_CODE =:com_code and LEVEL !='6'";

        SQLQuery sqlQuery = session.createSQLQuery(sqlQueryString).addEntity(InvoiceStaticDTO.class);
        sqlQuery.setString("date_base_from", date_base + " 00:00:00");
        sqlQuery.setString("date_base_to", date_base + " 23:59:59");
        sqlQuery.setString("date_base_before_from", date_threemonth + " 00:00:00");
        sqlQuery.setString("date_base_before_to", date_base_before + " 23:59:59");
        sqlQuery.setString("tier_code", tier_code);
        sqlQuery.setString("com_code", com_code);


        InvoiceStaticDTO aStatic = (InvoiceStaticDTO) sqlQuery.uniqueResult();

        return aStatic;
    }

    // 배송율 통계 데이터 - 전체

    // 배송율 통계 데이터 (등록일+1 ~ +3일에 배송완료) -전체
    @Override
    public List<InvoiceAlllPercentDTO> findDelivereyStaticOneDayAll(String date_base, String date) {

        System.out.println("REG_DATE : "+date+" , MOD_DATE: "+date_base);

        Session session = getCurrentSession();

        String sqlQueryString ="select distinct a.tier_code, isnull(invoice,0) as invoice, a.reg_date from " +
                "(select tier_code, reg_date from invoice_number where reg_date=:date)a left join " +
                "(select count(invoice) as invoice, tier_code FROM invoice inner join invoice_detail on invoice.id = invoice_detail.invoice_id " +
                "where invoice.LEVEL='6' " +
                "AND invoice.REG_DATE BETWEEN :date_from AND :date_to " +
                "AND invoice_detail.time BETWEEN :date_base_from AND :date_base_to " +
                "group by tier_code) b on a.tier_code=b.tier_code order by a.tier_code";


        SQLQuery sqlQuery = session.createSQLQuery(sqlQueryString).addEntity(InvoiceAlllPercentDTO.class);
        sqlQuery.setString("date", date);
        sqlQuery.setString("date_from", date + " 00:00:00");
        sqlQuery.setString("date_to", date + " 23:59:59");
        sqlQuery.setString("date_base_from", date_base + " 00:00:00");
        sqlQuery.setString("date_base_to", date_base + " 23:59:59");

        List<InvoiceAlllPercentDTO> staticPercentList = (List<InvoiceAlllPercentDTO>) sqlQuery.list();

        return staticPercentList;
    }


    // 배송율 통계 데이터 (등록일+4일이상에 배송완료) -전체
    @Override
    public List<InvoiceAlllPercentDTO> findDelivereyStaticFourDayAll(String date_base, String date) {

        System.out.println("REG_DATE : "+date+" , MOD_DATE: "+date_base);

        Session session = getCurrentSession();


        String sqlQueryString ="select distinct a.tier_code, isnull(invoice,0) as invoice, a.reg_date from " +
                "(select tier_code, reg_date from invoice_number where reg_date=:date)a left join " +
                "(select count(invoice) as invoice, tier_code FROM invoice inner join invoice_detail on invoice.id = invoice_detail.invoice_id " +
                "where invoice.LEVEL='6' " +
                "AND invoice.REG_DATE <= :date_from " +
                "AND invoice_detail.time BETWEEN :date_base_from AND :date_base_to " +
                "group by tier_code) b on a.tier_code=b.tier_code order by a.tier_code";

        SQLQuery sqlQuery = session.createSQLQuery(sqlQueryString).addEntity(InvoiceAlllPercentDTO.class);
        sqlQuery.setString("date", date);
        sqlQuery.setString("date_base_from", date_base + " 00:00:00");
        sqlQuery.setString("date_base_to", date_base + " 23:59:59");
        sqlQuery.setString("date_from", date + " 00:00:00");


        List<InvoiceAlllPercentDTO> staticPercentList = (List<InvoiceAlllPercentDTO>) sqlQuery.list();

        return staticPercentList;
    }


    // 배송율 통계 데이터 (등록일+1 ~ +3일에 배송완료)
    @Override
    public List<InvoicePercentDTO> findDelivereyStaticOneDay(String date_base, String date) {

        System.out.println("REG_DATE : "+date+" , MOD_DATE: "+date_base);

        Session session = getCurrentSession();

        String sqlQueryString = "select row_number() over (order by a.tier_code) id, " +
                "a.tier_code, a.com_code, a.REG_DATE, isnull(invoice,0) as invoice from " +
                "(select tier_code, com_code, reg_date from invoice_number where reg_date=:date)a " +
                "left join (select count(invoice) as invoice, com_code, tier_code FROM invoice inner join invoice_detail on invoice.id = invoice_detail.invoice_id " +
                "where invoice.LEVEL='6' AND invoice.REG_DATE BETWEEN :date_from AND :date_to " +
                "AND invoice_detail.time BETWEEN :date_base_from AND :date_base_to group by com_code, tier_code) b " +
                "on a.tier_code=b.tier_code and a.com_code=b.com_code order by a.tier_code, a.com_code";

        SQLQuery sqlQuery = session.createSQLQuery(sqlQueryString).addEntity(InvoicePercentDTO.class);
        sqlQuery.setString("date", date);
        sqlQuery.setString("date_from", date + " 00:00:00");
        sqlQuery.setString("date_to", date + " 23:59:59");
        sqlQuery.setString("date_base_from", date_base + " 00:00:00");
        sqlQuery.setString("date_base_to", date_base + " 23:59:59");

        List<InvoicePercentDTO> invoicePercentDTOList = (List<InvoicePercentDTO>) sqlQuery.list();

        return invoicePercentDTOList;
    }



    // 배송율 통계 데이터 (등록일+4일이상에 배송완료)
    @Override
    public List<InvoicePercentDTO> findDelivereyStaticFourDay(String date_base, String date) {

        System.out.println("REG_DATE : "+date+" , MOD_DATE: "+date_base);

        Session session = getCurrentSession();

        String sqlQueryString = "select row_number() over (order by a.tier_code) id, " +
                "a.tier_code, a.com_code, a.REG_DATE, isnull(invoice,0) as invoice from " +
                "(select tier_code, com_code, reg_date from invoice_number where reg_date=:date)a " +
                "left join (select count(invoice) as invoice, com_code, tier_code FROM invoice inner join invoice_detail on invoice.id = invoice_detail.invoice_id " +
                "where invoice.LEVEL='6' AND invoice.REG_DATE <= :date_from " +
                "AND invoice_detail.time BETWEEN :date_base_from AND :date_base_to group by com_code, tier_code) b " +
                "on a.tier_code=b.tier_code and a.com_code=b.com_code order by a.tier_code, a.com_code";

        SQLQuery sqlQuery = session.createSQLQuery(sqlQueryString).addEntity(InvoicePercentDTO.class);
        sqlQuery.setString("date", date);
        sqlQuery.setString("date_base_from", date_base + " 00:00:00");
        sqlQuery.setString("date_base_to", date_base + " 23:59:59");
        sqlQuery.setString("date_from", date + " 00:00:00");


        List<InvoicePercentDTO> invoicePercentDTOList = (List<InvoicePercentDTO>) sqlQuery.list();

        return invoicePercentDTOList;
    }


    // 데이터 0 표현을 위한 Number Table
    @Override
    public List<InvoiceNumberDTO> findBasedList() {
        Session session = getCurrentSession();

        String sqlQueryString = "select  ROW_NUMBER() OVER (ORDER BY tier_code, com_code) AS id, tier_code, com_code " +
                "from invoice group by tier_code, com_code";

        SQLQuery sqlQuery = session.createSQLQuery(sqlQueryString).addEntity(InvoiceNumberDTO.class);


        List<InvoiceNumberDTO> invoiceNumberDTOList = (List<InvoiceNumberDTO>) sqlQuery.list();

        return invoiceNumberDTOList;
    }


}