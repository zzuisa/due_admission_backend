package cn.zzuisa.service;

import cn.zzuisa.entity.Log;
import cn.zzuisa.entity.Notification;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * @author Ao
 * @date 2020-04-27 04:01
 */
public interface NotificationService extends IService<Notification> {
    IPage<Map<String, Object>> pageEntity(IPage<Map<String, Object>> page, Notification notification);

    int sendNotice(Notification notification);

}
