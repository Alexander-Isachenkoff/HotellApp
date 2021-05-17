package service;

import access.RegistrationDAO;
import entities.Registration;

import java.util.List;

public class RegistrationService
{
	private RegistrationDAO dao = new RegistrationDAO();

	public Registration findById(int id) {
		return dao.findById(id);
	}

	public void save(Registration registration) {
		dao.save(registration);
	}

	public void update(Registration registration) {
		dao.update(registration);
	}

	public void saveOrUpdate(Registration registration) {
		dao.saveOrUpdate(registration);
	}

	public void delete(Registration registration) {
		dao.delete(registration);
	}

	public List<Registration> findAll() {
		return dao.selectAll();
	}
}
