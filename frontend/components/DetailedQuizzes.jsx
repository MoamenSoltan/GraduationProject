import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';

import useAxiosPrivate from '../hooks/useAxiosPrivate';

const DetailedQuizzes = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [quiz, setQuiz] = useState(null);
  const axiosPrivate = useAxiosPrivate()

  useEffect(() => {
    const fetchQuizDetails = async () => {
      try {
        const res = await axiosPrivate.get(`/instructor/quiz/${id}`);
        setQuiz(res.data);
      } catch (error) {
        console.error("Failed to fetch quiz details:", error);
      }
    };

    fetchQuizDetails();
  }, [id]);

  if (!quiz) {
    return (
      <div className="text-center mt-10 text-gray-500">
        Loading quiz details...
      </div>
    );
  }

  return (
    <div className="max-w-4xl mx-auto mt-10 bg-white p-6 rounded-lg shadow-md">
      <h1 className="text-2xl font-bold mb-4 text-blue-700">{quiz.name}</h1>
      <p className="mb-2"><span className="font-semibold">Description:</span> {quiz.description}</p>
      <p className="mb-2"><span className="font-semibold">Total Questions:</span> {quiz.questions.length}</p>
      <p className="mb-2"><span className="font-semibold">Created On:</span> {new Date(quiz.createdAt).toLocaleDateString()}</p>

      <h2 className="text-lg font-semibold mt-6 mb-2">Questions:</h2>
      <ul className="list-disc list-inside space-y-1">
        {quiz.questions.map((q, index) => (
          <li key={q.id}>
            <span className="font-medium">{index + 1}.</span> {q.text} ({q.type})
          </li>
        ))}
      </ul>

      <div className="mt-8 text-center">
        <button
          onClick={() => navigate(`/instructorDashboard/quizzes/${id}/submissions`)}
          className="bg-green-600 text-white px-6 py-2 rounded hover:bg-green-700 transition"
        >
          View Submissions
        </button>
      </div>
    </div>
  );
};

export default DetailedQuizzes;
