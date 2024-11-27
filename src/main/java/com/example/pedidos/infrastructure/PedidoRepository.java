package com.example.pedidos.infrastructure;

import com.example.pedidos.domain.Pedido;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PedidoRepository extends MongoRepository<Pedido, String> {
}
