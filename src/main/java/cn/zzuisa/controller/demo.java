package cn.zzuisa.controller;

import cn.zzuisa.utils.kit.HashKit;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @author Ao
 * @date 2020-05-14 16:56
 */
public class demo {
    public static void main(String[] args) {
        String salt = HashKit.generateSalt(16);
        System.out.println(HashKit.generateSalt(16));
        System.out.println(HashKit.md5("123bdd6797702c2412c7bd9816d8b311252"));
        System.out.println(((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()));


    }
}
