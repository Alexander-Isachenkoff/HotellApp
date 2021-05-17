package access;

import entities.Sex;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SexDAOTest
{
	private SexDAO dao = new SexDAO();

	@BeforeEach
	void clearAll() {
		for (Sex sex : dao.selectAll()) {
			dao.delete(sex);
		}
	}

	@Test
	void save() {
		Sex male = new Sex("Мужской");
		Sex female = new Sex("Женский");
		dao.save(male);
		dao.save(female);

		Sex loaded1 = dao.findById(male.getId());
		Sex loaded2 = dao.findById(female.getId());

		assertEquals(male.getName(), loaded1.getName());
		assertEquals(female.getName(), loaded2.getName());

		System.out.println(dao.selectAll());
	}

	@Test
	void update() {
	}

	@Test
	void saveOrUpdate() {
	}

	@Test
	void delete() {
	}

	@Test
	void selectAll() {
	}
}