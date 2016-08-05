package com.utk.user.helpman;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by user on 13-02-2016.
 */
public class User  implements Serializable {
    private static final long serialVersionUID = 0L;
    private String id;
    private String password;
    private String name;
    private String address;
    private Date dateOfRegistration;

    public User(String id, String passwd, String username, String addr, Date dor) {
        this.setId(id);
        this.setPassword(passwd);
        this.setName(username);
        this.setAddress(addr);
        this.setDateOfRegistration(dor);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDateOfRegistration() {
        return dateOfRegistration;
    }

    public void setDateOfRegistration(Date dateOfRegistration) {
        this.dateOfRegistration = dateOfRegistration;
    }
}
