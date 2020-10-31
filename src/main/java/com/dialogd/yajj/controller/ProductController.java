package com.dialogd.yajj.controller;

import com.dialogd.yajj.common.Page;
import com.dialogd.yajj.common.ResultBean;
import com.dialogd.yajj.common.response.RespAddress;
import com.dialogd.yajj.common.response.RespProductDetail;
import com.dialogd.yajj.common.response.RespShowCart;
import com.dialogd.yajj.entity.*;
import com.dialogd.yajj.mapper.*;
import com.dialogd.yajj.service.IProductService;
import com.dialogd.yajj.utils.DoubleUtil;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Date: 2020/4/6
 */
@Controller
@RequestMapping("/product")
@Api(tags = "产品管理")
@Slf4j
public class ProductController {

    @Autowired
    private IProductService productService;

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private ProvinceMapper provinceMapper;

    @Autowired
    private CityMapper cityMapper;

    @Autowired
    private AreaMapper areaMapper;

    @ApiOperation(value = "产品前台分页", notes = "产品前台分页")
    @RequestMapping(value = "/show")
    public String allProduct(Integer type, Page page, Model model){
        if (null == type){
            type = 0;
        }
        log.info("ProductController show param={}",type);
        PageInfo<Product> pageInfo = productService.getPageProduct(type,page);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("type",type);
        model.addAttribute("url","product/show");
        return "front/shopping";
    }

    @ApiOperation(value = "产品后台分页", notes = "产品后台分页")
    @RequestMapping(value = "pageList")
    public String pageList(String name,Integer state,Page page,Model model,HttpServletRequest request){
        log.info("ProductController pageList param name={},state={}",name,state);
        User loginUser = (User) request.getSession().getAttribute("loginUser");
        if (null == loginUser){
            return "redirect:/home/login";
        }
        PageInfo<Product> pageInfo = productService.getConditionPage(name,state,page,loginUser);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("name",name);
        model.addAttribute("url","product/pageList");
        return "back/product/productList";
    }


    @ApiOperation(value = "产品详情", notes = "产品详情")
    @RequestMapping(value = "/detail",method = RequestMethod.GET)
    public String detail(int id,Model model){
        log.info("ProductController detail param={}",id);
        Product product = productService.selectByPrimaryKey((long) id);
        RespProductDetail productDetail = convertProVO(product);
        model.addAttribute("product",productDetail);
        return "front/shoppDetail";
    }

    private RespProductDetail convertProVO(Product product) {
        RespProductDetail detail = new RespProductDetail();
        detail.setId(product.getId());
        detail.setName(product.getName());
        detail.setPrice(DoubleUtil.formatDouble(product.getPrice(),100.0));
        detail.setSalePrice(DoubleUtil.formatDouble(product.getSalePrice(),100.0));
        detail.setImage(product.getImage());
        return detail;
    }

    @ApiOperation(value = "添加购物车", notes = "添加购物车")
    @RequestMapping(value = "addCart",method = RequestMethod.GET)
    @ResponseBody
    public ResultBean addCart(int id, int count, HttpServletRequest request){
        User loginUser = (User) request.getSession().getAttribute("loginUser");
        if (null == loginUser){
            return ResultBean.FAIL("请您先登录");
        }
        log.info("ProductController addCart param,product_id={},count={},user_id={}",id,count,loginUser.getId());
        ResultBean resultBean = productService.addCart(id, count, loginUser.getId());
        if (resultBean.getCode() == 200){
            List<Cart> cartList = cartMapper.queryCartByUserid(loginUser.getId());
            request.getSession().setAttribute("cartNum",cartList.size());
        }
        return resultBean;
    }

    @ApiOperation(value = "展示购物车信息", notes = "展示购物车信息")
    @RequestMapping(value = "showCart",method = RequestMethod.GET)
    public String showCart(HttpServletRequest request,Model model){
        User loginUser = (User) request.getSession().getAttribute("loginUser");
        log.info("ProductController showCart param user_id={}",loginUser.getId());
        List<Cart> list = cartMapper.queryCartByUserid(loginUser.getId());
        List<RespShowCart> cartList = new ArrayList<>();
        String totalPrice = "";
        if (null != list && 0 != list.size()){
            double total = 0.0;
            for (Cart cart : list) {
                RespShowCart showCart = new RespShowCart();
                showCart.setId(cart.getId());
                showCart.setCount(cart.getCount());
                showCart.setPrice(DoubleUtil.div(cart.getPrice(),100.0,2));
                showCart.setProductId(cart.getProductId());
                showCart.setProductName(cart.getProductName());
                showCart.setUserId(cart.getUserId());
                total += showCart.getPrice()*showCart.getCount();
                totalPrice = DoubleUtil.formatDouble(total);
                cartList.add(showCart);
            }

        }
        model.addAttribute("totalPrice",totalPrice);
        model.addAttribute("cartList",cartList);
        return "front/center/shopCart";
    }

    @ApiOperation(value = "删除购物车", notes = "删除购物车")
    @RequestMapping(value = "/delCart",method = RequestMethod.GET)
    @ResponseBody
    public ResultBean delCart(int id, HttpServletRequest request){
        log.info("ProductController delCart param id={}",id);
        User loginUser = (User) request.getSession().getAttribute("loginUser");
        ResultBean resultBean = productService.delCart(id);
        if (resultBean.getCode() == 200){
            List<Cart> cartList = cartMapper.queryCartByUserid(loginUser.getId());
            request.getSession().setAttribute("cartNum",cartList.size());
        }
        return resultBean;
    }

