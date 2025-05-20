import React, { useEffect, useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import useAxiosPrivate from '../hooks/useAxiosPrivate';
import { Loader2 } from 'lucide-react'; // Optional: loader icon for loading state
import Modal from './Modal';
import toast from 'react-hot-toast';

const DetailedQuizzes = () => {
  const { quizId, courseId } = useParams();
  const navigate = useNavigate();
  const [quiz, setQuiz] = useState(null);
  const axiosPrivate = useAxiosPrivate();
  const [deleteModal, setdeleteModal] = useState(false)

  useEffect(() => {
    const fetchQuizDetails = async () => {
      try {
        const res = await axiosPrivate.get(`/instructor/quiz/course/${courseId}/get/${quizId}`);
        setQuiz(res.data);
        console.log("fetched quiz : ", res.data);
      } catch (error) {
        console.error("Failed to fetch quiz details:", error);
      }
    };

    fetchQuizDetails();
  }, []);

  const handleDelete = async ()=>{
    try {
      const response = await axiosPrivate.delete(`/instructor/quiz/course/${courseId}/delete/${quizId}`)
      navigate(`/instructorDashboard/Quizzes/all/${courseId}`)
    } catch (error) {
      toast.error(`an error occurred while deleting the task ${error}`)
    }
  }

  if (!quiz) {
    return (
      <div className="flex justify-center items-center h-screen">
        <Loader2 className="animate-spin text-blue-500" size={40} />
      </div>
    );
  }

  return (
    <div className="max-w-5xl mx-auto mt-10 p-6 bg-gradient-to-br from-white to-gray-100 rounded-xl shadow-lg">
      <div className="border-b pb-4 mb-6">
        <h1 className="text-3xl font-bold text-blue-700">{quiz.name}</h1>
        <p className="mt-2 text-gray-600">{quiz.description}</p>
      </div>

      <div className="grid grid-cols-2 md:grid-cols-4 gap-4 mb-8">
        <div className="p-4 bg-white rounded-lg shadow text-center">
          <p className="text-sm text-gray-500">Total Questions</p>
          <p className="text-xl font-bold">{quiz.questions.length}</p>
        </div>
        <div className="p-4 bg-white rounded-lg shadow text-center">
          <p className="text-sm text-gray-500">Total Score</p>
          <p className="text-xl font-bold">{quiz.totalDegree}</p>
        </div>
        <div className="p-4 bg-white rounded-lg shadow text-center">
          <p className="text-sm text-gray-500">Created On</p>
          <p className="text-xl font-bold">{quiz.createdAt ? new Date(quiz.createdAt).toLocaleDateString() : "N/A"}</p>
        </div>
        <div className="p-4 bg-white rounded-lg shadow text-center">
          <p className="text-sm text-gray-500">Time Limit</p>
          <p className="text-xl font-bold">{quiz.time ? `${quiz.time} min` : "No Limit"}</p>
        </div>
      </div>

      <h2 className="text-2xl font-semibold text-gray-700 mb-4">Questions</h2>
      <div className="space-y-6">
      {quiz.questions.map((q, index) => (
  <div key={q.id} className="p-5 bg-white rounded-lg shadow">
    <div className="flex justify-between items-start mb-2">
      <h3 className="text-lg font-semibold">{index + 1}. {q.question}</h3>
      <span className="text-sm bg-blue-100 text-blue-600 px-2 py-1 rounded">{q.questionType}</span>
    </div>

    <div className="ml-4 mt-3 space-y-2">
      {q.questionType === "SHORT_ANSWER" ? (
        <div className="p-3 rounded-md border border-blue-300 bg-blue-50 text-gray-700 italic">
          (Student will write their short answer here)
        </div>
      ) : (
        q.options?.map((opt) => (
          <div
            key={opt.optionId}
            className={`p-2 rounded-md border ${opt.option === q.correctAnswer ? 'border-green-400 bg-green-50' : 'border-gray-300'}`}
          >
            {opt.option}
          </div>
        ))
      )}
    </div>

    {q.correctAnswer && (
      <p className="text-right text-green-600 text-sm mt-2">
        Correct Answer: <span className="font-semibold">{q.correctAnswer}</span>
      </p>
    )}
  </div>
))}


      </div>

      <div className="mt-10 text-center m-auto flex w-[80%] justify-between">
        <button onClick={()=>setdeleteModal(true)} className='px-6 py-3 bg-red-600 hover:bg-red-700 text-white rounded-lg shadow transition-all'>
          Delete quiz
        </button>
        <button
          onClick={() => navigate(`/instructorDashboard/quizzes/${quiz.id}/course/${courseId}/submissions`)}
          className="px-6 py-3 bg-green-600 hover:bg-green-700 text-white rounded-lg shadow transition-all"
        >
          View Submissions
        </button>
      </div>
      <Modal open={deleteModal} onClose={()=>setdeleteModal(false)}>
      <div className="w-full p-6">
          <h3 className="text-xl w-full font-bold mb-4">Are you sure you want to delete this quiz?</h3>
          <div className="flex justify-center gap-4">
            <button
              onClick={()=>handleDelete()}
              className="px-4 py-2 bg-red-600 text-white font-medium rounded-lg hover:bg-red-700"
            >
              Yes, Delete
            </button>
            <button
              onClick={() => setdeleteModal(false)}
              className="px-4 py-2 bg-gray-600 text-white font-medium rounded-lg hover:bg-gray-700"
            >
              No, Cancel
            </button>
          </div>
        </div>
      </Modal>
    </div>
  );
};

export default DetailedQuizzes;
