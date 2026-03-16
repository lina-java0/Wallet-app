package org.application.service.operation;

import org.application.service.model.OperationType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class OperationStrategyFactory {

    private final Map<OperationType, OperationStrategy> strategies;

    public OperationStrategyFactory(List<OperationStrategy> strategies) {
        this.strategies = strategies.stream()
                .collect(Collectors.toMap(
                        OperationStrategy::getType,
                        Function.identity()
                ));
    }

    public OperationStrategy getStrategy(OperationType type) {
        return strategies.get(type);
    }
}
