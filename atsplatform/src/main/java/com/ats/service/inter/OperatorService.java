package com.ats.service.inter;

import com.ats.entity.Operator;

import java.util.List;
import java.util.Map;

/**
 * (Operator)表服务接口
 *
 * @author makejava
 * @since 2021-06-16 23:53:33
 */
public interface OperatorService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Operator queryById(Integer id);

    /**
     * 条件查询（带分页）
     *
     * @param params 传入参数
     * @return 对象列表
     */
    List<Operator> selectList(Map<String, String> params);

    /**
     * 符合条件总数查询
     *
     * @param params 传入参数
     * @return 对象列表
     */
    int selectListSize(Map<String, String> params);

    /**
     * 新增数据
     *
     * @param operator 实例对象
     * @return 是否成功
     */
    boolean insert(Operator operator);

    /**
     * 修改数据
     *
     * @param operator 实例对象
     * @return 是否成功
     */
    boolean update(Operator operator);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}

