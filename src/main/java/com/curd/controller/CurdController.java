package com.curd.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.base.BaseController;
import com.curd.service.CurdService;

/**
 * 系统简单的增删改查
 * 
 * @author DLHT
 *
 */
@Controller
@RequestMapping("curd")
public class CurdController extends BaseController {
	@Autowired
	private CurdService curdService;

	/*
	 * 显示所有的用户信息
	 */
	@RequestMapping("showUsers")
	public String showUsers(HttpServletRequest request, Map<String, Object> result) {
		String username = request.getParameter("loginUser");
		if (null == username || username.trim() == "") {
			username = "logintest";
		}
		result.put("listu", curdService.getAllUser());
		return "curd/listuser";
	}

	/*
	 * 删除指定id的信息
	 */
	@RequestMapping("delUser")
	public void delUser(HttpServletRequest request, HttpServletResponse response) {
		String userid = request.getParameter("id");
		String restr = "";
		if (null == userid || userid.trim() == "") {
			restr = "NULL";
		} else {
			long dell=curdService.delUserById(userid);
			if(dell>0){
				restr="succ";	
			}
		}
		PrintWriter writer = null;

		try {
			writer = response.getWriter();
			writer.write(restr);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			log.error("删除user时回写数据异常:"+e.getMessage());
			return;
		}
	}
}
