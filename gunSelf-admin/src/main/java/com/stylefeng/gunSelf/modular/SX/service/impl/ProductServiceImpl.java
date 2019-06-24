package com.stylefeng.gunSelf.modular.SX.service.impl;

import com.stylefeng.gunSelf.modular.system.model.Product;
import com.stylefeng.gunSelf.modular.system.dao.ProductMapper;
import com.stylefeng.gunSelf.modular.SX.service.IProductService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author stylezhang123
 * @since 2018-07-10
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

}
