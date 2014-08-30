/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.fks.pwm.phase3;

import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.jws.WebService;

/**
 *
 * @author itadmin
 */
@Stateless
@LocalBean
@WebService
public class PromoStoreUploadService {

    public PromoStoreUploadResp submitPromoUsingStoreFile(PromoStoreUploadReq request) {
        return new PromoStoreUploadResp();
    }

 
}
