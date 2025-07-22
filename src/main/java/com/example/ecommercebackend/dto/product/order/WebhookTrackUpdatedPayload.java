package com.example.ecommercebackend.dto.product.order;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WebhookTrackUpdatedPayload {
    @JsonProperty("event")
    private String event;

    @JsonProperty("metadata")
    private String metadata;

    @JsonProperty("data")
    private WebhookTrackUpdatedData data;

    public WebhookTrackUpdatedPayload(String event, String metadata, WebhookTrackUpdatedData data) {
        this.event = event;
        this.metadata = metadata;
        this.data = data;
    }

    public WebhookTrackUpdatedPayload() {
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getMetadata() {
        return metadata;
    }

    public void setMetadata(String metadata) {
        this.metadata = metadata;
    }

    public WebhookTrackUpdatedData getData() {
        return data;
    }

    public void setData(WebhookTrackUpdatedData data) {
        this.data = data;
    }
}
