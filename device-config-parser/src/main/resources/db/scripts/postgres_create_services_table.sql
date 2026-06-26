CREATE TABLE services (
    id UUID PRIMARY KEY,
    name TEXT,
    comment TEXT,
    type TEXT,
    service_definitions JSONB,
    additional_properties JSONB
);