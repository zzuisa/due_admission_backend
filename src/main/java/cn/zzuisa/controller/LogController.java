package cn.zzuisa.controller;


import cn.zzuisa.base.PageRequest;
import cn.zzuisa.base.R;
import cn.zzuisa.config.TokenManager;
import cn.zzuisa.entity.Account;
import cn.zzuisa.entity.Log;
import cn.zzuisa.entity.Student;
import cn.zzuisa.log.annotation.BussinessLog;
import cn.zzuisa.request.DissOtherDo;
import cn.zzuisa.request.LoginMember;
import cn.zzuisa.service.AccountService;
import cn.zzuisa.service.LogService;
import cn.zzuisa.service.StudentService;
import cn.zzuisa.utils.HostHolder;
import cn.zzuisa.utils.MailClient;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author frank
 * @since 2019-05-07
 */
@Api(tags = "logs operation API")
@RestController
@RequestMapping("/log")
public class LogController {

    @Autowired
    LogService logService;
    @Autowired
    HostHolder hostHolder;


    // 退出登录
    @GetMapping("/mylogs")
    public R mylogs(HttpServletRequest request,PageRequest pageRequest) {
        Integer userid = TokenManager.get(request.getHeader("token"));
        IPage<Log> page = new Page<>(pageRequest.getCurrent(), pageRequest.getSize());
        IPage<Map<String, Object>> logs = logService.pageMaps(page, new QueryWrapper<Log>().eq("user_id", userid).orderByDesc("create_time"));
        return R.ok(logs);
    }

}
