package com.techchallenge.producao.drivers.db.repositories;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.techchallenge.producao.drivers.db.entities.TemplateEntity;

@Repository
public interface TemplateRepository extends MongoRepository<TemplateEntity, String> {
	
}
