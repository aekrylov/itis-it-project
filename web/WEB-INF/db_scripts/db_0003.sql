ALTER TABLE public.users ADD role VARCHAR(8) DEFAULT 'user' NOT NULL;
UPDATE users SET role = 'admin' WHERE username = 'admin';