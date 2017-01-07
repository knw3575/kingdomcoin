package com.hilrara.kingdom.controller;

import com.hilrara.kingdom.hibernate.Role;
import com.hilrara.kingdom.hibernate.User;
import com.hilrara.kingdom.hibernate.Wallet;
import com.hilrara.kingdom.provider.KingdomRpcProvider;
import com.hilrara.kingdom.repository.UserRepository;
import com.hilrara.kingdom.repository.WalletRepository;
import edu.emory.mathcs.backport.java.util.Collections;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Created by kun7788 on 16. 7. 8..
 */
@Slf4j
@Controller
@RequestMapping("/")
public class RouteController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    KingdomRpcProvider kingdomRpcProvider;

    @Autowired
    WalletRepository walletRepository;

    @Transactional
    @RequestMapping("/")
    public ModelAndView index(@RequestParam(name="regist", defaultValue = "") String regist
            , @RequestParam Optional<String> error
            , @RequestParam Optional<String> account
            , @ModelAttribute User user) {
        ModelAndView mvn = new ModelAndView("index");
        mvn.addObject("user", user);

        if (user != null) {
            Wallet wallet = walletRepository.findOne(user.getId());
            mvn.addObject("wallet", wallet);
            mvn.addObject("balance", kingdomRpcProvider.getbalance(wallet.getUserId().toString()));
            List<Map<String, Object>> listtransactions = kingdomRpcProvider.listtransactions(wallet.getUserId().toString(), 1000);
            Collections.reverse(listtransactions);
            mvn.addObject("transactions", listtransactions);
        }
        return mvn;
    }

    @Transactional
    @RequestMapping("/regist")
    public String regist(@RequestParam("userid") String userid, @RequestParam("password") String password) {
        Optional<User> user = userRepository.findOneByEmail(userid);
        if (!user.isPresent()) {
            User _user = new User();
            _user.setEmail(userid);
            _user.setPasswordHash(new BCryptPasswordEncoder().encode(password));
            _user.setRole(Role.USER);
            userRepository.save(_user);


            String address = kingdomRpcProvider.getaccountaddress(_user.getId().toString());
            if (address != null) {
                Wallet wallet = new Wallet();
                wallet.setUserId(_user.getId());
                wallet.setAddress(address);
                walletRepository.save(wallet);
            }
        }
        return "redirect:/";
    }

    @RequestMapping("/send")
    public String send() {
        return "send";
    }

    @Transactional
    @RequestMapping("/dosend")
    public String doSend(@ModelAttribute User user, @RequestParam("address") String address, @RequestParam("amount") Double amount) {
        String tx = "";
        if (amount > 0) {
            tx = kingdomRpcProvider.sendfrom(user.getId().toString(), address, amount);
        }
        return "redirect:/?tx="+tx;
    }
}
