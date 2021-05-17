package access;

import entities.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class ServiceDAOTest
{
	private ServiceDAO serviceDAO = new ServiceDAO();
	private GuestDAO guestDAO = new GuestDAO();
	private RegistrationDAO registrationDAO = new RegistrationDAO();
	private RoomDAO roomDAO = new RoomDAO();
	private ServiceProvidedDAO serviceProvidedDAO = new ServiceProvidedDAO();
	private SexDAO sexDAO = new SexDAO();
	private CategoryDAO categoryDAO = new CategoryDAO();

	@BeforeEach
	void clearAll() {
		guestDAO.deleteAll();
		serviceDAO.deleteAll();
		registrationDAO.deleteAll();
		roomDAO.deleteAll();
		serviceProvidedDAO.deleteAll();
		sexDAO.deleteAll();
		categoryDAO.deleteAll();
	}

	@Test
	void save() {
		Sex sex = new Sex("Мужской");
		sexDAO.save(sex);

		Guest guest = new Guest("Петров", "Федор", "Иванович", sex, "05.05.1985", "89051587934", 1234, 123456);
		guestDAO.save(guest);

		Category category = new Category("Люкс", 1500, 2);
		categoryDAO.save(category);

		Room room = new Room(10, category, 2);
		roomDAO.save(room);

		Registration registration = new Registration(guest, room, LocalDate.now().toString(),
				LocalDate.now().plusDays(7).toString());
		registrationDAO.save(registration);

		Service service = new Service("Прачечная", 150);
		serviceDAO.save(service);

		String date = LocalDate.now().plusDays(1).toString();
		registration.getServiceProvidedList().add(new ServiceProvided(registration, service, date));

		registrationDAO.update(registration);
	}
}