package com.stylefeng.gunSelf.modular.SX.service.impl;

import com.stylefeng.gunSelf.modular.system.model.Customer;
import com.stylefeng.gunSelf.modular.system.dao.CustomerMapper;
import com.stylefeng.gunSelf.modular.SX.service.ICustomerService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author stylezhang123
 * @since 2018-07-11
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements ICustomerService {

}
