package cn.zzuisa.controller;

import cn.zzuisa.base.PageRequest;
import cn.zzuisa.base.R;
import cn.zzuisa.config.TokenManager;
import cn.zzuisa.entity.Files;
import cn.zzuisa.entity.Log;
import cn.zzuisa.entity.Type;
import cn.zzuisa.service.FilesService;
import cn.zzuisa.service.TypeService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author Ao
 * @date 2020-05-06 20:55
 */
@Api(value = "Type Operation Api")
@RestController
@RequestMapping("type")
public class TypeController {
    @Autowired
    TypeService typeService;
    @Autowired
    FilesService filesService;

    @GetMapping("list")
    public R page(HttpServletRequest request, PageRequest pageRequest) {
        IPage<Type> page = new Page<>(pageRequest.getCurrent(), pageRequest.getSize());
        IPage<Map<String, Object>> types = typeService.pageMaps(page, null);
        return R.ok(types);
    }
    @PostMapping("add")
    public R add(HttpServletRequest request,Type type) {
        type.setCreateTime(new Date());
        type.setUpdateTime(new Date());
        return R.ok(typeService.save(type));
    }
    @PutMapping("update")
    public R update(HttpServletRequest request,Type type) {
        type.setUpdateTime(new Date());
        if(typeService.updateById(type)){
            return R.ok("success");
        } else return R.error("failed");

    }
    @DeleteMapping("delete")
    public R delete(HttpServletRequest request, int id) {
        if (filesService.list(new QueryWrapper<Files>().eq("id", id)).size() > 0) {
            return R.error("sorry, you can't remove the type while you are using it.");
        }
        return R.ok(typeService.removeById(id));
    }
}
