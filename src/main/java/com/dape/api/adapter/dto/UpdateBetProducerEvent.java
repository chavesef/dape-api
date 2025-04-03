package com.dape.api.adapter.dto;

public class UpdateBetProducerEvent {

    private Long idtBet;
    private int betStatus;

    public UpdateBetProducerEvent(Builder builder) {
        this.idtBet = builder.idtBet;
        this.betStatus = builder.betStatus;
    }

    public static class Builder {
        private Long idtBet;
        private int betStatus;

        public Builder idtBet(Long idtBet) {
            this.idtBet = idtBet;
            return this;
        }

        public Builder betStatus(int betStatus) {
            this.betStatus = betStatus;
            return this;
        }

        public UpdateBetProducerEvent build() {
            return new UpdateBetProducerEvent(this);
        }
    }

    public int getBetStatus() {
        return betStatus;
    }

    public void setBetStatus(int betStatus) {
        this.betStatus = betStatus;
    }

    public Long getIdtBet() {
        return idtBet;
    }

    public void setIdtBet(Long idtBet) {
        this.idtBet = idtBet;
    }
}
