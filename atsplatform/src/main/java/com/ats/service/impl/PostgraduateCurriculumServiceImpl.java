package com.ats.service.impl;

import com.ats.dao.PostgraduateCurriculumDao;
import com.ats.entity.PostgraduateCurriculum;
import com.ats.service.inter.PostgraduateCurriculumService;
import com.ats.util.DataImport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.List;
import java.util.Map;

/**
 * (PostgraduateCurriculum)表服务实现类
 *
 * @author makejava
 * @since 2021-06-18 16:20:15
 */
@Service("postgraduateCurriculumService")
public class PostgraduateCurriculumServiceImpl implements PostgraduateCurriculumService {

    private static final Logger logger = LoggerFactory.getLogger(PostgraduateCurriculumServiceImpl.class);

    @Resource
    private PostgraduateCurriculumDao postgraduateCurriculumDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public PostgraduateCurriculum queryById(Integer id) {
        return this.postgraduateCurriculumDao.queryById(id);
    }

    /**
     * 条件查询（带分页）
     *
     * @param params 传入参数
     * @return 对象列表
     */
    @Override
    public List<PostgraduateCurriculum> selectList(Map<String, String> params) {
        try {
            if (params.containsKey("page") && params.containsKey("limit")) {
                int offset = (Integer.parseInt(params.get("page")) - 1) * Integer.parseInt(params.get("limit"));
                if (offset < 0) throw new InvalidParameterException("请传入正确的分页参数");
                params.put("offset", offset + "");
            }
        } catch (Exception e) {
            throw new InvalidParameterException("分页参数非法：" + e.getMessage());
        }
        return this.postgraduateCurriculumDao.selectList(params);
    }

    /**
     * 符合条件总数查询
     *
     * @param params 传入参数
     * @return 对象列表
     */
    @Override
    public int selectListSize(Map<String, String> params) {
        return this.postgraduateCurriculumDao.selectListSize(params);
    }

    /**
     * 新增数据
     *
     * @param postgraduateCurriculum 实例对象
     * @return 是否成功
     */
    @Override
    public boolean insert(PostgraduateCurriculum postgraduateCurriculum) {
        return this.postgraduateCurriculumDao.insertSelective(postgraduateCurriculum) > 0;
    }

    /**
     * 修改数据
     *
     * @param postgraduateCurriculum 实例对象
     * @return 是否成功
     */
    @Override
    public boolean update(PostgraduateCurriculum postgraduateCurriculum) {
        if (StringUtils.isEmpty(postgraduateCurriculum.getId()))
            throw new InvalidParameterException("请传入更新数据的主键");
        return this.postgraduateCurriculumDao.update(postgraduateCurriculum) > 0;
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.postgraduateCurriculumDao.deleteById(id) > 0;
    }

    /**
     * 课程导入 开启事物支持
     * @param in
     * @return
     */
    @Override
    @Transactional
    public boolean curriculumImport(FileInputStream in) {
        try{
            List<Object> postgraduateCurriculumList = DataImport.excelDataImport(in, new PostgraduateCurriculum());
            int count = postgraduateCurriculumDao.insertByList(postgraduateCurriculumList);
            if (count > 0){
                logger.info("数据导入成功");
                return true;
            }else{
                logger.error("数据导入失败");
            }
        }catch (IOException IO){
            logger.error("文件导入异常");
        }catch (Exception e){
            e.printStackTrace();
            logger.error("数据读取异常");
        }
        return false;
    }


}

