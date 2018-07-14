package com.nxist.controller.base;

import org.springframework.web.servlet.ModelAndView;

public class PageHandler {
	//处理页码，返回start(展示的第一条数据)
    public static int handle(Integer pageNo,Integer count,ModelAndView map){
        if (pageNo == null) {
            pageNo = 1;
        }
        Integer start = (pageNo - 1) * BaseData.PAGE_SIZE;
        // pageNo是当前所在的页数
        int i;// 计算页数
        if ((count % BaseData.PAGE_SIZE) == 0) {
            i = count / BaseData.PAGE_SIZE;
        } else {
            i = count / BaseData.PAGE_SIZE + 1;
        }
        // 页面获取的页数，大于查询得到的数据页数，就给pageNo取1
        if (pageNo > i) {
            pageNo = 1;
            start = 0;
        }
        map.addObject("recordCount", count);
        map.addObject("pageNo", pageNo);
        map.addObject("pageSize", BaseData.PAGE_SIZE);
        map.addObject("pageCount", i);
        return start;
    }
}
