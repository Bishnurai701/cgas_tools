package com.oagreport.services;



import java.util.*;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



import com.oagreport.domain.Agency;
import com.oagreport.domain.ServiceResponse;
import com.oagreport.domain.TreasuryOffice;
import com.oagreport.repository.TreasuryOfficeRepository;



@Service
@Transactional
public class ResourceService
{

    @Autowired 
    TreasuryOfficeRepository treasuryOfficeRepository;


    public ServiceResponse getTreasuryOffice()
    {
        
    
        Iterable<TreasuryOffice> treasuryOffices = null;
        Iterable<Agency> agencies = null;
    

      
            treasuryOffices = treasuryOfficeRepository.findAll();
     

       


        Map<String, Object> result = new HashMap<>();
        result.put("treasuryOffices", treasuryOffices);
        result.put("agencies",agencies);

        ServiceResponse response=new ServiceResponse(true,result);
        return response;
    }

    
}
