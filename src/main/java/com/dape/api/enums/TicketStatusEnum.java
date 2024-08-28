package com.dape.api.enums;

public enum TicketStatusEnum {
    PENDING(1),
    GREEN(2),
    RED(3);

    private final int codTicketStatus;

    TicketStatusEnum(int codTicketStatus) {
        this.codTicketStatus = codTicketStatus;
    }

    public int getCodTicketStatus() {
        return codTicketStatus;
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
