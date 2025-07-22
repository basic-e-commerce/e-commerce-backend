package com.example.ecommercebackend.dto.product.order;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WebhookTrackingUpdatedStatus {
    @JsonProperty("id")
    private String id;

    @JsonProperty("createdAt")
    private String createAt;

    @JsonProperty("updatedAt")
    private String updateAt;

    @JsonProperty("trackingStatusCode")
    private String trackingStatusCode;

    @JsonProperty("trackingSubStatusCode")
    private String trackingSubStatusCode;

    @JsonProperty("statusDetails")
    private String statusDetails;

    @JsonProperty("locationName")
    private String locationName;



    public WebhookTrackingUpdatedStatus(String id, String createAt, String updateAt, String trackingStatusCode, String trackingSubStatusCode, String statusDetails, String locationName) {
        this.id = id;
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.trackingStatusCode = trackingStatusCode;
        this.trackingSubStatusCode = trackingSubStatusCode;
        this.statusDetails = statusDetails;
        this.locationName = locationName;
    }

    public WebhookTrackingUpdatedStatus() {
    }

    public String getTrackingStatusCode() {
        return trackingStatusCode;
    }

    public void setTrackingStatusCode(String trackingStatusCode) {
        this.trackingStatusCode = trackingStatusCode;
    }

    public String getTrackingSubStatusCode() {
        return trackingSubStatusCode;
    }

    public void setTrackingSubStatusCode(String trackingSubStatusCode) {
        this.trackingSubStatusCode = trackingSubStatusCode;
    }

    public String getStatusDetails() {
        return statusDetails;
    }

    public void setStatusDetails(String statusDetails) {
        this.statusDetails = statusDetails;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
