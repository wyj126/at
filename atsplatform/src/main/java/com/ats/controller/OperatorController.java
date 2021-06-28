package com.ats.controller;

import com.ats.entity.Operator;
import com.ats.service.inter.OperatorService;
import com.ats.util.JsonResp;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * (Operator)表控制层
 *
 * @author makejava
 * @since 2021-06-16 23:53:33
 */
@RestController
@RequestMapping("operator")
public class OperatorController {
    /**
     * 服务对象
     */
    @Resource
    private OperatorService operatorService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return resp
     * @throws Exception
     */
    @GetMapping("/{id}")
    public JsonResp queryById(@PathVariable Integer id) throws Exception {
        Operator operator = this.operatorService.queryById(id);
        JsonResp resp = new JsonResp(JsonResp.STATE_OK, JsonResp.CODE_OK);
        resp.setObj(operator);
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
        List<Operator> list = this.operatorService.selectList(params);
        resp.setData(list);
        resp.setCount(this.operatorService.selectListSize(params));
        return resp;
    }

    /**
     * 新增数据
     *
     * @param operator 实例对象
     * @return resp
     * @throws Exception
     */
    @PostMapping
    public JsonResp insert(Operator operator) throws Exception {
        boolean flag = this.operatorService.insert(operator);
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
     * @param operator 实例对象
     * @return resp
     * @throws Exception
     */
    @PutMapping
    public JsonResp update(Operator operator) throws Exception {
        boolean flag = this.operatorService.update(operator);
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
        boolean flag = this.operatorService.deleteById(id);
        JsonResp resp = null;
        if (flag) {
            resp = new JsonResp(JsonResp.STATE_OK, JsonResp.CODE_OK);
        } else {
            resp = new JsonResp(JsonResp.STATE_ERR, JsonResp.CODE_ERR);
        }
        return resp;
    }

}

