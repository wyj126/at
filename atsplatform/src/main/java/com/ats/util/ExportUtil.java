package com.ats.util;

import com.ats.dto.Course;
import com.ats.dto.Student;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.xwpf.usermodel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;

/**
 * @author Wyj
 * date 2021-06-19
 */
public class ExportUtil {

    private static final Logger logger = LoggerFactory.getLogger(ExportUtil.class);

    /**
     * 解析替换docx文件
     * @param in 需要被解析的docx文件
     * @return
     * 分为两步 基本数据的替换 + 表格数据的替换
     */
    private static XWPFDocument returnXWPFDocumentByDocx(FileInputStream in, Map<String, String> contentMap){
        XWPFDocument docx = null;
        try {
            docx=new XWPFDocument(in);
            //07版需先获取段落；最后在获取以格式分割的最小单位run
            // 替换段落中的指定文字
            Iterator<XWPFParagraph> itPara = docx
                    .getParagraphsIterator();
            while (itPara.hasNext()) {
                XWPFParagraph paragraph = (XWPFParagraph) itPara.next();
                List<XWPFRun> runs = paragraph.getRuns();
                for (int i = 0; i < runs.size(); i++) {
                    String oneparaString = runs.get(i).getText(
                            runs.get(i).getTextPosition());
                    for (Map.Entry<String, String> entry : contentMap
                            .entrySet()) {
                        oneparaString = oneparaString.replace(
                                "{" + entry.getKey() + "}", entry.getValue());
                    }
                    runs.get(i).setText(oneparaString, 0);
                }
            }

            Iterator it = docx.getTablesIterator();
            //表格内容替换添加
            while(it.hasNext()){
                XWPFTable table = (XWPFTable)it.next();
                int rcount = table.getNumberOfRows();
                for(int i =0 ;i < rcount;i++){
                    XWPFTableRow row = table.getRow(i);
                    List<XWPFTableCell> cells =  row.getTableCells();
                    for (XWPFTableCell cell : cells){
                        for(Map.Entry<String,String> e : contentMap.entrySet()){
                            if (cell.getText().equals("{" + e.getKey() + "}")){
                                //删除原来内容
                                cell.removeParagraph(0);
                                //写入新内容
                                cell.setText(e.getValue());
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("docx模板内容替换写入新文件失败:"+e.getStackTrace());
        }
        return docx;
    }

    /**
     * 解析替换doc文件
     * @param in
     * @return 需要被解析的doc文件
     */
    private static HWPFDocument returnHWPFDocumentByDoc(FileInputStream in, Map<String, String> contentMap){
        //HWPFDocument 对应03版word
        HWPFDocument doc=null;
        try {
            //range获取word中的内容
            doc =new HWPFDocument(in);
            //range获取word中的内容
            Range range = doc.getRange();
            //通过一个Map来替换内容 Map中key值存被替换的内容  Map中value值存要替换的内容，最后通过一个循环实现
            for (Map.Entry<String,String> entry:contentMap.entrySet()){
                range.replaceText("{" + entry.getKey() + "}",entry.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("doc模板内容替换写入新文件失败:"+e.getStackTrace());
        }
        return doc;
    }


    /**
     * 文件模板替换
     * @param tmpFile 模板文件地址
     * @param contentMap 替换map集合
     * @param exportFile 导出文件夹路径
     * @param id  JavaBean id File唯一标识之一
     * @param updateTime 更新时间 File唯一标识之一
     * @return
     * @throws Exception
     */
    public static String CreatWordByModel(String  tmpFile, Map<String, String> contentMap, String exportFile, Integer id, Date updateTime) throws Exception{
        FileInputStream in = new FileInputStream(new File(tmpFile));
        String newFilePath  = "";
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        if(tmpFile.endsWith("doc")) {
            HWPFDocument hwpfDocument = returnHWPFDocumentByDoc(in, contentMap);
            hwpfDocument.write(byteArrayOutputStream);
            newFilePath = exportDocumentFile(tmpFile, byteArrayOutputStream.toByteArray(), id, exportFile, updateTime);
        }else if(tmpFile.endsWith("docx")){
            XWPFDocument xwpfDocument = returnXWPFDocumentByDocx(in,contentMap);
            xwpfDocument.write(byteArrayOutputStream);
            newFilePath = exportDocumentFile(tmpFile, byteArrayOutputStream.toByteArray(), id, exportFile, updateTime);
        }
        return newFilePath;
    }

    /**
     * 内容替换完毕后返回新文件路径
     * @param tmpFile 模板文件地址
     * @param array 字节数组
     * @param id 唯一标识
     * @param exportFile 导出文件夹路径
     * @param updateTime 更新时间 File唯一标识之一
     * @return
     */
    private static String exportDocumentFile(String  tmpFile,byte[] array,Integer id,String exportFile,Date updateTime){
        File fileDir = new File(exportFile);
        if(!fileDir.exists()){
            fileDir.mkdir();
        }
        String newPath = "";
        try{
            String[] arr = tmpFile.split("\\.");
            String suffix = arr[arr.length-1];
            newPath = exportFile + File.separator+"export_"+id+"_"+FileUtil.returnTimeName(updateTime)+"."+suffix;
            OutputStream outputStream = new FileOutputStream(newPath);
            outputStream.write(array);
            outputStream.close();
        }catch (IOException e){
            e.printStackTrace();
            logger.error("模板内容替换写入新文件失败:"+e.getStackTrace());
        }
        return  newPath;
    }


    public static Map<String, String> objectToMap(Student stu) throws IllegalAccessException {
        Map<String, String> map = new HashMap<>();
        map.put("name", stu.getName());
        map.put("stuNum", stu.getStuNum().toString());
        map.put("collage", stu.getCollage());
        map.put("major", stu.getMajor());
        map.put("admissionDate", stu.getAdmissionDate());
        map.put("tabulationDate", stu.getTabulationDate());
        map.put("instructor", stu.getInstructor());
        map.put("thesisTopic", stu.getThesisTopic());
        map.put("thesisScore", stu.getThesisScore());
        List<Course> courseList = stu.getCourse();
        for (int i = 0; i < 10; i++) {
            if (i<=courseList.size()-1){
                Course course = courseList.get(i);
                map.put(i+","+0,course.getCourseName());
                map.put(i+","+1,course.getCourseCredit().toString());
                map.put(i+","+2,course.getCourseScore());
            }else{
                map.put(i+","+0,"-");
                map.put(i+","+1,"-");
                map.put(i+","+2,"-");
            }
        }
        return map;
    }

}
