CREATE TABLE IF NOT EXISTS book (
    id VARCHAR(255) PRIMARY KEY,
    title VARCHAR(255),
    subtitle VARCHAR(255),
    publisher VARCHAR(255),
    publisher_date VARCHAR(255),
    description TEXT,
    page_count INT,
    small_thumbnail VARCHAR(255),
    thumbnail VARCHAR(255),
    list_price_amount DECIMAL(10, 2),
    list_price_currency VARCHAR(10),
    retail_price_amount DECIMAL(10, 2),
    retail_price_currency VARCHAR(10),
    buy_link VARCHAR(255),
    average_rating INT,
    ratings_count INT,
    language VARCHAR(50),
    kind VARCHAR(50)
);
CREATE TABLE IF NOT EXISTS customer (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255)
);
CREATE TABLE IF NOT EXISTS author (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS  category (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255)
);


CREATE TABLE IF NOT EXISTS book_author (
    book_id VARCHAR(255),
    author_id INT,
    FOREIGN KEY (book_id) REFERENCES book(id),
    FOREIGN KEY (author_id) REFERENCES author(id),
    PRIMARY KEY (book_id, author_id)
);

CREATE TABLE IF NOT EXISTS book_category (
    book_id VARCHAR(255),
    category_id INT,
    FOREIGN KEY (book_id) REFERENCES book(id),
    FOREIGN KEY (category_id) REFERENCES category(id),
    PRIMARY KEY (book_id, category_id)
);

CREATE TABLE IF NOT EXISTS customer_book (
    customer_id INT,
    book_id VARCHAR(255),
    FOREIGN KEY (customer_id) REFERENCES customer(id),
    FOREIGN KEY (book_id) REFERENCES book(id),
    PRIMARY KEY (customer_id, book_id)
);
