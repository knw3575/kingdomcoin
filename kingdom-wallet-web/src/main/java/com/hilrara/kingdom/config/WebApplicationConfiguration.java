package com.hilrara.kingdom.config;

import com.googlecode.jsonrpc4j.ProxyUtil;
import com.hilrara.kingdom.provider.KingdomRpcProvider;
import com.hilrara.kingdom.utils.JsonRpcHttpClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.net.MalformedURLException;

/**
 * Created by kun7788 on 2016. 8. 3..
 */
@Configuration
public class WebApplicationConfiguration {
    @Bean
    public KingdomRpcProvider kingdomRpcProvider() throws MalformedURLException {
        KingdomRpcProvider kingdomRpcProvider = ProxyUtil.createClientProxy(
                getClass().getClassLoader(),
                KingdomRpcProvider.class,
                JsonRpcHttpClientFactory.genJsonRpcHttpClient(
                        "http://kingdomcoinrpc:CyLWtAfE11dZawVUChSrKG1ADAXCPJ3KYFGcEAivTZJk@115.68.73.69:9382"
                ));
        return kingdomRpcProvider;
    }
}