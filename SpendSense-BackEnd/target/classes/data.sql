
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
INSERT INTO category (name,transaction_type,is_system) VALUES('Subscription','EXPENSE',True);

-- Income Categories
INSERT INTO category (name,transaction_type,is_system) VALUES('Salary','INCOME',True);
INSERT INTO category (name,transaction_type,is_system) VALUES('Dividends','INCOME',True);
INSERT INTO category (name,transaction_type,is_system) VALUES('Freelancing','INCOME',True);
INSERT INTO category (name,transaction_type,is_system) VALUES('Bonus','INCOME',True);
INSERT INTO category (name,transaction_type,is_system) VALUES('Allowance','INCOME',True);


--Insert Dummy Transactions for Sam Smith--
INSERT INTO user_transactions (id,amount,title,remarks,recurring,transaction_date,last_updated,user_account_id,category_id) VALUES (
UUID_TO_BIN(UUID()),125.50,'Groceries','Groceries from supermarket',false,'2026-02-09',NOW(),1,2);
INSERT INTO user_transactions (id,amount,title,remarks,recurring,transaction_date,next_due_date,last_updated,user_account_id,category_id) VALUES (
UUID_TO_BIN(UUID()),4000.00, 'Salary',"pay day",true,'2026-02-21','2026-03-21',NOW(),1,8);
INSERT INTO user_transactions (id,amount,title,remarks,recurring,transaction_date,last_updated,user_account_id,category_id) VALUES (
UUID_TO_BIN(UUID()),5000.00, 'Bonus','Performance Bonus',false,'2026-02-27',NOW(),1,11);
INSERT INTO user_transactions (id,amount,title,remarks,recurring,transaction_date,last_updated,user_account_id,category_id) VALUES (
UUID_TO_BIN(UUID()),5000.00, 'Bonus','Annual Bonus',false,'2026-02-22',NOW(),1,11);
INSERT INTO user_transactions (id,amount,title,remarks,recurring,transaction_date,next_due_date,last_updated,user_account_id,category_id) VALUES (
UUID_TO_BIN(UUID()),80.00, 'Phone bill','Circles life bill',true,'2026-02-05','2026-03-05',NOW(),1,3);
INSERT INTO user_transactions (id,amount,title,remarks,recurring,transaction_date,last_updated,user_account_id,category_id) VALUES (
UUID_TO_BIN(UUID()),5, 'MRT','Transportation to work and back',false,'2026-02-09',NOW(),1,4);
INSERT INTO user_transactions (id,amount,title,remarks,recurring,transaction_date,last_updated,user_account_id,category_id) VALUES (
UUID_TO_BIN(UUID()),5,'MRT','Transportation to work and back',false,'2026-02-10',NOW(),1,4);
INSERT INTO user_transactions (id,amount,title,remarks,recurring,transaction_date,last_updated,user_account_id,category_id) VALUES (
UUID_TO_BIN(UUID()),7.50,'Dinner','Hawker food below',false,'2026-02-09',NOW(),1,6);
INSERT INTO user_transactions (id,amount,title,remarks,recurring,transaction_date,next_due_date,last_updated,user_account_id,category_id) VALUES (
UUID_TO_BIN(UUID()),100.00, 'PruShield','Prudential Hospital Plan',true,'2026-02-09','2026-03-09',NOW(),1,5);
INSERT INTO user_transactions (id,amount,title,remarks,recurring,transaction_date,last_updated,user_account_id,category_id) VALUES (
UUID_TO_BIN(UUID()),40.00, 'Grab',"Grab to friend's place",false,'2026-02-11',NOW(),1,4);

-- Subscriptions --
INSERT INTO user_transactions (id,amount,title,remarks,recurring,transaction_date,next_due_date,last_updated,user_account_id,category_id) VALUES (
UUID_TO_BIN(UUID()),35.00,'Netflix','Netflix subscription',true,'2025-10-09','2025-11-09',NOW(),1,7);
INSERT INTO user_transactions (id,amount,title,remarks,recurring,transaction_date,next_due_date,last_updated,user_account_id,category_id) VALUES (
UUID_TO_BIN(UUID()),40.00,'Disney','Disney Subscription',true,'2025-10-09','2025-11-09',NOW(),1,7);
INSERT INTO user_transactions (id,amount,title,remarks,recurring,transaction_date,next_due_date,last_updated,user_account_id,category_id) VALUES (
UUID_TO_BIN(UUID()),19.00,'HBO Max','HBO subscription',true,'2025-10-09','2025-11-09',NOW(),1,7);
INSERT INTO user_transactions (id,amount,title,remarks,recurring,transaction_date,next_due_date,last_updated,user_account_id,category_id) VALUES (
UUID_TO_BIN(UUID()),110.00,'Microsoft Office','Microsoft Office subscription',true,'2025-10-09','2025-11-09',NOW(),1,7);
INSERT INTO user_transactions (id,amount,title,remarks,recurring,transaction_date,next_due_date,last_updated,user_account_id,category_id) VALUES (
UUID_TO_BIN(UUID()),20.00,'iCloud','Cloud Storage',true,'2025-10-09','2025-11-09',NOW(),1,7);
INSERT INTO user_transactions (id,amount,title,remarks,recurring,transaction_date,next_due_date,last_updated,user_account_id,category_id) VALUES (
UUID_TO_BIN(UUID()),13.00,'GoodNotes','Note taking application subscription',true,'2025-10-09','2025-11-09',NOW(),1,7);













