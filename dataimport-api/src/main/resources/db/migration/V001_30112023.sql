--
-- INITIAL SCRIPT DATA BASE
-- DATE: 30/11/2023
-- IVAN B. MORENO
--

-- create table customers
CREATE TABLE customers (
	id integer NOT NULL,
	name varchar(100) NOT NULL,
	cnpj varchar(14) NULL,
    address varchar(100) NULL,
    number varchar(10) NULL,
    complement varchar(50) NULL,
    city varchar(50) NULL,
    state varchar(2) NULL,
    zip_code varchar(8) NULL,
	email varchar(50) NULL,
    phone varchar(15) NULL,
    status varchar(1) NOT NULL,
    read_model_version varchar(3) NOT NULL,
    created_at timestamp without time zone NOT NULL,
    updated_at timestamp without time zone NOT NULL
);

-- create primary key company
ALTER TABLE customers
    ADD CONSTRAINT pk_customers PRIMARY KEY (id);

-- create sequence company
CREATE SEQUENCE seq_customers
    START 1
    INCREMENT 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

-- create table accounting accounts
CREATE TABLE accounting_accounts (
	id integer NOT NULL,
	description varchar(100) NOT NULL,
	status varchar(1) NOT NULL,
	created_at timestamp without time zone NOT NULL,
	updated_at timestamp without time zone NOT NULL
);

-- create primary key accounts
ALTER TABLE accounting_accounts
    ADD CONSTRAINT pk_accounting_accounts PRIMARY KEY (id);

-- create sequence accounts
CREATE SEQUENCE seq_accounting_accounts
    START 1
    INCREMENT 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

-- create table accounts_movement
CREATE TABLE balance_sheet (
    customers_id integer NOT NULL,
 	accounting_accounts_id integer NOT NULL,
 	month_year date NOT NULL,
 	cost_value numeric(15,2) NOT NULL,
 	created_at timestamp without time zone NOT NULL,
 	updated_at timestamp without time zone NOT NULL
);

-- create primary key accounts_movement
ALTER TABLE balance_sheet
    ADD CONSTRAINT pk_balance_sheet PRIMARY KEY (customers_id, accounting_accounts_id, month_year);

-- create foreign key for accounts_movement
ALTER TABLE balance_sheet
     ADD CONSTRAINT fk_customer_001 FOREIGN KEY (customers_id) REFERENCES customers(id);

ALTER TABLE balance_sheet
     ADD CONSTRAINT fk_accounting_accounts_001 FOREIGN KEY (accounting_accounts_id) REFERENCES accounting_accounts(id);

-- insert data in table customers
INSERT INTO customers (id, name, status, read_model_version, created_at, updated_at) VALUES
     (nextval('seq_customers'),'Client Sample', 'A', 'V01', current_timestamp, current_timestamp);

-- insert data in table accounts
INSERT INTO accounting_accounts (id,description,status,created_at,updated_at) VALUES
	 (nextval('seq_accounting_accounts'),'Ativo Circulante','A', current_timestamp, current_timestamp),
	 (nextval('seq_accounting_accounts'),'Caixa e Equivalentes de Caixa','A', current_timestamp, current_timestamp),
	 (nextval('seq_accounting_accounts'),'Valores a Receber','A', current_timestamp, current_timestamp),
	 (nextval('seq_accounting_accounts'),'Estoques','A', current_timestamp, current_timestamp),
	 (nextval('seq_accounting_accounts'),'Estoque de Cientes','A', current_timestamp, current_timestamp),
	 (nextval('seq_accounting_accounts'),'Outros Valores a Receber','A', current_timestamp, current_timestamp),
	 (nextval('seq_accounting_accounts'),'Ativo Não Circulante','A', current_timestamp, current_timestamp),
	 (nextval('seq_accounting_accounts'),'Investimentos','A', current_timestamp, current_timestamp),
	 (nextval('seq_accounting_accounts'),'Imobilizado','A', current_timestamp, current_timestamp);
