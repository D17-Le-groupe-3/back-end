--
-- Company
--

INSERT INTO company (id, remaining_rtt, rtt_taken) VALUES (1, 5, 2);

-- --------------------------------------------------------

--
-- Company holidays
--

INSERT INTO company_holiday (id, comment, date, type, company_id) VALUES (1, 'Jour de l''an', '2022-01-01', 'PUBLIC_HOLIDAY', 1);
INSERT INTO company_holiday (id, comment, date, type, company_id) VALUES (2, 'Lundi de Pâques', '2022-04-18', 'PUBLIC_HOLIDAY', 1);
INSERT INTO company_holiday (id, comment, date, type, company_id) VALUES (3, 'Fête du travail', '2022-05-01', 'PUBLIC_HOLIDAY', 1);
INSERT INTO company_holiday (id, comment, date, type, company_id) VALUES (4, 'Victoire 1945', '2022-05-08', 'PUBLIC_HOLIDAY', 1);
INSERT INTO company_holiday (id, comment, date, type, company_id) VALUES (5, 'Jeudi de l''Ascension', '2022-05-26', 'PUBLIC_HOLIDAY', 1);
INSERT INTO company_holiday (id, comment, date, type, company_id) VALUES (6, 'Lundi de Pentecôte', '2022-06-06', 'PUBLIC_HOLIDAY', 1);
INSERT INTO company_holiday (id, comment, date, type, company_id) VALUES (7, 'Fête nationale', '2022-07-14', 'PUBLIC_HOLIDAY', 1);
INSERT INTO company_holiday (id, comment, date, type, company_id) VALUES (8, 'Assomption', '2022-08-15', 'PUBLIC_HOLIDAY', 1);
INSERT INTO company_holiday (id, comment, date, type, company_id) VALUES (9, 'Toussaint', '2022-11-01', 'PUBLIC_HOLIDAY', 1);
INSERT INTO company_holiday (id, comment, date, type, company_id) VALUES (10, 'Armistice 1918', '2022-11-11', 'PUBLIC_HOLIDAY', 1);
INSERT INTO company_holiday (id, comment, date, type, company_id) VALUES (11, 'Jour de Noël', '2022-12-25', 'PUBLIC_HOLIDAY', 1);
INSERT INTO company_holiday (id, comment, date, type, status, company_id) VALUES (12, '', '2022-08-16', 'COMPANY_RTT', 'VALIDATED', 1);
INSERT INTO company_holiday (id, comment, date, type, status, company_id) VALUES (13, 'Pont de la Toussaint', '2022-10-31', 'INITIAL', 'COMPANY_RTT', 1);

-- --------------------------------------------------------

--
-- Departments
--

INSERT INTO department (id, label, company_id) VALUES (1, 'Management', 1);
INSERT INTO department (id, label, company_id) VALUES (2, 'Développement', 1);
INSERT INTO department (id, label, company_id) VALUES (3, 'Commercial', 1);
INSERT INTO department (id, label, company_id) VALUES (4, 'Service après vente', 1);

-- --------------------------------------------------------

--
-- Leave counters
--

INSERT INTO leave_counters (id, remaining_paid_leaves, paid_leaves_taken, remaining_rtt, rtt_taken, unpaid_leaves_taken) VALUES (1, 25, 0, 6, 0, 0);
INSERT INTO leave_counters (id, remaining_paid_leaves, paid_leaves_taken, remaining_rtt, rtt_taken, unpaid_leaves_taken) VALUES (2, 15, 10, 6, 0, 0);
INSERT INTO leave_counters (id, remaining_paid_leaves, paid_leaves_taken, remaining_rtt, rtt_taken, unpaid_leaves_taken) VALUES (3, 25, 0, 1, 5, 5);
INSERT INTO leave_counters (id, remaining_paid_leaves, paid_leaves_taken, remaining_rtt, rtt_taken, unpaid_leaves_taken) VALUES (4, 25, 0, 6, 0, 0);
INSERT INTO leave_counters (id, remaining_paid_leaves, paid_leaves_taken, remaining_rtt, rtt_taken, unpaid_leaves_taken) VALUES (5, 10, 15, 6, 0, 5);
INSERT INTO leave_counters (id, remaining_paid_leaves, paid_leaves_taken, remaining_rtt, rtt_taken, unpaid_leaves_taken) VALUES (6, 15, 10, 1, 5, 0);
INSERT INTO leave_counters (id, remaining_paid_leaves, paid_leaves_taken, remaining_rtt, rtt_taken, unpaid_leaves_taken) VALUES (7, 15, 10, 6, 0, 0);
INSERT INTO leave_counters (id, remaining_paid_leaves, paid_leaves_taken, remaining_rtt, rtt_taken, unpaid_leaves_taken) VALUES (8, 15, 10, 6, 0, 0);
INSERT INTO leave_counters (id, remaining_paid_leaves, paid_leaves_taken, remaining_rtt, rtt_taken, unpaid_leaves_taken) VALUES (9, 15, 10, 6, 0, 0);
INSERT INTO leave_counters (id, remaining_paid_leaves, paid_leaves_taken, remaining_rtt, rtt_taken, unpaid_leaves_taken) VALUES (10, 25, 0, 6, 0, 0);
INSERT INTO leave_counters (id, remaining_paid_leaves, paid_leaves_taken, remaining_rtt, rtt_taken, unpaid_leaves_taken) VALUES (11, 16, 9, 6, 0, 0);
INSERT INTO leave_counters (id, remaining_paid_leaves, paid_leaves_taken, remaining_rtt, rtt_taken, unpaid_leaves_taken) VALUES (12, 16, 9, 6, 0, 0);

