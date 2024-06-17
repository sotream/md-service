package com.sotream.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sotream.dto.MarketDataDTO;
import com.sotream.dto.MarketTradeDTO;
import jakarta.annotation.PostConstruct;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.WebSocketSession;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class MarketDataWebSocketHandler implements WebSocketHandler {

    private static final Logger logger = LogManager.getLogger(MarketDataWebSocketHandler.class);

    @Value("${FINNHUB_API_KEY}")
    private String FINNHUB_API_KEY;

    private final MarketDataService marketDataService;
    private final ObjectMapper objectMapper;
    private static final String FINNHUB_URL = "wss://ws.finnhub.io?token=";
    private final CopyOnWriteArrayList<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Autowired
    public MarketDataWebSocketHandler(MarketDataService marketDataService) {
        this.marketDataService = marketDataService;
        this.objectMapper = new ObjectMapper();
    }

    @PostConstruct
    public void connectToWebSocket() {
        ReactorNettyWebSocketClient client = new ReactorNettyWebSocketClient();

        client.execute(URI.create(FINNHUB_URL+FINNHUB_API_KEY), this).subscribe(
                null,
                error -> logger.error("WebSocket connection error: {}", error.getMessage()),
                () -> logger.info("WebSocket connection established")
        );
    }

    @Override
    @NonNull
    public Mono<Void> handle(@NonNull WebSocketSession session) {
        String initialMessage = "{\"type\":\"subscribe\",\"symbol\":\"BINANCE:BTCUSDT\"}";
        WebSocketMessage message = session.textMessage(initialMessage);

        Mono<Void> sendInitialMessage = session.send(Mono.just(message))
                .doOnSuccess(aVoid -> logger.info("Subscription message sent: {}", initialMessage))
                .doOnError(error -> logger.error("Error sending subscription message: {}", error.getMessage()));

        Mono<Void> receiveMessages = session.receive()
                .map(WebSocketMessage::getPayloadAsText)
                .doOnNext(payload -> logger.debug("Received message: {}", payload))
                .filter(this::isNotPingMessage)
                .flatMap(this::handleIncomingMessage)
                .then();

        return sendInitialMessage.then(receiveMessages);
    }

    private Mono<Void> handleIncomingMessage(String payload) {
        return Mono.fromRunnable(() -> {
            MarketDataDTO marketDataDTO = parseMarketDataFromJson(payload);

            if (marketDataDTO != null) {
                List<MarketTradeDTO> marketTrades = marketDataDTO.data();

                for (MarketTradeDTO marketTrade : marketTrades) {
                    marketDataService.saveMarketData(marketTrade);
                }

                logger.debug("Market data saved: {}", marketDataDTO);
            }
        });
    }

    private boolean isPingMessage(String payload) {
        try {
            MarketDataDTO marketDataDTO = objectMapper.readValue(payload, MarketDataDTO.class);

            return marketDataDTO != null && "ping".equals(marketDataDTO.type());
        } catch (Exception e) {
            logger.error("Error parsing or checking message type: {}", e.getMessage());
            return false;
        }
    }

    private boolean isNotPingMessage(String payload) {
        return !isPingMessage(payload);
    }

    private MarketDataDTO parseMarketDataFromJson(String jsonPayload) {
        try {
            return objectMapper.readValue(jsonPayload, MarketDataDTO.class);
        } catch (Exception e) {
            logger.error("Error parsing JSON: {}", e.getMessage());

            return null;
        }
    }
}
