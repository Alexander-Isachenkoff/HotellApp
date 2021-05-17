package entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Service")
public class Service
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "cost")
	private double cost;

	@OneToMany(mappedBy = "service", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<ServiceProvided> serviceProvidedList = new ArrayList<>();

	public Service() {}

	public Service(String name, double cost) {
		this.name = name;
		this.cost = cost;
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public List<ServiceProvided> getServiceProvidedList() {
		return serviceProvidedList;
	}
}
