package entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Room")
public class Room
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "name")
	private int name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_category")
	private Category category;

	@Column(name = "floor")
	private int floor;

	@OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Registration> registrations = new ArrayList<>();

	public Room() {}

	public Room(int name, Category category, int floor) {
		this.name = name;
		this.category = category;
		this.floor = floor;
	}

	public double getCost() {
		return category.getCost();
	}

	public int getId() {
		return id;
	}

	public int getName() {
		return name;
	}

	public void setName(int name) {
		this.name = name;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public int getFloor() {
		return floor;
	}

	public void setFloor(int floor) {
		this.floor = floor;
	}

	public List<Registration> getRegistrations() {
		return registrations;
	}

	public boolean getStatus() {
		LocalDate today = LocalDate.now();
		for (Registration registration : registrations) {
			if (registration.getDateOfDeparture().isAfter(today)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String toString() {
		return "Room{" +
				"name=" + name +
				", category=" + category +
				", floor=" + floor +
				'}';
	}
}
