import React, { useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import useAxiosPrivate from "../hooks/useAxiosPrivate"; // make sure this is set up correctly
import toast from "react-hot-toast";

const QuizSummary = () => {
  const { state } = useLocation();
  const navigate = useNavigate();
  const axiosPrivate = useAxiosPrivate();

  const { quiz, answers } = state || {};
  const [score, setScore] = useState(null);

  useEffect(() => {
    if (quiz && answers) {
      // ✅ Calculate score
      const totalCorrect = quiz.questions.reduce((count, question) => {
        if (question.type === "mcq" && answers[question.id] === question.correctAnswer) {
          return count + 1;
        }
        return count;
      }, 0);

      setScore(totalCorrect);

      // ✅ Send to backend
      const sendResults = async () => {
        try {
          await axiosPrivate.post("/student/quiz/submit", {
            quizId: quiz.ID, // assuming quiz has ID
            answers,
            score: totalCorrect,
          });
          toast.success("Results submitted successfully!");
        } catch (err) {
          toast.error("Failed to submit results");
          console.error(err);
        }
      };

      sendResults();
    }
  }, [quiz, answers, axiosPrivate]);

  if (!quiz || !answers) {
    return (
      <div className="text-center mt-10 text-red-500">
        No summary data available.
      </div>
    );
  }

  return (
    <div className="max-w-3xl mx-auto mt-10 p-6 bg-white shadow-lg rounded-lg">
      <h2 className="text-2xl font-bold mb-4 text-center text-green-700">Quiz Summary</h2>
      <h3 className="text-xl font-semibold mb-2">{quiz.name}</h3>

      {score !== null && (
        <p className="text-md text-gray-700 mb-6">
          Score: <span className="font-semibold">{score}</span> / {quiz.questions.length}
        </p>
      )}

      {quiz.questions.map((question, index) => (
        <div key={question.id || index} className="mb-6 border-b border-gray-200 pb-4 last:border-none">
          <p className="font-medium mb-2">
            {index + 1}. {question.text}
          </p>

          {question.type === "mcq" ? (
            <div className="space-y-2">
              {question.options.map((option, optIndex) => {
                const isCorrect = optIndex === question.correctAnswer;
                const isSelected = answers[question.id] === optIndex;

                let optionStyle = "border p-2 rounded transition ";

                if (isSelected && isCorrect) {
                  optionStyle += "border-green-500 bg-green-100";
                } else if (isSelected && !isCorrect) {
                  optionStyle += "border-red-500 bg-red-100";
                } else if (isCorrect) {
                  optionStyle += "border-green-500 bg-green-200 font-semibold";
                } else {
                  optionStyle += "border-gray-300";
                }

                return (
                  <div key={optIndex} className={optionStyle}>
                    {option}
                  </div>
                );
              })}
            </div>
          ) : (
            <>
              <p className="text-sm">
                <span className="font-semibold">Your Answer:</span>{" "}
                {answers[question.id] || "Not Answered"}
              </p>
              <p className="text-sm text-yellow-700">
                <span className="font-semibold">Note:</span> Essay answers are manually reviewed.
              </p>
            </>
          )}
        </div>
      ))}

      <div className="text-center mt-6">
        <button
          onClick={() => navigate("/studentDashboard/Quizzes")}
          className="px-6 py-2 bg-blue-600 text-white rounded hover:bg-blue-700 transition"
        >
          Back to Quizzes
        </button>
      </div>
    </div>
  );
};

export default QuizSummary;
