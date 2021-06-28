package com.ats.service.inter;

import com.ats.entity.PostgraduateCurriculum;

import java.io.FileInputStream;
import java.util.List;
import java.util.Map;

/**
 * (PostgraduateCurriculum)表服务接口
 *
 * @author makejava
 * @since 2021-06-18 16:20:15
 */
public interface PostgraduateCurriculumService {

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
     * @param params 传入参数
     * @return 对象列表
     */
    List<PostgraduateCurriculum> selectList(Map<String, String> params);

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
     * @param postgraduateCurriculum 实例对象
     * @return 是否成功
     */
    boolean insert(PostgraduateCurriculum postgraduateCurriculum);

    /**
     * 修改数据
     *
     * @param postgraduateCurriculum 实例对象
     * @return 是否成功
     */
    boolean update(PostgraduateCurriculum postgraduateCurriculum);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * 研究生课程导入
     * @param in
     * @return
     */
    boolean curriculumImport(FileInputStream in);


}

