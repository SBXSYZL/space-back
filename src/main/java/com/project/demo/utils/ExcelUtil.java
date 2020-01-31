package com.project.demo.utils;

import com.project.demo.error.BusinessException;
import com.project.demo.error.EmBusinessErr;
import javafx.util.Pair;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Auther: Yzl
 * @Date: 2019/11/2 10:58
 * @Description:
 */
public class ExcelUtil {
    private static DecimalFormat format = new DecimalFormat("#");

    public static List<UserImportModel> readExcel(MultipartFile file) throws BusinessException {
        Workbook workbook = null;
        //MultipartFile转HSSFWorkbook
        String filename = file.getOriginalFilename();
        String suffix = filename.substring(filename.indexOf(".") + 1);
        try {
            if (suffix.equals("xls")) {
                workbook = new HSSFWorkbook(new POIFSFileSystem(file.getInputStream()));
            } else {
                workbook = new XSSFWorkbook(OPCPackage.open(file.getInputStream()));
                workbook = (HSSFWorkbook) convertXSSFToHSSF((XSSFWorkbook) workbook, 4);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(EmBusinessErr.EXCEL_FORMAT_ERROR);
        }

        HSSFSheet sheet = ((HSSFWorkbook) workbook).getSheetAt(0);
        HSSFRow row = sheet.getRow(0);

        String userName = getCellContent(row.getCell(0));
        String position = getCellContent(row.getCell(1));
        String departmentName = getCellContent(row.getCell(2));
        String tel = getCellContent(row.getCell(3));
        if (!userName.equals("用户名") || !position.equals("职位") || !departmentName.equals("部门") || !tel.equals("手机号")) {
            throw new BusinessException(EmBusinessErr.EXCEL_FORMAT_ERROR);
        }

        List<UserImportModel> userImportModels = new ArrayList<>();
        int index = sheet.getLastRowNum();
        for (int i = 1; i <= index; i++) {
            row = sheet.getRow(i);
            userName = getCellContent(row.getCell(0));
            position = getCellContent(row.getCell(1));
            departmentName = getCellContent(row.getCell(2));
            tel = getCellContent(row.getCell(3));
            if (!checkPhoneNumber(tel)) {
                throw new BusinessException(EmBusinessErr.PHONENUMBER_FORMAT_ERROR);
            }
            UserImportModel userImportModel = new UserImportModel();
            userImportModel.setUserName(userName);
            userImportModel.setPosition(position);
            userImportModel.setDepartmentName(departmentName);
            userImportModel.setTel(tel);
            userImportModels.add(userImportModel);
        }
        return userImportModels;
    }

    public final static class UserImportModel {
        String userName;
        String position;
        String departmentName;
        String tel;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getDepartmentName() {
            return departmentName;
        }

        public void setDepartmentName(String departmentName) {
            this.departmentName = departmentName;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        @Override
        public String toString() {
            return "UserImportModel{" +
                    "userName='" + userName + '\'' +
                    ", role='" + position + '\'' +
                    ", groupName='" + departmentName + '\'' +
                    ", phoneNumber='" + tel + '\'' +
                    '}';
        }
    }

    private static String getCellContent(HSSFCell cell) throws BusinessException {
        if (cell == null) return null;
        String cellValue = null;
        if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
            cellValue = cell.getStringCellValue();
        } else if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
            cellValue = format.format(cell.getNumericCellValue());
        } else {
            throw new BusinessException(EmBusinessErr.EXCEL_FORMAT_ERROR);
        }

        return cellValue;
    }

    private static boolean checkPhoneNumber(String phoneNumber) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(phoneNumber);
        b = m.matches();
        return b;

    }

    public static HSSFWorkbook createExcel(List<Pair<String, String>> pairList) {
        String filename = "账号";
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        HSSFRow row = sheet.createRow(0);
        HSSFCell cell = row.createCell(0);
        cell.setCellValue("账号");
        cell = row.createCell(1);
        cell.setCellValue("密码");
        for (int i = 0; i < pairList.size(); i++) {
            row = sheet.createRow((i + 1));
            row.createCell(0);
            cell = row.createCell(0);
            cell.setCellValue(pairList.get(i).getKey());
            cell = row.createCell(1);
            cell.setCellValue(pairList.get(i).getValue());
        }
        workbook.setSheetName(0, filename);
        return workbook;
    }

    public static HSSFWorkbook convertXSSFToHSSF(XSSFWorkbook xssfWorkbook, Integer colNumber) {
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        for (int i = 0; i < xssfWorkbook.getNumberOfSheets(); i++) {
            HSSFSheet hssfSheet = hssfWorkbook.createSheet();
            XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(i);
            copySheets(xssfSheet, hssfSheet, colNumber);
        }
        return hssfWorkbook;
    }

    private static void copySheets(XSSFSheet source, HSSFSheet target, Integer colNumber) {
        for (int i = 0; i < source.getLastRowNum(); i++) {
            HSSFRow hssfRow = target.createRow(i);
            XSSFRow xssfRow = source.getRow(i);
            copyRow(xssfRow, hssfRow, colNumber);
        }
    }

    private static void copyRow(XSSFRow source, HSSFRow target, Integer colNumber) {
        for (int i = 0; i < colNumber; i++) {
            HSSFCell hssfCell = target.createCell(i);
            XSSFCell xssfCell = source.getCell(i);
            xssfCell.setCellType(Cell.CELL_TYPE_STRING);
            hssfCell.setCellValue(xssfCell.getRichStringCellValue().toString());
        }
    }

    public static void displayHSSFWorkbook(HSSFWorkbook hssfWorkbook) {
        HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(0);
        int index = hssfSheet.getLastRowNum();
        for (int j = 0; j < index; j++) {
            HSSFRow hssfRow = hssfSheet.getRow(j);
            for (int k = 0; k < 4; k++) {
                HSSFCell hssfCell = hssfRow.getCell(k);
                System.out.print(hssfCell.getStringCellValue() + '\t');
            }
            System.out.println();

        }
    }
}
