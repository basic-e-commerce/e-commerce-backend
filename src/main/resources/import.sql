INSERT INTO countries (
                       id,
                       iso,
                       upper_name,
                       name,
                       iso3,
                       num_code,
                       phone_code
)
VALUES (
        1,
        'TR',
        'TURKEY',
        'Turkey',
        'TUR',
        792,
        90
);

INSERT INTO countries (id, iso, upper_name, name, iso3, num_code, phone_code) VALUES (2, 'GB', 'UNITED KINGDOM', 'United Kingdom', 'GBR', 826, 44);
INSERT INTO countries (id, iso, upper_name, name, iso3, num_code, phone_code) VALUES (3, 'US', 'UNITED STATES', 'United States', 'USA', 840, 1);

INSERT INTO categories (
    category_name,
    category_description,
    active,
    created_at,
    updated_at,
    created_by,
    updated_by,
    parent_id,
    is_sub_category
)
VALUES (
           'Electronics',  -- category_name
           'All kinds of electronic products',  -- category_description
           TRUE,  -- active
           NOW(),  -- created_at
           NOW(),  -- updated_at
           1,  -- created_by (Admin ID, replace with actual ID)
           1,  -- updated_by (Admin ID, replace with actual ID)
           NULL,  -- parent_id (NULL if there is no parent category, replace with actual ID for a parent)
           TRUE  -- is_sub_category
       );