-- --------------------------------------------------------

--
-- Users
--

-- CEO
INSERT INTO user (id, email, first_name, last_name, password, role, department_id, leave_counters_id, manager_id) VALUES (1, 'jean-eudes.marouflard@gmail.com', 'Jean-Eudes', 'Marouflard', '123456', 'MANAGER', 1, 1, null);

-- Commercial
INSERT INTO user (id, email, first_name, last_name, password, role, department_id, leave_counters_id, manager_id) VALUES (2, 'marguerite.duchene@gmail.com', 'Marguerite', 'Duchêne', '123456', 'MANAGER', 3, 2, 1);
INSERT INTO user (id, email, first_name, last_name, password, role, department_id, leave_counters_id, manager_id) VALUES (3, 'christian.dumaine@gmail.com', 'Christian', 'Du Maine', '123456', 'EMPLOYEE', 3, 3, 2);

-- Administrator (larbin du CEO)
INSERT INTO user (id, email, first_name, last_name, password, role, department_id, leave_counters_id, manager_id) VALUES (4, 'jean-jacques.martin@gmail.com', 'Jean-Jacques', 'Martin', '123456', 'ADMINISTRATOR', 1, 4, 1);

-- Dévelopement
INSERT INTO user (id, email, first_name, last_name, password, role, department_id, leave_counters_id, manager_id) VALUES (5, 'juliette.marie@gmail.com', 'Juliette', 'Marie', '123456', 'MANAGER', 2, 5, 1);
INSERT INTO user (id, email, first_name, last_name, password, role, department_id, leave_counters_id, manager_id) VALUES (6, 'paul.fleuve@gmail.com', 'Paul', 'Fleuve', '123456', 'EMPLOYEE', 2, 6, 5);
INSERT INTO user (id, email, first_name, last_name, password, role, department_id, leave_counters_id, manager_id) VALUES (7, 'christine.maitre@gmail.com', 'Christine', 'Maitre', '123456', 'EMPLOYEE', 2, 7, 5);
INSERT INTO user (id, email, first_name, last_name, password, role, department_id, leave_counters_id, manager_id) VALUES (8, 'adrien.marcher@gmail.com', 'Adrien', 'Marcher', '123456', 'EMPLOYEE', 2, 8, 5);
INSERT INTO user (id, email, first_name, last_name, password, role, department_id, leave_counters_id, manager_id) VALUES (9, 'hubert.creux@gmail.com', 'Hubert', 'Creux', '123456', 'EMPLOYEE', 2, 9, 5);

-- Service après vente
INSERT INTO user (id, email, first_name, last_name, password, role, department_id, leave_counters_id, manager_id) VALUES (10, 'gaelle.sachet@gmail.com', 'Gaëlle', 'Sachet', '123456', 'MANAGER', 4, 10, 1);
INSERT INTO user (id, email, first_name, last_name, password, role, department_id, leave_counters_id, manager_id) VALUES (11, 'marc.jarre@gmail.com', 'Marc', 'Jarre', '123456', 'EMPLOYEE', 4, 11, 10);
INSERT INTO user (id, email, first_name, last_name, password, role, department_id, leave_counters_id, manager_id) VALUES (12, 'simone.muret@gmail.com', 'Simone', 'Muret', '123456', 'EMPLOYEE', 4, 12, 10);

-- --------------------------------------------------------

--
-- Leaves
--

INSERT INTO leave_ (id, start_date, end_date, type, reason, status, user_id) VALUES (1, '2022-03-07', '2022-03-18', 'PAID_LEAVE', '', 'VALIDATED', 2);
INSERT INTO leave_ (id, start_date, end_date, type, reason, status, user_id) VALUES (2, '2022-08-01', '2022-08-12', 'PAID_LEAVE', '', 'PENDING_VALIDATION', 2);
INSERT INTO leave_ (id, start_date, end_date, type, reason, status, user_id) VALUES (3, '2022-12-26', '2022-12-30', 'PAID_LEAVE', '', 'PENDING_VALIDATION', 2);

