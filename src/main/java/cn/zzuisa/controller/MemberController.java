package cn.zzuisa.controller;


import cn.zzuisa.base.R;
import cn.zzuisa.config.TokenManager;
import cn.zzuisa.entity.*;
import cn.zzuisa.log.annotation.BussinessLog;
import cn.zzuisa.mapper.StudentMapper;
import cn.zzuisa.request.DissOtherDo;
import cn.zzuisa.request.LoginMember;
import cn.zzuisa.service.*;
import cn.zzuisa.utils.HostHolder;
import cn.zzuisa.utils.MailClient;
import cn.zzuisa.utils.kit.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.*;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author frank
 * @since 2019-05-07
 */
@Api(tags = "users operation API")
@RestController
@RequestMapping("/member")
public class MemberController {

    @Autowired
    AccountService accountService;

    @Autowired
    MailClient mailClient;

    @Autowired
    TemplateEngine templateEngine;
    @Autowired
    StudentService studentService;
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    HostHolder hostHolder;


    @Value("${due.path.domain}")
    private String domain;

    @Value("")
    private String contextPath;

    //登录
    @PostMapping("/login")
    @BussinessLog(value = "Login#account")
    public R login(@RequestBody LoginMember member, HttpServletRequest request) {
        QueryWrapper<Account> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", member.getUsername())
                .eq("password", member.getPassword());
        Account one = accountService.getOne(queryWrapper);
        if (one == null) {
            return R.error("账号或密码错误");
        } else if (!one.getActivationCode().equals("ACTIVATED")) {
            return R.error(3,"未激活!");
        }

//        if(one.getStatus() != 0) {
//            return R.error("账号被限制");
//        }
        String token = UUID.randomUUID().toString();
        hostHolder.setUser(one);
        TokenManager.put(token, one.getId());
        HashMap<String, Object> map = new HashMap<>();
        if (one.getStudentId() != 0) {
            Student byId = studentService.getById(one.getStudentId());
            map.put("student", byId);
        }
        map.put("token", token);
        map.put("member", one);
        return R.ok(map);
    }

    // 注册账号
    @PostMapping("/join")
    public R join(@RequestBody Account account) {

        if (account.getUsername() == null) {
            return R.error("账号不能为空");
        }
        if (account.getEmail() == null) {
            return R.error("邮箱不能为空");
        }
        if (account.getPassword() == null) {
            return R.error("密码不能为空");
        }

        Account one = accountService.getOne(new QueryWrapper<Account>().eq("username", account.getUsername()));
        if (one != null) {
            return R.error("用户名已存在");
        }
        // 激活邮件
        Context context = new Context();
        context.setVariable("email", account.getEmail());
        // http://localhost:8080/community/activation/101/code
        account.setActivationCode("DUE" + (UUID.randomUUID().toString() + UUID.randomUUID()).replace("-", ""));
        account.setCreateTime(new Date());
        Student student = new Student();
        student.setAvatar("https://preview.pro.loacg.com/avatar2.jpg");
        studentService.save(student);
        account.setStudentId(student.getId());
        accountService.save(account);
        studentService.updateById(student.setUId(account.getId()));
        System.out.printf("ac:"+student.getId());

        one = accountService.getOne(new QueryWrapper<Account>().eq("username", account.getUsername()));
        String url = domain + contextPath + "/member/active/" + one.getId() + "/" + one.getActivationCode();
        context.setVariable("url", url);
        String content = templateEngine.process("/mail/activation", context);
        mailClient.sendMail(one.getEmail(), "Activate the account", content);
        return R.ok(account);
    }

    //修改密码
    @PutMapping("/password")
    @BussinessLog(value = "Account#update password")
    public R updatePassword(String password, HttpServletRequest request) {
        return R.ok("1");
//        Integer id = TokenManager.get(request.getHeader("token"));
//        Account account = accountService.getById(id);
//        account.setPassword(password);
//        accountService.updateById(account);
//        return R.ok("修改成功");
    }


    // 修改个人信息 头像 邮箱 电话 性别 昵称
    @PutMapping("/new")
    @BussinessLog(value = "Account#update profile")
    public R update(@RequestBody Student account, HttpServletRequest request) {
        Integer userId = TokenManager.get(request.getHeader("token"));
        Student student1 = studentService.getOne(new QueryWrapper<Student>().eq("u_id", userId));
        student1.setPhone(account.getPhone());
        student1.setNationality(account.getNationality());
        student1.setGender(account.getGender());
        student1.setName(account.getName());
        student1.setAddress(account.getAddress());
        student1.setCet4(account.getCet4());
        student1.setCet6(account.getCet6());
        student1.setApsAuthFile(account.getApsAuthFile());
        student1.setPassport(account.getPassport());
        student1.setBirthday(account.getBirthday());
        student1.setGerExam(account.getGerExam());
        student1.setExamAuthFile(account.getExamAuthFile());
        student1.setApsid(account.getApsid());
        student1.setApsPassed(account.getApsPassed());
        student1.setAvatar(account.getAvatar());
        student1.setSaved("y");
        studentService.updateById(student1);
        return R.ok(student1);
    }

    // 退出登录
    @GetMapping("/out")
    @BussinessLog(value = "Logout#user logout")
    public R<String> logout(HttpServletRequest request) {
        TokenManager.rm(request.getHeader("token"));
        return R.ok("退出登路");
    }

    @GetMapping("/active/{userId}/{code}")
    @BussinessLog(value = "Account#user get activation")
    public R active(@PathVariable("userId") int userId, @PathVariable("code") String code) {
        R r = accountService.active(userId, code);
        if (r.getCode() == 0) {
            return R.ok("success");
        } else {
            return R.error(r.getContent());
        }
    }

    @GetMapping("/getfiles")
    public R getFiles(HttpServletRequest request) {
        Integer uid = TokenManager.get((request.getHeader("token")));
        Map<String, Object> files = studentService.getFiles(uid);
        return R.ok(files);
    }
}
