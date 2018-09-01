package mrs.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mrs.domain.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	public User findByUserId(String userId);
}
