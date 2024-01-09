package com.techchallenge.producao.drivers.db.repositories;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.techchallenge.producao.drivers.db.entities.PedidoEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProducaoRepository extends MongoRepository<PedidoEntity, String> {
    @Aggregation(pipeline = {
            "{$sort: { dataCriacao: -1 } }",
            "{$group: {_id: \"$pedidoId\", dataCriacao: { $first: \"$dataCriacao\" }, status: { $first: \"$status\" } } }",
            "{$project: { _id: 0, pedidoId: \"$_id\", dataCriacao: 1, status: 1 } }"
    })
    List<PedidoEntity> findAllGroupByPedidoIdOrderByDataCriacao();

    @Aggregation(pipeline = {
            "{$match: { pedidoId: ?0 } }",
            "{$sort: { dataCriacao: -1 } }",
            "{$group: {_id: \"$pedidoId\", dataCriacao: { $first: \"$dataCriacao\" }, status: { $first: \"$status\" } } }",
            "{$project: { _id: 0, pedidoId: \"$_id\", dataCriacao: 1, status: 1 } }"
    })
    PedidoEntity findByPedidoId(String id);

    @Aggregation(pipeline = {
            "{$match: { pedidoId: ?0 } }",
            "{$sort: { dataCriacao: -1 } }",
            "{$project: { _id: 0, pedidoId: \"$_id\", dataCriacao: 1, status: 1 } }"
    })
    List<PedidoEntity> findAllByPedidoIdOrderByDataCriacao(String id);
}
