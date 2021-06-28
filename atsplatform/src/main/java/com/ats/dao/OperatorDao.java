package com.ats.dao;

import com.ats.entity.Operator;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * (Operator)表数据库访问层
 *
 * @author makejava
 * @since 2021-06-16 23:53:33
 */
@Mapper
public interface OperatorDao {

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
     * @param params 实例对象
     * @return 对象列表
     */
    List<Operator> selectList(Map<String, String> params);

    /**
     * 符合条件总数查询
     *
     * @param params 实例对象
     * @return 对象列表
     */
    int selectListSize(Map<String, String> params);

    /**
     * 新增数据
     *
     * @param operator 实例对象
     * @return 影响行数
     */
    int insertSelective(Operator operator);

    /**
     * 修改数据
     *
     * @param operator 实例对象
     * @return 影响行数
     */
    int update(Operator operator);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}

