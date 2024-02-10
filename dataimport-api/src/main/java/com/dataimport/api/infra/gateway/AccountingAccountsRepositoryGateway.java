package com.dataimport.api.infra.gateway;

import com.dataimport.api.application.gateway.AccountingAccountsGateway;
import com.dataimport.api.domain.AccountingAccounts;
import com.dataimport.api.domain.AggregateAccount;
import com.dataimport.api.domain.Status;
import com.dataimport.api.infra.database.jpa.entity.AccountingAccountsEntity;
import com.dataimport.api.infra.database.jpa.entity.AccountingAccountsEntity_;
import com.dataimport.api.infra.database.jpa.repository.AccountingAccountsRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
public class AccountingAccountsRepositoryGateway implements AccountingAccountsGateway {

    private final AccountingAccountsRepository accountingAccountsRepository;
    private static final Specification<AccountingAccountsEntity> statusInActiveAndInactive = (root, query, criteriaBuilder) ->
            criteriaBuilder.in(root.get(AccountingAccountsEntity_.STATUS)).value(List.of(Status.A, Status.I));

    @Override
    public List<AccountingAccounts> findAllByStatus(Status status) {
        return accountingAccountsRepository.findAllByStatus(status)
                .stream().map(AccountingAccountsEntity::toDomain).toList();
    }

    @Override
    public List<AccountingAccounts> findAllByAggregateAccount(AggregateAccount aggregateAccount) {
        AccountingAccountsEntity accountingAccountsAggregator = accountingAccountsRepository.getReferenceById(aggregateAccount.getId());
        return accountingAccountsRepository.findAllByAggregateAccount(accountingAccountsAggregator)
                .stream()
                .map(AccountingAccountsEntity::toDomain).toList();
    }

    @Override
    public Page<AccountingAccounts> findAll(Pageable pageable) {
        return accountingAccountsRepository.findAll(statusInActiveAndInactive, pageable)
                .map(AccountingAccountsEntity::toDomain);
    }

    @Override
    public Optional<AccountingAccounts> getOne(Integer id) {
        AccountingAccountsEntity accountingAccountsEntity = accountingAccountsRepository.getReferenceById(id);
        return Optional.of(accountingAccountsEntity).map(AccountingAccountsEntity::toDomain);
    }

    @Override
    public AccountingAccounts save(AccountingAccounts accountingAccounts) {
        AccountingAccountsEntity accountingAccountsEntity = accountingAccounts.toEntity();
        return accountingAccountsRepository.save(accountingAccountsEntity).toDomain();
    }

    @Override
    public List<AccountingAccounts> saveAll(List<AccountingAccounts> accountingAccounts) {
        List<AccountingAccountsEntity> accountingAccountsEntities =
                accountingAccounts.stream().map(AccountingAccounts::toEntity).toList();
        accountingAccountsRepository.saveAll(accountingAccountsEntities);
        return accountingAccountsEntities.stream().map(AccountingAccountsEntity::toDomain).toList();
    }

    @Override
    public void delete(AccountingAccounts accountingAccounts) {
        accountingAccountsRepository.deleteById(accountingAccounts.getId());
    }

    @Override
    public Page<AccountingAccounts> search(String search, Pageable pageable) {

        if (!StringUtils.hasText(search)) {
            return this.findAll(pageable);
        }

        StringBuilder pattern = new StringBuilder("%");
        pattern.append(search.toLowerCase());
        pattern.append("%");

        Specification<AccountingAccountsEntity> specification = (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.lower(root.get(AccountingAccountsEntity_.DESCRIPTION)), pattern.toString());
        specification.and(statusInActiveAndInactive);

        return accountingAccountsRepository.findAll(specification, pageable).map(AccountingAccountsEntity::toDomain);

    }
}
