CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

--Create Users if necessary
DO
$do$
BEGIN
      IF NOT EXISTS(
         SELECT
         FROM pg_catalog.pg_roles
         WHERE rolname = 'dbAdmin') THEN
         CREATE USER "dbAdmin" WITH
            LOGIN
            PASSWORD 'admin'
            SUPERUSER
            INHERIT
            NOCREATEDB
            CREATEROLE
            NOREPLICATION
            VALID UNTIL 'infinity';
END IF;
END
$do$;
