package com.cali.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GameInputBet {

    @JsonProperty("bet_amount")
    private double betAmount;

    public double getBetAmount() {
        return betAmount;
    }

    public void setBetAmount(double betAmount) {
        this.betAmount = betAmount;
    }
}
