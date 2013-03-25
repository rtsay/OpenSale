/* Create test users:
    1:
        username: superuser
        password: test
        role: superuser
    2:
        username: admin            
        password: test
        role: admin
    3:
        username: manager
        password: test
        role: manager
    4:
        username: cashier
        password: test
        role: cashier
*/
insert ignore into USERENTITY(id, username, password, usertype) values ('0', 'superuser', '/lIGdrGh2T2rqyMZ7qA2dPNjLq7rFj0eiCRPXrHeEOs=', '3');
insert ignore into USERENTITY(id, username, password, usertype) values ('1', 'admin', '/lIGdrGh2T2rqyMZ7qA2dPNjLq7rFj0eiCRPXrHeEOs=', '2');
insert ignore into USERENTITY(id, username, password, usertype) values ('2', 'manager', '/lIGdrGh2T2rqyMZ7qA2dPNjLq7rFj0eiCRPXrHeEOs=', '1');
insert ignore into USERENTITY(id, username, password, usertype) values ('3', 'cashier', '/lIGdrGh2T2rqyMZ7qA2dPNjLq7rFj0eiCRPXrHeEOs=', '0');
