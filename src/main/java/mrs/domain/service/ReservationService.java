package mrs.domain.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import mrs.domain.exception.AlreadyReservedException;
import mrs.domain.exception.UnavailableReservationException;
import mrs.domain.model.ReservableRoom;
import mrs.domain.model.ReservableRoomId;
import mrs.domain.model.Reservation;
import mrs.domain.model.RoleName;
import mrs.domain.model.User;
import mrs.domain.repository.ReservableRoomRepository;
import mrs.domain.repository.ReservationRepository;

@Service
@Transactional
public class ReservationService {
	@Autowired
	ReservationRepository reservationRepository;

	@Autowired
	ReservableRoomRepository reservableRoomRepository;

	public List<Reservation> findByReservableRoomId(ReservableRoomId reservableRoomId){
		return reservationRepository.findByReservableRoom_ReservableRoomIdOrderByStartTimeAsc(reservableRoomId);
	}

	public Reservation reserve(Reservation reservation) {
		ReservableRoomId reservableRoomId = reservation.getReservableRoom().getReservableRoomId();

		Optional<ReservableRoom> reservable = reservableRoomRepository.findById(reservableRoomId);

		//予約する日付場所が、予約不可能なとき、
		if(!reservable.isPresent()) {
			//例外発生
			throw new UnavailableReservationException("入力の日付、場所の組み合わせでは予約できません。");
		}


		boolean overlap = reservationRepository.findByReservableRoom_ReservableRoomIdOrderByStartTimeAsc(reservableRoomId)
				.stream()
				.anyMatch(x -> x.overlap(reservation));

		//データベース上に登録された予約情報と比較して場所、時間帯に重複があるとき
		if(overlap) {
			//例外発生
			throw new AlreadyReservedException("入力の時間帯はすでに予約済みです。");
		}

		reservationRepository.save(reservation);
		return reservation;
	}

	public void cancel(Integer reservationId, User user) {
		//ユーザーの権限を見てUSERの場合、自分の予約のみ
		//ADMINの場合全ての予約をキャンセル可能
		Reservation reservation;

		try {
			Optional<Reservation> reservationOpt = reservationRepository.findById(reservationId);
			reservation = reservationOpt.get();
		}catch(NoSuchElementException | IllegalArgumentException e) {
			//指定されたIDの予約が見つからなかったとき
			throw new IllegalArgumentException("指定されたIDの予約は存在しないか、nullが指定されました。");
		}
		if(user.getRoleName() == RoleName.USER
				&& !reservation.getUser().getUserId().equals(user.getUserId())) {
			throw new AccessDeniedException("ユーザーに予約をキャンセルする権限がありません。");
		}

		reservationRepository.delete(reservation);

	}
}
