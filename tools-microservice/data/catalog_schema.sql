-- Tool Table
CREATE TABLE Tool (
                      ToolCode VARCHAR PRIMARY KEY,
                      ToolType VARCHAR,
                      Brand VARCHAR
);

-- ToolCharges Table
CREATE TABLE ToolCharges (
                             ToolType VARCHAR PRIMARY KEY,
                             DailyCharge DECIMAL,
                             AppliedWeeklyCharge BOOLEAN,
                             AppliedWeekendCharge BOOLEAN,
                             AppliedHolidayCharge BOOLEAN,
                             FOREIGN KEY (ToolType) REFERENCES Tool(ToolType)
);