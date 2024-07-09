package com.dataimport.api.infra.database.jpa.repository;

import com.dataimport.api.infra.database.jpa.entity.BalanceSheetEntity;
import com.dataimport.api.infra.database.jpa.entity.BalanceSheetEntityPk;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BalanceSheetRepository extends JpaRepository<BalanceSheetEntity, BalanceSheetEntityPk> {
    @Modifying
    @Query(value = "DELETE FROM balance_sheet WHERE customers_id = :customerId AND month_year in (:monthYear)", nativeQuery = true)
    void deleteAllByCustomerIdAndMonthYearList(Integer customerId, List<LocalDate> monthYear);
}