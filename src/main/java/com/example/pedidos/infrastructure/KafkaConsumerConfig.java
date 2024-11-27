package com.example.pedidos.infrastructure;

import com.example.pedidos.domain.Pedido;
import com.example.pedidos.application.PedidoService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaConsumerConfig {

    private final PedidoService pedidoService;

    public KafkaConsumerConfig(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @KafkaListener(topics = "pedidos", groupId = "grupo-pedidos")
    public void processarPedido(Pedido pedido) {
        pedidoService.atualizarStatus(pedido.getId());
    }
}
