/* Creates sample products for testing. */
insert ignore into PRODUCTENTITY (
    UPC,    
    PRICE,
    PRODUCTDESCRIPTION,
    PRODUCTNAME,
    WEIGHT
) VALUES
(9001, 4.00, 'Test 1', 'Test product number one', 0),
(9002, 7.00, 'Test 2', 'Test product number two', 0),
(9003, 0.50, 'Test 3', 'Test product number three', 0);