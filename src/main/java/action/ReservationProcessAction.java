package action;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Remove;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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
import domain.Place;
import domain.Reservation;
import domain.Row;
import domain.Seance;
import domain.User;
import utils.UsefulUtils;

@Name("reservationProcess")
@Scope(ScopeType.SESSION)
public class ReservationProcessAction implements ReservationProcess {

	@In
	EntityManager entityManager;

	@DataModel
	private List<Seance> seances;
	
	@DataModelSelection
	private Seance selectedSeance;
	
	@Out(required = false)
	private Row selectedRow;
	
	@Out(required = false)
	private Reservation finishedReservation;
	
	@Factory
	public void getSeances() {
		Film selectedFilm = (Film) Contexts.getSessionContext().get("selectedFilm");
		if (selectedFilm != null) {
			seances = selectedFilm.getSeances();
		}
	}
	
	public void onRowSelect() {
		for (Row row : selectedSeance.getHall().getRows()) {
			if (row.getId().equals(selectedSeance.getSelectedRowId())) {
				selectedRow = row;
				break;
			}
		}
		if (selectedRow != null) {
			selectedSeance.setFilteredPlaces(removeReservedPlaces(selectedRow.getPlaces(), selectedSeance));
		}
	}
	
	private List<Reservation> findReservationsBySeance(Seance seance) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Reservation> query = criteriaBuilder.createQuery(Reservation.class);
		Root<Reservation> rootReservations = query.from(Reservation.class);
		query.where(criteriaBuilder.equal(rootReservations.get("seance"), selectedSeance.getId()));
		return entityManager.createQuery(query).getResultList();
	}
	
	private List<Place> removeReservedPlaces(List<Place> places, Seance seance) {
		List<Reservation> seanceReservations = findReservationsBySeance(seance);
		if (seanceReservations.isEmpty()) {
			return places;
		}
		List<Place> reservedPlaces = new ArrayList<Place>();
		for (Reservation reservation : seanceReservations) {
			reservedPlaces.add(reservation.getPlace());
		}
		List<Place> result = new ArrayList<Place>();
		for (Place place : places) {
			if (!reservedPlaces.contains(place)) {
				result.add(place);
			}
		}
		return result;
	}

	@Remove
	public void destroy() {

	}

	public String reserve() {
		Place selectedPlace = null;
		for (Place place : selectedSeance.getFilteredPlaces()) {
			if (place.getId().equals(selectedSeance.getSelectedPlaceId())) {
				selectedPlace = place;
				break;
			}
		}
		Reservation reservation = new Reservation();
		reservation.setPlace(selectedPlace);
		reservation.setSeance(selectedSeance);
		reservation.setUser((User) Contexts.getSessionContext().get("sessionUser"));
		entityManager.persist(reservation);
		finishedReservation = reservation;
		return "finishReservation.xhtml";
	}

	public String reserveMore() {
		Contexts.getSessionContext().set("finishedReservation", null);
		selectedSeance.setFilteredPlaces(removeReservedPlaces(selectedSeance.getFilteredPlaces(), selectedSeance));
		selectedSeance.setSelectedPlaceId(UsefulUtils.getFirst(selectedSeance.getFilteredPlaces()).getId());
		return "selectSeances.xhtml";
	}

}
