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
insert ignore into USERENTITY(username, password, usertype) values ('superuser', '/lIGdrGh2T2rqyMZ7qA2dPNjLq7rFj0eiCRPXrHeEOs=', '3');
insert ignore into USERENTITY(username, password, usertype) values ('admin', '/lIGdrGh2T2rqyMZ7qA2dPNjLq7rFj0eiCRPXrHeEOs=', '2');
insert ignore into USERENTITY(username, password, usertype) values ('manager', '/lIGdrGh2T2rqyMZ7qA2dPNjLq7rFj0eiCRPXrHeEOs=', '1');
insert ignore into USERENTITY(username, password, usertype) values ('cashier', '/lIGdrGh2T2rqyMZ7qA2dPNjLq7rFj0eiCRPXrHeEOs=', '0');
