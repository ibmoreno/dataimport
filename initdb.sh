#!/bin/bash
set -e

psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" -d "$POSTGRES_DB"  <<-EOSQL
  CREATE USER user_import WITH PASSWORD 'user_import';
  CREATE DATABASE data_import OWNER user_import;
EOSQL