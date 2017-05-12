package com.example.aisling.finalprojectaislingstafford.DTO;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Patrick on 12/05/2017.
 */

public class BillHistoryDTO implements Serializable {
    private String billName, billType;
    private double amount;
    private String datePaid;

    public BillHistoryDTO(String billName, String billType, double amount, String datePaid) {
        this.billName = billName;
        this.billType = billType;
        this.amount = amount;
        this.datePaid = datePaid;
    }

    public String getBillName() {
        return billName;
    }

    public void setBillName(String billName) {
        this.billName = billName;
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

    public String getDatePaid() {
        return datePaid;
    }

    public void setDatePaid(String datePaid) {
        this.datePaid = datePaid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BillHistoryDTO that = (BillHistoryDTO) o;

        if (Double.compare(that.amount, amount) != 0) return false;
        if (billName != null ? !billName.equals(that.billName) : that.billName != null)
            return false;
        if (billType != null ? !billType.equals(that.billType) : that.billType != null)
            return false;
        return datePaid != null ? datePaid.equals(that.datePaid) : that.datePaid == null;

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = billName != null ? billName.hashCode() : 0;
        result = 31 * result + (billType != null ? billType.hashCode() : 0);
        temp = Double.doubleToLongBits(amount);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (datePaid != null ? datePaid.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BillHistoryDTO{" +
                "billName='" + billName + '\'' +
                ", billType='" + billType + '\'' +
                ", amount=" + amount +
                ", datePaid=" + datePaid +
                '}';
    }
}
