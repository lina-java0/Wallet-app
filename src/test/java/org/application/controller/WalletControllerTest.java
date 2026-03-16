package org.application.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class WalletControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private UUID walletId;

    @Test
    void shouldCreateWallet() throws Exception {

        UUID walletId = UUID.randomUUID();

        String request = """
                {
                "walletId" : "%s"
                }
                """.formatted(walletId);

        mockMvc.perform(post("/api/v1/wallet")
                .contentType(MediaType.APPLICATION_JSON)
                .content(request))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldDeposit() throws Exception {

        UUID walletId = createWallet();

        String request = """
                {
                "walletId" : "%s",
                "operationType":"DEPOSIT",
                "amount":100
                }
                """.formatted(walletId);

        mockMvc.perform(post("/api/v1/wallet/operation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(100));
    }

    @Test
    void shouldDepositAndWithdraw() throws Exception {

        UUID walletId = createWallet();

        String depositRequest = """
                {
                "walletId" : "%s",
                "operationType":"DEPOSIT",
                "amount":200
                }
                """.formatted(walletId);

        mockMvc.perform(post("/api/v1/wallet/operation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(depositRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(200));

        String withdrawRequest = """
                {
                "walletId" : "%s",
                "operationType":"WITHDRAW",
                "amount":100
                }
                """.formatted(walletId);

        mockMvc.perform(post("/api/v1/wallet/operation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(withdrawRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.balance").value(100));
    }

    @Test
    void shouldFailWhenWithdrawTooMuch() throws Exception {

        UUID walletId = createWallet();

        String request = """
            {
            "walletId":"%s",
            "operationType":"WITHDRAW",
            "amount":100
            }
            """.formatted(walletId);

        mockMvc.perform(post("/api/v1/wallet/operation")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnWalletBalance() throws Exception {

        UUID walletId = createWallet();

        mockMvc.perform(get("/api/v1/wallet/" + walletId))
                .andExpect(status().isOk());
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
