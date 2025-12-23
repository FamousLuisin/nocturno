CREATE TABLE tb_posts(
    id UUID PRIMARY KEY,
    content TEXT NOT NULL,
    created_at TIMESTAMPTZ DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMPTZ,
    deleted_at TIMESTAMPTZ,
    number_likes INT DEFAULT 0,
    creator UUID NOT NULL,
    CONSTRAINT fk_posts_users FOREIGN KEY (creator) REFERENCES tb_users(id)
);