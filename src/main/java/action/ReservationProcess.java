package action;

import javax.ejb.Local;

@Local
public interface ReservationProcess {
	
	void onRowSelect();
	
	void getSeances();
	
	void destroy();
	
	String reserveMore();
	
	String reserve();

}
