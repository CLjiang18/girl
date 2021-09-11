package com.chen.controller;

import com.chen.dto.Exposer;
import com.chen.dto.SeckillExecution;
import com.chen.dto.SeckillResult;
import com.chen.enums.SeckillStaEnum;
import com.chen.exception.KillCloseException;
import com.chen.exception.RepeatKillException;
import com.chen.pojo.Seckill;
import com.chen.service.SeckillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/seckill")//url:模块/资源/{}/细分
public class SeckillController {
    private Logger logger= LoggerFactory.getLogger(this.getClass());
    @Autowired
    private SeckillService seckillService;
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String list(Model model)
    {
        //list.jsp+mode=ModelAndView
        //获取列表页
        List<Seckill> list=seckillService.getSeckillList();
        model.addAttribute("list",list);
        return "list";
    }
//    详情页
    @RequestMapping(value = "/{seckillId}/detail",method = RequestMethod.GET)
    public String detail(@PathVariable("seckillId") Long seckillId, Model model)
    {
        if (seckillId == null)
        {
            return "redirect:/seckill/list";
        }

        Seckill seckill=seckillService.getById(seckillId);
        if (seckill==null)
        {
            return "forward:/seckill/list";
        }

        model.addAttribute("seckill",seckill);

        return "detail";
    }
    @RequestMapping(value = "/{seckillId}/exposer",method = RequestMethod.POST,
                            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<Exposer> exposerSeckillResult(@PathVariable("seckillId")long seckillId){
        try{
            Exposer exposer = seckillService.exportSeckillUrl(seckillId);
            return new SeckillResult<Exposer>(true,exposer);
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            return new SeckillResult<Exposer>(false,e.getMessage());
        }
    }
    @RequestMapping(value = "/{seckillId}/{md5}/execution",
                    method = RequestMethod.POST,
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<SeckillExecution> execute(@PathVariable("seckillId") Long seckillId,
                                                   @PathVariable("md5") String md5,
                                                   @CookieValue(value = "userPhone",required = false) Long phone){
        //当验证信息很多时,可以用springmvc的验证
        if(phone == null){
            return new SeckillResult<SeckillExecution>(false,"未注册");
        }
        try{
            SeckillExecution seckillExecution = seckillService.executeSeckill(seckillId, phone, md5);
            return new SeckillResult<SeckillExecution>(true,seckillExecution);}
        catch (RepeatKillException e1){
            SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillStaEnum.REPEAT_KILL);
            return new SeckillResult<SeckillExecution>(true,seckillExecution);
        }
        catch (KillCloseException e2){
            SeckillExecution seckillExecution = new SeckillExecution(seckillId, SeckillStaEnum.END);
            return new SeckillResult<SeckillExecution>(true,seckillExecution);
        }
        catch (Exception e){
            logger.error(e.getMessage(),e);
            return new SeckillResult<SeckillExecution>(true,e.getMessage());
        }
    }
    //获取系统时间
    @RequestMapping(value = "/time/now",method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SeckillResult<Long> time()
    {
        Date now=new Date();
        return new SeckillResult<Long>(true,now.getTime());
    }
}
