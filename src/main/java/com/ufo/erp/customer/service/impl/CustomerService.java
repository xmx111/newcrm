package com.ufo.erp.customer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ufo.erp.customer.dao.interfaces.CustomerDao;
import com.ufo.erp.customer.entity.Customer;
import com.ufo.erp.customer.service.interfaces.ICustomerService;
import com.ufo.core.dao.BaseDao;
import com.ufo.core.service.BaseSpringDataService;
import com.ufo.core.service.IBaseSpringDataService;

@Service
@Transactional
public class CustomerService extends BaseSpringDataService<Customer, Integer> implements ICustomerService {
    
    @Autowired
    private CustomerDao customerDao;

    @Override
    public BaseDao<Customer, Integer> getEntityDao() {
        return customerDao;
    }
}
