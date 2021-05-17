package entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Registration")
public class Registration
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_guest")
	private Guest guest;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_room")
	private Room room;

	@Column(name = "date_of_placement")
	private String dateOfPlacement;

	@Column(name = "date_of_departure")
	private String dateOfDeparture;

	@OneToMany(mappedBy = "registration", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ServiceProvided> serviceProvidedList = new ArrayList<>();

	public Registration() {}

	public Registration(Guest guest, Room room, String dateOfPlacement, String dateOfDeparture) {
		this.guest = guest;
		this.room = room;
		this.dateOfPlacement = dateOfPlacement;
		this.dateOfDeparture = dateOfDeparture;
	}

	public int getId() {
		return id;
	}

	public Guest getGuest() {
		return guest;
	}

	public void setGuest(Guest guest) {
		this.guest = guest;
	}

	public Room getRoom() {
		return room;
	}

	public void setRoom(Room room) {
		this.room = room;
	}

	private LocalDate convertToLocalDate(String date){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
		return LocalDate.parse(date, formatter);
	}

	public LocalDate getDateOfPlacement() {
		if (dateOfPlacement == null) {
			return null;
		}
		return convertToLocalDate(dateOfPlacement);
	}

	public void setDateOfPlacement(LocalDate dateOfPlacement) {
		this.dateOfPlacement = dateOfPlacement.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

	}

	public LocalDate getDateOfDeparture() {
		if (dateOfDeparture == null) {
			return null;
		}
		return convertToLocalDate(dateOfDeparture);
	}

	public void setDateOfDeparture(LocalDate dateOfDeparture) {
		this.dateOfDeparture = dateOfDeparture.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
	}

	public List<ServiceProvided> getServiceProvidedList() {
		return serviceProvidedList;
	}

	public void setServiceProvidedList(List<ServiceProvided> serviceProvidedList) {
		this.serviceProvidedList = serviceProvidedList;
	}

	public long calcLivingDuration() {
		return ChronoUnit.DAYS.between(getDateOfPlacement(), getDateOfDeparture());
	}

	public double calcLivingCost() {
		return calcLivingDuration() * getRoom().getCost();
	}

	public double calcServicesCost() {
		double servicesCost = 0;
		for (ServiceProvided serviceProvided : getServiceProvidedList()) {
			servicesCost += serviceProvided.getService().getCost();
		}
		return servicesCost;
	}

	@Override
	public String toString() {
		return "Registration{" +
				"id=" + id +
				", guest=" + guest +
				", room=" + room +
				", dateOfPlacement='" + dateOfPlacement + '\'' +
				", dateOfDeparture='" + dateOfDeparture + '\'' +
				'}';
	}
}
