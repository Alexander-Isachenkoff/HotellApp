package service;

import access.RoomDAO;
import entities.Registration;
import entities.Room;

import java.time.LocalDate;
import java.util.List;

public class RoomService
{
	private RoomDAO dao = new RoomDAO();

	public Room findById(int id) {
		return dao.findById(id);
	}

	public void save(Room room) {
		dao.save(room);
	}

	public void update(Room room) {
		dao.update(room);
	}

	public void saveOrUpdate(Room room) {
		dao.saveOrUpdate(room);
	}

	public void delete(Room room) {
		dao.delete(room);
	}

	public List<Room> findAll() {
		return dao.selectAll();
	}
}
