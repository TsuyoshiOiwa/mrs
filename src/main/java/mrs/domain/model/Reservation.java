package mrs.domain.model;

import java.io.Serializable;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="reservation")
public class Reservation implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer reservationId;

	private LocalTime startTime;

	private LocalTime endTime;

	@ManyToOne
	@JoinColumns({@JoinColumn(name = "reserved_date"), @JoinColumn(name = "room_id")})
	private ReservableRoom reservableRoom;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	public Integer getReservationId() {
		return reservationId;
	}

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

	public ReservableRoom getReservableRoom() {
		return reservableRoom;
	}

	public void setReservableRoom(ReservableRoom reservableRoom) {
		this.reservableRoom = reservableRoom;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean overlap(Reservation reservation) {
		//日付、場所のチェック
		if(!this.getReservableRoom().getReservableRoomId()
			.equals(reservation.getReservableRoom().getReservableRoomId())) {
			return false;
		}

		//startTime<endTimeであることを確認
		if(reservation.startTime.isAfter(reservation.endTime)) {
			//不正な登録情報
			return true;
		}
		//時間重複のチェック
		else if(this.endTime.isBefore(reservation.startTime)
				|| this.startTime.isAfter(reservation.endTime)) {
			return false;
		}

		return true;

	}

}