    @ApiOperation(value = "结算购物车", notes = "结算购物车")
    @GetMapping("settleCart")
    public String settleCart(@RequestParam("ids") List<Integer> ids,Model model,HttpServletRequest request){
        log.info("settleCart param ids={}",ids);
        //根据id查询购物车信息
        List<Cart> list = cartMapper.selectByIds(ids);
        List<RespShowCart> cartList = new ArrayList<>();
        StringBuilder allCartId = new StringBuilder();
        String totalPrice = "";
        if (null != list && 0 != list.size()){
            double total = 20.0;
            for (Cart cart : list) {
                RespShowCart showCart = new RespShowCart();
                showCart.setId(cart.getId());
                showCart.setCount(cart.getCount());
                showCart.setPrice(DoubleUtil.div(cart.getPrice(),100.0,2));
                showCart.setProductId(cart.getProductId());
                showCart.setProductName(cart.getProductName());
                showCart.setUserId(cart.getUserId());
                total += showCart.getPrice()*showCart.getCount();
                totalPrice = DoubleUtil.formatDouble(total);
                if (StringUtils.isEmpty(allCartId)){
                    allCartId.append(""+cart.getId());
                }else {
                    allCartId.append("_"+cart.getId());
                }
                cartList.add(showCart);
            }

        }
        //根绝登录信息查询地址信息
        User loginUser = (User) request.getSession().getAttribute("loginUser");
        if (null == loginUser){
            return "front/login";
        }
        List<Address> addressList = addressMapper.selectByUserId(loginUser.getId());
        List<RespAddress> addrList = new ArrayList<>();
        for (Address address : addressList) {
            RespAddress addr = new RespAddress();
            addr.setId(address.getId());
            addr.setReceiver(address.getReceiver());
            addr.setPhone(address.getPhone());
            addr.setIsDefault(address.getIsDefault());
            //TODO 地址，省市区+街道
            Province province = provinceMapper.selectByPrimaryKey(Long.parseLong(address.getProvinceId().toString()));
            City city = cityMapper.selectByPrimaryKey(Long.parseLong(address.getCityId().toString()));
            Area area = areaMapper.selectByPrimaryKey(Long.parseLong(address.getAreaId().toString()));
            addr.setAddress(province.getName()+city.getName()+"市"+area.getName()+address.getStreet());
            addr.setUserId(address.getUserId());
            addr.setTag(address.getTag());
            addrList.add(addr);
        }
        model.addAttribute("totalPrice",totalPrice);
        model.addAttribute("cartList",cartList);
        model.addAttribute("addressList",addrList);
        model.addAttribute("allCartId",allCartId);
        return "front/center/pay";
    }

    @ApiOperation(value = "后台下架产品", notes = "后台下架产品")
    @GetMapping(value = "deleteById")
    @ResponseBody
    public ResultBean deleteById(@RequestParam("id") int id){
        log.info("deleteByIdProduct param id={}",id);
        return productService.deleteById(id);
    }

    @ApiOperation(value = "批量下架产品", notes = "批量下架产品")
    @GetMapping("batchDelProduct")
    @ResponseBody
    public ResultBean batchDelProduct(@RequestParam("ids") List<Integer> ids){
        log.info("batchDelProduct param={}",ids);
        return productService.batchDelProduct(ids);
    }

    @ApiOperation(value = "修改产品信息回显", notes = "修改产品信息回显")
    @RequestMapping("toUpdate/{id}")
    public String toUpdate(@PathVariable("id") int id,Model model){
        log.info("toUpdateProduct param id={}",id);
        Product product = productService.selectByPrimaryKey((long) id);
        model.addAttribute("product",product);
        return "back/product/update";
    }

    @ApiOperation(value = "修改产品信息", notes = "修改产品信息")
    @PostMapping("/update")
    public String updateUser(Product product){
        log.info("back updateUser param product={}",product);
        productService.updateByPrimaryKeySelective(product);
        return "redirect:/product/pageList";
    }

    @ApiOperation(value = "判断商品名是否存在", notes = "判断商品名是否存在")
    @RequestMapping(value = "/isExistName",method = RequestMethod.GET)
    @ResponseBody
    public ResultBean isExistName(@RequestParam("name") String name){
        log.info("ProductController isExistName param={}",name);
        return productService.isExistName(name);
    }

    @ApiOperation(value = "添加产品页面", notes = "添加产品页面")
    @GetMapping("/toAdd")
    public String toAdd(){
        return "back/product/add";
    }

    @ApiOperation(value = "添加产品", notes = "添加产品")
    @PostMapping("/add")
    public String addProduct(Product product,HttpServletRequest request){
        log.info("back addProduct param product={}",product);
        User loginUser = (User) request.getSession().getAttribute("loginUser");
        product.setCreateTime(new Date());
        product.setUpdateTime(new Date());
        product.setPrice(product.getPrice()*100);
        product.setSalePrice(product.getSalePrice()*100);
        product.setUserId(loginUser.getId());
        product.setTypeId(5);
        product.setTypeName("客厅家具");
        productService.insertSelective(product);
        return "forward:/product/pageList";
    }
}
