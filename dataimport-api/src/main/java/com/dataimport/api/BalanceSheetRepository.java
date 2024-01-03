package com.dataimport.api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceSheetRepository extends JpaRepository<BalanceSheet, BalanceSheetPk> {
}
