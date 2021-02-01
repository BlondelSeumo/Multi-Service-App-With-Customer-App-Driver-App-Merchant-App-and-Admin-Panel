package com.ourdevelops.ornids.models;

import io.realm.RealmObject;

/**
 * Created by Ourdevelops Team on 10/13/2019.
 */

public class FirebaseToken extends RealmObject {
    private String tokenId;

    public FirebaseToken(String tokenId) {
        this.tokenId = tokenId;
    }

    public FirebaseToken() {
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }
}
