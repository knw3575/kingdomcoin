package com.hilrara.kingdom.hibernate;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by kun7788 on 2016. 12. 18..
 */
@Entity
@Table(name = "wallet")
@Data
public class Wallet {
    @Id
    private Long userId;

    @Column(length = 100)
    private String address;
}
