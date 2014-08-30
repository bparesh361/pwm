/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fks.reports.excel;

import com.fks.promo.common.CommonUtil;
import com.fks.promo.common.PromotionPropertyUtil;
import com.fks.promo.common.PropertyEnum;
import com.fks.promo.entity.MapuserMCHF1;
import com.fks.promo.entity.MapuserMCHF2;
import com.fks.promo.entity.MapuserMCHF3;
import com.fks.promo.entity.MstEmployee;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Collection;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author ajitn
 */
@Stateless
@LocalBean
public class UserMCHExcel {

    public static final int Emp_Code = 0;
    public static final int Emp_Name = 1;
    public static final int Location = 2;
    public static final int Zone = 3;
    public static final int Format = 4;
    public static final int Emp_Contact_No = 5;
    public static final int Role = 6;
    public static final int Profile = 7;
    public static final int MC_Code = 8;

    public String generateReport(List<MstEmployee> initiatorList, List<MstEmployee> l1List, List<MstEmployee> l2List) {
        String filePath = null;
        try {
            String fileName = "UserMCH_" + CommonUtil.getCurrentTimeInMiliSeconds() + ".xlsx";
            filePath = PromotionPropertyUtil.getPropertyString(PropertyEnum.USER_MCH_MAPPING_EXCEl) + fileName;

            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheetName = workbook.createSheet("User_MCH_Mapping");
            XSSFRow dataRow = null;
            XSSFCell dataCell = null;

            XSSFFont font = ExcelUtil.getHeaderFont(workbook);
            XSSFCellStyle styleHeader = ExcelUtil.getHeaderStyle(workbook, font);
            XSSFCellStyle styleCenter = ExcelUtil.getCellStyle(workbook);

            XSSFRow newrow0 = sheetName.createRow(0);
            XSSFCell dataCell0 = ExcelUtil.getCell(newrow0, 0);
            dataCell0.setCellValue("User_MCH_Mapping");
            dataCell0.setCellStyle(styleHeader);

            int headerRowNum = 2, rowNum = 2;

            XSSFRow headerdataRow = generateReportHeader(rowNum, dataRow, dataCell, sheetName, styleHeader);
            rowNum++;
            generateReportData(rowNum, dataRow, dataCell, sheetName, styleCenter, sheetName, styleHeader, initiatorList, l1List, l2List, headerRowNum, headerdataRow);

            //Resizing Columns
            for (int i = Emp_Code; i <= Profile; i++) {
                sheetName.autoSizeColumn(i);
            }

            File tempFileLoc = new File(filePath);
            FileOutputStream foStream = new FileOutputStream(tempFileLoc);
            workbook.write(foStream);
            foStream.close();

        } catch (Exception ex) {
            System.out.println("------- error : " + ex.getMessage());
            ex.printStackTrace();
        }
        return filePath;
    }

