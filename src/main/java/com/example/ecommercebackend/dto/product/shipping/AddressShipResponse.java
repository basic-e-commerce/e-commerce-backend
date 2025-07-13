package com.example.ecommercebackend.dto.product.shipping;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class AddressShipResponse {

    @JsonProperty("result")
    private boolean result;
    @JsonProperty("additionalMessage")
    private String additionalMessage;
    @JsonProperty("limit")
    private int limit;
    @JsonProperty("page")
    private int page;
    @JsonProperty("sort")
    private String sort;
    @JsonProperty("totalRows")
    private int totalRows;
    @JsonProperty("totalPages")
    private int totalPages;
    @JsonProperty("data")
    private List<AddressReceiptDto> data;

    public AddressShipResponse(boolean result, String additionalMessage, int limit, int page, String sort, int totalRows, int totalPages, List<AddressReceiptDto> data) {
        this.result = result;
        this.additionalMessage = additionalMessage;
        this.limit = limit;
        this.page = page;
        this.sort = sort;
        this.totalRows = totalRows;
        this.totalPages = totalPages;
        this.data = data;
    }

    public AddressShipResponse() {
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public String getAdditionalMessage() {
        return additionalMessage;
    }

    public void setAdditionalMessage(String additionalMessage) {
        this.additionalMessage = additionalMessage;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public List<AddressReceiptDto> getData() {
        return data;
    }

    public void setData(List<AddressReceiptDto> data) {
        this.data = data;
    }
}
