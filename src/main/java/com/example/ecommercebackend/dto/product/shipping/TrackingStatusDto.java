package com.example.ecommercebackend.dto.product.shipping;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TrackingStatusDto {
    @JsonProperty("id")
    private String id;

    @JsonProperty("createdAt")
    private String createdAt;

    @JsonProperty("updatedAt")
    private String updatedAt;

    @JsonProperty("trackingStatusCode")
    private String trackingStatusCode;

    @JsonProperty("trackingSubStatusCode")
    private String trackingSubStatusCode;

    @JsonProperty("statusDetails")
    private String statusDetails;

    @JsonProperty("statusDate")
    private String statusDate;

    @JsonProperty("locationName")
    private String locationName;

    @JsonProperty("locationLat")
    private Double locationLat;

    @JsonProperty("locationLng")
    private Double locationLng;

    @JsonProperty("hash")
    private String hash;

    public TrackingStatusDto(String id, String createdAt, String updatedAt, String trackingStatusCode, String trackingSubStatusCode, String statusDetails, String statusDate, String locationName, Double locationLat, Double locationLng, String hash) {
        this.id = id;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.trackingStatusCode = trackingStatusCode;
        this.trackingSubStatusCode = trackingSubStatusCode;
        this.statusDetails = statusDetails;
        this.statusDate = statusDate;
        this.locationName = locationName;
        this.locationLat = locationLat;
        this.locationLng = locationLng;
        this.hash = hash;
    }

    public TrackingStatusDto() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
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

    public String getStatusDate() {
        return statusDate;
    }

    public void setStatusDate(String statusDate) {
        this.statusDate = statusDate;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Double getLocationLat() {
        return locationLat;
    }

    public void setLocationLat(Double locationLat) {
        this.locationLat = locationLat;
    }

    public Double getLocationLng() {
        return locationLng;
    }

    public void setLocationLng(Double locationLng) {
        this.locationLng = locationLng;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
