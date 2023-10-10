INSERT INTO public.users(username, password)
VALUES ('admin', '{noop}admin_password'),
       ('user', '{noop}user_password');

INSERT INTO public.authority(user_id, authoriry)
VALUES (1, 'ADMIN'),
       (1, 'USER'),
       (2, 'USER');