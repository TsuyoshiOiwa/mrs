package mrs.domain.service;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import mrs.domain.model.ReservableRoom;

@RunWith(SpringRunner.class)
@SpringBootTest
@TestPropertySource(locations = "/test.properties")
public class RoomServiceTest {
	@Autowired
	RoomService roomService;
	
	private static Logger log = LoggerFactory.getLogger(RoomServiceTest.class);
	
	@Test
	public void findReservableRoomsTest() {
		
		List<ReservableRoom> roomList;
		
		LocalDate today = LocalDate.now();
		roomList = roomService.findReservableRooms(today);
		
		assertNotNull(roomList);
		for(ReservableRoom i: roomList) {
			log.info("message");
			log.info(String.format("r_date: %s, id: %d", i.getReservedDate().toString(), i.getRoomId()));
			
		}
		log.info("test end");

		
	}
}
