package access;

import entities.ServiceProvided;

public class ServiceProvidedDAO extends DAO<ServiceProvided>
{
	@Override
	protected Class<ServiceProvided> getLoadingClass() {
		return ServiceProvided.class;
	}
}
