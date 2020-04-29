package cn.zzuisa.service.impl;

import cn.zzuisa.entity.Student;
import cn.zzuisa.mapper.StudentMapper;
import cn.zzuisa.service.StudentService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author Ao
 * @date 2020-04-24 18:02
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
    @Override
    public Map<String, Object> getFiles(int sid) {
        return baseMapper.getFiles(sid);
    }

    @Override
    public Student getByUid(int id) {
        return baseMapper.getByUid(id);
    }

    @Override
    public IPage<Map<String, Object>> pageEntity(IPage<Map<String, Object>> page, Student student) {
        return baseMapper.pageEntity(page, student);
    }

}
