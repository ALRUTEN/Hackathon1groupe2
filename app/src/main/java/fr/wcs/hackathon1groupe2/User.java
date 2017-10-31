package fr.wcs.hackathon1groupe2;

import java.util.ArrayList;
import java.util.List;

public class User {

    private String user_name;
    private String user_password;
    private List<Gift> listGift = new ArrayList<>();

    public User() {
    }

    public User(String user_name, String user_password) {
        this.user_name = user_name;
        this.user_password = user_password;
        this.listGift=null;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public List<Gift> getListGift() {return listGift;}

    public void setListGift(List<Gift> listGift) {this.listGift = listGift;}

    public void addListGift(Gift newGift) {this.listGift.add(newGift);}
}

