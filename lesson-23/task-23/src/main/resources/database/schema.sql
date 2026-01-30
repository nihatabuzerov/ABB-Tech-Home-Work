CREATE TABLE users
(
    id        UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    full_name VARCHAR(100)   NOT NULL,
    balance   DECIMAL(15, 2) NOT NULL,
    CONSTRAINT balance_non_negative CHECK (balance >= 0)
);

CREATE TABLE payments
(
    id         UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id    UUID           NOT NULL REFERENCES users(id),
    amount     DECIMAL(15, 2) NOT NULL,
    status     VARCHAR(20)    NOT NULL,
    created_at TIMESTAMP      NOT NULL
);