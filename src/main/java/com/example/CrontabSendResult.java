package com.example;

/**
 * Created by gim-anna on 2016. 10. 6..
 */

import com.example.dao.*;
import com.example.dto.*;
import com.example.model.*;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class CrontabSendResult {

    private Logger logger = LogManager.getLogger(getClass());


    @Autowired
    InvoiceDao invoiceDao;
    @Autowired
    InvoiceStaticDao invoiceStaticDao;
    @Autowired
    InvoiceNotDeliveredDao invoiceNotDeliveredDao;
    @Autowired
    InvoicePercentDao invoicePercentDao;
    @Autowired
    InvoiceNumberDao invoiceNumberDao;

    InvoiceDaoCustom invoiceDaoCustom;

    @Autowired
    public CrontabSendResult(InvoiceDaoCustom invoiceDaoCustom) {
        this.invoiceDaoCustom = invoiceDaoCustom;
    }

    private InvoiceStatic invoice_static;
    private InvoiceNotdelivered invoice_notdelivered;
    private InvoicePercent invoice_percent;
    private InvoiceNumber invoiceNumber;


    @Scheduled(fixedDelay = 50000000) // 3초마다 폴링
//    @Scheduled(cron = "0 0 02 * * ?") //매일 새벽 두시에 실행
    public void pollingSendResult() {

        SimpleDateFormat formatter
                = new SimpleDateFormat("yyyy-MM-dd");

        Calendar calendar = new GregorianCalendar();
        calendar.add(calendar.DATE, -1); // 하루 전
//        String date_base = formatter.format(calendar.getTime());
        String date_base = "2017-05-01";


        Calendar calendar2 = new GregorianCalendar();
        calendar2.add(calendar2.DATE, -2); // 이틀 전
//        String date_base_before = formatter.format(calendar2.getTime());
        String date_base_before = "2017-04-30";


        Calendar calendar3 = new GregorianCalendar(); // 3개월 전 (데이터베이스 기준일)
        calendar3.add(calendar3.MONTH, -3);
//        String date_threemonth = formatter.format(calendar3.getTime());
        String date_threemonth = "2017-01-27";


        ////////////////////////////////////////////// 미배송 통계 용

        Calendar calendar5 = new GregorianCalendar();
        calendar5.add(calendar5.DATE, -5); // 5일 전
//        String date_base_fivedaysbefore = formatter.format(calendar5.getTime());
//        String date_base_fivedaysbefore = "2017-05-19";


        Calendar calendar6 = new GregorianCalendar();
        calendar6.add(calendar6.DATE, -6); // 6일 전 -> 중복 비교일
//        String date_base_sixdaysbefore = formatter.format(calendar6.getTime());
//        String date_base_sixdaysbefore = "2017-05-18";


        ////////////////////////////////////////////// 배송율 통계 용

//        // 배송완료일(mod_date)당일(date-1)일 기준 , -1일 :: date_base_before
//        Calendar calendar_2before = new GregorianCalendar();
//        calendar_2before.add(calendar_2before.DATE, -3); // 배송완료일(mod_date) 당일(date-1)일 기준 , -2일
//        String date_2before = formatter.format(calendar_2before.getTime());
//
//        Calendar calendar_3before = new GregorianCalendar();
//        calendar_3before.add(calendar_3before.DATE, -4);  // 배송완료일(mod_date) 당일(date-1)일 기준 , -3일
//        String date_3before = formatter.format(calendar_3before.getTime());
//
//        Calendar calendar_4before = new GregorianCalendar();
//        calendar_4before.add(calendar_4before.DATE, -5); //  // 배송완료일(mod_date) 당일(date-1)일 기준 , -4일
//        String date_4before = formatter.format(calendar_4before.getTime());


        System.out.println("date_base(d-1) : " + date_base);
        System.out.println("(d-2) : " + date_base_before);
        System.out.println("compared month(m-3) : " + date_threemonth);
//        System.out.println("invoice_notdelivered :(d-5) : " + date_base_fivedaysbefore);
//        System.out.println("invoice_notdelivered :(d-6) : " + date_base_sixdaysbefore);
//        System.out.println("invoice_percent d-2 : " + date_2before );
//        System.out.println("invoice_percent d-3 : " + date_3before );
//        System.out.println("invoice_percent d-4 : " + date_4before );

        List<InvoiceNumberDTO> invoiceNumberDTOS = invoiceDaoCustom.findBasedList();

        for (InvoiceNumberDTO invoiceNumberDTO : invoiceNumberDTOS) {

            invoiceNumber = new InvoiceNumber();
            invoiceNumber.setTiercode(invoiceNumberDTO.getTier_code());
            invoiceNumber.setComcode(invoiceNumberDTO.getCom_code());
            invoiceNumber.setRegdate(date_base_before); // mod_date기준(배송완료) 1일전, -2일
            invoiceNumberDao.save(invoiceNumber);
        }
        System.out.println("complete to save table named INVOICE_NUMBER");

        // 전체
        List<InvoiceDTO> invoiceDTOS = invoiceDaoCustom.findTierCodeAndInvoice(date_base);

        for (InvoiceDTO invoiceDTO : invoiceDTOS) {
            System.out.println("tier_code: " + invoiceDTO.getTier_code());
            InvoiceStaticDTO aStaticic = invoiceDaoCustom.findStatic(date_base, date_base_before, invoiceDTO.getTier_code(), date_threemonth);

            // 미배송건수 (어제날짜 기준 4일전)
//            InvoiceStaticDTO aStaticicNot = invoiceDaoCustom.findStatic(date_base_fivedaysbefore, date_base_sixdaysbefore, invoiceDTO.getTier_code(), date_threemonth);
//            InvoiceStaticDTO aStaticicNotDelivered = invoiceDaoCustom.findNotDeliveredStatic(date_base_fivedaysbefore, date_base_sixdaysbefore, invoiceDTO.getTier_code(), date_threemonth); // 5일전, 6일전

            // 배송건수 통계
            invoice_static = new InvoiceStatic();
            invoice_static.setRegdate(date_base);
            invoice_static.setInvoice(invoiceDTO.getInvoice());
            invoice_static.setTiercode(invoiceDTO.getTier_code());
            invoice_static.setComcode("00"); //00은 전체
            invoice_static.setCnt(aStaticic.getCnt());
            invoiceStaticDao.save(invoice_static);

            // 미배송건수 통계
//            invoice_notdelivered = new InvoiceNotdelivered();
//            invoice_notdelivered.setRegdate(date_base_fivedaysbefore); // 등록일 - 현재 날짜 기준 level !=6 -->5일전(4일이상 배송되지 않는것하니..)
//            invoice_notdelivered.setInvoice(aStaticicNotDelivered.getCnt()); // 미배송건수
//            invoice_notdelivered.setCnt(aStaticicNot.getCnt()); // 요청건수(전체 유효 운송장갯수)
//            invoice_notdelivered.setTiercode(invoiceDTO.getTier_code());
//            invoice_notdelivered.setComcode("00"); // 00은 전체
////            //미배송율
//            double invoice = Double.parseDouble(aStaticicNotDelivered.getCnt()); // 미배송건수
//            double cnt = Double.parseDouble(aStaticicNot.getCnt()); // 전체
//            double divi = (invoice / cnt) * 100; // 미배송건수/ 전체
//
//            if (Double.isNaN(divi)) {             //NaN 여부 판단
//                invoice_notdelivered.setDiffpercent("0");
//            } else if (Double.isInfinite(divi)) {    // Infinity 여부 판단하기
//                invoice_notdelivered.setDiffpercent("0");
//            } else if (invoice == cnt){
//                invoice_notdelivered.setDiffpercent("100.0");
//            } else {
//                invoice_notdelivered.setDiffpercent(String.format("%.2f", divi));
//            }
//            invoiceNotDeliveredDao.save(invoice_notdelivered);

        }
        System.out.println("Complete to save table named INVOICE_NOTDELIVERED for all (com_code:00)");


        // 배송사별
        List<InvoiceAllDTO> invoiceAllDTOList = invoiceDaoCustom.findTierCodeAndInvoiceAndComcode(date_base);

        for (InvoiceAllDTO object : invoiceAllDTOList) {

            InvoiceStaticDTO aStaticic2 = invoiceDaoCustom.findStaticComcode(date_base, date_base_before, object.getTier_code(), object.getCom_code(), date_threemonth);

            // 미배송건수 (어제날짜 기준 4일전)
//            InvoiceStaticDTO aStaticicNot2 = invoiceDaoCustom.findStaticComcode(date_base_fivedaysbefore, date_base_sixdaysbefore, object.getTier_code(), object.getCom_code(), date_threemonth);
//            InvoiceStaticDTO aStaticicNotDelivered2 = invoiceDaoCustom.findNotDeliveredStaticComcode(date_base_fivedaysbefore, date_base_sixdaysbefore, object.getTier_code(), object.getCom_code(), date_threemonth);

            // 배송건수 통계
            invoice_static = new InvoiceStatic();
            invoice_static.setRegdate(date_base);
            invoice_static.setInvoice(object.getInvoice());
            invoice_static.setTiercode(object.getTier_code());
            invoice_static.setComcode(object.getCom_code());
            invoice_static.setCnt(aStaticic2.getCnt());
            invoiceStaticDao.save(invoice_static);

            // 미배송건수 통계
//            invoice_notdelivered = new InvoiceNotdelivered();
//            invoice_notdelivered.setRegdate(date_base_fivedaysbefore); // 등록일 - 현재 날짜 기준 level !=6 -->5일전(4일이상 배송되지 않는것하니..)
//            invoice_notdelivered.setInvoice(aStaticicNotDelivered2.getCnt()); // 미배송건수
//            invoice_notdelivered.setCnt(aStaticicNot2.getCnt()); // 요청건수(전체 유효 운송장갯수)
//            invoice_notdelivered.setTiercode(object.getTier_code());
//            invoice_notdelivered.setComcode(object.getCom_code());
//
////            //미배송율
//            double invoice = Double.parseDouble(aStaticicNotDelivered2.getCnt()); // 미배송건수
//            double cnt = Double.parseDouble(aStaticicNot2.getCnt()); // 전체
//            double divi = (invoice / cnt) * 100; // 미배송건수/ 전체
//
//            if (Double.isNaN(divi)) {             //NaN 여부 판단
//                invoice_notdelivered.setDiffpercent("0.00");
//            } else if (Double.isInfinite(divi)) {    // Infinity 여부 판단하기
//                invoice_notdelivered.setDiffpercent("0.00");
//            } else if (invoice == cnt){
//                invoice_notdelivered.setDiffpercent("100.0");
//            } else {
//                invoice_notdelivered.setDiffpercent(String.format("%.2f", divi));
//            }
//            invoiceNotDeliveredDao.save(invoice_notdelivered);

        }
        System.out.println("Complete to save table named INVOICE_NOTDELIVERED classified by com_code");

//        System.out.println("Start to save table named INVOICE_PERCENT!!!");
//
//        // 배송율 통계 - 전체
//        // 등록일 +1일
//        System.out.println("transit date is reg_date + 1 ( delivery_period : 1 , com_code : 00 )");
//        List<InvoiceAlllPercentDTO> invoice_percent1DayListAll = invoiceDaoCustom.findDelivereyStaticOneDayAll(date_base, date_base_before);
//
//        for (InvoiceAlllPercentDTO object : invoice_percent1DayListAll) {
//
//            invoice_percent = new InvoicePercent();
//            invoice_percent.setRegdate(object.getReg_date()); // 데이터 등록일 (배송완료일 기준)
//            invoice_percent.setComcode("00");
//            invoice_percent.setTiercode(object.getTier_code());
//            invoice_percent.setDelivery_period("1"); // 배송기간
//            invoice_percent.setInvoice(object.getInvoice()); // 배송된 운송장 갯수
//            invoicePercentDao.save(invoice_percent);
//
//        }
//
//        // 배송율 통계 - 전체
//        // 등록일 +2일
//        System.out.println("transit date is reg_date + 2 ( delivery_period : 2 , com_code : 00 )");
//        List<InvoiceAlllPercentDTO> invoice_percent2DayListAll = invoiceDaoCustom.findDelivereyStaticOneDayAll(date_base, date_2before);
//
//        for (InvoiceAlllPercentDTO object : invoice_percent2DayListAll) {
//
//            invoice_percent = new InvoicePercent();
//            invoice_percent.setRegdate(object.getReg_date()); // 데이터 등록일 (배송완료일 기준)
//            invoice_percent.setComcode("00");
//            invoice_percent.setTiercode(object.getTier_code());
//            invoice_percent.setDelivery_period("2"); // 배송기간
//            invoice_percent.setInvoice(object.getInvoice()); // 배송된 운송장 갯수
//            invoicePercentDao.save(invoice_percent);
//
//        }
//
//        // 배송율 통계 - 전체
//        // 등록일 +3일
//        System.out.println("transit date is reg_date + 3 ( delivery_period : 3 , com_code : 00 )");
//        List<InvoiceAlllPercentDTO> invoice_percent3DayListAll = invoiceDaoCustom.findDelivereyStaticOneDayAll(date_base, date_3before);
//
//        for (InvoiceAlllPercentDTO object : invoice_percent3DayListAll) {
//
//            invoice_percent = new InvoicePercent();
//            invoice_percent.setRegdate(object.getReg_date()); // 데이터 등록일 (배송완료일 기준)
//            invoice_percent.setComcode("00");
//            invoice_percent.setTiercode(object.getTier_code());
//            invoice_percent.setDelivery_period("3"); // 배송기간
//            invoice_percent.setInvoice(object.getInvoice()); // 배송된 운송장 갯수
//            invoicePercentDao.save(invoice_percent);
//
//        }
//
//        // 배송율 통계 - 전체
//        // 등록일 +4일 이상
//        System.out.println("transit date is more than reg_date + 4 ( delivery_period : 4 , com_code : 00 )");
//        List<InvoiceAlllPercentDTO> invoice_percent4DayListAll = invoiceDaoCustom.findDelivereyStaticFourDayAll(date_base, date_4before);
//
//        for (InvoiceAlllPercentDTO object : invoice_percent4DayListAll) {
//
//            invoice_percent = new InvoicePercent();
//            invoice_percent.setRegdate(date_4before); // 데이터 등록일 (배송완료일 기준)
//            invoice_percent.setComcode("00");
//            invoice_percent.setTiercode(object.getTier_code());
//            invoice_percent.setDelivery_period("4"); // 배송기간
//            invoice_percent.setInvoice(object.getInvoice()); // 배송된 운송장 갯수
//            invoicePercentDao.save(invoice_percent);
//
//        }
//
//        // 배송율 통계 - 배송사별
//        // 등록일 +1일
//        System.out.println("transit date is reg_date + 1 ( delivery_period : 1 classified by com_code )");
//        List<InvoicePercentDTO> invoice_percent1DayList = invoiceDaoCustom.findDelivereyStaticOneDay(date_base, date_base_before);
//
//        for (InvoicePercentDTO object : invoice_percent1DayList) {
//
//            invoice_percent = new InvoicePercent();
//            invoice_percent.setRegdate(object.getReg_date()); // 데이터 등록일 (배송완료일 기준)
//            invoice_percent.setComcode(object.getCom_code());
//            invoice_percent.setTiercode(object.getTier_code());
//            invoice_percent.setDelivery_period("1"); // 배송기간
//            invoice_percent.setInvoice(object.getInvoice()); // 배송된 운송장 갯수
//            invoicePercentDao.save(invoice_percent);
//
//        }
//
//        // 배송율 통계 - 배송사별
//        // 등록일 +2일
//        System.out.println("transit date is reg_date + 2 ( delivery_period : 2 classified by com_code )");
//        List<InvoicePercentDTO> invoice_percent2DayList = invoiceDaoCustom.findDelivereyStaticOneDay(date_base, date_2before);
//
//        for (InvoicePercentDTO object : invoice_percent2DayList) {
//
//            invoice_percent = new InvoicePercent();
//            invoice_percent.setRegdate(object.getReg_date()); // 데이터 등록일 (배송완료일 기준)
//            invoice_percent.setComcode(object.getCom_code());
//            invoice_percent.setTiercode(object.getTier_code());
//            invoice_percent.setDelivery_period("2"); // 배송기간
//            invoice_percent.setInvoice(object.getInvoice()); // 배송된 운송장 갯수
//            invoicePercentDao.save(invoice_percent);
//
//        }
//
//        // 배송율 통계 - 배송사별
//        // 등록일 +3일
//        System.out.println("transit date is reg_date + 3 ( delivery_period : 3 classified by com_code )");
//        List<InvoicePercentDTO> invoice_percent3DayList = invoiceDaoCustom.findDelivereyStaticOneDay(date_base, date_3before);
//
//        for (InvoicePercentDTO object : invoice_percent3DayList) {
//
//            invoice_percent = new InvoicePercent();
//            invoice_percent.setRegdate(object.getReg_date()); // 데이터 등록일 (배송완료일 기준)
//            invoice_percent.setComcode(object.getCom_code());
//            invoice_percent.setTiercode(object.getTier_code());
//            invoice_percent.setDelivery_period("3"); // 배송기간
//            invoice_percent.setInvoice(object.getInvoice()); // 배송된 운송장 갯수
//            invoicePercentDao.save(invoice_percent);
//
//        }
//
//        // 배송율 통계 - 배송사별
//        // 등록일 +4일 이상
//        System.out.println("transit date is more than reg_date + 4 ( delivery_period : 4 classified by com_code )");
//        List<InvoicePercentDTO> invoice_percent4DayList = invoiceDaoCustom.findDelivereyStaticFourDay(date_base, date_4before);
//
//        for (InvoicePercentDTO object : invoice_percent4DayList) {
//
//            invoice_percent = new InvoicePercent();
//            invoice_percent.setRegdate(object.getReg_date()); // 데이터 등록일 (배송완료일 기준)
//            invoice_percent.setComcode(object.getCom_code());
//            invoice_percent.setTiercode(object.getTier_code());
//            invoice_percent.setDelivery_period("4"); // 배송기간
//            invoice_percent.setInvoice(object.getInvoice()); // 배송된 운송장 갯수
//            invoicePercentDao.save(invoice_percent);
//
//        }
        System.out.println("** COMPLETE TO SAVE FOR ALL!! **");
    }

}
