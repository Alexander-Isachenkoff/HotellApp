package access;

import entities.Category;

public class CategoryDAO extends DAO<Category>
{
	@Override
	protected Class<Category> getLoadingClass() {
		return Category.class;
	}
}
