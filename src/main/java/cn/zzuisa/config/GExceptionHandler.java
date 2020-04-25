package cn.zzuisa.config;

import cn.zzuisa.base.R;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GExceptionHandler {

    @ExceptionHandler(UnloginException.class)
    public R<String> unLogin() {
        return R.unLogin("未登录，请登录之后再操作");
    }

}
