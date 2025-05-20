import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router';
import useAxiosPrivate from '../../hooks/useAxiosPrivate';
import toast from 'react-hot-toast';

const QuizzesForaCourse = () => {
  const [quizzes, setQuizzes] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const { courseId } = useParams();
  const axiosPrivate = useAxiosPrivate();
  const navigate = useNavigate();

  useEffect(() => {
    const fetchQuizzes = async () => {
      try {
        const response = await axiosPrivate.get(`/student/quiz/course/${courseId}/getAll`);
        setQuizzes(response.data);
        setLoading(false);
      } catch (error) {
        setError("An error occurred while fetching quizzes.");
        setLoading(false);
        toast.error(`Error: ${error}`);
      }
    };

    fetchQuizzes();
  }, [courseId, axiosPrivate]);

  const submittedQuizzes = quizzes.filter(q => q.status === 'submitted' && !q.deleted);
  const unsubmittedQuizzes = quizzes.filter(q => q.status === 'unsubmitted' && !q.deleted);

  const renderQuizCard = (quiz) => (
    <div
      onClick={() => navigate(`/studentDashboard/Quizzes/${quiz.id}/course/${courseId}`,{state:{status:quiz.status}})}
      key={quiz.id}
      className="w-80 bg-white rounded-lg shadow-lg hover:shadow-xl transition-shadow p-6 cursor-pointer"
    >
      <h3 className="text-xl font-bold text-gray-800 mb-2">{quiz.name}</h3>
      <p className="text-gray-600 mb-2">{quiz.description}</p>
      <p className="text-gray-500 text-sm mb-2">Total Marks: {quiz.totalMarks}</p>
      <p className="text-gray-500 text-sm mb-2">Time: {quiz.time} minutes</p>
      <p className="text-gray-400 text-xs mb-4">Created by: {quiz.instructor.firstName} {quiz.instructor.lastName}</p>
      <p className="text-gray-500 text-xs">Created on: {new Date(quiz.createdAt).toLocaleDateString()}</p>
    </div>
  );

  return (
    <div className="md:w-[80%] w-full m-auto mt-10">
      <div className="w-full flex justify-between items-center mb-6">
        <h2 className="text-2xl font-semibold text-gray-800">Quizzes for this Course</h2>
      </div>

      {loading ? (
        <div className="text-center text-lg text-gray-500">Loading quizzes...</div>
      ) : error ? (
        <div className="text-center text-lg text-red-500">{error}</div>
      ) : quizzes.length === 0 ? (
        <div className="text-center text-lg text-gray-400">No quizzes available for this course.</div>
      ) : (
        <>
          {/* Unsubmitted Quizzes */}
          <div className="mb-10">
            <h3 className="text-xl font-semibold text-gray-700 mb-4">Unsubmitted Quizzes</h3>
            {unsubmittedQuizzes.length === 0 ? (
              <p className="text-gray-500">No unsubmitted quizzes.</p>
            ) : (
              <div className="flex flex-wrap gap-6">
                {unsubmittedQuizzes.map(renderQuizCard)}
              </div>
            )}
          </div>

          {/* Submitted Quizzes */}
          <div>
            <h3 className="text-xl font-semibold text-gray-700 mb-4">Submitted Quizzes</h3>
            {submittedQuizzes.length === 0 ? (
              <p className="text-gray-500">No submitted quizzes.</p>
            ) : (
              <div className="flex flex-wrap gap-6">
                {submittedQuizzes.map(renderQuizCard)}
              </div>
            )}
          </div>
        </>
      )}
    </div>
  );
};

export default QuizzesForaCourse;
