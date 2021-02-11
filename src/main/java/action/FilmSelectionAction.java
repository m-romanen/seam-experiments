package action;

import java.util.List;

import javax.ejb.Remove;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Factory;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.annotations.datamodel.DataModel;
import org.jboss.seam.annotations.datamodel.DataModelSelection;
import org.jboss.seam.contexts.Contexts;

import domain.Film;

@Name("filmSelection")
@Scope(ScopeType.SESSION)
public class FilmSelectionAction implements FilmSelection {

	@In
	private EntityManager entityManager;

	@DataModel
	private List<Film> films;
	
	@DataModelSelection
	@Out(required = false)
	private Film selectedFilm;

	@Factory
	public void getFilms() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Film> query = criteriaBuilder.createQuery(Film.class);
		query.select(query.from(Film.class));
		films = entityManager.createQuery(query).getResultList();
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
