use ewallet;

--INSERTING ROLES
INSERT INTO role(name) VALUES ('ROLE_USER');
INSERT INTO role(name) VALUES ('ROLE_ADMIN');

--INSERTING A NEW USER WITH USERNAME 'user' AND PASSWORD 'password'
INSERT INTO `my_user`(`account_non_expired`, `account_non_locked`, `credentials_non_expired`, `email`, `enabled`, `full_name`, `password`, `random_token`, `username`) VALUES (1, 1, 1, 'un_email@yahoo.com', 1,'John Doe','$2a$12$JgnZoMdIycCGmHA4rdD.7uVoG6fBVyisLgwGGIAw53o9pqRsvB8ji','429b8c58-ff09-11ec-b939-0242ac120002','user');

--INSERTING A NEW USER WITH USERNAME 'admin' AND PASSWORD 'password'
INSERT INTO `my_user`(`account_non_expired`, `account_non_locked`, `credentials_non_expired`, `email`, `enabled`, `full_name`, `password`, `random_token`, `username`) VALUES (1, 1, 1, 'un_email@gmail.com', 1,'Jane Doe','$2a$12$JgnZoMdIycCGmHA4rdD.7uVoG6fBVyisLgwGGIAw53o9pqRsvB8ji','429b8c58-ff09-11ec-b939-0242ac120002','admin');

--INSERTING RELATIONSHIP BETWEEN USER AND ROLES
    --USER IS A BASIC USER
    INSERT INTO `users_roles`(`user_id`, `role_id`) VALUES (1,1);

    --ADMIN IS AN USER, BUT ALSO AN ADMIN
    INSERT INTO `users_roles`(`user_id`, `role_id`) VALUES (2,1);
    INSERT INTO `users_roles`(`user_id`, `role_id`) VALUES (2,2);