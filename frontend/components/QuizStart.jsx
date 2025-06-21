import React, { useState, useEffect, useRef } from 'react';
import { useLocation, useNavigate, useParams } from 'react-router-dom';
import useAxiosPrivate from '../hooks/useAxiosPrivate';
import toast from 'react-hot-toast';

const QuizStart = () => {
  const {quizId,courseId} = useParams()
  const { state } = useLocation();
  const navigate = useNavigate();
  const axiosPrivate = useAxiosPrivate()
  const [currentIndex, setCurrentIndex] = useState(0);
  const [answers, setAnswers] = useState([]); // Object to store answers: { questionId: selectedOption }
  const quiz = state?.quiz;  // Destructure quiz from the location state

  // Countdown timer state
  const [timeLeft, setTimeLeft] = useState(null); // in seconds
  const timerRef = useRef();

  // useEffect #1: Initialize timer when quiz data is available or changes
  // This effect sets the timer based on the quiz.time (in minutes) when the quiz is loaded.
  useEffect(() => {
    if (quiz && quiz.time && !isNaN(quiz.time) && Number(quiz.time) > 0) {
      setTimeLeft(Number(quiz.time) * 60); // Convert minutes to seconds
    }
  }, [quiz]);
  // It's good practice to use multiple useEffect hooks for different concerns.
  // Here, one useEffect is responsible for initializing the timer when quiz data changes.

  // useEffect #2: Countdown logic and auto-submit
  // This effect handles the countdown by decrementing timeLeft every second.
  // When timeLeft reaches zero, it automatically submits the quiz.
  useEffect(() => {
    if (timeLeft === null) return; // Timer not initialized yet
    if (timeLeft <= 0) {
      handleSubmit(); // Auto-submit when timer finishes
      return;
    }

    /**
     * Summary:
Use setInterval for repeated actions (like a countdown timer).
Use setTimeout for a one-time delayed action.
Let me know if you want code examples for a specific use case!
     */
    // Set up interval to decrement timer every second
    timerRef.current = setInterval(() => {
      setTimeLeft((prev) => (prev > 0 ? prev - 1 : 0));
    }, 1000);
    // Clean up interval on unmount or when timeLeft changes
    return () => clearInterval(timerRef.current);
  }, [timeLeft]);
  // Using a separate useEffect for the countdown logic keeps concerns isolated and code maintainable.

  // Format time as mm:ss for display
  const formatTime = (secs) => {
    const m = Math.floor(secs / 60).toString().padStart(2, '0');
    const s = (secs % 60).toString().padStart(2, '0');
    return `${m}:${s}`;
  };

  // Early return if there's no quiz data provided
  if (!quiz) {
    return <div>No quiz data provided. Please try again.</div>;
  }

  

  // Get the current question based on currentIndex
  const currentQuestion = quiz.questions[currentIndex];

  const handleAnswerChange = (questionId, value) => {
    //function returns means it sets the state , because we are using {} not () ''instant return
    setAnswers(prevAnswers => {
      const existingAnswerIndex = prevAnswers.findIndex(ans => ans.questionId === questionId);
  
      if (existingAnswerIndex !== -1) {
        // Question was already answered, so update it
        const updatedAnswers = [...prevAnswers];
        updatedAnswers[existingAnswerIndex] = { ...updatedAnswers[existingAnswerIndex], answer: value };
        return updatedAnswers;
      } else {
        // New answer, add it
        return [...prevAnswers, { questionId, answer: value }];
      }
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

  const handleSubmit = async () => {
    console.log("answers:", answers);
  
    try {
      const response = await axiosPrivate.post(
        `/student/quiz/${quizId}/course/${courseId}/submit`,
         answers   // Send answers in the body
      );
      toast.success("Quiz submitted successfully!");
      console.log(response.data); // Optional: see what backend returns
      // After successful submit, maybe navigate to summary
      quiz.showResults ?
      navigate(`/studentDashboard/Quizzes/${quizId}/${courseId}/summary`, { state: { answers, quiz } })
      :navigate(`/studentDashboard/Quizzes/all/${courseId}`)
    } catch (error) {
      console.error("Submission Error:", error);
      toast.error(`An error occurred: ${error.response?.data?.detail || error.message}`);
    }
  };
  

  return (
    <div className="max-w-3xl mx-auto mt-10 p-4 bg-white shadow rounded-lg">
      {/* Countdown Timer */}
      {quiz.time && !isNaN(quiz.time) && Number(quiz.time) > 0 && timeLeft !== null && (
        <div className="mb-4 flex items-center justify-center">
          {/* Timer display updates every second, shows minutes and seconds */}
          <span className="text-lg font-semibold text-red-600 bg-gray-100 px-4 py-2 rounded shadow">
            Time Left: {formatTime(timeLeft)}
          </span>
        </div>
      )}
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
                checked={answers.find((ans)=>(ans.questionId===currentQuestion.id))?.answer===opt.option}
                onChange={() => handleAnswerChange(currentQuestion.id,opt.option)}
              />
              <label>{opt.option}</label>
            </div>
          ))}

        {/* Render essay input (if applicable) */}
        {currentQuestion.questionType === "SHORT_ANSWER" && (
          <textarea
            rows={5}
            className="w-full border p-2 mt-2"
            value={answers.find(ans => ans.questionId === currentQuestion.id)?.answer || ''}
            onChange={(e) => handleAnswerChange(currentQuestion.id, e.target.value)}
            
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
