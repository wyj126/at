package com.ats.dao;

import com.ats.entity.PostgraduateCurriculum;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

/**
 * (PostgraduateCurriculum)表数据库访问层
 *
 * @author makejava
 * @since 2021-06-18 16:20:14
 */
@Mapper
public interface PostgraduateCurriculumDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    PostgraduateCurriculum queryById(Integer id);

    /**
     * 条件查询（带分页）
     *
     * @param params 实例对象
     * @return 对象列表
     */
    List<PostgraduateCurriculum> selectList(Map<String, String> params);

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
     * @param postgraduateCurriculum 实例对象
     * @return 影响行数
     */
    int insertSelective(PostgraduateCurriculum postgraduateCurriculum);

    /**
     * 修改数据
     *
     * @param postgraduateCurriculum 实例对象
     * @return 影响行数
     */
    int update(PostgraduateCurriculum postgraduateCurriculum);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    /**
     * 研究生课程导入
     * @param postgraduateCurriculumList
     * @return
     */
    int insertByList(@Param("postgraduateCurriculumList")List<Object> postgraduateCurriculumList);

}

