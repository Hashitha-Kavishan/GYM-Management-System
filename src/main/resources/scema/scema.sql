DROP DATABASE IF EXISTS GymManagement;
CREATE DATABASE IF NOT EXISTS GymManagement;
USE GymManagement;

CREATE TABLE User (
                      user_id VARCHAR(50),
                      name VARCHAR(50),
                      password VARCHAR(50),
                      CONSTRAINT PRIMARY KEY (user_id)
);

CREATE TABLE Receptionist (
                              receptionist_id VARCHAR(50),
                              name VARCHAR(50),
                              address VARCHAR(100),
                              contact VARCHAR(20),
                              CONSTRAINT PRIMARY KEY (receptionist_id)
);

CREATE TABLE Membership_type (
                                 membership_type_id VARCHAR(50),
                                 membership_name VARCHAR(50),
                                 member_amount INT,
                                 member_period VARCHAR(50),
                                 sign_up_fee DOUBLE,
                                 CONSTRAINT PRIMARY KEY (membership_type_id, membership_name)
);

CREATE TABLE Member (
                        member_id VARCHAR(50),
                        name VARCHAR(50),
                        address VARCHAR(100),
                        age INT,
                        gender VARCHAR(10),
                        email VARCHAR(50),
                        contact VARCHAR(20),
                        join_date DATE,
                        membership_type_id VARCHAR(50),
                        CONSTRAINT PRIMARY KEY (member_id),
                        CONSTRAINT FOREIGN KEY (membership_type_id) REFERENCES Membership_type(membership_type_id) on Delete Cascade on Update Cascade
);

CREATE TABLE Suppliment (
                            suppliment_id VARCHAR(50),
                            name VARCHAR(50),
                            description VARCHAR(200),
                            price DOUBLE,
                            CONSTRAINT PRIMARY KEY (suppliment_id)
);

CREATE TABLE Registration (
                              registration_id VARCHAR(50),
                              member_id VARCHAR(50),
                              CONSTRAINT PRIMARY KEY (registration_id),
                              CONSTRAINT FOREIGN KEY (member_id) REFERENCES Member(member_id) on Delete Cascade on Update Cascade
);

CREATE TABLE Member_details (
                                supliment_id VARCHAR(50),
                                membership_type_id VARCHAR(50),
                                CONSTRAINT PRIMARY KEY (supliment_id, membership_type_id),
                                CONSTRAINT FOREIGN KEY (supliment_id) REFERENCES Suppliment(suppliment_id) on Delete Cascade on Update Cascade,
                                CONSTRAINT FOREIGN KEY (membership_type_id) REFERENCES Membership_type(membership_type_id) on Delete Cascade on Update Cascade
);

CREATE TABLE Workout_plan (
                              plan_id VARCHAR(50),
                              name VARCHAR(50),
                              description VARCHAR(200),
                              workout_date DATE,
                              CONSTRAINT PRIMARY KEY (plan_id)
);

CREATE TABLE Instructor (
                            instructor_id VARCHAR(50),
                            name VARCHAR(50),
                            address VARCHAR(100),
                            email VARCHAR(50),
                            contact VARCHAR(20),
                            CONSTRAINT PRIMARY KEY (instructor_id)
);

CREATE TABLE Workout_plan_details (
                                      plan_id VARCHAR(50),
                                      instructor_id VARCHAR(50),
                                      CONSTRAINT PRIMARY KEY (plan_id, instructor_id),
                                      CONSTRAINT FOREIGN KEY (plan_id) REFERENCES Workout_plan(plan_id) on Delete Cascade on Update Cascade,
                                      CONSTRAINT FOREIGN KEY (instructor_id) REFERENCES Instructor(instructor_id) on Delete Cascade on Update Cascade
);

CREATE TABLE Payment (
                         payment_id VARCHAR(50),
                         payment_date DATE,
                         amount VARCHAR(10),
                         member_id VARCHAR(50),
                         CONSTRAINT PRIMARY KEY (payment_id),
                         CONSTRAINT FOREIGN KEY (member_id) REFERENCES Member(member_id) on Delete Cascade on Update Cascade

);

