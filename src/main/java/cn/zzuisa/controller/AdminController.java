package cn.zzuisa.controller;

import cn.zzuisa.base.PageRequest;
import cn.zzuisa.base.R;
import cn.zzuisa.config.TokenManager;
import cn.zzuisa.entity.Student;
import cn.zzuisa.log.annotation.BussinessLog;
import cn.zzuisa.service.StudentService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author Ao
 * @date 2020-04-28 02:48
 */
@Api(tags = "admin operation API")
@RestController
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    StudentService studentService;

    @GetMapping("/page")
    public R page(PageRequest pageRequest, Student student) {
        System.out.printf("sP:"+student.toString()
        );
        IPage<Map<String, Object>> page = new Page<>(pageRequest.getCurrent(), pageRequest.getSize());
        IPage<Map<String, Object>> mapIPage = studentService.pageEntity(page, student);
        return R.ok(mapIPage);
    }
}
