package com.nepo.fevercat.ui.mine.utils;

import android.content.Context;
import android.os.Environment;
import android.util.SparseArray;

import com.nepo.fevercat.ui.mine.bean.MotionBean;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.PrintSetup;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.io.FileOutputStream;

import static com.umeng.socialize.utils.DeviceConfig.context;

public class WriteExcelUtils2 {
    private static WriteExcelUtils2 instance;
    private String fileName;
    public static WriteExcelUtils2 getInstance() {
        if (instance == null) {
            synchronized (WriteExcelUtils2.class) {
                if (instance == null) {
                    instance = new WriteExcelUtils2();
                }
            }
        }
        return instance;
    }

    /**
     * excle表格的后缀
     */
    public final static String SUFFIX = ".xls";
    public final static String DIR_FILE_NAME = "EXCLE_DIR";

    public final String FIRST_ROW_CONTENT = "运动量化表";
    public final String[] SENCOND_VALUES = {"姓名", "", "年龄", "", "诊断", "", "性别", ""};
    public final String[] THREE_VALUES = {"时间", "项目内容", "项目前温度", "项目前温度", "项目前脉", "项目后脉", "项目前血压", "项目后血压"};
    public final String[] FOUR = {"日期", "时间", "左", "右", "左", "右", "左", "右", "左", "右", "左高", "左低", "右高", "右低", "左高", "左低", "右高", "右低"};


    private Workbook createWorkbook() {
        return new HSSFWorkbook();
    }

    public void writeExecleToFile(Context context, int row, String[][] tableData) {
        //创建工作簿
        Workbook workbook = createWorkbook();
        SparseArray<CellStyle> cellStyles = creatCellStyles(workbook, row);
        //创建execl中的一个表
        Sheet sheet = workbook.createSheet();
        // setSheet(sheet);
        /**
         * 这里你会发现一个有趣的现象，SetColumnWidth的第二个参数要乘以256，这是怎么回事呢？其实，这个参数的单位是1/256个字符宽度，
         * 也就是说，这里是把B列的宽度设置为了100个字符。
         */
        //        sheet.setDefaultRowHeight((short) (20*20));
        //        sheet.setColumnWidth(1,15*256);
        //        sheet.setColumnWidth(2,35*256);
        //创建第一行
        Row headerRow = sheet.createRow(0);
        headerRow.setHeight((short) (30 * 20));
        // 设置第一行：48pt的字体的内容
        //        headerRow.setHeightInPoints(60);
        //创建第一行中第一单元格
        Cell cell = headerRow.createCell(0);
        cell.setCellValue(FIRST_ROW_CONTENT);
        cell.setCellStyle(cellStyles.get(0));
        mergingCells(sheet, CellRangeAddress.valueOf("$A$1:$S$1"));
        //创建第二行
        Row secondRow = sheet.createRow(1);
        //        secondRow.setHeightInPoints(45);
        createSecRow(sheet, secondRow, cellStyles.get(1));
        Row thirdRow = sheet.createRow(2);
        //创建第三行
        createThirdRow(sheet, thirdRow, cellStyles.get(2));
        //创建第四行
        Row fourRow = sheet.createRow(3);
        createForthRow(sheet, fourRow, cellStyles.get(3));
        //创建数据
        if (row >= 5) {
            for (int i = 4; i < row; i++) {
                createDataRow(sheet, sheet.createRow(i), cellStyles.get(i), tableData[i - 4]);
            }
        }
        writeFile(workbook, getFile(context,fileName));
    }

    private void createDataRow(Sheet sheet, Row row, CellStyle cellStyle, String[] data) {
        for (int i = 0; i < data.length; i++) {
            Cell cell = row.createCell(i);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(data[i]);
        }
    }

    private void createForthRow(Sheet sheet, Row fourRow, CellStyle cellStyle) {
        for (int i = 0; i < FOUR.length; i++) {
            int j = i;
            if (i >= 2) {
                j = i + 1;
            }
            Cell cell = fourRow.createCell(j);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(FOUR[i]);
        }
    }

