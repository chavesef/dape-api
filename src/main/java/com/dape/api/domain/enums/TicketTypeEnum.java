package com.dape.api.domain.enums;

public enum TicketTypeEnum {
    SIMPLE(1, "Bilhete simples"),
    MULTIPLE(2, "Bilhete múltiplo"),;

    private final int codTicketType;
    private final String desTicketType;

    TicketTypeEnum(int codTicketType, String desTicketType) {
        this.codTicketType = codTicketType;
        this.desTicketType = desTicketType;
    }

    public int getCodTicketType() {
        return codTicketType;
    }

    public String getDesTicketType() {
        return desTicketType;
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
