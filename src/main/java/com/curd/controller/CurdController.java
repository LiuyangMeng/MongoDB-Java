package com.curd.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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
}
