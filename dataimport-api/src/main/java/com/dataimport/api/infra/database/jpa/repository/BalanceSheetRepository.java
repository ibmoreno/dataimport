package com.dataimport.api.infra.database.jpa.repository;

import com.dataimport.api.infra.database.jpa.entity.BalanceSheetEntity;
import com.dataimport.api.infra.database.jpa.entity.BalanceSheetEntityPk;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceSheetRepository extends JpaRepository<BalanceSheetEntity, BalanceSheetEntityPk> {
}
