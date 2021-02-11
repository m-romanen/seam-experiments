package action;

import javax.ejb.Local;

@Local
public interface Login {
	
	String login();
	
	String logout();
	
	void destroy();

}
