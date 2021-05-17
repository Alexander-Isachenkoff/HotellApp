package control.util;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;

public abstract class ColumnCallback<T, E> implements Callback<TableColumn<T, E>, TableCell<T, E>>
{
	@Override
	public TableCell<T, E> call(TableColumn<T, E> param) {
		return new TableCell<T, E>()
		{
			@Override
			protected void updateItem(E item, boolean empty) {
				if (empty) {
					setText(null);
				} else {
					setText(makeText(item));
				}
			}
		};
	}

	public abstract String makeText(E item);
}
