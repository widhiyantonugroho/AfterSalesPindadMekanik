package com.pindad.aftersalespindadmekanik.Modul;

import com.google.gson.annotations.SerializedName;

/**
 * Created by WIDHIYANTO NUGROHO on 09/02/2018.
 */

public class Users {
    @SerializedName("id_customer")
    private String id_customer;

    public Users(){

    }
    public Users(String id_customer) {
        this.id_customer = id_customer;
    }

    public String getId_customer() {
        return id_customer;
    }

    public void setId_customer(String id_customer) {
        this.id_customer = id_customer;
    }

}
