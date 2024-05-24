package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.PersonDAO;
import model.Person;

/**
 * Servlet implementation class DeleteServlet
 */
@WebServlet("/DeleteServlet")
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String url = null;

		List<Integer> idList = getIDListFromValues(request, "person");
		
		PersonDAO pdao = new PersonDAO();
		String msg;
		
		List<Person> plist = new ArrayList<>();
		if(idList.size() > 0) {
			plist = pdao.selectPersonList(idList);
			url = "WEB-INF/jsp/deletelist.jsp";
			request.setAttribute("personList", plist);
			RequestDispatcher dispatcher = request.getRequestDispatcher(url);
			dispatcher.forward(request, response);
		}else {
			response.sendRedirect("TemplateServlet");
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		PersonDAO pdao = new PersonDAO();
		String msg;

		List<Integer> idList = getIDListFromValues(request, "id");
		
			if(idList.size() == 0) {
			msg = "人物が選択されていません";
			}else {
				if(pdao.deletePersonList(idList)) {
					msg = "選択された人物の登録を削除しました。";
				}else {
					msg = "選択された人物の削除に失敗しました。";
			}
		}
		request.setAttribute("Msg", msg);
		
		List<Person> plist = pdao.selectAll();
		request.setAttribute("personList", plist);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/jsp/main.jsp");
		dispatcher.forward(request, response);
	}
	
	String[] getParameterListOrNull(HttpServletRequest request, String keyname) {
		Optional<String[]> opList = Optional.ofNullable(request.getParameterValues(keyname));
		return opList.orElse(new String[0]);
	}
	
	List<Integer> getIDListFromValues(HttpServletRequest request, String keyname) {
		String[] sArray = getParameterListOrNull(request,keyname);
		List<Integer> idList = new ArrayList<>();
		if(sArray.length > 0 ) {
			for(int i = 0; i < sArray.length; i++) {
				if(!sArray[i].equals("on")) {
				idList.add(Integer.parseInt(sArray[i]));
				}
			}
		}
		return idList;
	}
}
