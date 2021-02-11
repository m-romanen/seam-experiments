package action.impl;

import java.util.List;

import javax.ejb.Remove;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.datamodel.DataModelSelection;
import org.jboss.seam.contexts.Contexts;

import action.FilmSelection;
import domain.Film;
import service.ReservationService;

@Name("filmSelection")
@Scope(ScopeType.SESSION)
public class FilmSelectionAction implements FilmSelection {

	@In
	private ReservationService reservationService;

	@DataModel
	private List<Film> films;
	
	@DataModelSelection
	@Out(required = false)
	private Film selectedFilm;

	@Factory
	public void getFilms() {
		films = reservationService.findAllFilms();
	}
	
	public String showSeances() {
		return "selectSeances.xhtml";
	}
	
	public String chooseAnotherFilm() {
		Contexts.getSessionContext().set("seances", null);
		return "selectFilms.xhtml";
	}

	@Remove
	public void destroy() {

	}

}
