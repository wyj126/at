package com.ats.util;

import com.monitorjbl.xlsx.StreamingReader;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *数据导入
 * @author Wyj
 * date 2021-06-17
 */
public class DataImport {

    /**
     * 方法重载
     * @param in
     * @param object 传入类对象
     * @return
     * @throws Exception
     */
    public static List<Object> excelDataImport(FileInputStream in,Object object) throws Exception{
        //创建一个对象集合
        List<Object> objects = new ArrayList<>();
        Workbook wk = StreamingReader.builder()
                .rowCacheSize(100)  //缓存到内存中的行数，默认是10
                .bufferSize(4096)  //读取资源时，缓存到内存的字节大小，默认是1024
                .open(in);  //打开资源，必须，可以是InputStream或者是File，注意：只能打开XLSX格式的文件
        int sheetNums = wk.getNumberOfSheets();
        for(int i = 0 ; i < sheetNums;i ++){
            Sheet sheet = wk.getSheetAt(i);
            String sheetName = wk.getSheetName(i);
            //遍历所有的行
            for (Row row : sheet) {
                //跳过第一行
                if (row.getRowNum() == 0){
                    continue;
                }
                //设置临时名称
                String[] tempName = new String[10];
                int j = 0;
                //遍历所有的列
                for (Cell cell : row) {
                    try{
                        tempName[j] = cell.getStringCellValue();
                        j++;
                    }catch (IndexOutOfBoundsException ie){
                        ie.printStackTrace();
                    }
                }
                //获取属性值
                Field[] fields = object.getClass().getDeclaredFields();
                //创建临时对象
                Object tempObject = object.getClass().newInstance();
                for(Field field : fields){
                    //打破封装
                    field.setAccessible(true);
                    //获取属性名称
                    String name = field.getName();
                    if("chineseName".equals(name)){
                        field.set(tempObject,tempName[0]);
                    }else if("englishName".equals(name)){
                        field.set(tempObject,tempName[1]);
                    }else if("credit".equals(name)){
                        field.set(tempObject,Double.valueOf(tempName[2]));
                    }
                }
                objects.add(tempObject);
            }
        }
        return objects;
    }

    /**
     * 原始方法 获取excel中的大量数据
     * @param file
     * @return
     * @throws Exception
     */
    public static Map<String, List<String>> excelDataImport(File file) throws Exception{
        FileInputStream in = new FileInputStream(file);
        Map<String,List<String>> mapData = new HashMap<String, List<String>>();
        Workbook wk = StreamingReader.builder()
                .rowCacheSize(100)  //缓存到内存中的行数，默认是10
                .bufferSize(4096)  //读取资源时，缓存到内存的字节大小，默认是1024
                .open(in);  //打开资源，必须，可以是InputStream或者是File，注意：只能打开XLSX格式的文件
        int sheetNums = wk.getNumberOfSheets();
        for(int i = 0 ; i < sheetNums;i ++){
            List<String> sheetData = new ArrayList<String>();
            Sheet sheet = wk.getSheetAt(i);
            String sheetName = wk.getSheetName(i);
            //遍历所有的行
            for (Row row : sheet) {
                StringBuilder sb = new StringBuilder();
                //遍历所有的列
                for (Cell cell : row) {
                    sb.append(cell.getStringCellValue());
                }
                sheetData.add(sb.toString());
            }
            mapData.put(sheetName,sheetData);

        }
        return mapData;
    }

}
