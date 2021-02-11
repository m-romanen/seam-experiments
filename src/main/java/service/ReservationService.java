package service;

import java.util.List;

import javax.ejb.Local;

import domain.Film;
import domain.Place;
import domain.Reservation;
import domain.Seance;

@Local
public interface ReservationService {
	
	List<Film> findAllFilms();
	
	List<Reservation> findReservationsBySeance(Seance seance);
	
	void saveReservation(Reservation reservation);
	
	List<Place> findSeanceReservedPlaces(Seance seance);

}
