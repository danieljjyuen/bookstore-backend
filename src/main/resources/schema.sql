CREATE TABLE IF NOT EXISTS book (
    pk INT AUTO_INCREMENT PRIMARY KEY,
    id VARCHAR(255) UNIQUE,
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
    name VARCHAR(255),
    username VARCHAR(255) UNIQUE
);
CREATE TABLE IF NOT EXISTS author (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS category (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255)
);
CREATE TABLE IF NOT EXISTS book_author (
    author INT,
    book INT,
    primary key(book, author)
);

CREATE TABLE IF NOT EXISTS book_category (
    book INT,
    category INT,
    primary key(book, category)
);

CREATE TABLE IF NOT EXISTS customer_book (
    customer INT,
    book INT,
    primary key(customer, book)
);
