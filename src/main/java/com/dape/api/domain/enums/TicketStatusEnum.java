package com.dape.api.domain.enums;

public enum TicketStatusEnum {
    PENDING(1, "Bilhete pendente"),
    GREEN(2, "Bilhete vencido"),
    RED(3, "Bilhete perdido"),;

    private final int codTicketStatus;
    private final String desTicketStatus;

    TicketStatusEnum(int codTicketStatus, String desTicketStatus) {
        this.codTicketStatus = codTicketStatus;
        this.desTicketStatus = desTicketStatus;
    }

    public int getCodTicketStatus() {
        return codTicketStatus;
    }

    public String getDesTicketStatus() {
        return desTicketStatus;
    }

    public static TicketStatusEnum fromCode(int codTicketStatus) {
        for (TicketStatusEnum ticketStatusEnum : TicketStatusEnum.values()) {
            if (codTicketStatus == ticketStatusEnum.getCodTicketStatus()) {
                return ticketStatusEnum;
            }
        }
        throw new UnsupportedOperationException("Unsupported ticket status code: " + codTicketStatus);
    }
}
