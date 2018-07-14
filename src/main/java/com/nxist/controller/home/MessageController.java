package com.nxist.controller.home;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.nxist.dao.MessageDao;
import com.nxist.model.Message;

@Controller
@RequestMapping(value="/home")
public class MessageController {
	
	@Resource
	private MessageDao messageDao;
	
	@RequestMapping(value="/getMessageData",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getMessageData(HttpServletResponse response,HttpServletRequest request) throws IOException {
        List<Message> messageDataList=messageDao.findAllMessage();
        int size=messageDataList.size();
        for(int i=0;i<size;i++) {
        	messageDataList.get(i).setMessageDate(null);
        }
        String jsonString = JSON.toJSONString(messageDataList);
        return jsonString;
	}
	
	/**
	 * 获取消息图片
	 * @param response
	 * @param picture
	 * @throws IOException
	 */
	@RequestMapping("/returnMessagePicture")  
	public void returnMessagePicture(HttpServletResponse response,String picture) throws IOException {          
	    FileInputStream fis = null;  
	    File file = new File("C://userpic//messagepic/"+picture);  
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
	
	@RequestMapping(value="/getMessageContent",produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public String getMessageContent(HttpServletResponse response,Integer messageId) throws IOException {
        Message message=messageDao.findMessageByMessageId(messageId);
        message.setMessageDate(null);
        String jsonString = JSON.toJSONString(message);
        return jsonString;
	}

}
