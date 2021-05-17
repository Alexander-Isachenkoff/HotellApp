package access;

import entities.Guest;
import entities.Sex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GuestDAOTest
{
	private SexDAO sexDao = new SexDAO();
	private GuestDAO guestDao = new GuestDAO();

	@BeforeEach
	void clearAll() {
		for (Guest guest : guestDao.selectAll()) {
			guestDao.delete(guest);
		}
		for (Sex sex : sexDao.selectAll()) {
			sexDao.delete(sex);
		}
	}

	@Test
	void save() {
		Sex male = new Sex("Мужской");
		sexDao.save(male);
		Guest guest1 = new Guest("Петров","Федор","Иванович", male, "05.05.1985", "89051587934",1234,123456);
		guestDao.save(guest1);

		Guest loaded1 = guestDao.findById(guest1.getId());

		assertEquals(guest1.getName(), loaded1.getName());
		assertEquals(guest1.getPassportId(), loaded1.getPassportId());
		assertEquals(guest1.getPassportSeries(), loaded1.getPassportSeries());

		System.out.println(guestDao.selectAll());
	}

	@Test
	void update() {
	}
}