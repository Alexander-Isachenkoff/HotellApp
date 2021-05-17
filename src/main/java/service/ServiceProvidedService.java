package service;

import access.ServiceProvidedDAO;
import entities.ServiceProvided;

public class ServiceProvidedService
{
	private final ServiceProvidedDAO dao = new ServiceProvidedDAO();

	public void saveOrUpdate(ServiceProvided service) {
		dao.saveOrUpdate(service);
	}

	public void delete(ServiceProvided serviceProvided) {
		dao.delete(serviceProvided);
	}
}
