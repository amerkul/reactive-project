CREATE TABLE addresses (
    address_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    country VARCHAR(30) NOT NULL,
    state VARCHAR(30) NOT NULL,
    state_abbreviation VARCHAR(10) NOT NULL,
    city VARCHAR(30) NOT NULL,
    street VARCHAR(100) NOT NULL UNIQUE,
    zip INT NOT NULL UNIQUE,
    created TIMESTAMP,
    updated TIMESTAMP
);

CREATE TABLE phones (
    phone_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    number INT NOT NULL,
    area_code INT NOT NULL,
    country_code INT NOT NULL,
    created TIMESTAMP,
    updated TIMESTAMP
);

CREATE TABLE users (
    user_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    role VARCHAR(20) NOT NULL,
    username VARCHAR(30) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);

CREATE TABLE customers (
    user_id BIGINT PRIMARY KEY,
    email VARCHAR(50) UNIQUE,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
    ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE vendors (
    user_id BIGINT PRIMARY KEY,
    account_number VARCHAR(30) NOT NULL UNIQUE,
    name VARCHAR(30) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
    ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE businesses (
    business_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    favour VARCHAR(50) NOT NULL,
    cost NUMERIC(8,2) NOT NULL,
    address_id BIGINT NOT NULL,
    phone_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT businesses_addresses_fk
    FOREIGN KEY (address_id) REFERENCES addresses(address_id)
    ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT businesses_phones_fk
    FOREIGN KEY (phone_id) REFERENCES phones(phone_id)
    ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT businesses_vendors_fk
    FOREIGN KEY (user_id) REFERENCES vendors(user_id)
    ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE billings (
    billing_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(20) NOT NULL,
    last_name VARCHAR(20) NOT NULL,
    address_id BIGINT NOT NULL,
    phone_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    CONSTRAINT billings_addresses_fk
    FOREIGN KEY (address_id) REFERENCES addresses(address_id)
    ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT billings_phones_fk
    FOREIGN KEY (phone_id) REFERENCES phones(phone_id)
    ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT billings_users_fk
    FOREIGN KEY (user_id) REFERENCES users(user_id)
    ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE payments (
    payment_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    amount NUMERIC(8,2) NOT NULL,
    created TIMESTAMP,
    billing_id BIGINT NOT NULL,
    CONSTRAINT payments_billings_fk
    FOREIGN KEY (billing_id) REFERENCES billings(billing_id)
    ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE orders (
    order_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    total_cost NUMERIC(8,2) NOT NULL,
    created TIMESTAMP,
    user_id BIGINT,
    CONSTRAINT orders_customers_fk
    FOREIGN KEY (user_id) REFERENCES customers(user_id)
    ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE order_details (
    order_details_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    order_id BIGINT NOT NULL,
    business_id BIGINT NOT NULL,
    number INT NOT NULL,
    cost NUMERIC(8,2) NOT NULL,
    CONSTRAINT order_details_orders_fk
    FOREIGN KEY (order_id) REFERENCES orders(order_id)
    ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT order_details_businesses_fk
    FOREIGN KEY (business_id) REFERENCES businesses(business_id)
    ON DELETE CASCADE ON UPDATE CASCADE
);
