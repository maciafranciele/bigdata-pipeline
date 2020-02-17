CREATE TABLE tbl_dim_customer(
	customer_id TEXT PRIMARY KEY,
	language VARCHAR(5) NOT NULL,
	created_at TIMESTAMP NOT NULL,
	active BOOLEAN NOT NULL,
	customer_name TEXT NOT NULL,
	phone_are INT NOT NULL,
	phone_number INT NOT NULL
);

CREATE TABLE tbl_dim_merchant(
	merchant_id TEXT PRIMARY KEY,
	created_at TIMESTAMP NOT NULL,
	enabled BOOLEAN NOT NULL,
	price_range INT NOT NULL,
	average_ticket FLOAT NOT NULL,
	takeout_time INT NOT NULL,
	delivery_time INT NOT NULL,
	minimum_order_value FLOAT NOT NULL,
	merchant_zip_code INT NOT NULL,
	city  TEXT NOT NULL,
	state CHAR(2) NOT NULL,
	country TEXT NOT NULL
);

CREATE TABLE tbl_dim_order(
	order_id TEXT PRIMARY KEY,
	scheduled BOOLEAN NOT NULL,
	customer_id TEXT NOT NULL REFERENCES tbl_dim_customer (customer_id),
	delivery_address_city TEXT NOT NULL,
	delivery_address_state TEXT NOT NULL,
	delivery_address_country TEXT NOT NULL,
	delivery_address_district TEXT NOT NULL,
	delivery_address_zip_code INT NOT NULL,
	merchant_id TEXT NOT NULL REFERENCES tbl_dim_merchant (merchant_id),
	created_at TIMESTAMP NOT NULL,
	total_amount FLOAT NOT NULL);

CREATE TABLE tbl_dim_order_items(
	order_items_id TEXT PRIMARY KEY,
	name TEXT NOT NULL,
	quantity INT NOT NULL,
	unitPrice FLOAT NOT NULL,
	sequence INT NOT NULL,
	garnishItems TEXT NOT NULL,
	addition FLOAT NOT NULL,
	totalAddition FLOAT NOT NULL,
	discount FLOAT NOT NULL,
	totalDiscount FLOAT NOT NULL,
	id_order TEXT NOT NULL REFERENCES tbl_dim_order (order_id)
);

CREATE TABLE tbl_fact_order(
   date TIMESTAMP NOT NULL,
   customer_id TEXT NOT NULL REFERENCES tbl_dim_customer (customer_id),
   merchant_id TEXT NOT NULL REFERENCES tbl_dim_merchant (merchant_id),
   order_qty INT NOT NULL,
   order_amount FLOAT NOT NULL,
   order_discount FLOAT,
   item_qty INT,
   item_value FLOAT
);


 CREATE TABLE tbl_fact_merchant(
	 date TIMESTAMP NOT NULL,
	 merchant_id TEXT NOT NULL,
	 order_qty INT,
	 order_amount FLOAT,
	 item_qty INT,
	 item_value FLOAT,
	 order_discount FLOAT,
	 FOREIGN KEY (merchant_id) REFERENCES tbl_dim_merchant (merchant_id)
);

CREATE TABLE tbl_fact_client(
	date TIMESTAMP NOT NULL,
	customer_id TEXT NOT NULL REFERENCES tbl_dim_customer (customer_id),
	order_qty INT,
	order_amount FLOAT,
	order_discount FLOAT,
	item_qty INT,
	item_value float  
);