package com.example.pedidos.application;

import com.example.pedidos.domain.Pedido;
import com.example.pedidos.infrastructure.PedidoRepository;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class PedidoService {

    private final PedidoRepository repository;
    private final KafkaTemplate<String, Pedido> kafkaTemplate;

    public PedidoService(PedidoRepository repository, KafkaTemplate<String, Pedido> kafkaTemplate) {
        this.repository = repository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public Pedido criarPedido(Pedido pedido) {
        pedido.setStatus("AGUARDANDO_ENVIO"); // Define o status inicial
        Pedido salvo = repository.save(pedido); // Salva no MongoDB
        kafkaTemplate.send("pedidos", salvo); // Envia o pedido para o Kafka
        return salvo;
    }

    public Pedido consultarPedido(String id) {
        // Busca o pedido pelo ID ou lança uma exceção caso não exista
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pedido não encontrado"));
    }

    public void atualizarStatus(String id) {
        // Busca o pedido, atualiza o status e salva novamente
        Pedido pedido = consultarPedido(id);
        pedido.setStatus("ENVIADO_TRANSPORTADORA");
        repository.save(pedido);
    }
}
