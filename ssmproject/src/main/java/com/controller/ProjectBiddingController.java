package com.controller;

import com.domain.Bid;
import com.domain.Projectbidding;
import com.domain.Projectbiddingfile;
import com.domain.Users;
import com.service.BidService;
import com.service.ProjectbiddingService;
import com.service.ProjectbiddingfileService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * 招标项目相关
 */
@Controller
public class ProjectBiddingController {

    @Resource(name ="projectbiddingServiceImpl" )
    ProjectbiddingService projectbiddingService;

    @Resource(name = "projectbiddingfileServiceImpl")
    ProjectbiddingfileService projectbiddingfileService;

    @Resource(name = "bidServiceImpl")
    BidService bidService;

    /**
     * 增加公开招标，增加一个招标项目
     * @return
     */
    @RequestMapping("/insertProjectbidding")
    public String insertProjectbidding(HttpServletRequest request, Projectbidding projectbidding, @RequestParam("file") MultipartFile[] file) throws IOException {
        projectbidding.setBz("0");
        Users user = (Users) request.getSession().getAttribute("user");//获取当前登陆的用户对象
        projectbidding.setOwner(user.getId());
        projectbiddingService.insert(projectbidding);//保存招标项目,并且回写主键id的值,在bean对象里面id属性上加上@GeneratedValue(generator = "JDBC")
        for (MultipartFile multipartFile : file) {
            //保存文件信息
            Projectbiddingfile projectbiddingfile = new Projectbiddingfile();
            projectbiddingfile.setProjectbiddingid(projectbidding.getId());
            projectbiddingfile.setFilepath(multipartFile.getOriginalFilename());
            projectbiddingfileService.insert(projectbiddingfile);
            multipartFile.transferTo(new File("D:\\新建文件夹\\"+multipartFile.getOriginalFilename()));
        }
        // System.out.println(projectbidding.getEndtime());
        request.setAttribute("href","/jsp/insertprojectbidding.jsp");
        return "commonmessage";
    }

    /**
     * 发布公开招标，查询用户增加的招标项目，待发布的招标项目
     * @return
     */
    @RequestMapping("/selectProjectbidding")
    public String selectProjectbidding(HttpServletRequest request){
        Users user = (Users) request.getSession().getAttribute("user");//获取当前登陆的用户
        Projectbidding projectbidding=new Projectbidding();
        projectbidding.setOwner(user.getId());
        projectbidding.setBz("0");
        List<Projectbidding> projectbiddings = projectbiddingService.selectAll2(projectbidding);
        request.setAttribute("projectbiddings",projectbiddings);
        return "selectprojectbidding";
    }

    /**
     * 查询正在进行的招标项目，发布成功的项目（与用户有关系）
     * @param request
     * @return
     */
    @RequestMapping("/selectNowProjectbidding")
    public String selectNowProjectbidding(HttpServletRequest request){
        Users user = (Users) request.getSession().getAttribute("user");//获取当前登陆的用户
        Projectbidding projectbidding=new Projectbidding();
        projectbidding.setOwner(user.getId());
        projectbidding.setBz("1");
        List<Projectbidding> projectbiddings = projectbiddingService.selectAll2(projectbidding);
        request.setAttribute("projectbiddings",projectbiddings);
        return "selectnowprojectbidding";
    }

    /**
     * 确认发布招标按钮操作
     * @param request
     * @param projectbidding
     * @return
     */
    @RequestMapping("/doProjectbidding")
    public String doProjectbidding(HttpServletRequest request,Projectbidding projectbidding){
        projectbidding.setBz("1");
        Calendar calendar =new GregorianCalendar();

        projectbidding.setReleasetime(calendar.get(Calendar.YEAR)+"-"+(calendar.get(Calendar.MONTH)+1)+"-"+calendar.get(Calendar.DATE));//设置发布时间
        projectbiddingService.updateByPrimaryKeySelective(projectbidding);
        request.setAttribute("href","/selectProjectbidding");
        return "commonmessage";
    }

    /**
     * 首页查询正在进行的招标项目（与用户没有关系）
     * @param request
     * @return
     */
    @RequestMapping("/selectNowProjectbiddingWeb")
    public String selectNowProjectbiddingWeb(HttpServletRequest request){
        Projectbidding projectbidding=new Projectbidding();
        projectbidding.setBz("1");
        List<Projectbidding> projectbiddings = projectbiddingService.selectAll(projectbidding);
        request.setAttribute("projectbiddings",projectbiddings);
        return "/web/selectnowprojectbiddingweb";
    }

    /**
     *系统员工后台的  开标项目  菜单
     * @param request
     * @return
     */
    @RequestMapping("/selectProjectbiddingIsOpen")
    public String selectProjectbiddingIsOpen(HttpServletRequest request){
        Projectbidding projectbidding=new Projectbidding();
        projectbidding.setBz("1");//已经发布的，正在进行中的招标项目
        List<Projectbidding> projectbiddings = projectbiddingService.selectAll3(projectbidding);//获取开标的项目
        request.setAttribute("projectbiddings",projectbiddings);
        return "selectprojectbiddingisopen";
    }

    /**
     * 开始开标项目
     * 点击开始开标后查询该招标项目信息和该项目附件以及投了改标的供应商相关信息以和供应商相关附件
     * @param request
     * @return
     */
    @RequestMapping("/projectbiddingIsOpen")
    public String projectbiddingIsOpen(HttpServletRequest request,Projectbidding projectbidding){
        // Bid bid = new Bid();
        // bid.setProjectbiddingid(id);
        //1、根据招标项目的id获取这个招标项目相关信息
        List<Projectbidding> projectbiddings = projectbiddingService.selectAll2(projectbidding);//查询指定id的招标项目信息及附件

        //2、获取到这个招标项目的投标的相关供应商信息及投标信息
        List<Bid> bids = bidService.selectByProjectbiddingid(projectbidding.getId());


        request.setAttribute("projectbiddings",projectbiddings);//把当前的这个招标项目信息显示
        request.setAttribute("bids",bids);//把当前的这个投标项目信息显示
        return "projectbiddingisopen";
    }

    /**
     * 确认中标的供应商
     * @return
     */
    @RequestMapping(name = "/setBigwinner")
    public String setBigwinner(HttpServletRequest request,Integer supplierid,Integer projectbiddingid){
        Projectbidding projectbidding = new Projectbidding();

        projectbidding.setBigwinner(supplierid);
        projectbidding.setId(projectbiddingid);
        projectbidding.setBz("2");//表示结束的招标项目
        projectbiddingService.updateByPrimaryKeySelective(projectbidding);//修改招标项目表里面的招标项目的中标供应商
        request.setAttribute("href","/selectProjectbiddingIsOpen");
        return "commonmessage";
    }
}
