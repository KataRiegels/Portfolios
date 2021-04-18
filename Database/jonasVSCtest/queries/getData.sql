-- Make a user interface in javaFX where a user can select a student 
-- and get print out courses taken and the grades and the average grade for student 
-- and a course and get average grade on a selected course.
SELECT studentName,grade,Grades.courseID,courseName,courseYear,fallSemester,teacherName 
FROM Grades 
INNER JOIN Courses ON Grades.courseID=Courses.courseID 
WHERE studentName='Aisha Lincoln';

SELECT AVG(grade) as Average 
FROM Grades 
WHERE studentName='Aisha Lincoln';

SELECT AVG(grade) as CourseAverage 
FROM Grades 
INNER JOIN Courses ON Grades.courseID=Courses.courseID 
WHERE courseName='Software Development';