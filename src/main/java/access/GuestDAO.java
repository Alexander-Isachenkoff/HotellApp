package access;

import entities.Guest;

public class GuestDAO extends DAO<Guest>
{
	protected Class<Guest> getLoadingClass() {
		return Guest.class;
	}
}
