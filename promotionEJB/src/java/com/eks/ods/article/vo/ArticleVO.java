/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.eks.ods.article.vo;

/**
 *
 * @author Paresb
 */
public class ArticleVO {

    private String articleCode;
    private String articleDesc;
    private String mcCode;
    private String mcDesc;
    private String mrp;
    private RespCode respCode;
    private String msg;
    private String brand;
    private String brandDesc;
    private String seasonCode;

    public ArticleVO(String articleCode, String articleDesc, String mcCode, String mcDesc) {
        this.articleCode = articleCode;
        this.articleDesc = articleDesc;
        this.mcCode = mcCode;
        this.mcDesc = mcDesc;
    }

    public ArticleVO(RespCode respCode, String msg) {
        this.respCode = respCode;
        this.msg = msg;
    }

    public ArticleVO(String articleCode, String articleDesc, String mcCode) {
        this.articleCode = articleCode;
        this.articleDesc = articleDesc;
        this.mcCode = mcCode;
    }

    public ArticleVO(String articleCode, String articleDesc, String mcCode, String mcDesc, String brand, String seasonCode) {
        this.articleCode = articleCode;
        this.articleDesc = articleDesc;
        this.mcCode = mcCode;
        this.mcDesc = mcDesc;
        this.brand = brand;
        this.seasonCode = seasonCode;
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

    public ArticleVO() {
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public RespCode getRespCode() {
        return respCode;
    }

    public void setRespCode(RespCode respCode) {
        this.respCode = respCode;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSeasonCode() {
        return seasonCode;
    }

    public void setSeasonCode(String seasonCode) {
        this.seasonCode = seasonCode;
    }

    public String getBrandDesc() {
        return brandDesc;
    }

    public void setBrandDesc(String brandDesc) {
        this.brandDesc = brandDesc;
    }

    @Override
    public String toString() {
        return "ArticleVO{" + "articleCode=" + articleCode + ", articleDesc=" + articleDesc + ", mcCode=" + mcCode + ", mcDesc=" + mcDesc + '}';
    }

    public String getMrp() {
        return mrp;
    }

    public void setMrp(String mrp) {
        this.mrp = mrp;
    }
}
