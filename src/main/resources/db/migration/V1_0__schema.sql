CREATE TABLE parent (
    id UUID PRIMARY KEY
);

CREATE TABLE child (
    id UUID PRIMARY KEY,
    value INTEGER NOT NULL,
    parent_id UUID NOT NULL,
    CONSTRAINT parent_fk FOREIGN KEY (parent_id) REFERENCES parent (id)
);