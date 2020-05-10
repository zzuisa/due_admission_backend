package cn.zzuisa.service.impl;

import cn.zzuisa.base.R;
import cn.zzuisa.entity.Account;
import cn.zzuisa.entity.Log;
import cn.zzuisa.mapper.AccountMapper;
import cn.zzuisa.mapper.LogMapper;
import cn.zzuisa.service.AccountService;
import cn.zzuisa.service.LogService;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author frank
 * @since 2020-04-27
 */
@Service
public class LogServiceImpl extends ServiceImpl<LogMapper, Log> implements LogService {

    @Autowired
    LogMapper logMapper;

    @Override
    public IPage<Map<String, Object>> getAllDetail(IPage<Map<String, Object>> page) {
        return logMapper.getAllDetail(page);
    }

}
