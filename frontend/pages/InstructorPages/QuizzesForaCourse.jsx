import React, { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom'; // ❗ Fix here: should be 'react-router-dom' not 'react-router'
import useAxiosPrivate from '../../hooks/useAxiosPrivate';
import { trimText } from '../../utils/trim';
import toast from 'react-hot-toast';
import Modal from '../../components/Modal';

const QuizzesForaCourse = () => {
  const { courseId } = useParams();
  const [allQuizzes, setAllQuizzes] = useState([]);
  const [quizInfo, setQuizInfo] = useState({ name: '', description: '', image: null, courseId });
  const [modal, setModal] = useState(false);
  const [questions, setQuestions] = useState([]);
  const [currentQuestion, setCurrentQuestion] = useState({
    type: 'mcq',
    text: '',
    options: ['', '', '', ''],
    correctAnswerIndex: null,
  });
  const [loading, setLoading] = useState(false);
  const [refetch, setRefetch] = useState(false);

  const axiosPrivate = useAxiosPrivate();
  const navigate = useNavigate();

  const handleAddQuestion = () => {
    if (!currentQuestion.text.trim()) {
      toast.error('Question text cannot be empty');
      return;
    }
    if (currentQuestion.type === 'mcq' && currentQuestion.options.some(opt => !opt.trim())) {
      toast.error('All MCQ options must be filled');
      return;
    }
    if(currentQuestion.correctAnswerIndex===null&&currentQuestion.type==="mcq")
    {
      toast.error("Correct answer must be choosen")
      return
    }
    setQuestions([...questions, currentQuestion]);
    setCurrentQuestion({
      type: 'mcq',
      text: '',
      options: ['', '', '', ''],
      correctAnswerIndex: null,
    });
  };

  const handleFinishQuiz = async () => {
    if (questions.length === 0) {
      toast.error('Add at least one question');
      return;
    }

    const formattedQuestions = questions.map((q) => ({
      questionText: q.text,
      questionType: q.type.toUpperCase(),
      options: q.type === 'mcq' ? q.options.map((opt) => ({ option: opt })) : [],
      correctAnswer: q.type === 'mcq' ? q.options[q.correctAnswerIndex] : '',
      score: 1,
    }));

    const payload = {
      name: quizInfo.name,
      description: quizInfo.description,
      time: 0,
      totalDegree: formattedQuestions.length,
      questions: formattedQuestions,
    };

    try {
      setLoading(true);
      await axiosPrivate.post(`/instructor/quiz/course/${courseId}/create`, payload);
      toast.success('Quiz created successfully');
      setModal(false);
      setRefetch(prev => !prev);
      setQuestions([]);
      setQuizInfo({ name: '', description: '', image: null, courseId });
    } catch (error) {
      toast.error(`Error: ${error.response?.data?.detail || error.message}`);
      console.error('Error creating quiz:', error);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    const fetchQuizzes = async () => {
      try {
        const response = await axiosPrivate.get(`/instructor/quiz/course/${courseId}/getAll`);
        setAllQuizzes(response.data);
        console.log('Fetched quizzes:', response.data);
      } catch (error) {
        toast.error('Error fetching quizzes');
      }
    };

    fetchQuizzes();
  }, [courseId, refetch, axiosPrivate]);

  return (
    <div className="md:w-[80%] w-full m-auto mt-10">
      <h2 className="text-2xl font-semibold mb-4 text-gray-800">Quizzes</h2>

      {allQuizzes.length > 0 ? (
        <div className="flex flex-wrap gap-4">
          {allQuizzes.map((quiz) => (
            <div
              onClick={() => navigate(`/instructorDashboard/Quizzes/${quiz.id}/course/${courseId}`)}
              key={quiz.id}
              className="w-72 bg-white rounded-lg shadow-lg hover:scale-105 hover:shadow-2xl transition-all duration-300 cursor-pointer"
            >
              <div className="p-4 space-y-2">
                <h3 className="text-xl font-semibold text-gray-800">{quiz.name}</h3>
                <p className="text-sm text-gray-600">
                  Description: {trimText(quiz.description, 20)}
                </p>
                <p className="text-sm text-gray-600">
                  Number of questions: {quiz.questions?.length || 0}
                </p>
                <div className="flex justify-between items-center mt-2">
                  <button className="text-blue-500 hover:underline">View Details</button>
                </div>
              </div>
            </div>
          ))}
        </div>
      ) : (
        <div>
          <h2 className="text-xl mb-4 text-gray-400">No Quizzes Found</h2>
        </div>
      )}

      <button
        onClick={() => setModal(true)}
        className="fixed bottom-5 right-5 p-4 w-28 h-28 rounded-full bg-blue-600 text-white hover:scale-105 transition-all text-2xl"
      >
        +
      </button>

      {/* Modal */}
      <Modal open={modal} onClose={() => setModal(false)}>
        <div className="p-6 max-w-xl mx-auto space-y-6">
          <h2 className="text-2xl font-bold text-gray-800">Create New Quiz</h2>

          {/* Quiz Info */}
          <div className="space-y-4">
            <div>
              <label className="block text-sm font-medium text-gray-700">Quiz Name</label>
              <input
                type="text"
                className="w-full border rounded px-3 py-2"
                value={quizInfo.name}
                onChange={(e) => setQuizInfo({ ...quizInfo, name: e.target.value })}
                placeholder="Enter quiz name"
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700">Description</label>
              <textarea
                rows={3}
                className="w-full border rounded px-3 py-2"
                value={quizInfo.description}
                onChange={(e) => setQuizInfo({ ...quizInfo, description: e.target.value })}
                placeholder="Enter quiz description"
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700">Optional Image</label>
              <input
                type="file"
                className="block w-full text-sm text-gray-700 file:mr-4 file:py-2 file:px-4
                           file:rounded-full file:border-0 file:text-sm file:font-semibold
                           file:bg-blue-50 file:text-blue-700 hover:file:bg-blue-100 transition"
                onChange={(e) => setQuizInfo({ ...quizInfo, image: e.target.files[0] })}
              />
            </div>
          </div>

          <hr />

          {/* Add Question */}
          <div className="space-y-4">
            <h3 className="text-lg font-semibold text-gray-700">Question {questions.length + 1}</h3>

            <textarea
              rows={2}
              className="w-full border rounded px-3 py-2"
              placeholder="Enter question text"
              value={currentQuestion.text}
              onChange={(e) => setCurrentQuestion({ ...currentQuestion, text: e.target.value })}
            />

            <div>
              <label className="block text-sm font-medium text-gray-700">Question Type</label>
              <select
                className="w-full border rounded px-3 py-2"
                value={currentQuestion.type}
                onChange={(e) => setCurrentQuestion({ ...currentQuestion, type: e.target.value })}
              >
                <option value="mcq">Multiple Choice</option>
                <option value="SHORT_ANSWER">Essay</option>
              </select>
            </div>

            {/* MCQ Options */}
            {currentQuestion.type === 'mcq' && (
              <div className="space-y-2">
                <label className="block text-sm font-medium text-gray-700 mb-1">Options</label>
                {currentQuestion.options.map((opt, idx) => (
                  <div key={idx} className="flex items-center gap-3">
                    <input
                      type="text"
                      className="flex-1 border rounded px-3 py-1"
                      placeholder={`Option ${idx + 1}`}
                      value={opt}
                      onChange={(e) => {
                        const newOptions = [...currentQuestion.options];
                        newOptions[idx] = e.target.value;
                        setCurrentQuestion({ ...currentQuestion, options: newOptions });
                      }}
                    />
                    <input
                      type="radio"
                      name="correct"
                      required
                      checked={currentQuestion.correctAnswerIndex === idx}
                      onChange={() =>
                        setCurrentQuestion({ ...currentQuestion, correctAnswerIndex: idx })
                      }
                    />
                    <label className="text-sm text-gray-600">Correct</label>
                  </div>
                ))}
              </div>
            )}
          </div>

          {/* Modal Action Buttons */}
          <div className="flex justify-between mt-6">
            <button
              className="bg-gray-100 disabled:text-gray-200 disabled:hover:bg-gray-100 hover:bg-gray-200 text-gray-800 px-4 py-2 rounded"
              onClick={handleAddQuestion}
              disabled={loading}
            >
              Add Question
            </button>
            <button
              className="bg-blue-600 hover:bg-blue-700 text-white px-6 py-2 rounded"
              onClick={handleFinishQuiz}
              disabled={loading}
            >
              {loading ? 'Saving...' : 'Finish Quiz'}
            </button>
          </div>
        </div>
      </Modal>
    </div>
  );
};

export default QuizzesForaCourse;
