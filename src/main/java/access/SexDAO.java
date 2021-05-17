package access;

import entities.Sex;

public class SexDAO extends DAO<Sex>
{
	protected Class<Sex> getLoadingClass() {
		return Sex.class;
	}
}
