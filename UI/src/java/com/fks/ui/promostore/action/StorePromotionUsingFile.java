/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.promostore.action;

import com.fks.ui.common.action.ActionBase;
import com.fks.ui.constants.WebConstants;
import org.apache.log4j.Logger;
import java.io.File;

/**
 *
 * @author prayos
 */
public class StorePromotionUsingFile extends ActionBase {

   
    private static Logger logger = Logger.getLogger(StorePromotionUsingFile.class.getName());
    private File storePrmFileUpload;
    private File file;
    private String storePrmFileUploadFileName;
    String strUserID;

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    

   

    public String submitStorepromoUsingFile() {
        
        logger.info("======== StorepromoUsingFile Submit Promo FIle called =========== ");
        logger.info("# : "+getSessionMap().get(WebConstants.USER_NAME).toString() );
        strUserID = getSessionMap().get(WebConstants.USER_NAME).toString();
        logger.info("#USER : "+strUserID );

        try{
            if (null != storePrmFileUpload) {
                logger.info("#storePromoFileUpload.length() :"+storePrmFileUploadFileName.length());
                logger.info("#file name :"+storePrmFileUploadFileName);
                if (storePrmFileUpload.length() == 0) {
                    addActionError("Empty file can not be uploaded.");
                     return "error";
                }
            }else {
            logger.info("#file name :"+storePrmFileUploadFileName);
            return "success";
            }


            
                
            
            
            }catch(Exception e){
                logger.info("Exception in Store Promo ----- ");
            e.printStackTrace();
            return ERROR;
            }
            return "success";
    
    }

//    public String getStorePrmFileUpload() {
//        return storePrmFileUpload;
//    }
//
//    public void setStorePrmFileUpload(String storePrmFileUpload) {
//        this.storePrmFileUpload = storePrmFileUpload;
//    }
//  public File getStorePromoFileUploadFileName() {
//        return storePromoFileUploadFileName;
//    }
//
//    public void setStorePromoFileUploadFileName(String storePromoFileUploadFileName) {
//        this.storePromoFileUploadFileName = storePromoFileUploadFileName;
//    }


       public File getStorePrmFileUpload() {
        return storePrmFileUpload;
    }

    public void setStorePrmFileUpload(File storePrmFileUpload) {
        this.storePrmFileUpload = storePrmFileUpload;
    }

    public String getStorePrmFileUploadFileName() {
        return storePrmFileUploadFileName;
    }

    public void setStorePrmFileUploadFileName(String storePrmFileUploadFileName) {
        this.storePrmFileUploadFileName = storePrmFileUploadFileName;
    }



   
}
