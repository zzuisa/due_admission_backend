package cn.zzuisa.service;

import cn.zzuisa.entity.Student;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author frank
 * @since 2019-05-07
 */
public interface StudentService extends IService<Student> {
    Map<String, Object> getFiles(int sid);

    Student getByUid(int id);

    IPage<Map<String, Object>> pageEntity(IPage<Map<String, Object>> page, Student student);

}
