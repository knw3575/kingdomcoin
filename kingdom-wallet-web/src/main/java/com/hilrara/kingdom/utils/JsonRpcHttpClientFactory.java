package com.hilrara.kingdom.utils;

import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import sun.misc.BASE64Encoder;

import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tommy on 2016. 3. 18..
 */
public class JsonRpcHttpClientFactory {

    public static JsonRpcHttpClient genJsonRpcHttpClient(String url) throws MalformedURLException {
        Authenticator.setDefault(new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("kingdomcoinrpc",
                        "CyLWtAfE11dZawVUChSrKG1ADAXCPJ3KYFGcEAivTZJk".toCharArray());
            }
        });

        return new JsonRpcHttpClient(new URL("http://115.68.73.69:9382"));
    }
}
