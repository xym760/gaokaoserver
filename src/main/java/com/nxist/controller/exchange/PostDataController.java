package com.nxist.controller.exchange;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.nxist.dao.TopicDao;
import com.nxist.dao.UserDao;
import com.nxist.model.Topic;
import com.nxist.model.TopicData;
import com.nxist.model.User;

@Controller
@RequestMapping(value="/exchange")
public class PostDataController {
	
	@Resource
	private TopicDao topicDao;
	@Resource
	private UserDao userDao;
	
	@RequestMapping(value="/insertTopicWithoutPictue",method=RequestMethod.POST)
	@ResponseBody
    public int insertTopicWithoutPicture(@RequestBody TopicData topicData) {
		Topic topic=new Topic();
		topic.setUserId(topicData.getUserId());
		topic.setContent(topicData.getContent());
		topic.setDate(new Date());
		topic.setLocation("宁夏");
		System.out.println("发表用户是："+topicData.getUserId());
		if(topicData.getIsHavePicture()) {//如果有图返回插入话题后的ID
			String pictureList="";
			for(String s:topicData.getPictureList()) {
				pictureList+=s+",";
			}
			System.out.println("图片List："+pictureList);
			topic.setPictureList(pictureList);
			topicDao.insertTopicWithoutPicture(topic);
			System.out.println("插入后的ID："+topic.getTopicId());
			return topic.getTopicId();
		}else {
			System.out.println("没有图片List：");
			topicDao.insertTopicWithoutPicture(topic);
			System.out.println("插入后的ID："+topic.getTopicId());
			return topic.getTopicId();
		}
    }
	
	/**
	 * 上传话题图片最多九张
	 * @param request
	 * @param response
	 * @throws IOException 
	 */
	@RequestMapping(value="/insertTopicPicture",method=RequestMethod.POST)
	@ResponseBody
    public void insertTopicPicture(MultipartFile imgfile0,MultipartFile imgfile1,
    		MultipartFile imgfile2,MultipartFile imgfile3,MultipartFile imgfile4,
    		MultipartFile imgfile5,MultipartFile imgfile6,MultipartFile imgfile7,
    		MultipartFile imgfile8) throws IOException {
		File dir = new File("C:\\userpic\\");
		//如果文件夹不存在,创建目录
        if (!dir.exists()){
            dir.mkdirs();
        }
		if(imgfile0!=null) {
			convertMultipartFileToFile(imgfile0,dir);
		}
		if(imgfile1!=null) {
			convertMultipartFileToFile(imgfile1,dir);
		}
		if(imgfile2!=null) {
			convertMultipartFileToFile(imgfile2,dir);
		}
		if(imgfile3!=null) {
			convertMultipartFileToFile(imgfile3,dir);
		}
		if(imgfile4!=null) {
			convertMultipartFileToFile(imgfile4,dir);
		}
		if(imgfile5!=null) {
			convertMultipartFileToFile(imgfile5,dir);
		}
		if(imgfile6!=null) {
			convertMultipartFileToFile(imgfile6,dir);
		}
		if(imgfile7!=null) {
			convertMultipartFileToFile(imgfile7,dir);
		}
		if(imgfile8!=null) {
			convertMultipartFileToFile(imgfile8,dir);
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
	    //关闭流
        if (fis != null) {
        	fis.close();
        }
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
	@RequestMapping(value="/getTopicData",produces = {"application/json;charset=UTF-8"})
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
        System.out.println("已添加1："+file.getName());
	}
}
