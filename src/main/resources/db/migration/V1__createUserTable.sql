CREATE TABLE IF NOT EXISTS users
(
    id         BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(150)         NOT NULL,
    last_name  VARCHAR(150)         NOT NULL,
    email      VARCHAR(255) UNIQUE NOT NULL,
    is_deleted BOOLEAN                      DEFAULT false,
    created_on TIMESTAMPTZ         NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ         NOT NULL DEFAULT NOW(),
    created_by VARCHAR(150)         NOT NULL,
    updated_by VARCHAR(150)         NOT NULL
);
