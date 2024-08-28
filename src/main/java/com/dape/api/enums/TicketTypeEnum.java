package com.dape.api.enums;

public enum TicketTypeEnum {
    SIMPLE(1),
    MULTIPLE(2);

    private final int codTicketType;

    TicketTypeEnum(int codTicketType) {
        this.codTicketType = codTicketType;
    }

    public int getCodTicketType() {
        return codTicketType;
    }

    public static TicketTypeEnum fromCode(int codTicketType) {
        for (TicketTypeEnum ticketTypeEnum : TicketTypeEnum.values()) {
            if (ticketTypeEnum.getCodTicketType() == codTicketType) {
                return ticketTypeEnum;
            }
        }
        throw new UnsupportedOperationException("Unsupported ticket code: " + codTicketType);
    }
}