INSERT INTO User VALUE ('u001', 'John', 'password123');
INSERT INTO User VALUE ('u002', 'Jane', 'password456');

INSERT INTO receptionist VALUES ('r001','Kumar', 'No. 123, Galle Road, Colombo', '+94 77 1234567');

INSERT INTO Membership_type VALUES('mt001', 'gold', 10000, '1 year', 5000);
INSERT INTO Membership_type VALUES('mt002', 'silver', 8000, '6 months', 3000);
INSERT INTO Membership_type VALUES('mt003', 'platinum', 15000, '2 years', 10000);

INSERT INTO Member VALUES('m001','Kamal', 'No. 234, Kandy Road, Colombo', 27, 'Male', 'kamal@gmail.com', '+94 71 2345678', '2022-01-01', 'mt001');
INSERT INTO Member VALUES('m002','Nimali', 'No. 567, Negombo Road, Gampaha', 32, 'Female', 'nimali@yahoo.com', '+94 77 3456789', '2021-06-01', 'mt001');
INSERT INTO Member VALUES('m003','Rajitha', 'No. 678, Galle Road, Colombo', 29, 'Male', 'rajitha@gmail.com', '+94 76 4567890', '2021-11-01','mt002');
INSERT INTO Member VALUES('m004','Samantha', 'No. 89, Peradeniya Road, Kandy', 35, 'Male', 'samantha@hotmail.com', '+94 72 5678901', '2021-03-01','mt002');
INSERT INTO Member VALUES('m005','Dilani', 'No. 456, Old Road, Colombo', 28, 'Female', 'dilani@gmail.com', '+94 76 6789012', '2022-02-01', 'mt003');
INSERT INTO Member VALUES('m006','Rashmi', 'No. 345, New Road, Kegalle', 31, 'Female', 'rashmi@yahoo.com', '+94 71 7890123', '2021-07-01', 'mt003');
INSERT INTO Member VALUES('m007','Nishantha', 'No. 123, Kadawatha Road, Gampaha', 26, 'Male', 'nishantha@gmail.com', '+94 72 8901234', '2022-03-01', 'mt003');
INSERT INTO Member VALUES('m008','Chaminda', 'No. 789, Negombo Road, Wattala', 30, 'Male', 'chaminda@hotmail.com', '+94 76 9012345', '2021-09-01','mt001');
INSERT INTO Member VALUES('m009','Shanika', 'No. 234, Havelock Road, Colombo', 33, 'Female', 'shanika@yahoo.com', '+94 71 0123456', '2021-12-01','mt002');
INSERT INTO Member VALUES('m010','Lahiru', 'No. 456, Galle Road, Kalutara', 27, 'Male', 'lahiru@gmail.com', '+94 76 1234567', '2022-01-01','mt002');

INSERT INTO Suppliment VALUES('sp001', 'Whey Protein', 'Protein supplement for muscle building', 5000);
INSERT INTO Suppliment VALUES('sp002', 'Pre-Workout', 'Energy supplement for workout', 3000);
INSERT INTO Suppliment VALUES('sp003', 'Creatine', 'Muscle strength and power supplement', 2500);
INSERT INTO Suppliment VALUES('sp004', 'BCAA', 'Amino acid supplement for muscle recovery', 2000);
INSERT INTO Suppliment VALUES('sp005', 'Glutamine', 'Amino acid supplement for muscle recovery', 1500);
INSERT INTO Suppliment VALUES('sp006', 'Fish Oil', 'Omega-3 fatty acid supplement for overall health', 2000);
INSERT INTO Suppliment VALUES('sp007', 'Multivitamin', 'Daily vitamin and mineral supplement', 1000);
INSERT INTO Suppliment VALUES('sp008', 'Fat Burner', 'Supplement to aid weight loss', 3500);
INSERT INTO Suppliment VALUES('sp009', 'ZMA', 'Supplement for muscle recovery and better sleep', 3000);
INSERT INTO Suppliment VALUES('sp010', 'Beta-Alanine', 'Muscle endurance supplement', 2200);

