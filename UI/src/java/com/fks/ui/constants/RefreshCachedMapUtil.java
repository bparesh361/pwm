/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.constants;


import com.fks.promo.master.service.StoreVO;
import com.fks.ui.master.vo.StoreVoUI;
import java.util.List;

/**
 *
 * @author ajitn
 */
public class RefreshCachedMapUtil {

    public void refreshStoreMap() {
      
        // CacheMaster.formatSet.clear();
        CacheMaster.formatSet.clear();
        CacheMaster.StoreVOMap.clear();
        CacheMaster.StoreMap.clear();
        CacheMaster.HoStoreMap.clear();
        CacheMaster.ZoneStoreCodeMap.clear();
        CacheMaster.ZoneStoreMap.clear();


        List<com.fks.promo.master.service.StoreVO> listStoreVo = ServiceMaster.getOrganizationMasterService().getAllOrganizationDtl();
        StoreVoUI storeVoUI;
        if (listStoreVo.size() > 0 && !listStoreVo.isEmpty()) {
            for (StoreVO vo : listStoreVo) {
                if (vo.isIsStoreBlocked()== Boolean.FALSE) {
                    if (vo.getLocationID().toString().equals("3")) {
                        CacheMaster.formatSet.add(vo.getFormat());
                    }
                    storeVoUI = new StoreVoUI();
                    storeVoUI.setCity(vo.getCity());
                    storeVoUI.setFormat(vo.getFormat());
                    storeVoUI.setRegion(vo.getRegion());
                    storeVoUI.setState(vo.getState());
                    storeVoUI.setZoneName(vo.getZoneName());
                    storeVoUI.setZoneId(vo.getZoneId());
                    storeVoUI.setLocationID(vo.getLocationID());
                    storeVoUI.setLocationName(vo.getLocationName());
                    storeVoUI.setStoreClass(vo.getStoreClass());
                    storeVoUI.setStoreDesc(vo.getStoreDesc());
                    storeVoUI.setStoreID(vo.getStoreID());
                    CacheMaster.StoreVOMap.put(vo.getStoreID(), storeVoUI);
                    String storeDesc;

                    if (vo.getLocationID().toString().equalsIgnoreCase("2")) {
                        storeDesc = vo.getStoreID().concat(" : ").concat(vo.getStoreDesc());
                        CacheMaster.StoreMap.put(vo.getStoreID().toString(), storeDesc);
                    } else if (vo.getLocationID().toString().equals("1")) {                       
                        storeDesc = vo.getStoreID().concat(" : ").concat(vo.getStoreDesc());
                        CacheMaster.HoStoreMap.put(vo.getStoreID().toString(), storeDesc);
                           CacheMaster.ZoneStoreCodeMap.put(vo.getStoreID().toString(), storeDesc);
                    } else if (vo.getLocationID().toString().equalsIgnoreCase("3")) {                        
                        storeDesc = vo.getStoreID().concat(" : ").concat(vo.getStoreDesc());
                        CacheMaster.ZoneStoreCodeMap.put(vo.getStoreID().toString(), storeDesc);
                    }
                    CacheMaster.ZoneStoreMap.put("-1", "---- Select Zone Site ---- ");
                }
            }
        } else {
            CacheMaster.StoreMap.put("-1", "---- Select Site ---- ");
            CacheMaster.HoStoreMap.put("-1", "---- Select Ho Site ---- ");
            CacheMaster.ZoneStoreMap.put("-1", "---- Select Zone Site ---- ");
        }

    }
}
