package com.playground.rnd.configs;

import lombok.*;

@Getter
@Setter
@Builder
public class FlagTransactionConfig {
    private Double flagAmount;
    private int flagPeriodInDays;

    /**
     * Get the default config.
     * NOTE: You can set custom values for the config from API or as deemed fit and store in db or cache.
     * In such a case, you'd have a FlagTransactionConfigService that will return the configured FlagTransactionConfig value.
     * @return FlagTransactionConfig.
     */
    public static FlagTransactionConfig getDefault(){

        // if cache or db is available, get the record from db/cache
        // otherwise (not in db or cache) return this default.
        return FlagTransactionConfig.builder()
                .flagAmount(10000.00)
                .flagPeriodInDays(7)
                .build();
    }
}
