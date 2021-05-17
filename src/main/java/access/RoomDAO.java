package access;

import entities.Room;

public class RoomDAO extends DAO<Room>
{
	@Override
	protected Class<Room> getLoadingClass() {
		return Room.class;
	}
}
