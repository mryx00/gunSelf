package com.stylefeng.gunSelf.modular.blog.controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/play")
public class PlayController {

    private String musicFileUploadPath = System.getProperty("user.dir")+"/music/";

    @RequestMapping(value = "/{id}")
    public ModelAndView getAudio(@PathVariable String id, HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        System.out.println("id: " + id);
        String range = request.getHeader("Range");
        if (range!=null){
            String[] rs = range.split("\\=");
            range = rs[1].split("\\-")[0];
        }else{
            range = "0";
        }

        //String path = request.getServletContext().getRealPath("/");
        File file = new File(musicFileUploadPath + id + ".mp3");
        if(!file.exists()) throw new RuntimeException("音频文件不存在 --> 404");
        OutputStream os = response.getOutputStream();
        FileInputStream fis = new FileInputStream(file);
        long length = file.length();
        System.out.println("file length : " + length);
        // 播放进度
        int count = 0;
        // 播放百分比
        int percent = (int)(length * 1.0);
        int irange = Integer.parseInt(range);
        length = length - irange;
        response.addHeader("Accept-Ranges", "bytes");
        response.addHeader("Content-Length", length + "");
        response.addHeader("Content-Range", "bytes " + range + "-" + length + "/" + length);
        response.addHeader("Content-Type", "audio/mpeg;charset=UTF-8");
        int len = 0;
        byte[] b = new byte[1024];
        while ((len = fis.read(b)) != -1) {
            os.write(b, 0, len);
            count += len;
            if(count >= percent){
                break;
            }
        }
        System.out.println("count: " + count + ", percent: " + percent);
        fis.close();
        os.close();
        return null;
    }

}



