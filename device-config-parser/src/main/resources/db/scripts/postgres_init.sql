CREATE TABLE hosts (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    comment TEXT,
    ips JSONB,
    fqdn VARCHAR(255),
    type VARCHAR(100),
    is_negate BOOLEAN,
    additional_properties JSONB
);