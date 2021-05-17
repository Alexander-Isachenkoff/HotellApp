package entities;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Category")
public class Category
{
	@Id
	@GeneratedValue(strategy  = GenerationType.IDENTITY)
	private int id;

	@Column(name = "name")
	private String name;

	@Column(name = "cost")
	private double cost;

	@Column(name = "number_of_beds")
	private int bedsNumber;

	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Room> rooms = new ArrayList<>();

	public Category() {}

	public Category(String name, double cost, int bedsNumber) {
		this.name = name;
		this.cost = cost;
		this.bedsNumber = bedsNumber;
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

	public int getBedsNumber() {
		return bedsNumber;
	}

	public void setBedsNumber(int bedsNumber) {
		this.bedsNumber = bedsNumber;
	}

	@Override
	public String toString() {
		return "Category{" +
				"id=" + id +
				", name='" + name + '\'' +
				", cost=" + cost +
				'}';
	}
}
