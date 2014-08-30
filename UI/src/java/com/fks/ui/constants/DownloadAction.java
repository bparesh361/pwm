/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.ui.constants;

import com.opensymphony.xwork2.ActionSupport;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;

/**
 *
 * @author krutij
 */
public class DownloadAction extends ActionSupport implements ServletResponseAware, ServletRequestAware {

    public static final Logger logger = Logger.getLogger(DownloadAction.class.getName());
    private String listData;
    private String path;
    private HttpServletResponse response;
    private HttpServletRequest request;

    public String getListData() {
        return listData;
    }

    public void setListData(String listData) {
        this.listData = listData;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String execute() throws Exception {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void downloadMultiplePromoTemplateFile() {
        logger.info("------------- Inside Downloading template file");
        try {
            FileInputStream fin = null;
            DataInputStream din = null;
            File file = null;
            byte[] buffer = null;
            try {
                buffer = new byte[1024];

                String filePath = PromotionPropertyUtil.getPropertyString(PropertyEnum.SUB_PROMOTION_TEMPLATE_FILE_PATH);
                file = new File(filePath);
                if (file.exists()) {
                    System.out.println("----------- Template File Found.");
                    fin = new FileInputStream(file);
                    din = new DataInputStream(fin);
                    logger.info("Din..." + din);
                    ServletOutputStream sout = response.getOutputStream();
                    response.setContentType("application/vnd.ms-excel");
                    response.setContentLength((int) file.length());
                    String contentDisposition = " :attachment;";
                    response.setHeader("Content-Disposition", String.valueOf((new StringBuffer(String.valueOf(contentDisposition))).append("filename=").append(file.getName())));

                    while (din != null && din.read(buffer) != -1) {
                        sout.write(buffer);
                    }
                } else {
                    System.out.println("################### No Temmplate File Exist.");
                }

            } catch (IOException ex) {
                logger.fatal("Exception generated while downoading file : Exception is " + ex.getLocalizedMessage());
                throw new FileNotFoundException("File Not Found: " + path);
            } finally {
                if (din != null) {
                    din.close();
                    din = null;
                }

                if (fin != null) {
                    fin.close();
                    fin = null;
                }

                buffer = null;
                file = null;
            }


        } catch (Exception ex) {
            System.out.println("----------- Error downloadExcel() : " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void downloadMultiplePromoRequestFile() {
        logger.info("------------- Inside Downloading template file");
        try {
            if (getPath() != null) {

                FileInputStream fin = null;
                DataInputStream din = null;
                File file = null;
                byte[] buffer = null;
                try {
                    buffer = new byte[1024];

                    file = new File(getPath());
                    fin = new FileInputStream(file);
                    din = new DataInputStream(fin);
                    logger.info("Din..." + din);
                    ServletOutputStream sout = response.getOutputStream();
                    response.setContentType("application/vnd.ms-excel");
                    response.setContentLength((int) file.length());
                    String contentDisposition = " :attachment;";
                    response.setHeader("Content-Disposition", String.valueOf((new StringBuffer(String.valueOf(contentDisposition))).append("filename=").append(file.getName())));

                    while (din != null && din.read(buffer) != -1) {
                        sout.write(buffer);
                    }

                } catch (IOException ex) {
                    logger.fatal("Exception generated while downoading file : Exception is " + ex.getLocalizedMessage());
                    throw new FileNotFoundException("File Not Found: " + path);
                } finally {
                    if (din != null) {
                        din.close();
                        din = null;
                    }

                    if (fin != null) {
                        fin.close();
                        fin = null;
                    }

                    buffer = null;
                    file = null;
                }

            }
        } catch (Exception ex) {
            System.out.println("----------- Error downloadExcel() : " + ex.getMessage());
            ex.printStackTrace();
        }



    }

    public void downloadExcelfile() {
        try {
            /*
            
            response.setContentLength((int) outputFile.length());
            String contentDisposition = " :attachment;";
            response.setHeader("Content-Disposition", String.valueOf((new StringBuffer(String.valueOf(contentDisposition))).append("filename=").append(outputFile.getName())));
             */
            if (getPath() != null) {

                FileInputStream fin = null;
                DataInputStream din = null;
                File file = null;
                byte[] buffer = null;
                try {
                    buffer = new byte[1024];

                    file = new File(getPath());
                    fin = new FileInputStream(file);
                    din = new DataInputStream(fin);
                    logger.info("Din..." + din);
                    ServletOutputStream sout = response.getOutputStream();
                    response.setContentType("application/vnd.ms-excel");
                    response.setContentLength((int) file.length());
                    String contentDisposition = " :attachment;";
                    response.setHeader("Content-Disposition", String.valueOf((new StringBuffer(String.valueOf(contentDisposition))).append("filename=").append(file.getName())));

                    while (din != null && din.read(buffer) != -1) {
                        sout.write(buffer);
                    }

                } catch (IOException ex) {
                    logger.fatal("Exception generated while downoading file : Exception is " + ex.getLocalizedMessage());
                    throw new FileNotFoundException("File Not Found: " + path);
                } finally {
                    if (din != null) {
                        din.close();
                        din = null;
                    }

                    if (fin != null) {
                        fin.close();
                        fin = null;
                    }

                    buffer = null;
                    file = null;
                }

            }
        } catch (Exception ex) {
            System.out.println("----------- Error downloadExcel() : " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public void downloadfile() throws Exception {
        if (getPath() != null) {

            FileInputStream fin = null;
            DataInputStream din = null;
            File file = null;
            byte[] buffer = null;
            try {
                buffer = new byte[1024];

                file = new File(getPath());
                fin = new FileInputStream(file);
                din = new DataInputStream(fin);
                logger.info("Din..." + din);
                ServletOutputStream sout = response.getOutputStream();
                response.setContentType("application/txt");
                response.setContentLength((int) file.length());
                response.setHeader("Content-Disposition", "attachment;filename=\"" + PromotionUtil.getFileNameWithoutSpace(file.getName()) + "\"");
                while (din != null && din.read(buffer) != -1) {
                    sout.write(buffer);
                }

            } catch (IOException ex) {
                logger.fatal("Exception generated while downoading file : Exception is " + ex.getLocalizedMessage());
                throw new FileNotFoundException("File Not Found: " + path);
            } finally {
                if (din != null) {
                    din.close();
                    din = null;
                }

                if (fin != null) {
                    fin.close();
                    fin = null;
                }

                buffer = null;
                file = null;
            }

        }
    }

    @Override
    public void setServletResponse(HttpServletResponse hsr) {
        this.response = hsr;
    }

    @Override
    public void setServletRequest(HttpServletRequest hsr) {
        this.request = hsr;
    }
}
