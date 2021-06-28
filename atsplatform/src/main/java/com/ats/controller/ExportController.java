package com.ats.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ats.dto.Course;
import com.ats.dto.Student;
import com.ats.util.ExportUtil;
import com.ats.util.FileUtil;
import com.ats.util.JsonResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Wyj
 * date 2021-06-19
 */
@RestController
@RequestMapping("/export")
public class ExportController {

    private static final Logger logger = LoggerFactory.getLogger(ExportController.class);

    @Value("${create_postGraduate_word_path}")
    private String create_postGraduate_word_path;

    @Value("${export_postGraduate_word_path}")
    private String export_postGraduate_word_path;

    @Value("${pdf_postGraduate_path}")
    private String pdf_postGraduate_path;


    @RequestMapping(value = "/registerDoc", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public JsonResp registerDoc(@RequestBody String jsonString) throws IllegalAccessException, ParseException {


        /**
         * k : y
         *
         * jsonObject.get("k").toString = y(Stirng对象)
         */
        System.out.println("前端传来的数据"+jsonString);

        JsonResp resp = new JsonResp();
        JSONObject jsonObject = (JSONObject) JSONObject.parse(jsonString);

        //创建临时学生对象
        Student student = new Student();
        student.setType(Integer.parseInt(jsonObject.get("type").toString()));
        student.setName(jsonObject.get("name").toString());
        student.setStuNum(Integer.parseInt(jsonObject.get("stuNum").toString()));
        student.setCollage(jsonObject.get("collage").toString());
        student.setMajor(jsonObject.get("major").toString());
        student.setInstructor(jsonObject.get("instructor").toString());
        student.setAdmissionDate(jsonObject.get("admissionDate").toString());
        student.setThesisTopic(jsonObject.get("thesisTopic").toString());
        student.setThesisScore(jsonObject.get("thesisScore").toString());
        List<Course> courseList = JSON.parseArray(jsonObject.get("course").toString(), Course.class);
        student.setCourse(courseList);
        Date date = new Date();
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        String month = Integer.toString(ca.get(Calendar.MONTH) + 1);
        String day = Integer.toString(ca.get(Calendar.DATE));
        if (ca.get(Calendar.MONTH) + 1 < 10){
            month = "0"+month;
        }
        if (ca.get(Calendar.DATE) < 10){
            day = "0"+day;
        }
        student.setTabulationDate(month+"/"+day+"/"+ca.get(Calendar.YEAR));

        //固定参数替换
        //pdf文件路径
        String pdfFilePath = "";
        //word模板路径
        String createWordModelPath = "";
        //word文件导出路径
        String exportWordFilePath = "";
        //判断学生类型对应不同路径
        if (student.getType() == 1){
            //postGraduate
            pdfFilePath = pdf_postGraduate_path;
            createWordModelPath = create_postGraduate_word_path;
            exportWordFilePath = export_postGraduate_word_path;
            //对姓名 专业名称 指导老师 的长度控制
            while(student.getName().length()<25){
                student.setName(student.getName()+" ");
            }
            while(student.getMajor().length()<45){
                student.setMajor(student.getMajor()+" ");
            }
            while(student.getInstructor().length()<20){
                student.setInstructor(student.getInstructor()+" ");
            }
        }else if(student.getType() == 0){
            //underGraduate
            //todo
        }else{
            resp.setState(JsonResp.STATE_ERR);
            resp.setMsg("学生信息异常");
            return resp;
        }

        //将对象转为map类型
        Map<String,String> contentMap = ExportUtil.objectToMap(student);
        String exportWordPath= null;
        try {
            String ExistFile= FileUtil.existFileWithName(pdfFilePath,Integer.parseInt(contentMap.get("stuNum")),new Date());
            if(ExistFile == null){
                synchronized (this){
                    exportWordPath = ExportUtil.CreatWordByModel(createWordModelPath,contentMap,exportWordFilePath,Integer.parseInt(contentMap.get("stuNum")),new Date());
                }
                String pdfPath = FileUtil.wordToPdf(exportWordPath,pdfFilePath,FileUtil.returnFileNameWithDoc(exportWordPath,pdfFilePath));
                String[] urlArray = pdfPath.split("\\\\");
                String pdfName = urlArray[urlArray.length - 1];
                resp.setMsg(pdfName);
                resp.setState(JsonResp.STATE_OK);
            }else{
                resp.setMsg(ExistFile);
                resp.setState(JsonResp.STATE_OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("ExportController:"+e.getMessage());
            resp.setState(JsonResp.STATE_WARN);
            resp.setMsg("模板导出失败");
        }
        return resp;
    }


}
