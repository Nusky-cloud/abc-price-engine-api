INSERT INTO product 
(product_id, product_name, product_description, carton_price, unit_quantity_per_carton) 
VALUES 
((SELECT nextval('hibernate_sequence')), 'Penguin-ears', 'Penguin-ears', 175.00, 20),
((SELECT nextval('hibernate_sequence')), 'Horseshoe', 'Horseshoe', 825.00, 5);