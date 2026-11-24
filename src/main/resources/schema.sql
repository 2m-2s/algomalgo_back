CREATE TABLE IF NOT EXISTS db_connection_test (
    id         BIGINT       PRIMARY KEY,
    message    VARCHAR(100) NOT NULL,
    created_at TIMESTAMPTZ  NOT NULL DEFAULT now()
);

INSERT INTO db_connection_test (id, message)
VALUES (1, 'spring boot connected')
    ON CONFLICT (id) DO NOTHING;