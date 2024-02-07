-- create column accounting_accounts
ALTER TABLE accounting_accounts ADD aggregate_account_id integer NULL;

-- create foreign key for accounting_accounts
ALTER TABLE accounting_accounts
     ADD CONSTRAINT fk_accounting_accounts_002 FOREIGN KEY (aggregate_account_id) REFERENCES accounting_accounts(id);

-- create view balance_sheet with aggregator id
CREATE OR REPLACE VIEW balance_sheet_view
AS SELECT COALESCE(aa.aggregate_account_id, aa.id) AS id,
    COALESCE(ag.description, aa.description) AS description,
    bs.cost_value,
    bs.month_year
        FROM balance_sheet bs
    JOIN accounting_accounts aa ON aa.id = bs.accounting_accounts_id
    LEFT JOIN accounting_accounts ag ON ag.id = aa.aggregate_account_id;