package cn.zzuisa.service;

import cn.zzuisa.entity.Log;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * @author Ao
 * @date 2020-04-27 04:01
 */
public interface LogService extends IService<Log> {
    IPage<Map<String, Object>> getAllDetail(IPage<Map<String, Object>> page);
}
