/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.promo.service;

import com.fks.promo.common.Resp;
import com.fks.promo.master.vo.EmployeeVo;
import java.util.List;
import java.util.Map;

/**
 *
 * @author krutij
 */
public class RequestResp {

    private Resp resp;
//    private Map<String, List<ReqRespVO>> map;
//    private Map<String, String> maps;
    private List<ReqRespVO> list;

    public RequestResp(Resp resp, List<ReqRespVO> list) {
        this.resp = resp;
        this.list = list;
    }

    public List<ReqRespVO> getList() {
        return list;
    }

    public void setList(List<ReqRespVO> list) {
        this.list = list;
    }
    

//    public RequestResp(Resp resp, Map<String, String> maps) {
//        this.resp = resp;
//        this.maps = maps;
//    }

    

//    public Map<String, String> getMaps() {
//        return maps;
//    }
//
//    public void setMaps(Map<String, String> maps) {
//        this.maps = maps;
//    }

    public RequestResp(Resp resp) {
        this.resp = resp;
    }

//    public RequestResp(Resp resp, Map<String, List<ReqRespVO>> map) {
//        this.resp = resp;
//        this.map = map;
//    }

    public RequestResp() {
    }

//    public Map<String, List<ReqRespVO>> getMap() {
//        return map;
//    }
//
//    public void setMap(Map<String, List<ReqRespVO>> map) {
//        this.map = map;
//    }

    public Resp getResp() {
        return resp;
    }

    public void setResp(Resp resp) {
        this.resp = resp;
    }
}
