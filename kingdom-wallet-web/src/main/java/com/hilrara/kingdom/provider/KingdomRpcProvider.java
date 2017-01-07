package com.hilrara.kingdom.provider;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by kun7788 on 2016. 8. 25..
 */
public interface KingdomRpcProvider {

    List<Map<String, Object>> listtransactions(String account, Integer count);
    String getaccountaddress(String account);
    List<String> getaddressesbyaccount(String account);
    Object getbalance(String account);
    String sendfrom(String fromaccount, String tobitcoinaddress, Double amount);
}