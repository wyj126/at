package com.ats.dao;

import com.ats.entity.UndergraduateCurriculum;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * (UndergraduateCurriculum)表数据库访问层
 *
 * @author makejava
 * @since 2021-06-18 16:20:17
 */
@Mapper
public interface UndergraduateCurriculumDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    UndergraduateCurriculum queryById(Integer id);

    /**
     * 条件查询（带分页）
     *
     * @param params 实例对象
     * @return 对象列表
     */
    List<UndergraduateCurriculum> selectList(Map<String, String> params);

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
     * @param undergraduateCurriculum 实例对象
     * @return 影响行数
     */
    int insertSelective(UndergraduateCurriculum undergraduateCurriculum);

    /**
     * 修改数据
     *
     * @param undergraduateCurriculum 实例对象
     * @return 影响行数
     */
    int update(UndergraduateCurriculum undergraduateCurriculum);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}

