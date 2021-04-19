SELECT studentName,grade,Grades.courseID,courseName,courseYear,fallSemester,teacherName 
FROM Grades 
INNER JOIN Courses ON Grades.courseID=Courses.courseID 
WHERE studentName='Aisha Lincoln';

SELECT AVG(grade) as Average 
FROM Grades 
WHERE studentName='Aisha Lincoln';

SELECT grade as CourseAverage 
FROM Grades 
INNER JOIN Courses ON Grades.courseID=Courses.courseID 
WHERE Grades.courseID='ES1F19';

SELECT studentName FROM Students;