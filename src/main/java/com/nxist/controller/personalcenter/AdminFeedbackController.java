package com.nxist.controller.personalcenter;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.nxist.controller.base.BaseData;
import com.nxist.controller.base.BaseUtil;
import com.nxist.controller.base.PageHandler;
import com.nxist.dao.FeedbackDao;
import com.nxist.dao.MessageDao;
import com.nxist.dao.UserDao;
import com.nxist.model.Feedback;
import com.nxist.model.FeedbackCustom;
import com.nxist.model.Message;
/**
 * 管理员用户消息
 * @author xym760
 *
 */
@Controller
@RequestMapping(value="/admin")
public class AdminFeedbackController {
	@Resource
	private FeedbackDao feedbackDao;
	@Resource
	private UserDao userDao;
	
	
	/**
	 * 用户反馈管理
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value = "feedbackManage")
    public ModelAndView getDataList(Integer pageNo) throws ParseException {
		ModelAndView mav = new ModelAndView();
		int count=feedbackDao.getFeedbackSum();
		int start=PageHandler.handle(pageNo, count, mav);
		List<FeedbackCustom> dataList=feedbackDao.findAllFeedback(start,BaseData.PAGE_SIZE);
		int size=dataList.size();
		for(int i=0;i<size;i++) {
			String tempString=dataList.get(i).getContent();
			if(tempString.length()>120) {
				dataList.get(i).setContent(tempString.substring(0, 110)+"......");
			}
		}
        mav.addObject("dataList",dataList);
        mav.setViewName("jsp/personalcenter/feedback");
        return mav;
	}
	
	/**
	 * 跳转至查看页面
	 * @param messageId
	 * @return
	 */
	@RequestMapping(value = "feedbackDetail")
    public ModelAndView detail(int feedbackId) {
		Feedback feedback=feedbackDao.findFeedbackByFeedbackId(feedbackId);
		FeedbackCustom feedbackCustom=new FeedbackCustom();
		feedbackCustom.setFeedbackId(feedback.getFeedbackId());
		feedbackCustom.setUserName(userDao.findUserNameByUserId(feedback.getUserId()));
		feedbackCustom.setContent(feedback.getContent());
		feedbackCustom.setPicture(feedback.getPicture());
		feedbackCustom.setContact(feedback.getContact());
		feedbackCustom.setFeedbackDate(feedback.getFeedbackDate());
		feedbackCustom.setStatus(feedback.getStatus());
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("feedbackCustom",feedbackCustom);
        mav.setViewName("jsp/personalcenter/feedbackDetail");
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
	/*@RequestMapping(value="/feedbackManageUpdateDispose",method=RequestMethod.POST)
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
    }*/
	
	/**
	 * 删除message
	 * @param messageId
	 * @return
	 */
	@RequestMapping(value = "feedbackDelete")
    public String delete(int feedbackId) {
		//删除本地消息图片文件
		Feedback feedback=feedbackDao.findFeedbackByFeedbackId(feedbackId);
		BaseUtil.delete("C:/userpic/feedbackpic/"+feedback.getFeedbackId()+"_"+feedback.getPicture());
		//删除数据库中的记录
		feedbackDao.deleteFeedback(feedbackId);
        return "redirect:feedbackManage";
	}
	
	/**
	 * 审核反馈
	 * @param feedbackId
	 * @return
	 */
	@RequestMapping(value = "feedbackAudit")
    public String audit(int feedbackId) {
		feedbackDao.auditFeedback(feedbackId);
        return "redirect:feedbackManage";
	}
	
	@RequestMapping(value = "feedbackCancelAudit")
    public String cancelAudit(int feedbackId) {
		feedbackDao.cancelAuditFeedback(feedbackId);
        return "redirect:feedbackManage";
	}
	
	
	/**
	 * 跳转至消息增加页面
	 * @return
	 */
	@RequestMapping(value = "feedbackManageAdd")
    public ModelAndView add() {
		ModelAndView mav = new ModelAndView();
        mav.setViewName("jsp/messageAdd");
        return mav;
	}
	
	//上传消息图片
	@RequestMapping(value="/uploadfeedbackPicture",method=RequestMethod.POST,produces = "text/html;charset=UTF-8")
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
	@RequestMapping("/getfeedbackPicture")  
	public void getMessagePicture(HttpServletResponse response,String picture) throws IOException {          
	    FileInputStream fis = null;  
	    File file = new File("C://userpic//feedbackpic/"+picture);  
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

