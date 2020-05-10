package cn.zzuisa.controller;


import cn.zzuisa.base.PageRequest;
import cn.zzuisa.base.R;
import cn.zzuisa.config.TokenManager;
import cn.zzuisa.entity.Log;
import cn.zzuisa.entity.Notification;
import cn.zzuisa.log.annotation.BussinessLog;
import cn.zzuisa.service.LogService;
import cn.zzuisa.service.NotificationService;
import cn.zzuisa.utils.HostHolder;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ao
 * @since 2020-04-27
 */
@Api(tags = "notification operation API")
@RestController
@RequestMapping("/notice")
public class NotificationController {

    @Autowired
    NotificationService notificationService;
    @Autowired
    HostHolder hostHolder;


    // 查看我的消息
    @GetMapping("/page")
    public R page(HttpServletRequest request, PageRequest pageRequest, Notification notification) {
        Integer userid = TokenManager.get(request.getHeader("token"));
        notification.setUserId(userid);
        IPage<Map<String, Object>> page = new Page<>(pageRequest.getCurrent(), pageRequest.getSize());
        IPage<Map<String, Object>> mapIPage = notificationService.pageEntity(page, notification);
        return R.ok(mapIPage);
    }

    @PostMapping("/send")
    @BussinessLog(value = "send notification#operation")
    public R send(HttpServletRequest request, @RequestBody Notification notification) {
        notification.setCreateTime(new Date());
        notification.setIsread("0");
        int k = notificationService.sendNotice(notification);
        if (k > 0) {
            return R.ok("success");
        } else {
            return R.error("operation failed");
        }
    }

    @PutMapping("/update")
    public R update(HttpServletRequest request, @RequestBody Notification notification) {
        return R.ok(notificationService.updateById(notification));
    }

}
