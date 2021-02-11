package service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;

import domain.Film;
import domain.Place;
import domain.Reservation;
import domain.Seance;
import service.ReservationService;

@AutoCreate
@Name("reservationService")
@Scope(ScopeType.APPLICATION)
public class ReservationServiceImpl implements ReservationService {
	
	@In
	private EntityManager entityManager;

	public List<Film> findAllFilms() {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Film> query = criteriaBuilder.createQuery(Film.class);
		query.select(query.from(Film.class));
		return entityManager.createQuery(query).getResultList();
	}
	
	public List<Reservation> findReservationsBySeance(Seance seance) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Reservation> query = criteriaBuilder.createQuery(Reservation.class);
		Root<Reservation> rootReservations = query.from(Reservation.class);
		query.where(criteriaBuilder.equal(rootReservations.get("seance"), seance.getId()));
		return entityManager.createQuery(query).getResultList();
	}

	public void saveReservation(Reservation reservation) {
		entityManager.persist(reservation);
	}

	public List<Place> findSeanceReservedPlaces(Seance seance) {
		List<Place> result = new ArrayList<Place>();
		List<Reservation> seanceReservations = findReservationsBySeance(seance);
		for (Reservation reservation : seanceReservations) {
			result.add(reservation.getPlace());
		}
		return result;
	}

}
