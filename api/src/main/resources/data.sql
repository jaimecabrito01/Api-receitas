INSERT INTO roles (role_id, nome)
VALUES (1, 'admin')
ON CONFLICT (role_id) DO NOTHING;

INSERT INTO roles (role_id, nome)
VALUES (2, 'basic')
ON CONFLICT (role_id) DO NOTHING;
