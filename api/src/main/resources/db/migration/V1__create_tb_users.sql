CREATE TABLE tb_users (
    id UUID,
    display_name VARCHAR(15) NOT NULL,
    username VARCHAR(15) UNIQUE NOT NULL,
    email VARCHAR(50) UNIQUE NOT NULL,
    password TEXT NOT NULL,
    bio TEXT,
    role VARCHAR(15) DEFAULT 'user',
    status VARCHAR(15) DEFAULT 'active',
    birth DATE NOT NULL,
    picture TEXT,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);