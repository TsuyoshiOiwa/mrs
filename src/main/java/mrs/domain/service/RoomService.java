package mrs.domain.service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mrs.domain.model.MeetingRoom;
import mrs.domain.model.ReservableRoom;
import mrs.domain.repository.MeetingRoomRepository;
import mrs.domain.repository.ReservableRoomRepository;

@Service
@Transactional
public class RoomService {

	@Autowired
	ReservableRoomRepository reservableRoomRepository;

	@Autowired
	MeetingRoomRepository meetingRoomRepository;

	public List<ReservableRoom> findReservableRooms(LocalDate date){
		return reservableRoomRepository.findByReservableRoomId_reservedDateOrderByReservableRoomId_roomIdAsc(date);
	}

	public MeetingRoom findMeetingRoom(Integer id) {
		return meetingRoomRepository.findById(id)
				.orElseThrow(() -> new NoSuchElementException("指定されたIDの会議室は存在しません。"));
	}

}
