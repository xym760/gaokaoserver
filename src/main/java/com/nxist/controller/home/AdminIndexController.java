package com.nxist.controller.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping(value="/admin")
public class AdminIndexController {
	
	@RequestMapping(value="/index")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView();
        mav.addObject("index","请在右侧选择项进行管理！" );
        mav.setViewName("jsp/hint");
        return mav;
	}

}