-- Profil avec une demande dépassant le solde
INSERT INTO leave_ (id, start_date, end_date, type, reason, status, user_id) VALUES (4, '2022-01-03', '2022-01-07', 'UNPAID_LEAVE', 'Ceci est ma raison', 'VALIDATED', 3);
INSERT INTO leave_ (id, start_date, end_date, type, reason, status, user_id) VALUES (5, '2022-01-10', '2022-01-14', 'RTT', '', 'VALIDATED', 3);
INSERT INTO leave_ (id, start_date, end_date, type, reason, status, user_id) VALUES (6, '2022-10-31', '2022-12-30', 'PAID_LEAVE', 'Trop long, doit être refusé par le batch de nuit', 'INITIAL', 3);

INSERT INTO leave_ (id, start_date, end_date, type, reason, status, user_id) VALUES (7, '2021-12-27', '2021-12-31', 'PAID_LEAVE', '', 'REJECTED', 4);
INSERT INTO leave_ (id, start_date, end_date, type, reason, status, user_id) VALUES (8, '2022-01-03', '2022-01-07', 'PAID_LEAVE', '', 'PENDING_VALIDATION', 4);
INSERT INTO leave_ (id, start_date, end_date, type, reason, status, user_id) VALUES (9, '2022-12-26', '2022-12-30', 'PAID_LEAVE', '', 'REJECTED', 4);

-- Profil plutôt complet avec demandes passées, futures d'un peu tous les types et tous les statuts, avec chevauchée de mois
INSERT INTO leave_ (id, start_date, end_date, type, reason, status, user_id) VALUES (10, '2021-12-27', '2021-12-31', 'PAID_LEAVE', 'requête validée dans le passé', 'VALIDATED', 5);
INSERT INTO leave_ (id, start_date, end_date, type, reason, status, user_id) VALUES (11, '2022-03-07', '2022-03-18', 'PAID_LEAVE', 'requête validée dans le futur', 'VALIDATED', 5);
INSERT INTO leave_ (id, start_date, end_date, type, reason, status, user_id) VALUES (12, '2022-05-30', '2022-06-03', 'UNPAID_LEAVE', 'C''est juste pour mettre un congé entre deux mois pour vous embêter <3', 'VALIDATED', 5);
INSERT INTO leave_ (id, start_date, end_date, type, reason, status, user_id) VALUES (13, '2022-07-18', '2022-07-22', 'RTT', '', 'REJECTED', 5);
INSERT INTO leave_ (id, start_date, end_date, type, reason, status, user_id) VALUES (14, '2022-07-25', '2022-07-29', 'PAID_LEAVE', '', 'PENDING_VALIDATION', 5);
INSERT INTO leave_ (id, start_date, end_date, type, reason, status, user_id) VALUES (15, '2022-12-26', '2022-12-30', 'PAID_LEAVE', 'Devrait être acceptée par le batch de nuit', 'INITIAL', 5);

-- Congés se superposant par roulement au sein d'un même département, pour tester l'affichage, avec un RTT au milieu
INSERT INTO leave_ (id, start_date, end_date, type, reason, status, user_id) VALUES (16, '2022-02-28', '2022-03-11', 'PAID_LEAVE', '', 'VALIDATED', 6);
INSERT INTO leave_ (id, start_date, end_date, type, reason, status, user_id) VALUES (17, '2022-03-14', '2022-03-18', 'RTT', '', 'VALIDATED', 6);

INSERT INTO leave_ (id, start_date, end_date, type, reason, status, user_id) VALUES (18, '2022-03-07', '2022-03-18', 'PAID_LEAVE', '', 'VALIDATED', 7);

INSERT INTO leave_ (id, start_date, end_date, type, reason, status, user_id) VALUES (19, '2022-03-14', '2022-03-25', 'PAID_LEAVE', '', 'VALIDATED', 8);

INSERT INTO leave_ (id, start_date, end_date, type, reason, status, user_id) VALUES (20, '2022-03-21', '2022-04-01', 'PAID_LEAVE', '', 'VALIDATED', 9);

-- Congés se superposant avec un jour férié au milieu. Une en INITIAL pour tester le calcul de jours décomptés par le batch de nuit
INSERT INTO leave_ (id, start_date, end_date, type, reason, status, user_id) VALUES (21, '2022-04-11', '2022-04-22', 'PAID_LEAVE', 'Attention au jour férié au milieu !', 'INITIAL', 10);

INSERT INTO leave_ (id, start_date, end_date, type, reason, status, user_id) VALUES (22, '2022-04-11', '2022-04-22', 'PAID_LEAVE', 'Attention au jour férié au milieu !', 'VALIDATED', 11);

INSERT INTO leave_ (id, start_date, end_date, type, reason, status, user_id) VALUES (23, '2022-04-11', '2022-04-22', 'PAID_LEAVE', 'Attention au jour férié au milieu !', 'VALIDATED', 12);