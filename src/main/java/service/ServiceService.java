package service;

import access.ServiceDAO;
import entities.Service;

import java.util.List;

public class ServiceService
{
	private ServiceDAO dao = new ServiceDAO();

	public Service findById(int id) {
		return dao.findById(id);
	}

	public void save(Service service) {
		dao.save(service);
	}

	public void saveOrUpdate(Service service) {
		dao.saveOrUpdate(service);
	}

	public void delete(Service service) {
		dao.delete(service);
	}

	public List<Service> findAll() {
		return dao.selectAll();
	}
}
