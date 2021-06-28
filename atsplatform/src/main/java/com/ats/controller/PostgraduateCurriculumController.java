package com.ats.controller;

import com.ats.entity.PostgraduateCurriculum;
import com.ats.service.inter.PostgraduateCurriculumService;
import com.ats.util.JsonResp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * (PostgraduateCurriculum)表控制层
 *
 * @author makejava
 * @since 2021-06-18 16:20:16
 */
@RestController
@RequestMapping("postgraduateCurriculum")
public class PostgraduateCurriculumController {
    /**
     * 服务对象
     */
    @Resource
    private PostgraduateCurriculumService postgraduateCurriculumService;

    private static final Logger logger = LoggerFactory.getLogger(PostgraduateCurriculumController.class);

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return resp
     * @throws Exception
     */
    @GetMapping("/{id}")
    public JsonResp queryById(@PathVariable Integer id) throws Exception {
        PostgraduateCurriculum postgraduateCurriculum = this.postgraduateCurriculumService.queryById(id);
        JsonResp resp = new JsonResp(JsonResp.STATE_OK, JsonResp.CODE_OK);
        resp.setObj(postgraduateCurriculum);
        return resp;
    }

    /**
     * 条件查询（带分页），参数名和实体类一致，另规定：
     * page 表示页数
     * limit 表示每页显示数据条数
     *
     * @param params 传入参数,K-V
     * @return resp
     * @throws Exception
     */
    @GetMapping("/list")
    public JsonResp list(@RequestParam Map<String, String> params) throws Exception {
        JsonResp resp = new JsonResp(JsonResp.STATE_OK, JsonResp.CODE_OK);
        List<PostgraduateCurriculum> list = this.postgraduateCurriculumService.selectList(params);
        resp.setData(list);
        resp.setCount(this.postgraduateCurriculumService.selectListSize(params));
        return resp;
    }

    /**
     * 新增数据
     *
     * @param postgraduateCurriculum 实例对象
     * @return resp
     * @throws Exception
     */
    @PostMapping
    public JsonResp insert(PostgraduateCurriculum postgraduateCurriculum) throws Exception {
        boolean flag = this.postgraduateCurriculumService.insert(postgraduateCurriculum);
        JsonResp resp = null;
        if (flag) {
            resp = new JsonResp(JsonResp.STATE_OK, JsonResp.CODE_OK);
        } else {
            resp = new JsonResp(JsonResp.STATE_ERR, JsonResp.CODE_ERR);
        }
        return resp;
    }

    /**
     * 修改数据
     *
     * @param postgraduateCurriculum 实例对象
     * @return resp
     * @throws Exception
     */
    @PutMapping
    public JsonResp update(PostgraduateCurriculum postgraduateCurriculum) throws Exception {
        boolean flag = this.postgraduateCurriculumService.update(postgraduateCurriculum);
        JsonResp resp = null;
        if (flag) {
            resp = new JsonResp(JsonResp.STATE_OK, JsonResp.CODE_OK);
        } else {
            resp = new JsonResp(JsonResp.STATE_ERR, JsonResp.CODE_ERR);
        }
        return resp;
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return resp
     * @throws Exception
     */
    @DeleteMapping("/{id}")
    public JsonResp deleteById(@PathVariable Integer id) throws Exception {
        boolean flag = this.postgraduateCurriculumService.deleteById(id);
        JsonResp resp = null;
        if (flag) {
            resp = new JsonResp(JsonResp.STATE_OK, JsonResp.CODE_OK);
        } else {
            resp = new JsonResp(JsonResp.STATE_ERR, JsonResp.CODE_ERR);
        }
        return resp;
    }

    /**
     * 课程导入
     * @return
     */
    @RequestMapping("/currImport")
    public JsonResp curriculumImport(@RequestParam(required = false,value = "file") MultipartFile file) {
        JsonResp jsonResp = new JsonResp();
        //判断文件名是否为空
        if (file.getOriginalFilename() == ""){
            jsonResp.setState(JsonResp.STATE_ERR);
            jsonResp.setMsg("文件名为空");
            return jsonResp;
        }
        if (file.isEmpty()){
            jsonResp.setState(JsonResp.STATE_ERR);
            jsonResp.setMsg("未上传文件或上传文件为空");
            return jsonResp;
        }
        try{
            boolean flag = postgraduateCurriculumService.curriculumImport((FileInputStream) file.getInputStream());
            if(flag){
                jsonResp.setState(JsonResp.STATE_OK);
                jsonResp.setMsg("数据导入成功");
            }else{
                jsonResp.setState(JsonResp.STATE_ERR);
                jsonResp.setMsg("数据导入失败");
            }
        }catch (IOException io){
            logger.error("文件转换流失败");
            jsonResp.setState(JsonResp.STATE_ERR);
            jsonResp.setMsg("文件转换失败");
            return jsonResp;
        }
        return jsonResp;
    }

}

