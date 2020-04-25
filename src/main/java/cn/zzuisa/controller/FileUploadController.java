package cn.zzuisa.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.zzuisa.base.R;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "文件上传接口")
@Controller
@RequestMapping("/file")
public class FileUploadController {
    private static final String[] IMGTYPE = {
            ".jpg",".icon",".png",".jpeg",".gif"
    };
    static String domain = "http://127.0.0.1:888/";
    @ResponseBody
    @ApiOperation("图片文件上传")
    @PostMapping("/img")
    public R<String> imgUpload(MultipartFile file, HttpServletRequest request) throws IllegalStateException, IOException {
        if(file == null) {
            return R.error("上传文件为空");
        }
        if(file.getSize() > 1024 * 1024 * 10) {
            return R.error("上传文件限定10M");
        }
        String fileName = file.getOriginalFilename();
        String fileNameNoSuffix = UUID.randomUUID().toString().replaceAll("-", "");
        // 文件后缀
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        for (String string : IMGTYPE) {
            if(string.equals(suffix.toLowerCase())) {
                return common(request,file,fileNameNoSuffix,suffix);
            }
        }
        return R.error("文件类型限定为.jpg,.icon,.png,.jpeg,.gif");
    }

    public R<String> common(HttpServletRequest request,MultipartFile file,String fileNameNoSuffix,String suffix) throws IOException {
        String realPath = request.getServletContext().getRealPath("/imgupload");
        // 存储路径
        File fileUploadPath = new File(realPath);
        if(!fileUploadPath.exists()) {
            fileUploadPath.mkdirs();
        }
        File targetFile = new File(fileUploadPath + "/" + fileNameNoSuffix + suffix);
        file.transferTo(targetFile);
        return R.ok(domain + "/file/imgupload/" + targetFile.getName());
    }
    @ResponseBody
    @ApiOperation("通用文件上传")
    @PostMapping("/")
    public R<String> fileUpload(MultipartFile file,HttpServletRequest request) throws IllegalStateException, IOException {
        if(file == null) {
            return R.error("上传文件为空");
        }
        if(file.getSize() > 1024 * 1024 * 10) {
            return R.error("上传文件过大");
        }
        String fileName = file.getOriginalFilename();
        String fileNameNoSuffix = UUID.randomUUID().toString().replaceAll("-", "");
        // 文件后缀
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        return common(request,file,fileNameNoSuffix,suffix);
    }

    @ResponseBody
    @GetMapping(value = "/imgupload/{path}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void img(HttpServletRequest request, HttpServletResponse response, @PathVariable String path) throws IOException {
        String realPath = request.getServletContext().getRealPath("/imgupload") + "/" + path ;
        System.out.println(realPath);
        File f = new File(realPath);
        System.out.println("存在吗" + f.exists());
        ImageIO.write(ImageIO.read(f),"gif",response.getOutputStream());
    }



}
