package org.example.dto;

public class ItemDTO {
    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public VolumeInfoDTO getVolumeInfo() {
        return volumeInfo;
    }

    public void setVolumeInfoDTO(VolumeInfoDTO volumeInfo) {
        this.volumeInfo = volumeInfo;
    }

    private String kind;
    private String id;
    private VolumeInfoDTO volumeInfo;

    public SaleInfoDTO getSaleInfo() {
        return saleInfo;
    }

    public void setSaleInfo(SaleInfoDTO saleInfo) {
        this.saleInfo = saleInfo;
    }

    private SaleInfoDTO saleInfo;

    @Override
    public String toString() {
        return "id :" + this.id +
                volumeInfo.toString() + " " +
                saleInfo.toString();
    }
}
