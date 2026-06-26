CREATE TABLE interfaces (
    id UUID PRIMARY KEY,
    name VARCHAR(250),
    mode VARCHAR(250),
    netmask VARCHAR(250),
    ips JSONB,
    enable VARCHAR(250),
    hwdevice VARCHAR(250)
);