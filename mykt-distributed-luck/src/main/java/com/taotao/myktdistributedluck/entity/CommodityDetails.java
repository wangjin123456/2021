package com.taotao.myktdistributedluck.entity;

/**
 * @ClassName CommodityDetails
 * @Author 蚂蚁课堂余胜军 QQ644064779 www.mayikt.com
 * @Version V1.0
 **/
public class CommodityDetails {
    private Long id;
    private String name;
    private Long stock;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Long getStock() {
        return stock;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStock(Long stock) {
        this.stock = stock;
    }
}

