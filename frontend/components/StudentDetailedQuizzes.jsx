import React, { useEffect, useState } from 'react';
import { useParams, useNavigate, useLocation } from 'react-router';
import useAxiosPrivate from '../hooks/useAxiosPrivate';
import toast from 'react-hot-toast';
import Modal from '../components/Modal';

const StudentDetailedQuizzes = () => {
  const { quizId, courseId } = useParams();
  const axiosPrivate = useAxiosPrivate();
  const navigate = useNavigate();

  const [quiz, setQuiz] = useState(null);
  const [modal, setModal] = useState(false);
  const location = useLocation()
  const {status} = location.state

  useEffect(() => {
    const fetchQuiz = async () => {
      try {
        const res = await axiosPrivate.get(`/student/quiz/${quizId}/course/${courseId}/get`);
        setQuiz(res.data);
        console.log(" quiz :",res.data);
        console.log("quiz status :",status);

        
      } catch (err) {
        toast.error("Failed to fetch quiz details");
      }
    };

    fetchQuiz();
  }, [quizId, courseId, axiosPrivate]);

  const handleStartQuiz = () => {
    navigate(`/studentDashboard/Quizzes/${quizId}/${courseId}/start`, { state: { quiz } });
  };

  if (!quiz) return <div className="text-center">Loading quiz details...</div>;

  return (
    <div className="w-full md:w-[80%]  m-auto mt-10 p-6 bg-white rounded-lg shadow-lg">
      <h1 className="text-3xl font-bold text-gray-800">{quiz.name}</h1>
      <p className="text-gray-600 mt-2">{quiz.description}</p>
      <p className="text-sm text-gray-500 mt-1">Total Questions: {quiz.questions.length}</p>
      <p className="text-sm text-gray-500 mt-1">Total Marks: {quiz.totalDegree}</p>
      <p className='text-sm text-gray-500 mt-1'>Time :{quiz.time} minutes</p>
      {/* TODO:ADD time */}

      <div className="mt-6">
        <h2 className="text-2xl font-semibold text-gray-800">Questions</h2>
        <ul className="space-y-4 mt-4">
          {/* {quiz.questions.map((question) => (
            <li key={question.id} className="p-4 bg-gray-100 rounded-lg shadow-sm">
              <h3 className="text-lg font-semibold text-gray-800">{question.question}</h3>
              <p className="text-sm text-gray-600 mt-2">Score: {question.score}</p>
              <div className="mt-2">
                <p className="text-sm text-gray-600">Options:</p>
                <ul className="space-y-2 mt-2">
                  {question.options.map((option) => (
                    <li key={option.optionId} className="text-gray-700">
                      - {option.option}
                    </li>
                  ))}
                </ul>
              </div>
              
            </li>
          ))} */}
          <li className='p-4 bg-gray-100 rounded-lg shadow-sm'>start quiz to view questions</li>
        </ul>
      </div>

      <div className="flex justify-center mt-6">
        {
          status==="unsubmitted" 
          ?<button
          onClick={() => setModal(true)}
          className="px-6 py-2 bg-blue-600 text-white rounded hover:bg-blue-700"
        >
          Start Quiz
        </button>
        :quiz.showResults 
        ?<button
        onClick={() => navigate(`/studentDashboard/Quizzes/${quizId}/${courseId}/summary`)}
        className="px-6 py-2 bg-green-600 text-white rounded hover:bg-green-700"
      >
        view result
      </button>
      :<h1>Results not allowed by instructor</h1>
        }
      </div>

      {/* Modal */}
      <Modal open={modal} onClose={() => setModal(false)}>
        <div className="p-4 space-y-4">
          <h2 className="text-xl font-semibold text-gray-800">Ready to start?</h2>
          <p>This quiz contains {quiz.questions.length} questions.</p>
          <div className="flex justify-end gap-4">
            <button
              onClick={() => setModal(false)}
              className="text-gray-600 hover:underline"
            >
              Not Now
            </button>
            <button
              onClick={handleStartQuiz}
              className="px-4 py-2 bg-green-600 text-white rounded"
            >
              Start Quiz
            </button>
          </div>
        </div>
      </Modal>
    </div>
  );
};

export default StudentDetailedQuizzes;
