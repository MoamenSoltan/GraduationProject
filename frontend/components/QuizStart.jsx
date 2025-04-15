import React, { useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';

const QuizStart = () => {
  const { state } = useLocation();
  const navigate = useNavigate();
  const quiz = state?.quiz;

  const [currentIndex, setCurrentIndex] = useState(0);
  const [answers, setAnswers] = useState({}); // { questionId: selectedOption }

  if (!quiz) {
    return <div>No quiz data provided.</div>;
  }

  const currentQuestion = quiz.questions[currentIndex];

  const handleAnswerChange = (value) => {
    setAnswers({
      ...answers,
      [currentQuestion.id]: value,
    });
  };

  const handleNext = () => {
    if (currentIndex < quiz.questions.length - 1) {
      setCurrentIndex(currentIndex + 1);
    }
  };

  const handlePrev = () => {
    if (currentIndex > 0) {
      setCurrentIndex(currentIndex - 1);
    }
  };

  const handleSubmit = () => {
    // TODO: send `answers` to API
    console.log("Submitted Answers:", answers);
    navigate("/studentDashboard/Quizzes/summary", { state: { answers, quiz } });
  };

  return (
    <div className="max-w-3xl mx-auto mt-10 p-4 bg-white shadow rounded-lg">
      <h2 className="text-xl font-bold mb-4">{quiz.name}</h2>
      <p className="mb-2 text-gray-600">{quiz.description}</p>

      <div className="border p-4 rounded mb-6">
        <p className="font-medium mb-3">
          Question {currentIndex + 1} of {quiz.questions.length}
        </p>
        <p className="mb-4 text-lg">{currentQuestion.text}</p>

        {currentQuestion.type === "mcq" &&
          currentQuestion.options.map((opt, idx) => (
            <div key={idx} className="flex items-center gap-2 mb-2">
              <input
                type="radio"
                name={`question-${currentQuestion.id}`}
                value={idx}
                checked={answers[currentQuestion.id] === idx}
                onChange={() => handleAnswerChange(idx)}
              />
              <label>{opt}</label>
            </div>
          ))}

        {currentQuestion.type === "essay" && (
          <textarea
            rows={5}
            className="w-full border p-2 mt-2"
            value={answers[currentQuestion.id] || ''}
            onChange={(e) => handleAnswerChange(e.target.value)}
            placeholder="Write your answer here..."
          />
        )}
      </div>

      <div className="flex justify-between">
        <button
          disabled={currentIndex === 0}
          onClick={handlePrev}
          className="px-4 py-2 bg-gray-300 rounded disabled:opacity-50"
        >
          Previous
        </button>

        {currentIndex === quiz.questions.length - 1 ? (
          <button
            onClick={handleSubmit}
            className="px-4 py-2 bg-green-600 text-white rounded"
          >
            Submit Quiz
          </button>
        ) : (
          <button
            onClick={handleNext}
            className="px-4 py-2 bg-blue-600 text-white rounded"
          >
            Next
          </button>
        )}
      </div>
    </div>
  );
};

export default QuizStart;
