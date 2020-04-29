package cn.zzuisa.mapper;

import cn.zzuisa.entity.Student;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author Ao
 * @date 2020-04-24 17:09
 */
@Mapper
public interface StudentMapper extends BaseMapper<Student> {
    Map<String,Object> getFiles(int id);

    Student getByUid(int uid);

    IPage<Map<String, Object>> pageEntity(IPage<Map<String, Object>> page, @Param("u") Student student);

}
