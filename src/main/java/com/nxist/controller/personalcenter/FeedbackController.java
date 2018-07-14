package com.nxist.controller.personalcenter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.nxist.dao.FeedbackDao;
import com.nxist.dao.UserDao;
import com.nxist.model.Feedback;
import com.nxist.model.User;

@Controller
@RequestMapping(value="/personalcenter")
public class FeedbackController {
	
	@Resource
	private FeedbackDao feedbackDao;
	@Resource
	private UserDao userDao;
	
	@RequestMapping(value="/insertFeedbackWithoutPictue",method=RequestMethod.POST)
	@ResponseBody
    public int insertFeedbackWithoutPictue(@RequestBody Feedback feedback) {
		feedback.setFeedbackDate(new Date());
		System.out.println("上传反馈图片文件名是："+feedback.getPicture());
		feedbackDao.insertFeedbackWithoutPicture(feedback);
		return feedback.getFeedbackId();
    }
	
	/**
	 * 上传话题图片最多1张
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping(value="/insertFeedbackPicture",method=RequestMethod.POST)
	@ResponseBody
    public void insertTopicPicture(MultipartFile imgfile0) throws IOException {
		File dir = new File("C:\\userpic\\feedbackpic\\");
		//如果文件夹不存在,创建目录
        if (!dir.exists()){
            dir.mkdirs();
        }
		if(imgfile0!=null) {
			convertMultipartFileToFile(imgfile0,dir);
		}
    }
	/**
	 * 获取话题图片
	 * @param response
	 * @param picture
	 * @throws IOException
	 */
	@RequestMapping("/getPicture")  
	public void getPicture(HttpServletResponse response,String picture) throws IOException {          
	    FileInputStream fis = null;  
	    File file = new File("C://userpic/"+picture);  
	    //File file = new File("home/images/test.png"); 服务器目录和本地图片的区别是图片路径  
	    fis = new FileInputStream(file);  
	    response.setContentType("image/jpg"); //设置返回的文件类型     
	    response.setHeader("Access-Control-Allow-Origin", "*");//设置该图片允许跨域访问  
	    IOUtils.copy(fis, response.getOutputStream());   
	}
	
	/**
	 * 获取用户头像
	 * @param response
	 * @param picture
	 * @throws IOException
	 */
	@RequestMapping("/getUserAvatar")  
	public void getUserAvatar(HttpServletResponse response,String picture) throws IOException {          
	    FileInputStream fis = null;  
	    File file = new File("C://userpic//avatar/"+picture);  
	    //File file = new File("home/images/test.png"); 服务器目录和本地图片的区别是图片路径  
	    fis = new FileInputStream(file);  
	    response.setContentType("image/jpg"); //设置返回的文件类型     
	    response.setHeader("Access-Control-Allow-Origin", "*");//设置该图片允许跨域访问  
	    IOUtils.copy(fis, response.getOutputStream());
	    //关闭流
        if (fis != null) {
        	fis.close();
        }
	}
	
	/**
	 * 获取所有话题数据
	 * @param user
	 * @return
	 * @throws IOException
	 */
	/*@RequestMapping(value="/getTopicData",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getTopicData(HttpServletResponse response,HttpServletRequest request) throws IOException {
		System.out.println("空间用户名："+request.getParameter("userAccount"));
		User user=userDao.findByUserAccount(request.getParameter("userAccount"));
		if(user==null)
			user=new User();
        List<TopicData> topicDataList=topicDao.findAllTopic();
        int size=topicDataList.size();
        for(int i=0;i<size;i++) {
        	if(topicDao.isLike(user.getUserId(), topicDataList.get(i).getTopicId())==0)
        		topicDataList.get(i).setIsLike(false);
        	else
        		topicDataList.get(i).setIsLike(true);
        }
        String jsonString = JSON.toJSONString(topicDataList);
        return jsonString;
	}*/
	
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
        String feedbackString=fileName.substring(0,fileName.indexOf("_"));
        System.out.println("待修改反馈图片："+fileName);
        Feedback feedback=new Feedback();
        feedback.setFeedbackId(Integer.parseInt(feedbackString));
        feedback.setPicture(fileName.substring(fileName.indexOf("_")+1,fileName.length()));
        feedbackDao.updatePicture(feedback);
        System.out.println("已添加反馈图片："+file.getName());
	}
}
