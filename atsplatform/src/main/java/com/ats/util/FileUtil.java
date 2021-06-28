package com.ats.util;

import com.aspose.words.Document;
import com.aspose.words.SaveFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author Wyj
 * date 2021-06-19
 */
public class FileUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 根据原文件名生成pdf名
     * @param path
     * @param targetDir
     * @return
     */
    public static String returnFileNameWithDoc(String path,String targetDir){
        String[] Array = path.split("\\\\");
        String str = Array[Array.length - 1];
        System.out.println(str);
        return targetDir + str.substring(0, str.lastIndexOf("."))+ ".pdf";
    }

    /**
     * word转pdf
     * url:word文件地址全路径
     * targetDir: pdf文件夹路径
     * pdfFile：pdf目标全路径
     */
    public static String wordToPdf(String url, String targetDir,String pdfFile) throws Exception {
        FileOutputStream fileOutputStream = null;
        File targetPath = new File(targetDir);
        if (!targetPath.exists()) {
            targetPath.mkdir();
        }
        try {
            File target = new File(pdfFile);
            if (target.exists()) {
                target.delete();
            }
            fileOutputStream = new FileOutputStream(target);
            Document doc = new Document(url);
            // 全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF,
            doc.save(fileOutputStream, SaveFormat.PDF);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("转换失败："+e);
        }finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return pdfFile;
    }


    /**
     *
     * @param file 目标文件
     * @param Aim_File 关键字
     * @return
     */
    private static Map<String,String> getAimFile(File file, String Aim_File) {
        //忽略掉临时文件，以~$起始的文件名
        if (file.getName().startsWith("~$")) return null;
        Map<String,String> File_Message=new HashMap<String,String>();
        if(file.getName().contains(Aim_File)){
            File_Message.put("File_Name",file.getName());
            File_Message.put("File_Path",file.getPath());
        }
        return  File_Message;
    }

    /**
     *
     * @param Directory_Name 目标文件路径
     * @param Aim_File  关键字
     * @return
     */
    public static List<Map<String,String>> FileName(String Directory_Name,String Aim_File){
        List<Map<String,String>> Aim_FileName=new ArrayList<Map<String,String>>();
        File Directory=new File(Directory_Name);
        //判断该文件或目录是否存在，不存在时在控制台输出提醒
        if (!Directory.exists()) {
            System.out.println("文件目录不存在");
            return null;
        }
        //判断传入的Directory是否是一个文件，如果是一个文件，直接进行处理
        if (!Directory.isDirectory()) {
            if (Directory.isFile()) {
                Map<String,String> AimFile=getAimFile(Directory,Aim_File);
                if(AimFile.size()>0){
                    Aim_FileName.add(AimFile);
                }
            }
            return Aim_FileName;
        }

        //获取此目录下的所有文件名与目录名
        String[] fileList = Directory.list();
        for (int i = 0; i < fileList.length; i++) {
            //遍历文件目录
            String string = fileList[i];
            File file = new File(Directory.getPath(),string);
            //如果是一个目录，输出目录名后，进行递归
            if (file.isDirectory()) {
                //递归
                Aim_FileName.addAll(FileName(file.getPath(),Aim_File));
            } else {
                Map<String,String> AimFile=getAimFile(file,Aim_File);
                if(AimFile.size()>0){
                    Aim_FileName.add(AimFile);
                }
            }
        }
        return Aim_FileName;
    }


    public static String returnTimeName(Date updateTime){
        String date = "";
        if(updateTime != null){
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
            date = format.format(updateTime);
        }else{
            date = null;
        }
        return date;
    }


    /**
     * 判断指定文件夹中是否有对应的PDF文件
     * @param targetDir
     * @param id
     * @param updateTime
     * @return fileName
     */
    public static String existFileWithName(String targetDir, Integer id, Date updateTime){
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmm");
        String name = "export_"+id+"_"+FileUtil.returnTimeName(updateTime);
        List<Map<String,String>> ExitFileList = FileUtil.FileName(targetDir,name);
        if(ExitFileList.size()==0){
            return null;
        }else{
            for(Map<String,String> MayBeFile:ExitFileList){
                if(MayBeFile.get("File_Name").equals(name+".pdf")){
                    System.out.println(name+".pdf");
                    return name+".pdf";
                }
            }
        }
        return null;
    }


}
