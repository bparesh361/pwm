/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fks.promotion.vo;

import com.fks.promo.common.Resp;
import com.fks.promo.master.vo.StoreVO;
import com.fks.promo.service.CommonPromoVo;
import java.util.List;

/**
 *
 * @author krutij
 */
public class OrganizationDtlResp {
   // private List<MstZoneVO> zoneList;
     private List<CommonPromoVo> zoneList;
    private List<String> stateRegionCityList;
    //private List<StoreVO> storeVOList;
    private List<CommonPromoVo> storeVOList;
    private Resp resp;

    public OrganizationDtlResp(Resp resp) {
        this.resp = resp;
    }

    public OrganizationDtlResp( Resp resp,List<CommonPromoVo> zoneList) {
        this.zoneList = zoneList;
        this.resp = resp;
    }


    public OrganizationDtlResp(List<String> stateRegionCityList, Resp resp) {
        this.stateRegionCityList = stateRegionCityList;
        this.resp = resp;
    }

    public OrganizationDtlResp() {
        
    }

    public List<CommonPromoVo> getZoneList() {
        return zoneList;
    }

    public void setZoneList(List<CommonPromoVo> zoneList) {
        this.zoneList = zoneList;
    }

   

    public Resp getResp() {
        return resp;
    }

    public void setResp(Resp resp) {
        this.resp = resp;
    }

    public List<String> getStateRegionCityList() {
        return stateRegionCityList;
    }

    public void setStateRegionCityList(List<String> stateRegionCityList) {
        this.stateRegionCityList = stateRegionCityList;
    }

    public List<CommonPromoVo> getStoreVOList() {
        return storeVOList;
    }

    public void setStoreVOList(List<CommonPromoVo> storeVOList) {
        this.storeVOList = storeVOList;
    }


}
