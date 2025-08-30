CREATE TABLE tb_users (
    id UUID,
    name VARCHAR(25) NOT NULL,
    display_name VARCHAR(15) NOT NULL,
    username VARCHAR(15) UNIQUE NOT NULL,
    email VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(50) NOT NULL,
    bio TEXT,
    role VARCHAR(15) DEFAULT 'user',
    status VARCHAR(15) DEFAULT 'active',
    birth DATE NOT NULL,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP
);