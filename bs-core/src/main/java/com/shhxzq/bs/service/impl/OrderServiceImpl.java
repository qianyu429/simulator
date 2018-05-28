package com.shhxzq.bs.service.impl;

import com.github.pagehelper.PageHelper;
import com.shhxzq.bs.constants.AppConstants;
import com.shhxzq.bs.model.Order;
import com.shhxzq.bs.model.Transaction;
import com.shhxzq.bs.service.OrderService;
import com.shhxzq.bs.util.DateUtil;
import com.shhxzq.bs.util.FileUtil;
import com.shhxzq.bs.util.StringUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by houjiagang on 2016/12/19.
 */
@Service
@Transactional
public class OrderServiceImpl extends BaseService<Order> implements OrderService {


    @Value("${app.dzpath}")
    private String dzPath;


    @Override
    public List<Order> findOrders(int pageNow, String orderType) {
        Example example = new Example(Order.class);

        Example.Criteria criteria = example.createCriteria();
        if (StringUtil.isNotEmpty(orderType)) {
            criteria.andEqualTo("orderType", orderType);
        }

        example.setOrderByClause("id desc");
        PageHelper.startPage(pageNow, AppConstants.PAGE_SIZE);
        return super.selectByExample(example);
    }

    @Override
    public Order findOrder(Long id) {
        return super.selectByPrimaryKey(id);
    }

    @Override
    public int updateOrder(Order order) {
        return super.updateByPrimaryKeySelective(order);
    }

    @Override
    public int deleteOrder(Long id) {
        return super.deleteByPrimaryKey(id);
    }

    @Override
    public int saveOrder(Order order) {
        return super.insertSelective(order);
    }

    @Override
    public String genFile(String orderType) {

        List<Order> orders = findFileOrders(orderType);

        String path = "download/cft/";
        String fileName = "90020000_" + "accountapplication" + "_" + DateUtil.format8Date(new Date()) + ".txt";
        if ("2".equals(orderType)) {
            fileName = "90020000_" + "tradeapplication" + "_" + DateUtil.format8Date(new Date()) + ".txt";
        }
        String head = null;
        try {
            head = buildFileHead(orderType, orders);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String rootPath = StringUtil.isEmpty(dzPath) ? AppConstants.APP_ROOT : dzPath;

        boolean flag = FileUtil.genFile(rootPath + path, fileName, head, "", orders);


        return fileName;
    }

    private List<Order> findFileOrders(String orderType) {
        Example example = new Example(Order.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtil.isNotEmpty(orderType)) {
            criteria.andEqualTo("orderType", orderType);
        }
        example.setOrderByClause("id desc");
        return super.selectByExample(example);
    }

    private String buildFileHead(String orderType, List<Order> orders) throws Exception {
        String head = "总笔数:" + orders.size() + "\n" + "机构号|子机构号|合作方客户编号|客户类型|客户姓名|证件类型|证件号码|手机号码|银行卡号|附加信息|交易账号|应答码|应答信息";
        if ("2".equals(orderType)) {
            head = "总笔数:" + orders.size() + "|总申请金额" + getAmount(orders, "orderType", true, "2") + "|总申请份额" + getShare(orders, "orderType", true, "2") + "\n" + "机构号|子机构号|订单号|合作方客户编号|交易账号|产品代码|申请金额|申请份额|支付方式|银行卡号|银行通道|收费方式|业务码";

        }
        return head;

    }


    private String getAmount(List<Order> orders, String field, boolean eq, String... vals) throws Exception {
        Class clazz = Order.class;
        BigDecimal total = new BigDecimal(0);
        for (Order order : orders) {
            String val = String.valueOf(clazz.getMethod("get" + StringUtil.capitalize(field)).invoke(order));
            if (eq && StringUtil.in(val, vals)) {
                BigDecimal amount = new BigDecimal(order.getAmount());
                total = total.add(amount);
            } else if (!eq && !StringUtil.in(val, vals)) {
                BigDecimal amount = new BigDecimal(order.getAmount());
                total = total.add(amount);
            }
        }
        return String.valueOf(total);
    }

    private String getShare(List<Order> orders, String field, boolean eq, String... vals) throws Exception {
        Class clazz = Order.class;
        BigDecimal total = new BigDecimal(0);
        for (Order order : orders) {
            String val = String.valueOf(clazz.getMethod("get" + StringUtil.capitalize(field)).invoke(order));
            if (eq && StringUtil.in(val, vals)) {
                BigDecimal amount = new BigDecimal(order.getShare());
                total = total.add(amount);
            } else if (!eq && !StringUtil.in(val, vals)) {
                BigDecimal amount = new BigDecimal(order.getShare());
                total = total.add(amount);
            }
        }
        return String.valueOf(total);
    }
}

