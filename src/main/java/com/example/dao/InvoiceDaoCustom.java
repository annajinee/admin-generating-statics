package com.example.dao;

import com.example.dto.*;
import com.example.model.*;

import java.util.List;

/**
 * Created by annakim on 2017. 3. 3..
 */
public interface InvoiceDaoCustom {

    /////////////// 배송건수 통계
    // 전체
    List<InvoiceDTO> findTierCodeAndInvoice(String date_base);

    InvoiceStaticDTO findStatic(String date_base, String date_base_before, String tier_code, String date_threemonth);

    // 배송사 별
    List<InvoiceAllDTO> findTierCodeAndInvoiceAndComcode(String date_base);

    InvoiceStaticDTO findStaticComcode(String date_base, String date_base_before, String tier_code, String com_code, String date_threemonth);

    /////////////// 미배송건수 통계
    // 전체
    InvoiceStaticDTO findNotDeliveredStatic(String date_base, String date_base_before, String tier_code, String date_threemonth);

    //배송사별
    InvoiceStaticDTO findNotDeliveredStaticComcode(String date_base, String date_base_before, String tier_code, String com_code, String date_threemonth);


    /////////////// 배송률 통계
    /////// 전체
    // 배송율 통계 데이터 (등록일+1 ~ +3일에 배송완료)
    List<InvoiceAlllPercentDTO> findDelivereyStaticOneDayAll(String date_base, String date);

    // 배송율 통계 데이터 (등록일+4일이상에 배송완료)
    List<InvoiceAlllPercentDTO> findDelivereyStaticFourDayAll(String date_base, String date);


    /////// 배송사 별
    // 배송율 통계 데이터 (등록일+1 ~ +3일에 배송완료)
    List<InvoicePercentDTO> findDelivereyStaticOneDay(String date_base, String date);

    // 배송율 통계 데이터 (등록일+4일이상에 배송완료)
    List<InvoicePercentDTO> findDelivereyStaticFourDay(String date_base, String date);


    //데이터 0 표현을 위한 Number Table
    List<InvoiceNumberDTO> findBasedList();


}
