package org.application.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class WalletControllerConcurrentTest {

    @Autowired
    private MockMvc mockMvc;

    private UUID walletId;

    @Test
    void shouldHandleConcurrentDeposits() throws Exception {

        UUID walletId = createWallet();

        int threads = 1000;
        ExecutorService executor = Executors.newFixedThreadPool(50);

        CountDownLatch latch = new CountDownLatch(threads);

        for (int i = 0; i < threads; i++) {

            executor.submit(() -> {

                try {
                    String request = """
                        {
                        "walletId":"%s",
                        "operationType":"DEPOSIT",
                        "amount":1
                        }
                        """.formatted(walletId);

                    mockMvc.perform(post("/api/v1/wallet/operation")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(request));

                } catch (Exception ignored) {}

                latch.countDown();
            });
        }

        latch.await();

        mockMvc.perform(get("/api/v1/wallet/" + walletId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(1000));
    }

    private UUID createWallet() throws Exception {
        UUID id = UUID.randomUUID();
        String request = """
                {
                "walletId":"%s"
                }
                """.formatted(id);

        mockMvc.perform(post("/api/v1/wallet")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated());

        return id;
    }
}
