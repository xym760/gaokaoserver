package com.nxist.controller.home;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.nxist.controller.base.BaseUtil;
import com.nxist.dao.MessageDao;
import com.nxist.model.Message;
/**
 * 管理员用户消息
 * @author xym760
 *
 */
@Controller
@RequestMapping(value="/admin")
public class AdminMessageController {
	@Resource
	private MessageDao messageDao;
	
	
	/**
	 * 用户消息管理
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "messageManage")
    public ModelAndView getDataList() throws ParseException {
		List<Message> dataList=messageDao.findAllMessage();
		int size=dataList.size();
		for(int i=0;i<size;i++) {
			String tempString=dataList.get(i).getContent();
			if(tempString.length()>120) {
				dataList.get(i).setContent(tempString.substring(0, 110)+"......");
			}
		}
		ModelAndView mav = new ModelAndView();
        mav.addObject("dataList",dataList);
        mav.setViewName("jsp/message");
        return mav;
	}
	
	/**
	 * 跳转至查看页面
	 * @param messageId
	 * @return
	 */
	@RequestMapping(value = "messageDetail")
    public ModelAndView detail(int messageId) {
		Message message=messageDao.findMessageByMessageId(messageId);
		ModelAndView mav = new ModelAndView();
		mav.addObject("message",message);
        mav.setViewName("jsp/messageDetail");
        return mav;
	}
	
	/**
	 * 跳转至修改页面
	 * @param messageId
	 * @return
	 */
	@RequestMapping(value = "messageUpdate")
    public ModelAndView update(int messageId) {
		Message message=messageDao.findMessageByMessageId(messageId);
		ModelAndView mav = new ModelAndView();
		mav.addObject("message",message);
        mav.setViewName("jsp/messageUpdate");
        return mav;
	}
	
	/**
	 * 修改消息
	 * @param messageId
	 * @param title
	 * @param picture
	 * @param content
	 * @return
	 */
	@RequestMapping(value="/messageManageUpdateDispose",method=RequestMethod.POST)
	@ResponseBody
    public int updateDispose(int messageId,String title, String picture, String content) {
		Message message=messageDao.findMessageByMessageId(messageId);
		message.setTitle(title);
		if(!message.getPicture().equals(picture)) {//如果更改了图片
			//删除本地图片
			BaseUtil.delete("C:/userpic/messagepic/"+message.getMessageId()+"_"+message.getPicture());
			//更改数据库记录
			message.setPicture(picture);
		}
		message.setContent(content);
		message.setMessageDate(new Date());
		messageDao.updateMessageWithoutPicture(message);
		System.out.println("更改后的ID："+message.getMessageId());
		return message.getMessageId();
    }
	
	/**
	 * 删除message
	 * @param messageId
	 * @return
	 */
	@RequestMapping(value = "messageDelete")
    public String delete(int messageId) {
		//删除本地消息图片文件
		Message message=messageDao.findMessageByMessageId(messageId);
		BaseUtil.delete("C:/userpic/messagepic/"+message.getMessageId()+"_"+message.getPicture());
		//删除数据库中的记录
		messageDao.deleteMessage(messageId);
        return "redirect:messageManage";
	}
	
	
	/**
	 * 跳转至消息增加页面
	 * @return
	 */
	@RequestMapping(value = "messageManageAdd")
    public ModelAndView add() {
		ModelAndView mav = new ModelAndView();
        mav.setViewName("jsp/messageAdd");
        return mav;
	}
	
	/**
	 * 消息增加处理(不上传图片)
	 * @param title
	 * @param picture
	 * @param content
	 * @return
	 */
	@RequestMapping(value="/messageManageAddDispose",method=RequestMethod.POST)
	@ResponseBody
    public int addDispose(String title, String picture, String content) {
		Message message=new Message();
		message.setTitle(title);
		message.setPicture(picture);
		message.setContent(content);
		message.setMessageDate(new Date());
		messageDao.insertMessageWithoutPicture(message);
		System.out.println("插入后的ID："+message.getMessageId());
		return message.getMessageId();
    }
	
	//上传消息图片
	@RequestMapping(value="/uploadMessagePicture",method=RequestMethod.POST,produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String uploadMessagePicture(MultipartFile picture,String id) throws IllegalStateException, IOException {
		File dir = new File("C:\\userpic\\messagepic\\");
		//如果文件夹不存在,创建目录
        if (!dir.exists()){
            dir.mkdirs();
        }
        if(picture!=null) {
			convertMultipartFileToFile(picture,dir,id+"_");
			return "添加图片成功";
		}else {
			return "图片上传失败！";
		}
	}
	
	/**
	 * 获取消息图片
	 * @param response
	 * @param picture
	 * @throws IOException
	 */
	@RequestMapping("/getMessagePicture")  
	public void getMessagePicture(HttpServletResponse response,String picture) throws IOException {          
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
	
	private void convertMultipartFileToFile(MultipartFile multipartFile,File dir,String messageId) throws IllegalStateException, IOException {
		// 获取文件名
        String fileName = messageId+multipartFile.getOriginalFilename();
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
