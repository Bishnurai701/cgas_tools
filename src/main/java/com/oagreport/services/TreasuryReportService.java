package com.oagreport.services;



import org.hibernate.procedure.ProcedureOutputs;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import com.oagreport.domain.ReportRequestModel;
import com.oagreport.domain.ServiceResponse;

import javax.persistence.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
@RequestScope
public class TreasuryReportService {

    @PersistenceContext
    EntityManager entityManager;

    public ServiceResponse GetMaLePa17Report(ReportRequestModel request)
    {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("ACCOUNT_TMS_REPORT.MaLePa17")
                .registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter(3, Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter(4, Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter(5, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(6, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(7, Integer.class, ParameterMode.OUT)
                .registerStoredProcedureParameter(8, String.class, ParameterMode.OUT)
                .registerStoredProcedureParameter(9, void.class, ParameterMode.REF_CURSOR)
                .setParameter(1,request.getFiscalYearId())
                .setParameter(2,request.getAgencyId())
                .setParameter(3,request.getAccountId())
                .setParameter(4,request.getMonthId())
                .setParameter(5,request.getDateFrom())
                .setParameter(6,request.getDateTo());

        try {
            query.execute();
            Integer nStatus = (Integer) query.getOutputParameterValue(7);
            String nMessage = (String) query.getOutputParameterValue(8);

            Map<String, Object> result = new HashMap<>();
            List<Object[]> data=query.getResultList();


            if (nStatus==0)
            {
                result.put("data", data);
            }



            StoredProcedureQuery query1 = entityManager.createStoredProcedureQuery("ACCOUNT_TMS_REPORT.MaLePa17_Summary")
                    .registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(2, Long.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(3, Long.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(4, Long.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(5, String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(6, String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(7, Integer.class, ParameterMode.OUT)
                    .registerStoredProcedureParameter(8, String.class, ParameterMode.OUT)
                    .registerStoredProcedureParameter(9, void.class, ParameterMode.REF_CURSOR)
                    .setParameter(1,request.getFiscalYearId())
                    .setParameter(2,request.getAgencyId())
                    .setParameter(3,request.getAccountId())
                    .setParameter(4,request.getMonthId())
                    .setParameter(5,request.getDateFrom())
                    .setParameter(6,request.getDateTo());

                query1.execute();
                Integer nStatus1 = (Integer) query1.getOutputParameterValue(7);
                String nMessage1 = (String) query1.getOutputParameterValue(8);
                List<Object[]> summary=query1.getResultList();

                result.put("status", nStatus);
                result.put("message", nMessage);
                result.put("summary", summary);


                ServiceResponse response=new ServiceResponse(true,nMessage,result);
            return response;
        } finally {
            query.unwrap(ProcedureOutputs.class).release();
        }
    }

    public ServiceResponse GetMaLePa22Report(ReportRequestModel request)
    {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("ACCOUNT_TMS_REPORT.MaLePa22")
                .registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter(3, Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter(4, Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter(5, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(6, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(7, Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter(8, Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter(9, Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter(10, Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter(11, Integer.class, ParameterMode.OUT)
                .registerStoredProcedureParameter(12, String.class, ParameterMode.OUT)
                .registerStoredProcedureParameter(13, void.class, ParameterMode.REF_CURSOR)
//                .registerStoredProcedureParameter(10, void.class, ParameterMode.REF_CURSOR)
//                .registerStoredProcedureParameter(11, void.class, ParameterMode.REF_CURSOR)
                .setParameter(1,request.getFiscalYearId())
                .setParameter(2,request.getAgencyId())
                .setParameter(3,request.getAccountId())
                .setParameter(4,request.getMonthId())
                .setParameter(5,request.getDateFrom())
                .setParameter(6,request.getDateTo())
                .setParameter(7,request.getPayeeId())
                .setParameter(8,request.getHeadId())
                .setParameter(9,request.getLedgerTypeId())
                .setParameter(10,request.getActivityId());


        try {
            query.execute();
            Integer nStatus = (Integer) query.getOutputParameterValue(11);
            String nMessage = (String) query.getOutputParameterValue(12);

            Map<String, Object> result = new HashMap<>();
            List<Object[]> data=query.getResultList();

//            List<Object[]> title = (List<Object[]>) query.getOutputParameterValue(9);
//            List<Object[]> data = (List<Object[]>) query.getOutputParameterValue(10);
//            List<Object[]> summary = (List<Object[]>) query.getOutputParameterValue(11);

            if (nStatus==0)
            {
                //result.put("title", title);
                result.put("data", data);
                //result.put("summary", summary);
            }
            result.put("status", nStatus);
            result.put("message", nMessage);

            ServiceResponse response=new ServiceResponse(true,nMessage,result);
            return response;
        } finally {
            query.unwrap(ProcedureOutputs.class).release();
        }
    }

    public ServiceResponse GetMaLePa208Report(ReportRequestModel request)
    {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("ACCOUNT_TMS_REPORT.MaLePa208")
                .registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter(3, Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter(4, Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter(5, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(6, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(7, Integer.class, ParameterMode.OUT)
                .registerStoredProcedureParameter(8, String.class, ParameterMode.OUT)
                .registerStoredProcedureParameter(9, void.class, ParameterMode.REF_CURSOR)
                .setParameter(1,request.getFiscalYearId())
                .setParameter(2,request.getAgencyId())
                .setParameter(3,request.getAccountId())
                .setParameter(4,request.getMonthId())
                .setParameter(5,request.getDateFrom())
                .setParameter(6,request.getDateTo());

        try {
            query.execute();
            Integer nStatus = (Integer) query.getOutputParameterValue(7);
            String nMessage = (String) query.getOutputParameterValue(8);
            Map<String, Object> result = new HashMap<>();

            List<Object[]> data=query.getResultList();

            StoredProcedureQuery query1 = entityManager.createStoredProcedureQuery("ACCOUNT_TMS_REPORT.MaLePa208_SUMMARY")
                    .registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(2, Long.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(3, Long.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(4, Long.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(5, String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(6, String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(7, Integer.class, ParameterMode.OUT)
                    .registerStoredProcedureParameter(8, String.class, ParameterMode.OUT)
                    .registerStoredProcedureParameter(9, void.class, ParameterMode.REF_CURSOR)
                    .setParameter(1,request.getFiscalYearId())
                    .setParameter(2,request.getAgencyId())
                    .setParameter(3,request.getAccountId())
                    .setParameter(4,request.getMonthId())
                    .setParameter(5,request.getDateFrom())
                    .setParameter(6,request.getDateTo());

            query1.execute();

            Integer nStatus1 = (Integer) query1.getOutputParameterValue(7);
            List<Object[]> previous=query1.getResultList();

            if (nStatus==0)
            {
                result.put("data", data);
            }

            if (nStatus1==0)
            {
                result.put("summary", previous);
            }

            ServiceResponse response=new ServiceResponse(true,nMessage,result);
            return response;
        } finally {
            query.unwrap(ProcedureOutputs.class).release();
        }
    }

    public ServiceResponse GetMaLePa209Report(ReportRequestModel request)
    {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("ACCOUNT_TMS_REPORT.MaLePa209")
                .registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter(3, Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter(4, Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter(5, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(6, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(7, Integer.class, ParameterMode.OUT)
                .registerStoredProcedureParameter(8, String.class, ParameterMode.OUT)
                .registerStoredProcedureParameter(9, void.class, ParameterMode.REF_CURSOR)
                .setParameter(1,request.getFiscalYearId())
                .setParameter(2,request.getAgencyId())
                .setParameter(3,request.getAccountId())
                .setParameter(4,request.getMonthId())
                .setParameter(5,request.getDateFrom())
                .setParameter(6,request.getDateTo());

        try {
            query.execute();


            Integer nStatus = (Integer) query.getOutputParameterValue(7);
            String nMessage = (String) query.getOutputParameterValue(8);

            Map<String, Object> result = new HashMap<>();

            List<Object[]> data=query.getResultList();

            StoredProcedureQuery query1 = entityManager.createStoredProcedureQuery("ACCOUNT_TMS_REPORT.MaLePa209_SUMMARY")
                    .registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(2, Long.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(3, Long.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(4, Long.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(5, String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(6, String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(7, Integer.class, ParameterMode.OUT)
                    .registerStoredProcedureParameter(8, String.class, ParameterMode.OUT)
                    .registerStoredProcedureParameter(9, void.class, ParameterMode.REF_CURSOR)
                    .setParameter(1,request.getFiscalYearId())
                    .setParameter(2,request.getAgencyId())
                    .setParameter(3,request.getAccountId())
                    .setParameter(4,request.getMonthId())
                    .setParameter(5,request.getDateFrom())
                    .setParameter(6,request.getDateTo());

            query1.execute();


            Integer nStatus1 = (Integer) query1.getOutputParameterValue(7);
            List<Object[]> previous=query1.getResultList();

            if (nStatus==0)
            {
                result.put("data", data);
            }

            if (nStatus1==0)
            {
                result.put("previous", previous);
            }

            ServiceResponse response=new ServiceResponse(true,nMessage,result);
            return response;
        } finally {
            query.unwrap(ProcedureOutputs.class).release();
        }
    }

    public ServiceResponse GetMaLePa210Report(ReportRequestModel request)
    {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("ACCOUNT_TMS_REPORT.MaLePa210")
                .registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter(3, Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter(4, Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter(5, Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter(6, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(7, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(8, Integer.class, ParameterMode.OUT)
                .registerStoredProcedureParameter(9, String.class, ParameterMode.OUT)
                .registerStoredProcedureParameter(10, void.class, ParameterMode.REF_CURSOR)
                .setParameter(1,request.getFiscalYearId())
                .setParameter(2,request.getAgencyId())
                .setParameter(3,request.getAccountId())
                .setParameter(4,request.getMonthId())
                .setParameter(5,request.getTypeId())
                .setParameter(6,request.getDateFrom())
                .setParameter(7,request.getDateTo());

        try {
            query.execute();
            Integer nStatus = (Integer) query.getOutputParameterValue(8);
            String nMessage = (String) query.getOutputParameterValue(9);
            Map<String, Object> result = new HashMap<>();
            List<Object[]> data=query.getResultList();

            if (nStatus==0)
            {
                result.put("data", data);
            }

            ServiceResponse response=new ServiceResponse(true,nMessage,result);
            return response;
        } finally {
            query.unwrap(ProcedureOutputs.class).release();
        }
    }


    public ServiceResponse GetMaLePa211Report(ReportRequestModel request)
    {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("ACCOUNT_TMS_REPORT.MaLePa211")
                .registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter(3, Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter(4, Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter(5, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(6, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(7, Integer.class, ParameterMode.OUT)
                .registerStoredProcedureParameter(8, String.class, ParameterMode.OUT)
                .registerStoredProcedureParameter(9, void.class, ParameterMode.REF_CURSOR)
                .setParameter(1,request.getFiscalYearId())
                .setParameter(2,request.getAgencyId())
                .setParameter(3,request.getAccountId())
                .setParameter(4,request.getMonthId())
                .setParameter(5,request.getDateFrom())
                .setParameter(6,request.getDateTo());

        try {
            query.execute();
            Integer nStatus = (Integer) query.getOutputParameterValue(7);
            String nMessage = (String) query.getOutputParameterValue(8);

            Map<String, Object> result = new HashMap<>();
            List<Object[]> data=query.getResultList();
            if (nStatus==0)
            {
                result.put("data", data);
            }

            ServiceResponse response=new ServiceResponse(true,nMessage,result);
            return response;
        } finally {
            query.unwrap(ProcedureOutputs.class).release();
        }
    }

    public ServiceResponse GetMaLePa213Report(ReportRequestModel request)
    {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("ACCOUNT_TMS_REPORT.MaLePa213")
                .registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter(3, Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter(4, Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter(5, Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter(6, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(7, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(8, Integer.class, ParameterMode.OUT)
                .registerStoredProcedureParameter(9, String.class, ParameterMode.OUT)
                .registerStoredProcedureParameter(10, void.class, ParameterMode.REF_CURSOR)
                .setParameter(1,request.getFiscalYearId())
                .setParameter(2,request.getAgencyId())
                .setParameter(3,request.getAccountId())
                .setParameter(4,request.getMonthId())
                .setParameter(5,request.getTypeId())
                .setParameter(6,request.getDateFrom())
                .setParameter(7,request.getDateTo());

        try {
            query.execute();
            Integer nStatus = (Integer) query.getOutputParameterValue(8);
            String nMessage = (String) query.getOutputParameterValue(9);
            Map<String, Object> result = new HashMap<>();
            List<Object[]> data=query.getResultList();

            StoredProcedureQuery query1 = entityManager.createStoredProcedureQuery("ACCOUNT_TMS_REPORT.MaLePa213_SUMMARY")
                    .registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(2, Long.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(3, Long.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(4, Long.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(5, Long.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(6, String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(7, String.class, ParameterMode.IN)
                    .registerStoredProcedureParameter(8, Integer.class, ParameterMode.OUT)
                    .registerStoredProcedureParameter(9, String.class, ParameterMode.OUT)
                    .registerStoredProcedureParameter(10, void.class, ParameterMode.REF_CURSOR)
                    .setParameter(1,request.getFiscalYearId())
                    .setParameter(2,request.getAgencyId())
                    .setParameter(3,request.getAccountId())
                    .setParameter(4,request.getMonthId())
                    .setParameter(5,request.getTypeId())
                    .setParameter(6,request.getDateFrom())
                    .setParameter(7,request.getDateTo());

            query1.execute();


            Integer nStatus1 = (Integer) query1.getOutputParameterValue(8);
            List<Object[]> summary=query1.getResultList();

            if (nStatus==0)
            {
                result.put("data", data);
            }

            if (nStatus1==0)
            {
                result.put("summary", summary);
            }

            ServiceResponse response=new ServiceResponse(true,nMessage,result);
            return response;
        } finally {
            query.unwrap(ProcedureOutputs.class).release();
        }
    }
    public ServiceResponse GetMaLePa214Report(ReportRequestModel request)
    {
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("ACCOUNT_TMS_REPORT.MaLePa214")
                .registerStoredProcedureParameter(1, Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter(3, Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter(4, Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter(5, Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter(6, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(7, String.class, ParameterMode.IN)
                .registerStoredProcedureParameter(8, Integer.class, ParameterMode.OUT)
                .registerStoredProcedureParameter(9, String.class, ParameterMode.OUT)
                .registerStoredProcedureParameter(10, void.class, ParameterMode.REF_CURSOR)
                .setParameter(1,request.getFiscalYearId())
                .setParameter(2,request.getAgencyId())
                .setParameter(3,request.getAccountId())
                .setParameter(4,request.getMonthId())
                .setParameter(5,request.getTypeId())
                .setParameter(6,request.getDateFrom())
                .setParameter(7,request.getDateTo());

        try {
            query.execute();
            Integer nStatus = (Integer) query.getOutputParameterValue(8);
            String nMessage = (String) query.getOutputParameterValue(9);
            Map<String, Object> result = new HashMap<>();
            List<Object[]> data=query.getResultList();

            if (nStatus==0)
            {
                result.put("data", data);
                result.put("settleDuePeski","0");
                result.put("peskiKharcha","0");
            }

            ServiceResponse response=new ServiceResponse(true,nMessage,result);
            return response;
        } finally {
            query.unwrap(ProcedureOutputs.class).release();
        }
    }
}
