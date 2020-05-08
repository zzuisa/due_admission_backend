package cn.zzuisa.service.impl;

import cn.zzuisa.entity.Notification;
import cn.zzuisa.mapper.NotificationMapper;
import cn.zzuisa.service.NotificationService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author Ao
 * @date 2020-04-27 13:09
 */
@Service
public class NotificationServiceImpl extends ServiceImpl<NotificationMapper, Notification> implements NotificationService {

    @Autowired
    NotificationMapper notificationMapper;
    @Override
    public IPage<Map<String,Object>> pageEntity(IPage<Map<String,Object>> page, Notification notification) {
        return notificationMapper.pageEntity(page, notification);
    }

    @Override
    public int sendNotice(Notification notification) {
        return notificationMapper.insert(notification);
    }

}
