package action;

import javax.ejb.Local;

@Local
public interface FilmSelection {
	
	void getFilms();
	
	String showSeances();
	
	String chooseAnotherFilm();
	
	void destroy();

}
