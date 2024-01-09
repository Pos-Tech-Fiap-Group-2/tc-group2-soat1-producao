package com.techchallenge.producao.drivers.db.repositories;
import org.springframework.beans.factory.config.Scope;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.techchallenge.producao.drivers.db.entities.PedidoEntity;

import java.util.Optional;

@Repository
public interface ProducaoRepository extends MongoRepository<PedidoEntity, String> {

}
