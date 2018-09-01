package mrs.app.reservation;

import java.io.Serializable;
import java.time.LocalTime;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import mrs.annotaion.EndTimeMustBeAfterStartTime;
import mrs.annotaion.ThirtyMinutesUnit;

@EndTimeMustBeAfterStartTime
public class ReservationForm implements Serializable {
	@NotNull(message = "必須です")
	@DateTimeFormat(pattern = "HH:mm")
	@ThirtyMinutesUnit(message = "30分単位で入力してください")
	private LocalTime startTime;

	@NotNull(message = "必須です")
	@DateTimeFormat(pattern = "HH:mm")
	@ThirtyMinutesUnit
	private LocalTime endTime;

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}
}
