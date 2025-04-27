import React, { useState } from 'react';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import useAxiosPrivate from '../hooks/useAxiosPrivate';
import toast from 'react-hot-toast';

const QuizStart = () => {
  const {quizId,courseId} = useParams()
  const { state } = useLocation();
  const navigate = useNavigate();
  const axiosPrivate = useAxiosPrivate()
  const [currentIndex, setCurrentIndex] = useState(0);
  const [answers, setAnswers] = useState({}); // Object to store answers: { questionId: selectedOption }
  const quiz = state?.quiz;  // Destructure quiz from the location state

  // Early return if there's no quiz data provided
  if (!quiz) {
    return <div>No quiz data provided. Please try again.</div>;
  }

  

  // Get the current question based on currentIndex
  const currentQuestion = quiz.questions[currentIndex];

  const handleAnswerChange = (value) => {
    // Update the answers state with the selected option or essay answer
    setAnswers({
      ...answers,
      [currentQuestion.id]: value,
    });
  };

  const handleNext = () => {
    // Move to the next question
    if (currentIndex < quiz.questions.length - 1) {
      setCurrentIndex(currentIndex + 1);
    }
  };

  const handlePrev = () => {
    // Go back to the previous question
    if (currentIndex > 0) {
      setCurrentIndex(currentIndex - 1);
    }
  };

  const handleSubmit = async() => {
    console.log("answers : ",answers);
    
    try {
      const response = axiosPrivate.post(`/student/quiz/${quizId}/course/${courseId}/submit`,answers)
      toast.success("Quiz submitted successfully!")
    } catch (error) {
      toast.error(`an error occurred : ${error.response.data.detail}`)
    }
    console.log("Submitted Answers:", answers);
    navigate(`/studentDashboard/Quizzes/${quizId}/${courseId}/summary`, { state: { answers, quiz } });
  };

  return (
    <div className="max-w-3xl mx-auto mt-10 p-4 bg-white shadow rounded-lg">
      <h2 className="text-xl font-bold mb-4">{quiz.name}</h2>
      <p className="mb-2 text-gray-600">{quiz.description}</p>

      {/* Quiz Question Section */}
      <div className="border p-4 rounded mb-6">
        <p className="font-medium mb-3">
          Question {currentIndex + 1} of {quiz.questions.length}
        </p>
        <p className="mb-4 text-lg">{currentQuestion.question}</p> {/* Display the question text */}

        {/* Render multiple-choice options */}
        {currentQuestion.questionType === "MCQ" &&
          currentQuestion.options.map((opt) => (
            <div key={opt.optionId} className="flex items-center gap-2 mb-2">
              <input
                type="radio"
                name={`question-${currentQuestion.id}`}
                value={opt.optionId}
                checked={answers[currentQuestion.id] === opt.optionId}
                onChange={() => handleAnswerChange(opt.optionId)}
              />
              <label>{opt.option}</label>
            </div>
          ))}

        {/* Render essay input (if applicable) */}
        {currentQuestion.questionType === "essay" && (
          <textarea
            rows={5}
            className="w-full border p-2 mt-2"
            value={answers[currentQuestion.id] || ''}
            onChange={(e) => handleAnswerChange(e.target.value)}
            placeholder="Write your answer here..."
          />
        )}
      </div>

      {/* Navigation Buttons */}
      <div className="flex justify-between">
        <button
          disabled={currentIndex === 0}
          onClick={handlePrev}
          className="px-4 py-2 bg-gray-300 rounded disabled:opacity-50"
        >
          Previous
        </button>

        {/* Show either Next or Submit depending on the current question */}
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
