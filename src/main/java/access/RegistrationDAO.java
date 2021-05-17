package access;

import entities.Registration;

public class RegistrationDAO extends DAO<Registration>
{
	@Override
	protected Class<Registration> getLoadingClass() {
		return Registration.class;
	}
}
