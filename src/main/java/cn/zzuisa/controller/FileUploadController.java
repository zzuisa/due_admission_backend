package cn.zzuisa.controller;

import java.io.*;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.zzuisa.base.R;
import cn.zzuisa.config.TokenManager;
import cn.zzuisa.entity.Account;
import cn.zzuisa.log.annotation.BussinessLog;
import cn.zzuisa.service.AccountService;
import cn.zzuisa.utils.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "files upload API")
@Controller
@RequestMapping("/file")
public class FileUploadController {
    @Autowired
    AccountService accountService;
    private static final String[] IMGTYPE = {
            ".jpg", ".icon", ".png", ".jpeg", ".gif"
    };
    static String domain = "http://127.0.0.1:888";

    @ResponseBody
    @ApiOperation("图片文件上传")
    @PostMapping("/img")
    public R<String> imgUpload(MultipartFile file, HttpServletRequest request) throws IllegalStateException, IOException {
        if (file == null) {
            return R.error("上传文件为空");
        }
        if (file.getSize() > 1024 * 1024 * 10) {
            return R.error("上传文件限定10M");
        }
        String fileName = file.getOriginalFilename();
        String fileNameNoSuffix = UUID.randomUUID().toString().replaceAll("-", "");
        // 文件后缀
        String suffix = "";
        try {
            suffix = fileName.substring(fileName.lastIndexOf("."));
        } catch (Exception e) {
            suffix = ".jpg";
        }
        for (String string : IMGTYPE) {
            if (string.equals(suffix.toLowerCase())) {
                return common(request, file, fileNameNoSuffix, suffix);
            }
        }
        return R.error("文件类型限定为.jpg,.icon,.png,.jpeg,.gif");
    }
    @BussinessLog(value = "Account#user uploads files or images")
    public R<String> common(HttpServletRequest request, MultipartFile file, String fileNameNoSuffix, String suffix) throws IOException {
//        String realPath = request.getServletContext().getRealPath("/fileupload");
        String t = "/Users/frank/Desktop/bachelor-title/due_admission_backend/fileupload";
        String realPath = "/Users/frank/Desktop/bachelor-title/due_admission_backend/fileupload/";
        // 存储路径
        File fileUploadPath = new File(realPath);
        if (!fileUploadPath.exists()) {
            fileUploadPath.mkdirs();
        }
        File targetFile = new File(fileUploadPath + "/" + fileNameNoSuffix + suffix);
        file.transferTo(targetFile);
        return R.ok("/file/fileupload/" + targetFile.getName());
    }

    @ResponseBody
    @ApiOperation("通用文件上传")
    @PostMapping("/common")
    public R<String> fileUpload(MultipartFile file, HttpServletRequest request) throws IllegalStateException, IOException {
        if (file == null) {
            return R.error("上传文件为空");
        }
        if (file.getSize() > 1024 * 1024 * 10) {
            return R.error("上传文件过大");
        }
        String fileName = file.getOriginalFilename();
        String u = "";
        Integer userId = TokenManager.get(request.getHeader("token"));
        Account a = accountService.getById(userId);
        if (a != null) {
            u = a.getUsername();
        }
        String fileNameNoSuffix = u + UUID.randomUUID().toString().replaceAll("-", "");
        // 文件后缀
        String suffix = fileName.substring(fileName.lastIndexOf("."));
        return common(request, file, fileNameNoSuffix, suffix);
    }

    @ResponseBody
    @GetMapping(value = "/fileupload/{path}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void img(HttpServletRequest request, HttpServletResponse response, @PathVariable String path) throws IOException {
        String t = "/Users/frank/Desktop/bachelor-title/due_admission_backend/fileupload";
        String realPath = "/Users/frank/Desktop/bachelor-title/due_admission_backend/fileupload/" + path;
//        String realPath = request.getServletContext().getRealPath("/fileupload") + "/" + path ;
        System.out.println(realPath);
        File f = new File(realPath);
        System.out.println("存在吗" + f.exists());
        if (f.exists()) {
            response.setContentType("application/force-download");// 设置强制下载不打开
            response.addHeader("Content-Disposition", "attachment;fileName=" + f.getName());// 设置文件名
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            try {
                fis = new FileInputStream(f);
                bis = new BufferedInputStream(fis);
                OutputStream os = response.getOutputStream();
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer, 0, i);
                    i = bis.read(buffer);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (bis != null) {
                    try {
                        bis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
//        ImageIO.write(ImageIO.read(f),"gif",response.getOutputStream());
    }


}
