package com.cbjs.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cbjs.dao.AuthDao;
import com.cbjs.dao.UserDao;
import com.cbjs.model.User;



@WebServlet(urlPatterns = { "/auth" })
public class AuthServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private AuthDao authDao;
	private String authNoti = "<br>";

	public void init() {
		authDao = new AuthDao();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("action");
		if (action != null && action.equals("logout")) {
			HttpSession session = req.getSession();
			session.invalidate();
		}
		req.setAttribute("authNoti", authNoti);
		RequestDispatcher dispatcher = req.getRequestDispatcher("auth.jsp");
		dispatcher.forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String action = req.getParameter("action");
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		String id = null;

		switch (action) {
		case "login": {
			User user = new User(username, password);
			user.setUsername(username);
			user.setPassword(password);
			try {
				if (authDao.validate(user)) {
					id = authDao.getId(username);
					HttpSession session = req.getSession();
					session.setAttribute("username", username);
					session.setAttribute("id", id);
					authNoti = "<br>";
					resp.sendRedirect("/JavaCBJS-1/index");
				} else {
					authNoti = "<h3 class=\"nes-text is-error\">Login fail</h3>";
					resp.sendRedirect("/JavaCBJS-1/auth");
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			break;
		}
		case "signup": {
			User user = new User(username, password);
			user.setUsername(username);
			user.setPassword(password);
			try {
				if (authDao.checkUser(user)) {
					authDao.registerUser(user);
					id = authDao.getId(username);
					HttpSession session = req.getSession();
					session.setAttribute("username", username);
					session.setAttribute("id", id);
					authNoti = "<br>";
					resp.sendRedirect("/JavaCBJS-1/index");//trang bảng xếp hạng
				} else {
					authNoti = "<h3 class=\"nes-text is-error\">Username already existed</h3>";
					resp.sendRedirect("/JavaCBJS-1/auth");//trang đăng ký
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			break;
		}
		default:
			resp.sendRedirect("/JavaCBJS-1/auth");
			break;
		}
	}

}
