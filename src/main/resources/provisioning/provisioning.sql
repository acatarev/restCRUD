insert into payments.customers (address, first_name, last_name, zip_code)
values
('Address Line 1','Ann','Catarev',12345),
('Address Line 2','Bella','BellaLastName',12345),
('Address Line 5','Amy','AmyLastName',12345),
('Address Line 4','Renata','RenataLastName',12345),
('Address Line 9','Emma','EmmaLastName',12345),
('Address Line 8','Sandra','SandraLastName',12345);

insert into payments.payments (amount, customer_id)
values
(105.23,1),
(189.25,1),
(728.47,2),
(125.45,4),
(495.62,6),
(295.32,5),
(1728.47,2),
(1225.45,4),
(4395.62,6),
(5295.32,5);