package service;

import access.GuestDAO;
import entities.Guest;

import java.util.List;

public class GuestService
{
	private GuestDAO dao = new GuestDAO();

	public Guest findById(int id) {
		return dao.findById(id);
	}

	public void save(Guest guest) {
		dao.save(guest);
	}

	public void saveOrUpdate(Guest guest) {
		dao.saveOrUpdate(guest);
	}

	public void delete(Guest guest) {
		dao.delete(guest);
	}

	public List<Guest> findAll() {
		return dao.selectAll();
	}
}
