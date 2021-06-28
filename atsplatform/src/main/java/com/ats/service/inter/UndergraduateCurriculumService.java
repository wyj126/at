package com.ats.service.inter;

import com.ats.entity.UndergraduateCurriculum;

import java.util.List;
import java.util.Map;

/**
 * (UndergraduateCurriculum)表服务接口
 *
 * @author makejava
 * @since 2021-06-18 16:20:18
 */
public interface UndergraduateCurriculumService {

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
     * @param params 传入参数
     * @return 对象列表
     */
    List<UndergraduateCurriculum> selectList(Map<String, String> params);

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
     * @param undergraduateCurriculum 实例对象
     * @return 是否成功
     */
    boolean insert(UndergraduateCurriculum undergraduateCurriculum);

    /**
     * 修改数据
     *
     * @param undergraduateCurriculum 实例对象
     * @return 是否成功
     */
    boolean update(UndergraduateCurriculum undergraduateCurriculum);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}

