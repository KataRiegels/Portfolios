-- Deleting data in tables before insert
DROP TABLE IF EXISTS Grades;
DROP TABLE IF EXISTS Courses;
DROP TABLE IF EXISTS Students;
DROP TABLE IF EXISTS Towns;

CREATE TABLE IF NOT EXISTS Courses(

    courseID TEXT,
    courseName TEXT,
    courseYear INTEGER,
    fallSemester BOOLEAN,
    teacherName TEXT,

    primary key (courseID,courseName,courseYear,fallSemester,teacherName)
);

CREATE TABLE IF NOT EXISTS Towns(

    townName TEXT,
    country TEXT,
    postal INTEGER,

    primary key (townName,country,postal)
);

CREATE TABLE IF NOT EXISTS Students(

    studentName TEXT,
    townName TEXT,

    primary key (studentName),
    foreign key (townName) REFERENCES Towns (townName) ON DELETE RESTRICT ON UPDATE CASCADE
);

CREATE TABLE IF NOT EXISTS Grades(

    studentName TEXT,
    courseID TEXT,
    grade INTEGER,

    foreign key (studentName) REFERENCES Students (studentName) ON DELETE RESTRICT ON UPDATE CASCADE,
    foreign key (courseID) REFERENCES Courses (courseID) ON DELETE RESTRICT ON UPDATE CASCADE
);

INSERT INTO Grades(studentName, courseID, grade)
VALUES  ('Aisha Lincoln',   'SDF19',       12),
        ('Aisha Lincoln',   'ES1F19',      10),
        ('Anya Nielsen',    'SDS20',       NULL),
        ('Anya Nielsen',    'ES1F19',      12),
        ('Alfred Jensen',   'SDF19',       7),
        ('Alfred Jensen',   'ES1F19',      10),
        ('Berta Bertelsen', 'SDS20',       NULL),
        ('Berta Bertelsen', 'ES1F19',      2),
        ('Albert Antonsen', 'SDF19',       10),
        ('Albert Antonsen', 'ES1F19',      7),
        ('Eske Eriksen',    'SDS20',       NULL),
        ('Eske Eriksen',    'ES1F19',      10),
        ('Olaf Olesen',     'SDF19',       4),
        ('Olaf Olesen',     'ES1F19',      12),
        ('Salma Simonsen',  'SDS20',       NULL),
        ('Salma Simonsen',  'ES1F19',      12),
        ('Theis Thomasen',  'SDF19',       12),
        ('Theis Thomasen',  'ES1F19',      12),
        ('Janet Jensen',    'SDS20',       NULL),
        ('Janet Jensen',    'ES1F19',      7);

INSERT INTO Courses(courseID, courseName, courseYear, fallSemester, teacherName)
VALUES  ('SDF19',   'Software Development',     2019,   TRUE,    'Line'),
        ('SDS20',   'Software Development',     2020,   FALSE,   'Line'),
        ('ES1F19',  'Essential Computing 1',    2019,   TRUE,    'Ebbe');

INSERT INTO Students(studentName, townName)
VALUES  ('Aisha Lincoln',    'Nykøbing F'),
        ('Anya Nielsen',     'Nykøbing F'),
        ('Alfred Jensen',    'Karlskrona'),
        ('Berta Bertelsen',  'Billund'),
        ('Albert Antonsen',  'Sorø'),
        ('Eske Eriksen',     'Eskildstrup'),
        ('Olaf Olesen',      'Odense'),
        ('Salma Simonsen',   'Stockholm'),
        ('Theis Thomasen',   'Tølløse'),
        ('Janet Jensen',     'Jyllinge');

INSERT INTO Towns(townName, country, postal)
VALUES  ('Nykøbing F',  'Denmark',  4800),
        ('Karlskrona',  'Sweden',   NULL),
        ('Billund',     'Denmark',  7190),
        ('Sorø',        'Denmark',  4180),
        ('Eskildstrup', 'Denmark',  4863),
        ('Odense',      'Denmark',  5000),
        ('Stockholm',   'Sweden',   NULL),
        ('Tølløse',     'Denmark',  4340),
        ('Jyllinge',    'Denmark',  4040);