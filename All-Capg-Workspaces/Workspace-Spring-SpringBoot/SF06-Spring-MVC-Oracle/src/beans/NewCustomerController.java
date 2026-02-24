package beans;


//mysql :
//create table customer_mvc(cid int primary key,cname char(100),emil char(100),mobile char(20));

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
//Step 15 : NewCustomerController
public class NewCustomerController implements Controller {
	private JdbcTemplate jt;  //Step 15 : jt got database connection form disp1-servlet.xml
	public JdbcTemplate getJt() {
		return jt;	}
	public void setJt(JdbcTemplate jt) {
		this.jt = jt;	}
	@Override
	                 //Step 16 : handleRequest() method
		public ModelAndView handleRequest(HttpServletRequest req,
			HttpServletResponse resp) throws Exception {
		          //Step 17: query will executed  : SELECT MAX(CID) FROM CUSTOMER_MVC
		int cid = jt.queryForInt("SELECT MAX(CID) FROM CUSTOMER_MVC");
	
		String cname = req.getParameter("cname").trim();  // Step 18 cname,email,mobile : got the data form the browser
		String email = req.getParameter("email").trim();
		String mobile = req.getParameter("mobile").trim();
		
		// Primary key increment
		cid++;   // Step-19  cid++ : increment for database record count
	jt.execute("INSERT INTO CUSTOMER_MVC VALUES(" + cid + ",'" + cname + "','" + email + "','" + mobile + "')");
	        		return new ModelAndView("/Success.jsp", "cid", new Integer(cid));
	}              // Step-20 moving to Success.jsp
	}
