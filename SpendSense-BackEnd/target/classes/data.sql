
--Insert Dummy user account --
INSERT INTO user_account (email,username,first_name,last_name,date_of_birth,occupation,password) VALUES ('dummyUser01@email.com',
'dummyUser01','Sam','Smith','1995-08-11','Cleaner','$2a$12$wOv.h5eRmnQY0pKhnP9GNegVt0xJRV0KgsD7w2yQKliX/qQA7wY9O');
INSERT INTO user_account (email,username,first_name,last_name,date_of_birth,occupation,password) VALUES ('dummyUser02@email.com',
'dummyUser02','Tony','Stark','1996-04-21','SWE','$2a$12$IrP.YECyun.ruDsuy2xeu.LIoAV6kWbaRdM493P5fAItUe09m.foa');

-- Default categories --
--Expense Categories --
INSERT INTO category (name,transaction_type,is_system) VALUES('Rent','EXPENSE',True);
INSERT INTO category (name,transaction_type,is_system) VALUES('Groceries','EXPENSE',True);
INSERT INTO category (name,transaction_type,is_system) VALUES('Utilities','EXPENSE',True);
INSERT INTO category (name,transaction_type,is_system) VALUES('Transportation','EXPENSE',True);
INSERT INTO category (name,transaction_type,is_system) VALUES('Insurance','EXPENSE',True);
INSERT INTO category (name,transaction_type,is_system) VALUES('Food','EXPENSE',True);

-- Income Categories
INSERT INTO category (name,transaction_type,is_system) VALUES('Salary','INCOME',True);
INSERT INTO category (name,transaction_type,is_system) VALUES('Dividends','INCOME',True);
INSERT INTO category (name,transaction_type,is_system) VALUES('Freelancing','INCOME',True);
INSERT INTO category (name,transaction_type,is_system) VALUES('Bonus','INCOME',True);
INSERT INTO category (name,transaction_type,is_system) VALUES('Allowance','INCOME',True);


--Insert Dummy Transactions for Sam Smith--
INSERT INTO user_transactions (id,amount,remarks,recurring,transaction_date,last_updated,user_account_id,category_id) VALUES (
UUID(),125.50, 'Groceries from supermarket',false,'2025-08-09',NOW(),1,2);
INSERT INTO user_transactions (id,amount,remarks,recurring,transaction_date,last_updated,user_account_id,category_id) VALUES (
UUID(),4000.00, 'Salary',false,'2025-08-31',NOW(),1,7);
INSERT INTO user_transactions (id,amount,remarks,recurring,transaction_date,last_updated,user_account_id,category_id) VALUES (
UUID(),5000.00, 'Bonus',false,'2025-08-31',NOW(),1,10);
INSERT INTO user_transactions (id,amount,remarks,recurring,transaction_date,last_updated,user_account_id,category_id) VALUES (
UUID(),5000.00, 'Bonus',false,'2025-08-31',NOW(),1,10);
INSERT INTO user_transactions (id,amount,remarks,recurring,transaction_date,last_updated,user_account_id,category_id) VALUES (
UUID(),80.00, 'Phone bill',true,'2025-08-05',NOW(),1,3);
INSERT INTO user_transactions (id,amount,remarks,recurring,transaction_date,last_updated,user_account_id,category_id) VALUES (
UUID(),5, 'Transportation to work and back',false,'2025-08-09',NOW(),1,4);
INSERT INTO user_transactions (id,amount,remarks,recurring,transaction_date,last_updated,user_account_id,category_id) VALUES (
UUID(),5, 'Transportation to work and back',false,'2025-08-10',NOW(),1,4);
INSERT INTO user_transactions (id,amount,remarks,recurring,transaction_date,last_updated,user_account_id,category_id) VALUES (
UUID(),7.50, 'Dinner',false,'2025-08-09',NOW(),1,6);
INSERT INTO user_transactions (id,amount,remarks,recurring,transaction_date,last_updated,user_account_id,category_id) VALUES (
UUID(),100.00, 'Health Insurance',true,'2025-08-09',NOW(),1,5);
INSERT INTO user_transactions (id,amount,remarks,recurring,transaction_date,last_updated,user_account_id,category_id) VALUES (
UUID(),40.00, "Grab to friend's place",false,'2025-08-11',NOW(),1,4);









