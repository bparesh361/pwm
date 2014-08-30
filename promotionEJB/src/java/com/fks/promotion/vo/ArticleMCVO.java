/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promotion.vo;

/**
 *
 * @author ajitn
 */
public class ArticleMCVO {

    private String articleCode;
    private String articleDesc;
    private String mcCode;
    private String mcDesc;
    private String brand;
    private String price;
    private String seasonCode;

    public ArticleMCVO(String articleCode, String articleDesc, String mcCode, String mcDesc, String brand, String price, String seasonCode) {
        this.articleCode = articleCode;
        this.articleDesc = articleDesc;
        this.mcCode = mcCode;
        this.mcDesc = mcDesc;
        this.brand = brand;
        this.price = price;
        this.seasonCode = seasonCode;
    }


    public ArticleMCVO() {
    }

    public ArticleMCVO(String articleCode, String articleDesc, String mcCode, String mcDesc) {
        this.articleCode = articleCode;
        this.articleDesc = articleDesc;
        this.mcCode = mcCode;
        this.mcDesc = mcDesc;
    }    

    public String getArticleCode() {
        return articleCode;
    }

    public void setArticleCode(String articleCode) {
        this.articleCode = articleCode;
    }

    public String getArticleDesc() {
        return articleDesc;
    }

    public void setArticleDesc(String articleDesc) {
        this.articleDesc = articleDesc;
    }

    public String getMcCode() {
        return mcCode;
    }

    public void setMcCode(String mcCode) {
        this.mcCode = mcCode;
    }

    public String getMcDesc() {
        return mcDesc;
    }

    public void setMcDesc(String mcDesc) {
        this.mcDesc = mcDesc;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getSeasonCode() {
        return seasonCode;
    }

    public void setSeasonCode(String seasonCode) {
        this.seasonCode = seasonCode;
    }
    
}
