package com.example.aisling.finalprojectaislingstafford.DTO;

import java.io.Serializable;

/**
 * Created by Patrick on 05/05/2017.
 */

public class MoneyOwedDTO implements Serializable {
    int ubId,  billId;
    private String name, billType;
    double amount;


    public MoneyOwedDTO(int ubId, int billId, String name, String billType, double amount) {
        this.ubId = ubId;
        this.billId = billId;
        this.name = name;
        this.billType = billType;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getUbId() {
        return ubId;
    }

    public void setUbId(int ubId) {
        this.ubId = ubId;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    @Override
    public String toString() {
        return "MoneyOwedDTO{" +
                "name='" + name + '\'' +
                ", billType='" + billType + '\'' +
                ", amount=" + amount +
                ", ubId=" + ubId +
                ", billId=" + billId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MoneyOwedDTO that = (MoneyOwedDTO) o;

        if (Double.compare(that.amount, amount) != 0) return false;
        if (ubId != that.ubId) return false;
        if (billId != that.billId) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return billType != null ? billType.equals(that.billType) : that.billType == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = name != null ? name.hashCode() : 0;
        result = 31 * result + (billType != null ? billType.hashCode() : 0);
        temp = Double.doubleToLongBits(amount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + ubId;
        result = 31 * result + billId;
        return result;
    }
}