    private void generateReportData(int rowNum, XSSFRow dataRow, XSSFCell cell, XSSFSheet sheetName, XSSFCellStyle cellStyle, XSSFSheet headerSheetName, XSSFCellStyle headerCellStyle, List<MstEmployee> initiatorList, List<MstEmployee> l1List, List<MstEmployee> l2List, int headerRowNo, XSSFRow headerdataRow) {

        if (initiatorList != null && initiatorList.size() > 0) {
            System.out.println("-------- emp size : " + initiatorList.size());
            for (MstEmployee emp : initiatorList) {
                //New Row Creation
                dataRow = sheetName.createRow(rowNum);
                int mcColumnNum = MC_Code;

                if (emp.getEmpCode() != null) {
                    String emp_Code = emp.getEmpCode().toString();
                    ExcelUtil.fillCell(rowNum, Emp_Code, emp_Code, dataRow, cell, sheetName, cellStyle);
                }

                if (emp.getEmployeeName() != null) {
                    String emp_Name = emp.getEmployeeName();
                    ExcelUtil.fillCell(rowNum, Emp_Name, emp_Name, dataRow, cell, sheetName, cellStyle);
                }

                if (emp.getMstStore() != null) {
                    String Loc = emp.getMstStore().getMstLocation().getLocationName();
                    ExcelUtil.fillCell(rowNum, Location, Loc, dataRow, cell, sheetName, cellStyle);

                    String zon = emp.getMstStore().getMstZone().getZoneName();
                    ExcelUtil.fillCell(rowNum, Zone, zon, dataRow, cell, sheetName, cellStyle);

                    String form = emp.getMstStore().getFormatName();
                    ExcelUtil.fillCell(rowNum, Format, form, dataRow, cell, sheetName, cellStyle);
                }

                if (emp.getContactNo() != null) {
                    String contactNo = emp.getContactNo().toString();
                    ExcelUtil.fillCell(rowNum, Emp_Contact_No, contactNo, dataRow, cell, sheetName, cellStyle);
                }

                if (emp.getMstRole() != null) {
                    String rol = emp.getMstRole().getRoleName();
                    ExcelUtil.fillCell(rowNum, Role, rol, dataRow, cell, sheetName, cellStyle);
                }


                ExcelUtil.fillCell(rowNum, Profile, "F1", dataRow, cell, sheetName, cellStyle);

                Collection<MapuserMCHF1> f1MCsList = emp.getMapuserMCHF1Collection();
                if (f1MCsList != null && f1MCsList.size() > 0) {
                    int i = 1;
                    for (MapuserMCHF1 f1MCCode : f1MCsList) {

                        ExcelUtil.fillCell(headerRowNo, mcColumnNum, ("Mapped MC " + i), headerdataRow, cell, headerSheetName, headerCellStyle);
                        headerSheetName.autoSizeColumn(mcColumnNum);
                        i++;

                        String mcCode = f1MCCode.getMch().getMcCode();
                        ExcelUtil.fillCell(rowNum, mcColumnNum, mcCode, dataRow, cell, sheetName, cellStyle);
                        mcColumnNum++;
                    }
                }
                rowNum++;
            }
        }

        if (l1List != null && l1List.size() > 0) {
            System.out.println("-------- emp size : " + l1List.size());
            for (MstEmployee emp : l1List) {
                //New Row Creation
                dataRow = sheetName.createRow(rowNum);
                int mcColumnNum = MC_Code;

                if (emp.getEmpCode() != null) {
                    String emp_Code = emp.getEmpCode().toString();
                    ExcelUtil.fillCell(rowNum, Emp_Code, emp_Code, dataRow, cell, sheetName, cellStyle);
                }

                if (emp.getEmployeeName() != null) {
                    String emp_Name = emp.getEmployeeName();
                    ExcelUtil.fillCell(rowNum, Emp_Name, emp_Name, dataRow, cell, sheetName, cellStyle);
                }

                if (emp.getMstStore() != null) {
                    String Loc = emp.getMstStore().getMstLocation().getLocationName();
                    ExcelUtil.fillCell(rowNum, Location, Loc, dataRow, cell, sheetName, cellStyle);

                    String zon = emp.getMstStore().getMstZone().getZoneName();
                    ExcelUtil.fillCell(rowNum, Zone, zon, dataRow, cell, sheetName, cellStyle);

                    String form = emp.getMstStore().getFormatName();
                    ExcelUtil.fillCell(rowNum, Format, form, dataRow, cell, sheetName, cellStyle);
                }

                if (emp.getContactNo() != null) {
                    String contactNo = emp.getContactNo().toString();
                    ExcelUtil.fillCell(rowNum, Emp_Contact_No, contactNo, dataRow, cell, sheetName, cellStyle);
                }

                if (emp.getMstRole() != null) {
                    String rol = emp.getMstRole().getRoleName();
                    ExcelUtil.fillCell(rowNum, Role, rol, dataRow, cell, sheetName, cellStyle);
                }


                ExcelUtil.fillCell(rowNum, Profile, "F2", dataRow, cell, sheetName, cellStyle);

                Collection<MapuserMCHF2> f2MCsList = emp.getMapuserMCHF2Collection();
                if (f2MCsList != null && f2MCsList.size() > 0) {
                    int i = 1;
                    for (MapuserMCHF2 f2MCCode : f2MCsList) {

                        ExcelUtil.fillCell(headerRowNo, mcColumnNum, ("Mapped MC " + i), headerdataRow, cell, headerSheetName, headerCellStyle);
                        headerSheetName.autoSizeColumn(mcColumnNum);
                        i++;

                        String mcCode = f2MCCode.getMch().getMcCode();
                        ExcelUtil.fillCell(rowNum, mcColumnNum, mcCode, dataRow, cell, sheetName, cellStyle);
                        mcColumnNum++;
                    }
                }
                rowNum++;
            }
        }

        if (l2List != null && l2List.size() > 0) {
            System.out.println("-------- emp size : " + l2List.size());
            for (MstEmployee emp : l2List) {
                //New Row Creation
                dataRow = sheetName.createRow(rowNum);
                int mcColumnNum = MC_Code;

                if (emp.getEmpCode() != null) {
                    String emp_Code = emp.getEmpCode().toString();
                    ExcelUtil.fillCell(rowNum, Emp_Code, emp_Code, dataRow, cell, sheetName, cellStyle);
                }

                if (emp.getEmployeeName() != null) {
                    String emp_Name = emp.getEmployeeName();
                    ExcelUtil.fillCell(rowNum, Emp_Name, emp_Name, dataRow, cell, sheetName, cellStyle);
                }

                if (emp.getMstStore() != null) {
                    String Loc = emp.getMstStore().getMstLocation().getLocationName();
                    ExcelUtil.fillCell(rowNum, Location, Loc, dataRow, cell, sheetName, cellStyle);

                    String zon = emp.getMstStore().getMstZone().getZoneName();
                    ExcelUtil.fillCell(rowNum, Zone, zon, dataRow, cell, sheetName, cellStyle);

                    String form = emp.getMstStore().getFormatName();
                    ExcelUtil.fillCell(rowNum, Format, form, dataRow, cell, sheetName, cellStyle);
                }

                if (emp.getContactNo() != null) {
                    String contactNo = emp.getContactNo().toString();
                    ExcelUtil.fillCell(rowNum, Emp_Contact_No, contactNo, dataRow, cell, sheetName, cellStyle);
                }

                if (emp.getMstRole() != null) {
                    String rol = emp.getMstRole().getRoleName();
                    ExcelUtil.fillCell(rowNum, Role, rol, dataRow, cell, sheetName, cellStyle);
                }


                ExcelUtil.fillCell(rowNum, Profile, "F3", dataRow, cell, sheetName, cellStyle);

                Collection<MapuserMCHF3> f3MCsList = emp.getMapuserMCHF3Collection();
                if (f3MCsList != null && f3MCsList.size() > 0) {
                    int i = 1;
                    for (MapuserMCHF3 f3MCCode : f3MCsList) {

                        ExcelUtil.fillCell(headerRowNo, mcColumnNum, ("Mapped MC " + i), headerdataRow, cell, headerSheetName, headerCellStyle);
                        headerSheetName.autoSizeColumn(mcColumnNum);
                        i++;

                        String mcCode = f3MCCode.getMch().getMcCode();
                        ExcelUtil.fillCell(rowNum, mcColumnNum, mcCode, dataRow, cell, sheetName, cellStyle);
                        mcColumnNum++;
                    }
                }
                rowNum++;
            }
        }
    }

    private XSSFRow generateReportHeader(int rowNum, XSSFRow dataRow, XSSFCell cell, XSSFSheet hssfSheet, XSSFCellStyle cellStyle) {

        dataRow = hssfSheet.createRow(rowNum);

        ExcelUtil.fillCell(rowNum, Emp_Code, "Emp Code", dataRow, cell, hssfSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, Emp_Name, "Emp Name", dataRow, cell, hssfSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, Location, "Location", dataRow, cell, hssfSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, Zone, "Zone", dataRow, cell, hssfSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, Format, "Format", dataRow, cell, hssfSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, Emp_Contact_No, "Emp Contact No", dataRow, cell, hssfSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, Role, "Role", dataRow, cell, hssfSheet, cellStyle);
        ExcelUtil.fillCell(rowNum, Profile, "Profile", dataRow, cell, hssfSheet, cellStyle);

        return dataRow;
    }
}
