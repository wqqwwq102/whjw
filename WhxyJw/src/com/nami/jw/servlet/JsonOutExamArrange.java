package com.nami.jw.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.nami.jw.base.JwBaseUrl;
import com.nami.jw.base.JwQuery;

public class JsonOutExamArrange extends HttpServlet{
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setContentType("text/plain");
		resp.setCharacterEncoding("utf-8");
		req.setCharacterEncoding("utf-8");
		
		PrintWriter out = resp.getWriter();
		JSONObject jobj = new JSONObject();
		
		String username = req.getParameter("username");
		String usernumber = req.getParameter("usernumber");
		String urlcode = req.getParameter("urlcode");
		String xnd = req.getParameter("xnd");
		String xqd = req.getParameter("xqd");
		String isCurrent = req.getParameter("iscurrent");
		String gradeUrl = JwBaseUrl.getExamArrangeUrl(urlcode, usernumber, username);
		String mainHeader = JwBaseUrl.getMainHeader(urlcode, usernumber);
		String viewstate = JwQuery.getViewstate(gradeUrl, mainHeader);
		List<Map<String, String>> list = JwQuery.getExamArrange(gradeUrl, mainHeader, viewstate, Boolean.valueOf(isCurrent), xnd, xqd);
		if(list == null || list.size()<1){
			jobj.put("succ", false);
			jobj.put("returnCode", 0);
		}else{
			jobj.put("succ", true);
			jobj.put("returnCode", 1);
			jobj.put("xn", xnd);
			jobj.put("xq", xqd);
			JSONArray jArray = JSONArray.fromObject(list);
			jobj.put("data", jArray);
		}
		out.write(jobj.toString());
		out.flush();
		out.close();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doPost(req, resp);
	}
	
}
