package domain;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.jboss.seam.annotations.Name;

@Entity
@Table(name = "seance")
@Name("seance")
public class Seance {
	
	@Id
	private Integer id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "hall_id")
	private Hall hall;
	
	private Date start_date;
	
	private Integer price;
	
	@Transient
	private Integer selectedRowId;
	
	@Transient
	private Integer selectedPlaceId;
	
	@Transient
	private List<Place> filteredPlaces;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Hall getHall() {
		return hall;
	}

	public void setHall(Hall hall) {
		this.hall = hall;
	}

	public Integer getPrice() {
		return price;
	}

	public void setPrice(Integer price) {
		this.price = price;
	}

	public Date getStart_date() {
		return start_date;
	}

	public void setStart_date(Date start_date) {
		this.start_date = start_date;
	}

	public Integer getSelectedRowId() {
		return selectedRowId;
	}

	public void setSelectedRowId(Integer selectedRowId) {
		this.selectedRowId = selectedRowId;
	}

	public Integer getSelectedPlaceId() {
		return selectedPlaceId;
	}

	public void setSelectedPlaceId(Integer selectedPlaceId) {
		this.selectedPlaceId = selectedPlaceId;
	}

	public List<Place> getFilteredPlaces() {
		return filteredPlaces;
	}

	public void setFilteredPlaces(List<Place> filteredPlaces) {
		this.filteredPlaces = filteredPlaces;
	}

}
