package com.nxist.controller.personalcenter;

import java.io.File;
import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.nxist.controller.base.BaseUtil;
import com.nxist.dao.UserDao;
import com.nxist.model.Feedback;
import com.nxist.model.Message;
import com.nxist.model.User;

/**
 * 用户控制器
 * @author 徐源茂
 *
 */
@Controller
@RequestMapping(value="/user")
public class UserController {
	@Resource
	private UserDao userDao;
	
	@RequestMapping("/view")
	public String view() {
		return "main/login";
	}
	
	/**
	 * 用户登录
	 * @param model Android端发送的JSON对象
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/login",method=RequestMethod.POST,produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String login(@RequestBody User model,HttpSession session) {
		System.out.println("You come in......."+model.getUserAccount());
		User user=userDao.findByUserAccount(model.getUserAccount());
		if(user==null||!user.getPassword().equals(model.getPassword())) {
			model=null;
			return JSON.toJSONString(model);
		}else {
			//在Session里保存信息,用于验证用户是否登录  
	        session.setAttribute("userId", user.getUserId());
	        if(user.getAvatar()==null)
	        	user.setAvatar("");
	        if(user.getProvince()==null)
	        	user.setProvince("");
	        if(user.getSubject()==null)
	        	user.setSubject("");
	        if(user.getScore()==null)
	        	user.setScore(0);
			return JSON.toJSONString(user);
		}
	}
	
	@RequestMapping(value="/register",method=RequestMethod.POST)
	@ResponseBody
	public int register(@RequestBody User model,HttpSession session) {
		System.out.println("You come register......."+model.getUserAccount());
		User user=userDao.findByUserAccount(model.getUserAccount());
		if(user==null) {//如果该账户未注册
			User temp=model;
			//如果账户和密码不为空
			if(!temp.getUserAccount().equals("")&&temp.getUserAccount()!=null&&!temp.getPassword().equals("")&&temp.getPassword()!=null) {
				if(temp.getAvatar()==null||temp.getAvatar().equals(""))
					temp.setAvatar("a");
				if(temp.getUserName()==null||temp.getUserName().equals(""))
					temp.setUserName("未命名用户");
				if(temp.getProvince()==null||temp.getProvince().equals(""))
					temp.setProvince("北京");
				if(temp.getScore()==null)
					temp.setScore(0);
				if(temp.getSubject()==null||temp.getSubject().equals(""))
					temp.setSubject("理科");
				userDao.insertUser(temp);
			}
			return temp.getUserId();//返回插入的ID
		}else {//该用户已注册，则向前台发送-1
			return -1;
		}
	}
	
	/**
	 * 修改用户信息
	 * @param model
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/update",method=RequestMethod.POST)
	@ResponseBody
	public int updateUser(@RequestBody User model,HttpSession session) {
		System.out.println("You come update......."+model.getUserAccount());
		User user=userDao.findByUserAccount(model.getUserAccount());
		if(user==null) {//如果不存在该用户
			return -1;//表示更新失败
		}else {//存在该用户
			if(user.getPassword().equals(model.getPassword())){//如果密码正确
				userDao.updateUser(model);
				return 1;
			}else {
				return -1;//
			}
		}
	}
	
	@RequestMapping(value="/logout")  
    public String logout(HttpSession session) throws Exception{  
        //清除Session  
        session.invalidate();
        return "redirect:/jsp/login.jsp";  
    }
	
	@RequestMapping(value="/getUserInfo",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getUserInfo(HttpServletResponse response,Integer userId) throws IOException {
		System.out.println("用户Id......."+userId);
		User user=userDao.findUserByUserId(userId);
        String jsonString = JSON.toJSONString(user);
        return jsonString;
	}
	
	/**
	 * 上传用户头像图片最多1张
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping(value="/insertUserAvatarkPicture",method=RequestMethod.POST)
	@ResponseBody
    public void insertUserAvatarkPicture(MultipartFile imgfile0) throws IOException {
		File dir = new File("C:\\userpic\\avatar\\");
		//如果文件夹不存在,创建目录
        if (!dir.exists()){
            dir.mkdirs();
        }
		if(imgfile0!=null) {
			convertMultipartFileToFile(imgfile0,dir);
		}
    }
	
	/**
	 * 将MultipartFile转换成文件到指定目录下
	 * @param multipartFile 要转换的 MultipartFile
	 * @param dir 要存放的目录
	 * @throws IllegalStateException
	 * @throws IOException
	 */
	private void convertMultipartFileToFile(MultipartFile multipartFile,File dir) throws IllegalStateException, IOException {
		// 获取文件名
        String fileName = multipartFile.getOriginalFilename();
        // 获取文件后缀
        ///String suffix=fileName.substring(fileName.lastIndexOf("."));
        // 用原文件名作为文件名，防止生成的临时文件重复
        //File file = File.createTempFile(fileName.substring(0, fileName.lastIndexOf(".")),suffix, dir);
        // MultipartFile to File
        File file=new File(dir.getAbsolutePath()+"\\\\"+fileName);
        multipartFile.transferTo(file);
        String avatarIdString=fileName.substring(0,fileName.indexOf("_"));
        System.out.println("用户头像："+fileName);
        User user=new User();
        user.setUserId(Integer.parseInt(avatarIdString));
        user.setAvatar(fileName.substring(fileName.indexOf("_")+1,fileName.length()));
        //删除之前的头像
        User tempUser=userDao.findUserByUserId(user.getUserId());
        BaseUtil.delete("C:/userpic/avatar/"+user.getUserId()+"_"+tempUser.getAvatar());
        userDao.updateAvatar(user);
        System.out.println("数据库中头像名："+user.getAvatar());
        System.out.println("已添加用户头像："+file.getName());
	}

}
