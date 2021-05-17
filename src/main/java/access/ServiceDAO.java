package access;

import entities.Service;

public class ServiceDAO extends DAO<Service>
{
	@Override
	protected Class<Service> getLoadingClass() {
		return Service.class;
	}
}
