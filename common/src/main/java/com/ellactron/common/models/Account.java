package com.ellactron.common.models;

import com.ellactron.common.forms.CredentialForm;

/**
 * Created by ji.wang on 2017-07-06.
 */
public class Account extends CredentialForm {
    String realm;

    public Account(){}
    public Account(String username, String password){
        super(username,password);
    }

    public String getRealm() {
        return realm;
    }

    public void setRealm(String realm) {
        this.realm = realm;
    }
}
