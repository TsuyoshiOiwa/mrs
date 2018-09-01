package mrs.domain.service;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import mrs.domain.model.ReservableRoom;
import mrs.domain.model.ReservableRoomId;
import mrs.domain.model.Reservation;
import mrs.domain.model.RoleName;
import mrs.domain.model.User;
import mrs.domain.repository.ReservableRoomRepository;
import mrs.domain.repository.ReservationRepository;
import mrs.domain.repository.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "/test.properties")
public class ReservationServiceTest {
	@Autowired
	ReservationService reservationService;

	@Autowired
	ReservableRoomRepository rrr;

	@Autowired
	ReservationRepository sss;

	@Autowired
	UserRepository ttt;

	Reservation reservationSample;

	private static Logger log = LoggerFactory.getLogger(ReservationServiceTest.class);

	@Before
	public void init() {
		reservationSample = new Reservation();

		User user = new User();
		ReservableRoom reservableRoom = new ReservableRoom(new ReservableRoomId(1, LocalDate.now()));

		user.setUserId("Hiroshi");
		user.setFirstName("Hiroshi");
		user.setLastName("田中");
		user.setPassword("Hiroshi");
		user.setRoleName(RoleName.USER);

		reservationSample.setUser(user);
		reservationSample.setReservableRoom(reservableRoom);
		reservationSample.setStartTime(LocalTime.of(13, 0));
		reservationSample.setEndTime(LocalTime.of(15, 0));
	}

	@Test
	public void reserveTest() {

		List<ReservableRoom> listReservableRoom = rrr.findAll();
		List<Reservation> listReservation = sss.findAll();

		log.info("****** list of reservable_room ******");
		for(ReservableRoom i: listReservableRoom) {
			String msg = MessageFormat.format("roomid:{0}, date:{1}", i.getRoomId(), i.getReservedDate());
			log.info(msg);
		}
		log.info("****** list end ******");

		log.info("****** list of reservation ******");
		for(Reservation i: listReservation) {
			String msg = MessageFormat.format("id:{0}, user:{1}, sTime:{2}", i.getReservationId(), i.getUser().getUserId(), i.getStartTime());
			log.info(msg);
		}
		log.info("****** list end ******");

		log.info("new reservation uploading...");
		String message = MessageFormat.format("roomid:{0}, date:{1}", reservationSample.getReservableRoom().getRoomId(), reservationSample.getReservableRoom().getReservedDate());
		log.info(message);
		reservationService.reserve(reservationSample);

		log.info("****** list of reservation ******");
		listReservation = sss.findAll();
		for(Reservation i: listReservation) {
			String msg = MessageFormat.format("id:{0}, user:{1}, sTime:{2}", i.getReservationId(), i.getUser().getUserId(), i.getStartTime());
			log.info(msg);
		}
		log.info("****** list end ******");
	}

	@Test
	public void cancelTest() {
		List<Reservation> listReservation = sss.findAll();

		log.info("****** list of reservation ******");
		for(Reservation i: listReservation) {
			String msg = MessageFormat.format("id:{0}, user:{1}, sTime:{2}", i.getReservationId(), i.getUser().getUserId(), i.getStartTime());
			log.info(msg);
		}
		log.info("****** list end ******");

		reservationService.cancel(2, reservationSample.getUser());
		reservationService.cancel(1, ttt.findByUserId("Kirito"));

		listReservation = sss.findAll();
		log.info("****** list of reservation ******");
		for(Reservation i: listReservation) {
			String msg = MessageFormat.format("id:{0}, user:{1}, sTime:{2}", i.getReservationId(), i.getUser().getUserId(), i.getStartTime());
			log.info(msg);
		}
		log.info("****** list end ******");
	}
}