INSERT INTO accounting_accounts (id,description,status,created_at,updated_at) VALUES
	 (nextval('seq_accounting_accounts'),'(-) Depreciação','A', current_timestamp, current_timestamp),
	 (nextval('seq_accounting_accounts'),'TOTAL DO ATIVO','A', current_timestamp, current_timestamp),
	 (nextval('seq_accounting_accounts'),'Passivo Circulante','A', current_timestamp, current_timestamp),
	 (nextval('seq_accounting_accounts'),'Passivo Não Circulante','A', current_timestamp, current_timestamp),
	 (nextval('seq_accounting_accounts'),'Patrimônio Líquido','A', current_timestamp, current_timestamp),
	 (nextval('seq_accounting_accounts'),'TOTAL DO PASSIVO','A', current_timestamp, current_timestamp),
	 (nextval('seq_accounting_accounts'),'(+) Receita Bruta','A', current_timestamp, current_timestamp),
	 (nextval('seq_accounting_accounts'),'(-) Deduções da Receita','A', current_timestamp, current_timestamp),
	 (nextval('seq_accounting_accounts'),'(-) Impostos sobre Venda','A', current_timestamp, current_timestamp),
	 (nextval('seq_accounting_accounts'),'(-) Devoluções','A', current_timestamp, current_timestamp);
INSERT INTO accounting_accounts (id,description,status,created_at,updated_at) VALUES
	 (nextval('seq_accounting_accounts'),'(=) Receita Líquida','A', current_timestamp, current_timestamp),
	 (nextval('seq_accounting_accounts'),'(-) CPV/CSP','A', current_timestamp, current_timestamp),
	 (nextval('seq_accounting_accounts'),'(=) Margem de Contribuição Primária','A', current_timestamp, current_timestamp),
	 (nextval('seq_accounting_accounts'),'(-) Despesas Variáveis','A', current_timestamp, current_timestamp),
	 (nextval('seq_accounting_accounts'),'(=) Margem de Contribuição','A', current_timestamp, current_timestamp),
	 (nextval('seq_accounting_accounts'),'(-) Custos e Despesas Fixas','A', current_timestamp, current_timestamp),
	 (nextval('seq_accounting_accounts'),'(-) Salários e Encargos','A', current_timestamp, current_timestamp),
	 (nextval('seq_accounting_accounts'),'(-) Gastos Indiretos de Fabricação','A', current_timestamp, current_timestamp);
INSERT INTO accounting_accounts (id,description,status,created_at,updated_at) VALUES
	 (nextval('seq_accounting_accounts'),'(-) Marketing','A', current_timestamp, current_timestamp),
	 (nextval('seq_accounting_accounts'),'(-) Gastos Gerais de Deslocamento','A', current_timestamp, current_timestamp),
	 (nextval('seq_accounting_accounts'),'(-) Despesas Administrativas','A', current_timestamp, current_timestamp),
	 (nextval('seq_accounting_accounts'),'(-) Outros Impostos','A', current_timestamp, current_timestamp),
	 (nextval('seq_accounting_accounts'),'(-) Consultoria Externa','A', current_timestamp, current_timestamp),
	 (nextval('seq_accounting_accounts'),'(-) Depreciação / Amortização','A', current_timestamp, current_timestamp),
	 (nextval('seq_accounting_accounts'),'(+) Reembolso de Despesas','A', current_timestamp, current_timestamp),
	 (nextval('seq_accounting_accounts'),'(=) Margem Operacional Líquida','A', current_timestamp, current_timestamp),
	 (nextval('seq_accounting_accounts'),'(+/-) Resultado Financeiro','A', current_timestamp, current_timestamp);
INSERT INTO accounting_accounts (id,description,status,created_at,updated_at) VALUES
	 (nextval('seq_accounting_accounts'),'(+/-) Resultado Não Operacional','A', current_timestamp, current_timestamp),
	 (nextval('seq_accounting_accounts'),'(=) Margem Líquida Antes dos Impostos','A', current_timestamp, current_timestamp),
	 (nextval('seq_accounting_accounts'),'(-) Impostos sobre o Lucro','A', current_timestamp, current_timestamp),
	 (nextval('seq_accounting_accounts'),'(=) Lucro Líquido após IRPJ/CSLL','A', current_timestamp, current_timestamp),
	 (nextval('seq_accounting_accounts'),'(+/-) Ajustes Ebitda/Lajida','A', current_timestamp, current_timestamp),
	 (nextval('seq_accounting_accounts'),'(=) Ebitda/Lajida','A', current_timestamp, current_timestamp),
	 (nextval('seq_accounting_accounts'),'(=) Lucro Operacional Após IR/CSLL (NOPAT/NOPLAT)','A', current_timestamp, current_timestamp);
INSERT INTO accounting_accounts (id,description,status,created_at,updated_at) VALUES
	 (nextval('seq_accounting_accounts'),'NCG - Necessidade Capital de Giro','A', current_timestamp, current_timestamp),
	 (nextval('seq_accounting_accounts'),'(=) Variação da NCG','A', current_timestamp, current_timestamp),
	 (nextval('seq_accounting_accounts'),'(=) Variação de Caixa','A', current_timestamp, current_timestamp);
