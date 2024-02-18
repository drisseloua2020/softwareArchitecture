-- Insert data into Tool table
INSERT INTO Tool (ToolCode, ToolType, Brand) VALUES
                                                 ('CHNS', 'Chainsaw', 'Stihl'),
                                                 ('LADW', 'Ladder', 'Werner'),
                                                 ('JAKD', 'Jackhammer', 'DeWalt'),
                                                 ('JAKR', 'Jackhammer', 'Ridgid');

-- Insert data into ToolCharges table
INSERT INTO ToolCharges (ToolType, DailyCharge, AppliedWeeklyCharge, AppliedWeekendCharge, AppliedHolidayCharge) VALUES
                                                                                                                     ('Chainsaw', 1.49, true, false, true),
                                                                                                                     ('Ladder', 1.99, true, true, false),
                                                                                                                     ('Jackhammer', 2.99, true, false, false);