    private void createThirdRow(Sheet sheet, Row threedRow, CellStyle cellStyle) {
        int sum = THREE_VALUES.length;
        int[] range = {1, 0, 1, 1, 1, 1, 3, 3};
        for (int i = 0; i < sum; i++) {
            int i1 = i + sumArray(range, i);
            Cell cell = threedRow.createCell(i1);
            cell.setCellStyle(cellStyle);
            if (range[i] != 0) {
                mergingCells(sheet, new CellRangeAddress(2, 2, i1, i1 + range[i]));
            }
            if (i == 1) {
                mergingCells(sheet, new CellRangeAddress(2, 3, i1, i1));
            }
            cell.setCellValue(THREE_VALUES[i]);
        }
    }

    private void createSecRow(Sheet sheet, Row secondRow, CellStyle cellStyle) {
        int sum = SENCOND_VALUES.length;
        int[] range = {0, 1, 0, 0, 0, 10, 0, 0};
        for (int i = 0, j = 0; i < sum; i++) {
            int i1 = i + sumArray(range, i);
            Cell cell = secondRow.createCell(i1);
            cell.setCellStyle(cellStyle);
            if (range[i] != 0) {
                mergingCells(sheet, new CellRangeAddress(1, 1, i1, i1 + range[i]));
            }
            cell.setCellValue(SENCOND_VALUES[i]);
        }
    }

    private int sumArray(int[] range, int i) {
        int sum = 0;
        for (int j = 0; j < i; j++) {
            sum += range[j];
        }
        return sum;
    }

    /**
     * 合并cell单元格
     * <p>
     * CellRangeAddress构造器中参数：
     * 参数1：first row(0-based)
     * 参数2：last row(0-based)
     * 参数3：first column(0-based)
     * 参数4：last column(0-based)
     *
     * @param sheet
     * @param cellRangeAddress
     */
    private static void mergingCells(Sheet sheet, CellRangeAddress cellRangeAddress) {
        sheet.addMergedRegion(cellRangeAddress);
    }

    /**
     * 设置Sheet表
     *
     * @param sheet
     */
    private static void setSheet(Sheet sheet) {
        // turn off gridlines（关闭网络线）
        sheet.setDisplayGridlines(false);
        sheet.setPrintGridlines(false);
        sheet.setFitToPage(true);
        sheet.setHorizontallyCenter(true);

        PrintSetup printSetup = sheet.getPrintSetup();
        printSetup.setLandscape(true);
        //只对HSSF需要用到的
        sheet.setAutobreaks(true);
        printSetup.setFitHeight((short) 1);
        printSetup.setFitWidth((short) 1);
    }

    /**
     * 获取指定文件
     *
     * @param context
     * @return
     */
    public static String getFile(Context context,String fileName) {
        File dirFile = getCacheFile(context, DIR_FILE_NAME);
        if (dirFile != null && !dirFile.exists()) {
            dirFile.mkdirs();
        }
        return dirFile.getAbsolutePath() + File.separator + fileName + SUFFIX;
    }

    /**
     * 创建各种不同单元格特征,根据个人需求不同而定 。
     *
     * @return
     */
    private static SparseArray<CellStyle> creatCellStyles(Workbook workbook, int row) {
        SparseArray<CellStyle> array = new SparseArray<>();
        //第一行的单元格特征
        CellStyle cellStyle0 = createBorderedStyle(workbook);
        Font font0 = creatFont(workbook);
        font0.setFontHeightInPoints((short) 22);
        font0.setBold(true);
        font0.setFontName("宋体");
        cellStyle0.setFont(font0);
        array.put(0, cellStyle0);

        //第二行的单元格特征
        CellStyle cellStyle1 = createBorderedStyle(workbook);
        Font font1 = creatFont(workbook);
        font1.setFontHeightInPoints((short) 12);
        font1.setFontName("宋体");
        cellStyle1.setFont(font1);
        cellStyle1.setWrapText(true);
        cellStyle1.setAlignment(HorizontalAlignment.CENTER);
        array.put(1, cellStyle1);
        //第三行的单元格特征
        array.put(2, cellStyle1);
        //第四行的单元格特征
        array.put(3, cellStyle1);
        if (row >= 5) {
            for (int i = 4; i < row; i++) {
                array.put(i, cellStyle1);
            }
        }
        return array;
    }

