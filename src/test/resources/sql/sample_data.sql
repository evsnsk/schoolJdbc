INSERT into courses (course_id, course_name, course_description)
VALUES
(10001,'Math', 'Mathematics is the science and study of quality, structure, space, and change.' ),
(10002, 'History', 'History is the study of change over time, and it covers all aspects of human society.'),
(10003, 'Music', 'Music is an art of sound in time that expresses ideas and emotions in significant forms through the elements of rhythm, melody, harmony, and color.'),
(10004, 'PE', 'Physical education provides cognitive content and instruction designed to develop motor skills, knowledge, and behaviors for physical activity and physical fitness.'),
(10005, 'Physics', 'Physics is the branch of science that deals with the structure of matter and how the fundamental constituents of the universe interact.'),
(10006,'Geography', 'Geography is a branch of inquiry that focuses on spatial information on Earth.'),
(10007, 'Informatics', 'Informatics involves the practice of information processing and the engineering of information systems. ');

INSERT into  groups(group_id, group_name)
VALUES
(10001, 'Group1'),
(10002, 'Group2'),
(10003, 'Group3');

INSERT into students(student_id, group_id, first_name, last_name)
VALUES
(10001, 10002, 'Name1', 'LastName1'),
(10002, 10001,'Name2','LastName2'),
(10003, 10003,'Name3','LastName3'),
(10004, 10003,'Name4','LastName4'),
(10005, 10003,'Name5','LastName5'),
(10006, 10002,'Name5','LastName5');

INSERT into  students_courses(student_id, course_id)
VALUES
(10001,10002),
(10001,10005),
(10002,10005),
(10002,10006),
(10003,10004),
(10004,10004);