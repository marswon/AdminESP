package com.cnpc.admin.dictionary.controller;

import com.cnpc.admin.basic.BaseController;
import com.cnpc.admin.dictionary.service.IDictionaryService;
import com.cnpc.admin.entity.PageData;
import com.cnpc.admin.utils.JsonJackUtil;
import com.cnpc.admin.utils.NotNUllUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/admin/dictionary")
public class DictionaryController extends BaseController{
    @Resource(name="dictionaryService")
    private IDictionaryService dictionaryService;

    /**
     * 根据条件查询，条件为空，查询所有数据
     * @return
     * @throws Exception
     */
    @RequestMapping("/list")
    public String list() throws Exception {
        PageData pd  = this.getPageData();
        PageHelper.startPage(1, 10);
        List<PageData> lists = dictionaryService.find(pd);
        PageInfo pageInfo = new PageInfo(lists);
        return JsonJackUtil.ObjectToJson(pageInfo);
    }

    /**
     * 查询唯一编码
     * @return
     */
    @RequestMapping("/findOnlyCode")
    public String findOnlyCode() throws Exception {
        PageData pd = this.getPageData();
        String code = pd.getString("code");
        PageInfo pageInfo = null;
        if(NotNUllUtil.notNull(code)){
            PageHelper.startPage(1, 10);
            List<PageData> dataList = dictionaryService.findOnlyCode(pd);
            pageInfo = new PageInfo(dataList);
            if (dataList != null && dataList.size()==0){
                pd.put("res","success");
            }else {
                pd.put("res","error");
                pd.put("error","code不能唯一");
            }
        }else{
            pd.put("res","error");
            pd.put("error","code不能为空");
        }
        return  JsonJackUtil.ObjectToJson(pageInfo);
    }

    /**
     * 新增
     * @return
     */
    @RequestMapping("/save")
    public String save() throws Exception {
        PageData pd = this.getPageData();
        dictionaryService.save(pd);
        return null;
    }

    /**
     * 修改
     * @return
     * @throws Exception
     */
    @RequestMapping("/update")
    public String update() throws Exception {
        PageData pd = this.getPageData();
        dictionaryService.update(pd);
        return null;
    }

    /**
     * 根据Id删除
     * @return
     */
    @RequestMapping("/deleted")
    public String deleted() throws Exception {
        PageData pd = this.getPageData();
        dictionaryService.deletedById(pd);
        pd.put("res","success");
        pd.put("success","删除成功！");
        return JsonJackUtil.ObjectToJson(pd);
    }


}
