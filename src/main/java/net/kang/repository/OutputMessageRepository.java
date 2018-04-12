package net.kang.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import net.kang.domain.OutputMessage;

public interface OutputMessageRepository extends MongoRepository<OutputMessage, String>{ // OutputMessage에 대한 Repository
	OutputMessage findTopByOrderByCurrentTimeDesc();
}
