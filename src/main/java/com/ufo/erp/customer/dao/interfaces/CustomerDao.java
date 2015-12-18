package com.ufo.erp.customer.dao.interfaces;

import org.springframework.stereotype.Repository;

import com.ufo.erp.customer.entity.Customer;
import com.ufo.core.dao.BaseDao;

@Repository
public interface CustomerDao extends BaseDao<Customer, Integer> {

}
