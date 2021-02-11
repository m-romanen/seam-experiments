package action;

import javax.ejb.Local;

@Local
public interface Login {
	
	public String login();
	
	public String logout();
	
	public void destroy();

}
