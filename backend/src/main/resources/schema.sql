drop table if exists books;

create table if not exists books
(
    id
    bigserial
    not
    null
    primary
    key,
    author
    text,
    name
    text
);

-- Inserting a single record
INSERT INTO books (author, name)
VALUES ('John Doe', 'The Book of Knowledge');

-- Inserting multiple records in a single query
INSERT INTO books (author, name)
VALUES ('Jane Smith', 'The Art of Programming'),
       ('Bob Johnson', 'Science and Nature');