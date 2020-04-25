package cn.zzuisa.service.impl;

import cn.zzuisa.entity.Student;
import cn.zzuisa.mapper.StudentMapper;
import cn.zzuisa.service.StudentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author Ao
 * @date 2020-04-24 18:02
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student> implements StudentService {
}
