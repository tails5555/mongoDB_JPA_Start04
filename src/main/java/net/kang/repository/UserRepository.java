package net.kang.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import net.kang.domain.User;

public interface UserRepository extends MongoRepository<User, String>{ // User에 대한 Repository
	Optional<User> findByUserId(String userId);
}
