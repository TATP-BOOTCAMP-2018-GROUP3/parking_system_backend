ALTER TABLE parking_clerk
ADD FOREIGN KEY (id) REFERENCES employee(id);

ALTER TABLE parking_lot
ADD FOREIGN KEY (employee_id) REFERENCES parking_clerk(id);

ALTER TABLE return_order
ADD FOREIGN KEY (parking_order_id) REFERENCES parking_order(id);

ALTER TABLE parking_order
ADD FOREIGN KEY (parking_lot_id) REFERENCES parking_lot(id);

ALTER TABLE parking_order
ADD FOREIGN KEY (owned_by_employee_id) REFERENCES parking_clerk(id);