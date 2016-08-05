package com.utk.user.helpman;

import java.io.Serializable;

/**
 * Created by user on 09-03-2016.
 */
public class Order implements Serializable {
    private Integer id;
    private String userId;
    private String partnerId;
    private String serviceRequested;
    private Integer requestTimestamp;
    private String qualityRating;
    private String priceRating;
    private String contactNumber;
    private String contactName;
    private String contactAddress;
    private Integer billedAmt;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return  serviceRequested + " : Rs. " + billedAmt;
    }

    public Integer getBilledAmt() {
        return billedAmt;
    }

    public void setBilledAmt(Integer billedAmt) {
        this.billedAmt = billedAmt;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPartnerId() {
        return partnerId;
    }

    public void setPartnerId(String partnerId) {
        this.partnerId = partnerId;
    }

    public String getServiceRequested() {
        return serviceRequested;
    }

    public void setServiceRequested(String serviceRequested) {
        this.serviceRequested = serviceRequested;
    }

    public Integer getRequestTimestamp() {
        return requestTimestamp;
    }

    public void setRequestTimestamp(Integer requestTimestamp) {
        this.requestTimestamp = requestTimestamp;
    }

    public String getQualityRating() {
        return qualityRating;
    }

    public void setQualityRating(String qualityRating) {
        this.qualityRating = qualityRating;
    }

    public String getPriceRating() {
        return priceRating;
    }

    public void setPriceRating(String priceRating) {
        this.priceRating = priceRating;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactAddress() {
        return contactAddress;
    }

    public void setContactAddress(String contactAddress) {
        this.contactAddress = contactAddress;
    }
}