INSERT INTO Registration VALUES('r001', 'm001');
INSERT INTO Registration VALUES('r002', 'm002');
INSERT INTO Registration VALUES('r003', 'm003');
INSERT INTO Registration VALUES('r004', 'm004');
INSERT INTO Registration VALUES('r005', 'm005');
INSERT INTO Registration VALUES('r006', 'm006');
INSERT INTO Registration VALUES('r007', 'm007');
INSERT INTO Registration VALUES('r008', 'm008');
INSERT INTO Registration VALUES('r009', 'm009');
INSERT INTO Registration VALUES('r010', 'm010');

INSERT INTO Member_details VALUES('sp001','mt001');
INSERT INTO Member_details VALUES('sp002','mt002');
INSERT INTO Member_details VALUES('sp003','mt003');
INSERT INTO Member_details VALUES('sp004','mt001');
INSERT INTO Member_details VALUES('sp005','mt002');
INSERT INTO Member_details VALUES('sp006','mt001');
INSERT INTO Member_details VALUES('sp007','mt002');
INSERT INTO Member_details VALUES('sp008','mt001');
INSERT INTO Member_details VALUES('sp009','mt001');
INSERT INTO Member_details VALUES('sp010','mt002');

INSERT INTO Workout_plan VALUES('wp001', 'Weight Training', 'Strength training using weights', '2022-04-01');
INSERT INTO Workout_plan VALUES('wp002', 'Yoga', 'Meditative exercise involving deep breathing', '2022-04-03');
INSERT INTO Workout_plan VALUES('wp003', 'Cardio', 'Aerobic exercise to improve cardiovascular health', '2022-04-05');
INSERT INTO Workout_plan VALUES('wp004', 'Pilates', 'Low-impact exercise to strengthen muscles and improve flexibility', '2022-04-07');
INSERT INTO Workout_plan VALUES('wp005', 'Cycling', 'Indoor or outdoor cycling for endurance and stamina', '2022-04-09');
INSERT INTO Workout_plan VALUES('wp006', 'Bootcamp', 'High-intensity interval training (HIIT)', '2022-04-11');
INSERT INTO Workout_plan VALUES('wp007', 'Zumba', 'Latin dance-based fitness class', '2022-04-13');
INSERT INTO Workout_plan VALUES('wp008', 'Kickboxing', 'Martial arts-inspired workout', '2022-04-15');
INSERT INTO Workout_plan VALUES('wp009', 'TRX', 'Total body workout using suspension training', '2022-04-17');
INSERT INTO Workout_plan VALUES('wp010', 'Barre', 'Combination of ballet, yoga, and Pilates', '2022-04-19');

INSERT INTO Instructor VALUES('i001', 'Kamal Perera', 'Colombo, Sri Lanka', 'kamalperera@email.com', '+94771234567');
INSERT INTO Instructor VALUES('i002', 'Nimal Silva', 'Kandy, Sri Lanka', 'nimalsilva@email.com', '+94772345678');

INSERT INTO Workout_plan_details VALUES('wp001','i001');
INSERT INTO Workout_plan_details VALUES('wp002','i002');
INSERT INTO Workout_plan_details VALUES('wp003','i001');
INSERT INTO Workout_plan_details VALUES('wp004','i001');
INSERT INTO Workout_plan_details VALUES('wp005','i001');
INSERT INTO Workout_plan_details VALUES('wp006','i002');
INSERT INTO Workout_plan_details VALUES('wp007','i002');
INSERT INTO Workout_plan_details VALUES('wp008','i002');
INSERT INTO Workout_plan_details VALUES('wp009','i002');
INSERT INTO Workout_plan_details VALUES('wp010','i002');

INSERT INTO Payment VALUES('001', '2022-01-01', '4500', 'm001');
INSERT INTO Payment VALUES('002', '2022-01-04', '5000', 'm002');
INSERT INTO Payment VALUES('003', '2022-01-06', '4500', 'm003');
INSERT INTO Payment VALUES('004', '2022-01-07', '5000', 'm004');





