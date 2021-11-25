package com.oagreport.services;

import oracle.jdbc.OracleTypes;
import org.hibernate.procedure.ProcedureOutputs;
import org.modelmapper.ModelMapper;
import org.springframework.data.jdbc.support.oracle.SqlReturnStruct;
import org.springframework.data.jpa.datatables.mapping.DataTablesInput;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.RequestScope;

import com.oagreport.domain.AccountDto;
import com.oagreport.domain.AgencyRequestDto;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.criteria.*;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Types;
import java.util.*;
import java.util.stream.Collectors;


@Service
@Transactional
@RequestScope
public class AuthorizationService {

    @PersistenceContext
    EntityManager entityManager;

   ModelMapper modelMapper;




    public List<AccountDto> GetBudgetAccount(AgencyRequestDto request){
        StoredProcedureQuery query = entityManager.createStoredProcedureQuery("BUDGET.GetBudget")
                .registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN)
                .registerStoredProcedureParameter(2, Long.class, ParameterMode.IN)
                .registerStoredProcedureParameter(3, Integer.class, ParameterMode.OUT)
                .registerStoredProcedureParameter(4, String.class, ParameterMode.OUT)
                .registerStoredProcedureParameter(5, void.class, ParameterMode.REF_CURSOR)
                .setParameter(1,request.getFiscalYearId())
                .setParameter(2,request.getAgencyId()
                );


        try {
            query.execute();
            Integer nStatus = (Integer) query.getOutputParameterValue(3);
            String nMessage = (String) query.getOutputParameterValue(4);
            Map<String, Object> result = new HashMap<>();
            List<Object[]> data=query.getResultList();
            if (nStatus==0)
            {
                List<AccountDto> items = data.stream().map(
                        o -> new AccountDto(
                                (BigDecimal) o[4],
                                (String) o[5],
                                (String) o[6] +"[" +(String) o[5]+"]" ,
                                (String) o[6] +"[" +(String) o[5]+"]"
                        ))
                        .collect(Collectors.toList());

                return items;
            }

        } finally {
            query.unwrap(ProcedureOutputs.class).release();
        }
        return null;
    }



}