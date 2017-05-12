package com.example.aisling.finalprojectaislingstafford.DTO;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.sql.Blob;

/**
 * Created by Patrick on 01/05/2017.
 */

public class User implements Serializable, Comparable {
    private  int uId, addrId,groupId;
    private String uEmail,uPass,fName,lName, unique_id;
 //   private Blob profileImage;


    public User(int uId, String unique_id, int addrId, int groupId, String uEmail, String uPass, String fName, String lName) {
        this.uId = uId;
        this.addrId = addrId;
        this.groupId = groupId;
        this.uEmail = uEmail;
        this.uPass = uPass;
        this.fName = fName;
        this.lName = lName;
        this.unique_id = unique_id;
    }

    public int getuId() {
        return uId;
    }

    public void setuId(int uId) {
        this.uId = uId;
    }

    public int getAddrId() {
        return addrId;
    }

    public void setAddrId(int addrId) {
        this.addrId = addrId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getuEmail() {
        return uEmail;
    }

    public void setuEmail(String uEmail) {
        this.uEmail = uEmail;
    }

    public String getuPass() {
        return uPass;
    }

    public void setuPass(String uPass) {
        this.uPass = uPass;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getlName() {
        return lName;
    }

    public void setlName(String lName) {
        this.lName = lName;
    }

    public String getUnique_id() {
        return unique_id;
    }

    public void setUnique_id(String unique_id) {
        this.unique_id = unique_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (uId != user.uId) return false;
        if (addrId != user.addrId) return false;
        if (groupId != user.groupId) return false;
        if (uEmail != null ? !uEmail.equals(user.uEmail) : user.uEmail != null) return false;
        if (uPass != null ? !uPass.equals(user.uPass) : user.uPass != null) return false;
        if (fName != null ? !fName.equals(user.fName) : user.fName != null) return false;
        if (lName != null ? !lName.equals(user.lName) : user.lName != null) return false;
        return unique_id != null ? unique_id.equals(user.unique_id) : user.unique_id == null;

    }

    @Override
    public int hashCode() {
        int result = uId;
        result = 31 * result + addrId;
        result = 31 * result + groupId;
        result = 31 * result + (uEmail != null ? uEmail.hashCode() : 0);
        result = 31 * result + (uPass != null ? uPass.hashCode() : 0);
        result = 31 * result + (fName != null ? fName.hashCode() : 0);
        result = 31 * result + (lName != null ? lName.hashCode() : 0);
        result = 31 * result + (unique_id != null ? unique_id.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
                "uId=" + uId +
                ", addrId=" + addrId +
                ", groupId=" + groupId +
                ", uEmail='" + uEmail + '\'' +
                ", uPass='" + uPass + '\'' +
                ", fName='" + fName + '\'' +
                ", lName='" + lName + '\'' +
                ", unique_id='" + unique_id + '\'' +
                '}';
    }

    @Override
    public int compareTo(@NonNull Object o) {
        return 0;
    }
}
