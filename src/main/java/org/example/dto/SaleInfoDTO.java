package org.example.dto;

public class SaleInfoDTO {
    private String country;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public ListPriceDTO getListPrice() {
        return listPrice;
    }

    public void setListPrice(ListPriceDTO listPrice) {
        this.listPrice = listPrice;
    }

    public RetailPriceDTO getRetailPrice() {
        return retailPrice;
    }

    public void setRetailPrice(RetailPriceDTO retailPrice) {
        this.retailPrice = retailPrice;
    }

    public String getBuyLink() {
        return buyLink;
    }

    public void setBuyLink(String buyLink) {
        this.buyLink = buyLink;
    }

    private ListPriceDTO listPrice;
    private RetailPriceDTO retailPrice;
    private String buyLink;

    @Override
    public String toString() {
        return "\n SaleInfoDTO{" +
                "\n country='" + country + '\'' +
                ", \n listPrice=" + listPrice +
                ", \n retailPrice=" + retailPrice +
                ", \n buyLink='" + buyLink + '\'' +
                '}';
    }
}
