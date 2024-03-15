CREATE TABLE IF NOT EXISTS Courses (

    course_id SERIAL PRIMARY KEY,
    course_name varchar(50),
    course_description varchar(250)
);

CREATE TABLE IF NOT EXISTS Students (

    student_id SERIAL PRIMARY KEY,
    group_id INT,
    first_name varchar(50),
    last_name varchar(50)
);

CREATE TABLE IF NOT EXISTS Groups (

    group_id SERIAL PRIMARY KEY,
    group_name varchar(100)
  );

