package service;

import access.SexDAO;
import entities.Sex;

import java.util.List;

public class SexService
{
	private SexDAO dao = new SexDAO();

	public Sex findById(int id) {
		return dao.findById(id);
	}

	public List<Sex> findAll() {
		return dao.selectAll();
	}
}
