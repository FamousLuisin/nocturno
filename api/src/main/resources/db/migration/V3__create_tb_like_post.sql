CREATE TABLE tb_post_likes (
    id UUID PRIMARY KEY,
    post_id UUID,
    user_id UUID,
    created_at TIMESTAMPTZ,
    CONSTRAINT fk_post_likes_user FOREIGN KEY (user_id) REFERENCES tb_users (id) ON DELETE CASCADE,
    CONSTRAINT fk_post_likes_post FOREIGN KEY (post_id) REFERENCES tb_posts (id) ON DELETE CASCADE,
    CONSTRAINT uk_tb_post_likes_user_post UNIQUE (user_id, post_id)
)