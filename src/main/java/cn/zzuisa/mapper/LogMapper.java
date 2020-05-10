package cn.zzuisa.mapper;

import cn.zzuisa.entity.Log;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author Ao
 * @date 2020-04-24 17:16
 */
@Mapper
public interface LogMapper extends BaseMapper<Log> {
    IPage<Map<String, Object>> getAllDetail(IPage<Map<String, Object>> page);

}
