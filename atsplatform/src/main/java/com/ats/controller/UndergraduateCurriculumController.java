package com.ats.controller;

import com.ats.entity.UndergraduateCurriculum;
import com.ats.service.inter.UndergraduateCurriculumService;
import com.ats.util.JsonResp;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * (UndergraduateCurriculum)表控制层
 *
 * @author makejava
 * @since 2021-06-18 16:20:18
 */
@RestController
@RequestMapping("undergraduateCurriculum")
public class UndergraduateCurriculumController {
    /**
     * 服务对象
     */
    @Resource
    private UndergraduateCurriculumService undergraduateCurriculumService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return resp
     * @throws Exception
     */
    @GetMapping("/{id}")
    public JsonResp queryById(@PathVariable Integer id) throws Exception {
        UndergraduateCurriculum undergraduateCurriculum = this.undergraduateCurriculumService.queryById(id);
        JsonResp resp = new JsonResp(JsonResp.STATE_OK, JsonResp.CODE_OK);
        resp.setObj(undergraduateCurriculum);
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
        List<UndergraduateCurriculum> list = this.undergraduateCurriculumService.selectList(params);
        resp.setData(list);
        resp.setCount(this.undergraduateCurriculumService.selectListSize(params));
        return resp;
    }

    /**
     * 新增数据
     *
     * @param undergraduateCurriculum 实例对象
     * @return resp
     * @throws Exception
     */
    @PostMapping
    public JsonResp insert(UndergraduateCurriculum undergraduateCurriculum) throws Exception {
        boolean flag = this.undergraduateCurriculumService.insert(undergraduateCurriculum);
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
     * @param undergraduateCurriculum 实例对象
     * @return resp
     * @throws Exception
     */
    @PutMapping
    public JsonResp update(UndergraduateCurriculum undergraduateCurriculum) throws Exception {
        boolean flag = this.undergraduateCurriculumService.update(undergraduateCurriculum);
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
        boolean flag = this.undergraduateCurriculumService.deleteById(id);
        JsonResp resp = null;
        if (flag) {
            resp = new JsonResp(JsonResp.STATE_OK, JsonResp.CODE_OK);
        } else {
            resp = new JsonResp(JsonResp.STATE_ERR, JsonResp.CODE_ERR);
        }
        return resp;
    }

}

