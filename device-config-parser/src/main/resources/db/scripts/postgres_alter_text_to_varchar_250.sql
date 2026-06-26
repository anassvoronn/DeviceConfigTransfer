ALTER TABLE services
    ALTER COLUMN name TYPE VARCHAR(250) USING name::varchar(250),
    ALTER COLUMN type TYPE VARCHAR(250) USING type::varchar(250),
    ALTER COLUMN comments TYPE TEXT USING comments::text;

ALTER TABLE policies
    ALTER COLUMN action TYPE VARCHAR(250) USING action::varchar(250),
    ALTER COLUMN expanded_action TYPE VARCHAR(250) USING expanded_action::varchar(250),
    ALTER COLUMN enable TYPE VARCHAR(250) USING enable::varchar(250),
    ALTER COLUMN global_nat TYPE VARCHAR(250) USING global_nat::varchar(250),
    ALTER COLUMN nat TYPE VARCHAR(250) USING nat::varchar(250),
    ALTER COLUMN rule_grp TYPE VARCHAR(250) USING rule_grp::varchar(250),
    ALTER COLUMN rule_name TYPE VARCHAR(250) USING rule_name::varchar(250);

ALTER TABLE hosts
    ALTER COLUMN comments TYPE TEXT USING comments::text;