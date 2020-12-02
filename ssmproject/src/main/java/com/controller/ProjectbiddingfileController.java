package com.controller;

import com.domain.Projectbiddingfile;
import com.service.ProjectbiddingfileService;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;

import java.io.IOException;


/**
 * 招标项目附件相关的controller处理类
 */
@Controller
public class ProjectbiddingfileController {
    @Resource(name = "projectbiddingfileServiceImpl")
    ProjectbiddingfileService projectbiddingfileService;

    /**
     * 招标项目附件文件下载
     *
     * @param response
     * @param id
     * @throws IOException
     */
    @RequestMapping("/projectbiddingfiledownload/{id}")
    public void projectbiddingfileDownload(HttpServletResponse response, @PathVariable(value = "id") int id) throws IOException {

        Projectbiddingfile projectbiddingfile = projectbiddingfileService.selectByPrimaryKey(id);//根据招标项目的附件id获取到附件对象

        response.setContentType("application/x-tar;charset=utf-8");
        response.setHeader("Content-disposition", "attachment;filename="
                + java.net.URLEncoder.encode(projectbiddingfile.getFilepath(), "UTF-8"));
        //获取要下载的文件的字节流 数组对象
        FileInputStream fileInputStream = new FileInputStream("D:\\新建文件夹\\" + projectbiddingfile.getFilepath());
        // byte[] bytes = FileCopyUtils.copyToByteArray(fileInputStream);//获取文件的字节数组对象
        FileCopyUtils.copy(fileInputStream, response.getOutputStream());//直接使用客服端的输出流输出要下载文件的输入流对象
    }

}
