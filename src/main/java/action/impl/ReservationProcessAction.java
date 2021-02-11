package action.impl;

import java.util.ArrayList;
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

import action.ReservationProcess;
import domain.Film;
import domain.Place;
import domain.Reservation;
import domain.Row;
import domain.Seance;
import domain.User;
import service.ReservationService;
import utils.UsefulUtils;

@Name("reservationProcess")
@Scope(ScopeType.SESSION)
public class ReservationProcessAction implements ReservationProcess {
	
	@In
	private ReservationService reservationService;

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
		selectedRow = getSelectedRow();
		if (selectedRow != null) {
			selectedSeance.setFilteredPlaces(removeReservedPlaces(selectedRow.getPlaces(), selectedSeance));
		}
	}
	
	private Row getSelectedRow() {
		for (Row row : selectedSeance.getHall().getRows()) {
			if (row.getId().equals(selectedSeance.getSelectedRowId())) {
				return row;
			}
		}
		return null;
	}
	
	public String reserve() {
		User sessionUser = (User) Contexts.getSessionContext().get("sessionUser");
		Reservation reservation = new Reservation(sessionUser, selectedSeance, getSelectedPlace());
		reservationService.saveReservation(reservation);
		finishedReservation = reservation;
		return "finishReservation.xhtml";
	}
	
	private Place getSelectedPlace() {
		for (Place place : selectedSeance.getFilteredPlaces()) {
			if (place.getId().equals(selectedSeance.getSelectedPlaceId())) {
				return place;
			}
		}
		return null;
	}

	public String reserveMore() {
		Contexts.getSessionContext().set("finishedReservation", null);
		selectedSeance.setFilteredPlaces(removeReservedPlaces(selectedSeance.getFilteredPlaces(), selectedSeance));
		selectedSeance.setSelectedPlaceId(UsefulUtils.getFirst(selectedSeance.getFilteredPlaces()).getId());
		return "selectSeances.xhtml";
	}
	
	private List<Place> removeReservedPlaces(List<Place> places, Seance seance) {
		List<Place> result = new ArrayList<Place>();
		List<Place> reservedPlaces = reservationService.findSeanceReservedPlaces(seance);
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

}
