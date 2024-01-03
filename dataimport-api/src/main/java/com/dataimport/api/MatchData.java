package com.dataimport.api;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MatchData {
    private Integer year;
    private List<Integer> months;
    @Builder.Default
    private Set<AccountingAccounts> accounts = new HashSet<>();
}