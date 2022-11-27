package com.cbjs.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(urlPatterns = { "/profile" })
public class ProfileServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();

		if (session.getAttribute("username") != null) {
			RequestDispatcher dispatcher = req.getRequestDispatcher("profile.jsp");
			dispatcher.forward(req, resp);
		} else {
			resp.sendRedirect("/JavaCBJS-1/auth");
		}
	}
}
