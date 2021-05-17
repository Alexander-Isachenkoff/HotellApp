package service;

import access.CategoryDAO;
import entities.Category;

import java.util.List;

public class CategoryService
{
	private CategoryDAO dao = new CategoryDAO();

	public Category findById(int id) {
		return dao.findById(id);
	}

	public void save(Category category) {
		dao.save(category);
	}

	public void saveOrUpdate(Category category) {
		dao.saveOrUpdate(category);
	}

	public void delete(Category category) {
		dao.delete(category);
	}

	public List<Category> findAll() {
		return dao.selectAll();
	}
}
