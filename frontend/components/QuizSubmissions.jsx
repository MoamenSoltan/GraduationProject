import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import useAxiosPrivate from '../hooks/useAxiosPrivate';

const QuizSubmissions = () => {
  const { quizId, courseId } = useParams();
  const [submissions, setSubmissions] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const axiosPrivate = useAxiosPrivate();
  const navigate = useNavigate();

  useEffect(() => {
    const fetchSubmissions = async () => {
      try {
        const response = await axiosPrivate.get(
          `/instructor/quiz/${quizId}/course/${courseId}/student/submission`
        );
        setSubmissions(response.data);
      } catch (err) {
        setError('Failed to load submissions.');
      } finally {
        setLoading(false);
      }
    };

    fetchSubmissions();
  }, [quizId, courseId]);

  const handleCardClick = (studentId) => {
    navigate(`/instructorDashboard/quizzes/${quizId}/course/${courseId}/student/${studentId}/submissions`);
  };

  if (loading) return <div className="text-center mt-10">Loading submissions...</div>;
  if (error) return <div className="text-red-500 text-center mt-10">{error}</div>;

  return (
    <div className="p-6 w-[80%] mx-auto mt-10">
      <h2 className="text-2xl font-bold mb-2 text-gray-800">Quiz Submissions</h2>
      <p className="text-sm text-gray-500 mb-6">
        Quiz ID: <span className="font-medium">{quizId}</span> | Course ID: <span className="font-medium">{courseId}</span>
      </p>

      {submissions.length === 0 ? (
        <p className="text-gray-600">No submissions found.</p>
      ) : (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {submissions.map((submission, index) => (
            <div
              key={index}
              onClick={() => handleCardClick(submission.studentId)}
              className="bg-white shadow-md rounded-xl p-5 border hover:shadow-lg transition cursor-pointer"
            >
              <div className="mb-2">
                <p className="text-sm text-gray-500">Student ID</p>
                <p className="font-semibold text-gray-800">{submission.studentId}</p>
              </div>
              <div className="mb-2">
                <p className="text-sm text-gray-500">Username</p>
                <p className="text-gray-700">{submission.username}</p>
              </div>
              <div className="mb-2">
                <p className="text-sm text-gray-500">Email</p>
                <p className="text-blue-600 underline">{submission.email}</p>
              </div>
              <div>
                <p className="text-sm text-gray-500">Submitted At</p>
                <p className="text-gray-700">
                  {new Date(submission.submissionTime).toLocaleString()}
                </p>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};

export default QuizSubmissions;
