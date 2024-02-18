-- RentalAgreement Table
CREATE TABLE RentalAgreement (
                                 RentalAgreementId DECIMAL PRIMARY KEY,
                                 DueDate DATE,
                                 ChargeDays DECIMAL,
                                 PreDiscountCharge DECIMAL,
                                 DiscountAmount DECIMAL,
                                 FinalCharge DECIMAL,
                                 CheckoutID DECIMAL,
                                 ToolCode VARCHAR,
                                 FOREIGN KEY (CheckoutID) REFERENCES Checkout(CheckoutID),
                                 FOREIGN KEY (ToolCode) REFERENCES Tool(ToolCode)
);

-- Checkout Table
CREATE TABLE Checkout (
                          CheckoutID DECIMAL PRIMARY KEY,
                          ToolCode VARCHAR,
                          RentalDayCount VARCHAR,
                          DiscountPercent DECIMAL,
                          CheckoutDate DATE
);

-- Holiday Table
CREATE TABLE Holiday (
                         HolidayCode VARCHAR PRIMARY KEY,
                         Description VARCHAR
);

-- Create Trigger Function
CREATE OR REPLACE FUNCTION check_checkout_conditions()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.RentalDayCount < 1 OR NEW.DiscountPercent > 100 OR NEW.DiscountPercent < 1 THEN
        RAISE EXCEPTION 'Invalid values for RentalDayCount or DiscountPercent';
END IF;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Create Trigger
CREATE TRIGGER check_checkout_conditions_trigger
    BEFORE INSERT OR UPDATE
                         ON Checkout
                         FOR EACH ROW
                         EXECUTE FUNCTION check_checkout_conditions();
