package com.techchallenge.producao.adapter.external.pedidos;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Component
public class PedidosAPI {

    @Value("${pedidos.api.host}")
    private String host;

    public void atualizarStatusDePedido(String id, String status) throws IOException {
        try {
            URL url = new URL(host + "/pedidos/" + id + "/status");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("PUT");
            con.setDoOutput(true);

            String jsonInputString = "{\"status\": \"" + status + "\"}";

            try (OutputStream os = con.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            con.getInputStream();

            if (con.getResponseCode() != 200) {
                throw new IOException("Erro ao atualizar status de pedido");
            }

            System.out.println("Status de pedido atualizado com sucesso");
        } catch (IOException e) {
            throw new IOException("Erro ao atualizar status de pedido");
        }
    }
}
