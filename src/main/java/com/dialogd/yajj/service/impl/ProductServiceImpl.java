package com.dialogd.yajj.service.impl;

import com.dialogd.yajj.common.Page;
import com.dialogd.yajj.common.ResultBean;
import com.dialogd.yajj.dao.IBaseDao;
import com.dialogd.yajj.entity.Cart;
import com.dialogd.yajj.entity.Product;
import com.dialogd.yajj.entity.User;
import com.dialogd.yajj.mapper.CartMapper;
import com.dialogd.yajj.mapper.ProductMapper;
import com.dialogd.yajj.mapper.ProductTypeMapper;
import com.dialogd.yajj.mapper.UserMapper;
import com.dialogd.yajj.service.IProductService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Date: 2020/4/6
 */
@Service
public class ProductServiceImpl extends BaseServiceImpl<Product> implements IProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductTypeMapper productTypeMapper;

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public IBaseDao<Product> getDao() {
        return productMapper;
    }

    @Override
    public PageInfo<Product> getPageProduct(int type, Page page) {

        //type:  0-全部  1-家具  2-餐厨  3-灯饰  4-家纺
        List<Product> productList = null;
        if (type==0){
            PageHelper.startPage(page.getCurrentPage(),page.getPageSize());
            productList = productMapper.list();
        }else {
            List<Integer> typeIds = productTypeMapper.selectByType(type);
            PageHelper.startPage(page.getCurrentPage(),page.getPageSize());
            productList = productMapper.queryByType(typeIds);
        }

        return new PageInfo<>(productList);
    }

    @Override
    public ResultBean addCart(int productId, int count, long userId) {
        //1.判断当前登录到用户的商品是否存在购物车中
        Cart curCart = cartMapper.selectIsExistTwoId(userId,productId);
        int result = 0;
        if (null != curCart){
            //存在，更新数量count
            curCart.setCount(curCart.getCount()+count);
            curCart.setUpdateTime(new Date());
            result = cartMapper.updateByPrimaryKeySelective(curCart);
        }else {
            //不存在，根据商品id查询商品信息，插入Cart中
            Product product = productMapper.selectByPrimaryKey((long) productId);
            Cart cart = new Cart();
            cart.setCount(count);
            cart.setPrice(product.getSalePrice());
            cart.setProductId(product.getId());
            cart.setProductName(product.getName());
            cart.setUserId(userId);
            result = cartMapper.insertSelective(cart);
        }
        if (result > 0){
            return ResultBean.SUCCESS("加入购物车成功");
        }else {
            return ResultBean.FAIL("加入购物车失败");
        }

    }

    @Override
    public ResultBean delCart(int id) {
        int result = cartMapper.deleteByPrimaryKey((long) id);
        if (result > 0){
            return ResultBean.SUCCESS("删除成功");
        }
        return ResultBean.FAIL("删除失败");
    }

    @Override
    public PageInfo getConditionPage(String name,Integer state, Page page, User loginUser) {
        Long roleType = 0L;
        if (0 != loginUser.getRole()){
            roleType = loginUser.getId();
        }
        Map<String,Object> param = new HashMap<>();
        param.put("name",name);
        param.put("roleType",roleType);
        if (null != state && state != (-1)){
            param.put("state",state);
        }
        PageHelper.startPage(page.getCurrentPage(),page.getPageSize());
        List<Product> productList = productMapper.getConditionPage(param);
        return new PageInfo(productList);
    }

    @Override
    public ResultBean deleteById(int id) {
        //下架-将flag修改，假删除
        Product product = new Product();
        product.setId((long) id);
        product.setFlag(false);
        int result = productMapper.updateByPrimaryKeySelective(product);
        if (result > 0){
            return ResultBean.SUCCESS("下架成功!");
        }
        return ResultBean.FAIL("下架失败!");
    }

    @Override
    public ResultBean batchDelProduct(List<Integer> ids) {
        productMapper.batchDelProduct(ids);
        return ResultBean.SUCCESS("批量下架成功");
    }

    @Override
    public ResultBean isExistName(String name) {
        int result = productMapper.isExistName(name);
        if (result > 0){
            return ResultBean.FAIL("商品名已存在");
        }
        return ResultBean.SUCCESS();
    }
}
