package com.drimtim.scrapend.repositories;

import com.drimtim.scrapend.model.Command;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by jonathan on 24/05/17.
 */
@Repository
public interface CommandRepository extends MongoRepository<Command, String> {}
