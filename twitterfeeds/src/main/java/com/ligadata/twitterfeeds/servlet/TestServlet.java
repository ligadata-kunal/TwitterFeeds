package com.ligadata.twitterfeeds.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ligadata.twitterfeeds.zookeeperclient.Client;

/**
 * Servlet implementation class TestServlet
 */
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {

		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Pragma", "no-cache");

		HttpSession session = request.getSession(true);

		synchronized (session) {

			java.io.PrintWriter out = response.getWriter();

			Client c = new Client("localhost:2181");
			try {
				out.println(c.getData());
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					c.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			out.close();

		}

	} // doGet

}
