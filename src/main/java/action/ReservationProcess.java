package action;

import javax.ejb.Local;

@Local
public interface ReservationProcess {
	
	void getSeances();
	
	void onRowSelect();
	
	String reserveMore();
	
	String reserve();
	
	void destroy();

}
