package com.ellactron.common.rest;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by ji.wang on 2017-07-02.
 */
public class RegisterUserForm {
    Integer userId;
    public Integer getId(){return userId; }
    public void setId(Integer id) {this.userId = id; }

    @Pattern(regexp="(?<username>\\b[\\w.%-]+)@(?<domain>[-.\\w]+\\.(?<ext>[A-Za-z]{2,4}\\b))", message="Not a valid email address")
    @NotNull(message="Username must be input")
    String username;

    //@NotNull
    String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() { return password; }

    public void setPassword(String password) { this.password = password; }

    /**
     * Constructor
     */
    public RegisterUserForm(){this(null,null);}
    public RegisterUserForm(String username, String password) {
        this.setPassword(password);
        this.setUsername(username);
    }
}
