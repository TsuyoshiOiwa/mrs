package mrs.domain.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name="reservable_room")
public class ReservableRoom implements Serializable {
	@EmbeddedId
	private ReservableRoomId reservableRoomId; // reserved_date, room_id

	@ManyToOne
	@JoinColumn(name = "room_id", insertable = false, updatable = false)
	@MapsId("roomId") // 他のテーブルと絡んだとき必要？2つのテーブルで主キーを同じにするときも使える
	private MeetingRoom meetingRoom; // room_id, room_name

	public ReservableRoom(ReservableRoomId reservableRoomId) {
		this.reservableRoomId = reservableRoomId;
	}

	public ReservableRoom() {

	}

	public Integer getRoomId() {
		return reservableRoomId.getRoomId();
	}
	public void setRoomId(Integer roomId) {
		reservableRoomId.setRoomId(roomId);
	}
	public LocalDate getReservedDate() {
		return reservableRoomId.getReservedDate();
	}
	public void setReservedDate(LocalDate reservedDate) {
		reservableRoomId.setReservedDate(reservedDate);
	}
	public String getRoomName() {
		return meetingRoom.getRoomName();
	}
	public void setRoomName(String roomName) {
		meetingRoom.setRoomName(roomName);
	}
	public ReservableRoomId getReservableRoomId() {
		return reservableRoomId;
	}
	public void setReservableRoomId(ReservableRoomId reservableRoomId) {
		this.reservableRoomId = reservableRoomId;
	}

}
