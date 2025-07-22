package com.example.ecommercebackend.dto.product.order;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WebhookTrackUpdatedData {
    @JsonProperty("id")
    private String id;

    @JsonProperty("createdAt")
    private String createAt;

    @JsonProperty("updatedAt")
    private String updateAt;

    @JsonProperty("trackingNumber")
    private String trackingNumber;

    @JsonProperty("trackingUrl")
    private String trackingUrl;

    @JsonProperty("trackingStatus")
    private WebhookTrackingUpdatedStatus trackingStatus;

    public WebhookTrackUpdatedData(String id, String createAt, String updateAt, String trackingNumber, String trackingUrl, WebhookTrackingUpdatedStatus trackingStatus) {
        this.id = id;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.trackingNumber = trackingNumber;
        this.trackingUrl = trackingUrl;
        this.trackingStatus = trackingStatus;
    }

    public WebhookTrackUpdatedData() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public String getTrackingUrl() {
        return trackingUrl;
    }

    public void setTrackingUrl(String trackingUrl) {
        this.trackingUrl = trackingUrl;
    }

    public WebhookTrackingUpdatedStatus getTrackingStatus() {
        return trackingStatus;
    }

    public void setTrackingStatus(WebhookTrackingUpdatedStatus trackingStatus) {
        this.trackingStatus = trackingStatus;
    }

    public String getCreateAt() {
        return createAt;
    }

    public void setCreateAt(String createAt) {
        this.createAt = createAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }
}
