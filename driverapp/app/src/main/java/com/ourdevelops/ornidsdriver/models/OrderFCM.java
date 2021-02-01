package com.ourdevelops.ornidsdriver.models;

import com.ourdevelops.ornidsdriver.json.fcm.FCMType;

import java.io.Serializable;

/**
 * Created by Ourdevelops Team on 19/10/2019.
 */
public class OrderFCM implements Serializable{
    public int type = FCMType.ORDER;
    public String driver_id;
    public String customer_id;
    public String transaction_id;
    public String response;
    public String desc;
    public String invoice;
    public String ordertime;
}
