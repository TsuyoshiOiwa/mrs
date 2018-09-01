package mrs.app.reservation;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import mrs.domain.exception.AlreadyReservedException;
import mrs.domain.exception.UnavailableReservationException;
import mrs.domain.model.MeetingRoom;
import mrs.domain.model.ReservableRoom;
import mrs.domain.model.ReservableRoomId;
import mrs.domain.model.Reservation;
import mrs.domain.model.RoleName;
import mrs.domain.model.User;
import mrs.domain.service.ReservationService;
import mrs.domain.service.RoomService;
import mrs.domain.service.user.ReservationUserDetails;

@Controller
@RequestMapping("reservations/{date}/{roomId}")
public class ReservationController {

	@Autowired
	ReservationService reservationService;
	@Autowired
	RoomService roomService;

	@ModelAttribute
	ReservationForm setUpForm() {
		ReservationForm form = new ReservationForm();

		form.setStartTime(LocalTime.of(9,0));
		form.setEndTime(LocalTime.of(10, 0));
		return form;
	}

	@RequestMapping(method=RequestMethod.GET)
	String reserveForm(@DateTimeFormat(iso=DateTimeFormat.ISO.DATE) @PathVariable("date") LocalDate date,
			@PathVariable("roomId") Integer roomId, Model model) {

		MeetingRoom meetingRoom = roomService.findMeetingRoom(roomId);
		ReservableRoomId reservableRoomId = new ReservableRoomId(roomId,date);
		List<Reservation> reservations = reservationService.findByReservableRoomId(reservableRoomId);

		List<LocalTime> timeList = Stream.iterate(LocalTime.of(0, 0), c -> (LocalTime)c.plusMinutes(15))
				.limit(96)
				.collect(Collectors.toList());

		model.addAttribute("room", meetingRoom);
		model.addAttribute("reservations", reservations);
		model.addAttribute("timeList", timeList);
		//model.addAttribute("user", dummyUser());

		return "reservation/reserveForm";
	}

	@RequestMapping(method=RequestMethod.POST)
	String reserve(@Validated ReservationForm form, BindingResult bindingResult,
			@AuthenticationPrincipal ReservationUserDetails userDetails,
			@DateTimeFormat(iso=DateTimeFormat.ISO.DATE) @PathVariable("date") LocalDate date,
			@PathVariable("roomId") Integer roomId,
			Model model) {

		//入力チェックの結果
		if(bindingResult.hasErrors()) {
			return reserveForm(date, roomId, model);
		}

		Reservation reservation = new Reservation();

		reservation.setStartTime(form.getStartTime());
		reservation.setEndTime(form.getEndTime());
		reservation.setUser(userDetails.getUser());
		reservation.setReservableRoom(new ReservableRoom(new ReservableRoomId(roomId, date)));

		try {
			reservationService.reserve(reservation);
		}catch(AlreadyReservedException | UnavailableReservationException e) {
			model.addAttribute("error", e.getMessage());
			return reserveForm(date, roomId, model);
		}

		return "redirect:/reservations/{date}/{roomId}";
	}

	@RequestMapping(method=RequestMethod.POST, params="cancel")
	String cancel(@RequestParam("reservationId") Integer reservationId,
	@AuthenticationPrincipal ReservationUserDetails userDetails,
	@DateTimeFormat(iso=DateTimeFormat.ISO.DATE) @PathVariable("date") LocalDate date,
	@PathVariable("roomId") Integer roomId,
	Model model) {

		try {
			reservationService.cancel(reservationId, userDetails.getUser());
		}catch(IllegalArgumentException | AccessDeniedException e) {
			model.addAttribute("error", e.getMessage());
			return reserveForm(date, roomId, model);
		}

		return "redirect:/reservations/{date}/{roomId}";
	}

	private User dummyUser() {
		User user = new User();
		user.setUserId("Akane");
		user.setFirstName("あかね");
		user.setLastName("Gilvert");
		user.setPassword("あかね");
		user.setRoleName(RoleName.USER);

		return user;
	}
}
