package control.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateColumnCallback<T> extends ColumnCallback<T, LocalDate>
{
	private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

	@Override
	public String makeText(LocalDate item) {
	return formatter.format(item);
	}
}
