package com.curd.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.ExtendedServletRequestDataBinder;

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
			long dell = curdService.delUserById(userid);
			if (dell > 0) {
				restr = "succ";
			}
		}
		PrintWriter writer = null;

		try {
			writer = response.getWriter();
			writer.write(restr);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			log.error("删除user时回写数据异常:" + e.getMessage());
			return;
		}
	}

	/*
	 * 新增或者更新数据
	 */
	@RequestMapping("saveOrUpdateUser")
	public void saveOrUpdateUser(HttpServletRequest request, HttpServletResponse response) {
		String objectid = request.getParameter("objectid");
		String name = request.getParameter("name");
		String age = request.getParameter("age");
		String likes = request.getParameter("likes");
		String restr = "";
		// 基本数据校验
		if (null == name || name.trim() == "" || null == age || age.trim() == "" || null == likes
				|| likes.trim() == "") {
			restr = "NULL";
		} else {
			// 处理likes数组
			String[] like = likes.trim().split(",");
			// 处理like中每个字段的前后空格并组装
			List<String> arrli=new ArrayList<String>();
			for (int i = 0; i < like.length; i++) {
				if(!like[i].trim().equals(""))
				arrli.add(like[i].trim());
			}
			long mondifycou = curdService
					.saveOrUpdateUser(new Object[] { objectid.trim(), name.trim(), age.trim(), arrli });
			if (mondifycou > 0) {
				restr = "saveupdate";
			}
		}
		PrintWriter writer = null;

		try {
			writer = response.getWriter();
			writer.write(restr);
			writer.flush();
			writer.close();
		} catch (IOException e) {
			log.error("新增或更新user时回写数据异常:" + e.getMessage());
			return;
		}
	}
}
