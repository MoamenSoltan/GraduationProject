import React from 'react';
import { useLocation, useParams } from 'react-router';

const StudentInCourse = () => {
  const { id, stdId } = useParams();
  const location = useLocation();
  const student = location.state?.student;

  return (
    <div className="p-6 max-w-md mx-auto bg-white shadow-md rounded-lg mt-10">
      <h2 className="text-xl font-bold mb-4 text-gray-800">Student Details</h2>
      {student ? (
        <ul className="space-y-2 text-gray-700">
          <li><strong>Course ID:</strong> {id}</li>
          <li><strong>Student ID:</strong> {student.studentId}</li>
          <li><strong>Username:</strong> {student.username}</li>
          <li><strong>Email:</strong> {student.email}</li>
          <li><strong>Degree:</strong> {student.degree}</li>
        </ul>
      ) : (
        <p className="text-red-500">No student data provided.</p>
      )}
    </div>
  );
};

export default StudentInCourse;
