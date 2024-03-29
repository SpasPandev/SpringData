package exam.model.entity;

import exam.model.enums.WarrantTypeEnum;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "laptops")
public class Laptop extends BaseEntity{

    private String macAddress;
    private Float cpuSpeed;
    private Integer ram;
    private Integer storage;
    private String description;
    private BigDecimal price;
    private WarrantTypeEnum warrantyType;
    private Shop shop;

    public Laptop() {
    }

    @Column(nullable = false, unique = true)
    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    @Column(nullable = false)
    public Float getCpuSpeed() {
        return cpuSpeed;
    }

    public void setCpuSpeed(Float cpuSpeed) {
        this.cpuSpeed = cpuSpeed;
    }

    @Column(nullable = false)
    public Integer getRam() {
        return ram;
    }

    public void setRam(Integer ram) {
        this.ram = ram;
    }

    @Column(nullable = false)
    public Integer getStorage() {
        return storage;
    }

    public void setStorage(Integer storage) {
        this.storage = storage;
    }

    @Column(columnDefinition = "TEXT", nullable = false)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(nullable = false)
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Enumerated
    @Column(nullable = false)
    public WarrantTypeEnum getWarrantyType() {
        return warrantyType;
    }

    public void setWarrantyType(WarrantTypeEnum warrantyType) {
        this.warrantyType = warrantyType;
    }

    @ManyToOne
    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }
}
