package com.jitterted.ebp.blackjack.domain;

import com.jitterted.ebp.blackjack.domain.port.GameMonitor;

public class DoNothingGameMonitor implements GameMonitor {
    @Override
    public void roundCompleted(Game game) {
        //ignore
    }
}
