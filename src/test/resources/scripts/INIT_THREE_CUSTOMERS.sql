-- 1st customer, id=1
insert into customer
(created_date, first_name, last_modified_date, second_name, version, id)
values (TIMESTAMP '2021-11-13 07:15:31.123456789', 'Polly', TIMESTAMP '2021-11-13 07:15:31.123456789', 'Mollowsky', 0,
        1);

insert into maddress
(city, contact_type, created_date, customer_id, hous_number, last_modified_date, name, street, version, zip, id)
values ('City-Big-Apple', 'PRIMARY', TIMESTAMP '2005-05-13 07:15:31.123456789', 1, '17A',
        TIMESTAMP '2005-05-13 07:15:31.123456789',
        'Polly Mollowsky', 'pirkolyStr.', 0, '98173', 2);
insert into maddress
(city, contact_type, created_date, customer_id, hous_number, last_modified_date, name, street, version, zip, id)
values ('City-Big-Apple', 'DELIVERY', TIMESTAMP '2005-05-13 07:15:31.123456789', 1, '17A',
        TIMESTAMP '2005-05-13 07:15:31.123456789',
        'Polly Mollowsky', 'pirkolyStr.', 0, '98173', 3);

insert into memail
(contact_type, created_date, customer_id, email, last_modified_date, version, id)
values ('PRIMARY', TIMESTAMP '2005-05-13 07:15:31.123456789', 1, 'mollyMollowsky@Bqv.com',
        TIMESTAMP '2005-05-13 07:15:31.123456789', 0, 4);

insert into mphone
(contact_type, created_date, customer_id, last_modified_date, phone, version, id)
values ('OFFICE', TIMESTAMP '2021-05-13 07:15:31.123456789', 1, TIMESTAMP '2021-05-13 07:15:31.123456789', '+494813', 0,
        5);

-- 2nd customer, id=10

insert into customer
(created_date, first_name, last_modified_date, second_name, version, id)
values (TIMESTAMP '2021-11-13 07:15:31.123456789', 'Sadid', TIMESTAMP '2021-11-13 07:15:31.123456789', 'Kenty', 0, 10);

insert into maddress
(city, contact_type, created_date, customer_id, hous_number, last_modified_date, name, street, version, zip, id)
values ('City-Siena', 'PRIMARY', TIMESTAMP '2005-05-13 07:15:31.123456789', 10, '44',
        TIMESTAMP '2005-05-13 07:15:31.123456789', 'Sadid Kenty',
        'BenutoStr.', 0, '87654', 12);
insert into maddress
(city, contact_type, created_date, customer_id, hous_number, last_modified_date, name, street, version, zip, id)
values ('City-Siena', 'OFFICE', TIMESTAMP '2005-05-13 07:15:31.123456789', 10, '67',
        TIMESTAMP '2005-05-13 07:15:31.123456789', 'Sadid Kenty',
        'KlindStr.', 0, '98745', 13);

insert into memail
(contact_type, created_date, customer_id, email, last_modified_date, version, id)
values ('PRIMARY', TIMESTAMP '2005-05-13 07:15:31.123456789', 10, 'sadid.kenty@Bqv.com',
        TIMESTAMP '2005-05-13 07:15:31.123456789', 0, 14);

insert into mphone
(contact_type, created_date, customer_id, last_modified_date, phone, version, id)
values ('OFFICE', TIMESTAMP '2021-05-13 07:15:31.123456789', 10, TIMESTAMP '2021-05-13 07:15:31.123456789', '+4948568',
        0, 15);

-- 3rd customer, id=20

insert into customer
(created_date, first_name, last_modified_date, second_name, version, id)
values (TIMESTAMP '2021-11-13 07:15:31.123456789', 'Alla', TIMESTAMP '2021-11-13 07:15:31.123456789', 'Yina', 0, 20);

insert into maddress
(city, contact_type, created_date, customer_id, hous_number, last_modified_date, name, street, version, zip, id)
values ('City-Moo', 'PRIMARY', TIMESTAMP '2005-05-13 07:15:31.123456789', 20, '78',
        TIMESTAMP '2005-05-13 07:15:31.123456789', 'Alla Yina',
        'PotatoStr.', 0, '87654', 22);
insert into maddress
(city, contact_type, created_date, customer_id, hous_number, last_modified_date, name, street, version, zip, id)
values ('City-Moo', 'OFFICE', TIMESTAMP '2005-05-13 07:15:31.123456789', 20, '777',
        TIMESTAMP '2005-05-13 07:15:31.123456789', 'Alla Yina',
        'PrikStr.', 0, '98745', 23);

insert into memail
(contact_type, created_date, customer_id, email, last_modified_date, version, id)
values ('PRIMARY', TIMESTAMP '2005-05-13 07:15:31.123456789', 20, 'alla.Yina@yahoo.com',
        TIMESTAMP '2005-05-13 07:15:31.123456789', 0, 24);

insert into mphone
(contact_type, created_date, customer_id, last_modified_date, phone, version, id)
values ('OFFICE', TIMESTAMP '2005-05-13 07:15:31.123456789', 20, TIMESTAMP '2005-05-13 07:15:31.123456789', '+4943245',
        0, 25);

-- TO_TIMESTAMP('2014-07-02 06:14:00.742000000', 'YYYY-MM-DD HH24:MI:SS.FF')
-- TIMESTAMP '2005-05-13 07:15:31.123456789'
-- INSERT INTO events (ts,description) VALUES (CURRENT_TIMESTAMP,'disc full');

-- INSERT INTO 'my_schema'.'table_with_a_timestamp' (timestamp_id, timestamp)
-- VALUES ('0001', timestamp '2020-01-01 00:00:00.001');