    /**
     * 设置表格的内容到四边的距离,表格四边的颜色
     * <p>
     * 对齐方式：
     * 水平： setAlignment();
     * 竖直：setVerticalAlignment()
     * <p>
     * 四边颜色：
     * 底边：  cellStyle.setBottomBorderColor()
     * <p>
     * 四边边距：
     * <p>
     * 填充：
     * <p>
     * 缩进一个字符：
     * setIndention()
     * <p>
     * 内容类型：
     * setDataFormat()
     *
     * @param workbook
     * @return
     */
    private static CellStyle createBorderedStyle(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        //对齐
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
/*     //重新设置单元格的四边颜色
    BorderStyle thin=BorderStyle.THIN;
    short blackColor_Index=IndexedColors.BLACK.getIndex();
    cellStyle.setBottomBorderColor(blackColor_Index);
    cellStyle.setBorderBottom(thin);
    cellStyle.setTopBorderColor(blackColor_Index);
    cellStyle.setBorderTop(thin);
    cellStyle.setRightBorderColor(blackColor_Index);
    cellStyle.setBorderRight(thin);
    cellStyle.setLeftBorderColor(blackColor_Index);
    cellStyle.setBorderLeft(thin);*/
        return cellStyle;
    }

    /**
     * 创建Font
     * <p>
     * 注意点：excle工作簿中字体最大限制为32767，应该重用字体，而不是为每个单元格都创建字体。
     * <p>
     * 其API:
     * setBold():设置粗体
     * setFontHeightInPoints():设置字体的点数
     * setColor():设置字体颜色
     * setItalic():设置斜体
     *
     * @param workbook
     * @return
     */
    private static Font creatFont(Workbook workbook) {
        Font font = workbook.createFont();
        return font;
    }

    /**
     * 将Excle表格写入文件中
     *
     * @param workbook
     * @param fileName
     */
    private static void writeFile(Workbook workbook, String fileName) {
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(fileName);
            workbook.write(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
                if (workbook != null) {
                    workbook.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static File getCacheFile(Context context, String name) {
        String cachePath;
        if (Environment.MEDIA_MOUNTED.equals(Environment
                .getExternalStorageState())
                || !Environment.isExternalStorageRemovable()) {

            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }
        return new File(cachePath + File.separator + name);
    }


    public void writeExecleToFile(Context context, Cell[][] data, MotionBean bean) {
        bean.name = SENCOND_VALUES[1] = data[1][1].getStringCellValue();
        bean.age = SENCOND_VALUES[3] = data[1][4].getStringCellValue();
        bean.diagnosis = SENCOND_VALUES[5] = data[1][6].getStringCellValue();
        bean.sex = SENCOND_VALUES[7] = data[1][18].getStringCellValue();
        String[][] tableData = new String[data.length - 3][19];
        if (data.length >= 5) {
            for (int i = 4; i < data.length; i++) {
                for (int j = 0; j < data[i].length; j++) {
                    Cell cell = data[i][j];
                    if (cell != null)
                        tableData[i - 4][j] = cell.getStringCellValue();
                }
            }
        }
        fileName=bean.getName()+"_"+bean.id+"_"+bean.type;
        writeExecleToFile(context, data.length, tableData);
    }


    public static void deleteFile(MotionBean bean) {
        File file = new File(getCacheFile(context, DIR_FILE_NAME), bean.getName() + "_" + bean.id + "_" + bean.type);
        if (file.exists())
        {
            file.delete();
        }
    }
}
