package com.ats.service.impl;

import com.ats.dao.UndergraduateCurriculumDao;
import com.ats.entity.UndergraduateCurriculum;
import com.ats.service.inter.UndergraduateCurriculumService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.Map;

/**
 * (UndergraduateCurriculum)表服务实现类
 *
 * @author makejava
 * @since 2021-06-18 16:20:17
 */
@Service("undergraduateCurriculumService")
public class UndergraduateCurriculumServiceImpl implements UndergraduateCurriculumService {
    @Resource
    private UndergraduateCurriculumDao undergraduateCurriculumDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public UndergraduateCurriculum queryById(Integer id) {
        return this.undergraduateCurriculumDao.queryById(id);
    }

    /**
     * 条件查询（带分页）
     *
     * @param params 传入参数
     * @return 对象列表
     */
    @Override
    public List<UndergraduateCurriculum> selectList(Map<String, String> params) {
        try {
            if (params.containsKey("page") && params.containsKey("limit")) {
                int offset = (Integer.parseInt(params.get("page")) - 1) * Integer.parseInt(params.get("limit"));
                if (offset < 0) throw new InvalidParameterException("请传入正确的分页参数");
                params.put("offset", offset + "");
            }
        } catch (Exception e) {
            throw new InvalidParameterException("分页参数非法：" + e.getMessage());
        }
        return this.undergraduateCurriculumDao.selectList(params);
    }

    /**
     * 符合条件总数查询
     *
     * @param params 传入参数
     * @return 对象列表
     */
    @Override
    public int selectListSize(Map<String, String> params) {
        return this.undergraduateCurriculumDao.selectListSize(params);
    }

    /**
     * 新增数据
     *
     * @param undergraduateCurriculum 实例对象
     * @return 是否成功
     */
    @Override
    public boolean insert(UndergraduateCurriculum undergraduateCurriculum) {
        return this.undergraduateCurriculumDao.insertSelective(undergraduateCurriculum) > 0;
    }

    /**
     * 修改数据
     *
     * @param undergraduateCurriculum 实例对象
     * @return 是否成功
     */
    @Override
    public boolean update(UndergraduateCurriculum undergraduateCurriculum) {
        if (StringUtils.isEmpty(undergraduateCurriculum.getId()))
            throw new InvalidParameterException("请传入更新数据的主键");
        return this.undergraduateCurriculumDao.update(undergraduateCurriculum) > 0;
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.undergraduateCurriculumDao.deleteById(id) > 0;
    }
}

