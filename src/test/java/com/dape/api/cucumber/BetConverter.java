package com.dape.api.cucumber;

import com.dape.api.domain.entity.Bet;
import com.dape.api.domain.enums.BetStatusEnum;
import io.cucumber.java.DataTableType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

public class BetConverter {

    @DataTableType
    public Bet betDataTableType(Map<String, String> map){
        Bet bet = new Bet();
        bet.setDesBet(map.get("desBet"));
        bet.setIdtBet(Long.parseLong(map.get("idt_bet")));
        bet.setNumOdd(new BigDecimal(map.get("num_odd")));
        bet.setFlgSelected(Integer.parseInt(map.get("flg_selected")));
        bet.setDatCreated(LocalDate.parse(map.get("dat_created")).atStartOfDay());
        bet.setDatUpdated(LocalDate.parse(map.get("dat_updated")).atStartOfDay());
        bet.setDesBet(map.get("des_bet"));
        bet.setBetStatusEnum(BetStatusEnum.valueOf(map.get("bet_status")));

        return bet;
    }
}
