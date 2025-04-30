import React, { useEffect, useState } from "react";
import { useLocation, useNavigate, useParams } from "react-router-dom";
import useAxiosPrivate from "../hooks/useAxiosPrivate"; // make sure this is set up correctly
import toast from "react-hot-toast";

const QuizSummary = () => {
  const { quizId, courseId } = useParams(); // Get quizId and courseId from the URL params
 
  const navigate = useNavigate();
  const axiosPrivate = useAxiosPrivate(); // Custom Axios hook for private requests

  const [quiz, setQuiz] = useState(null);
  
  

  useEffect(() => {
    // Fetch quiz result data from the backend when the component mounts
    const fetchQuizResult = async () => {
      try {
        const response = await axiosPrivate.get(
          `/student/quiz/${quizId}/course/${courseId}/result`
        );
        

        // Assuming the response data contains 'quiz' and 'answers' data
        setQuiz(response.data)

        
       

        
      } catch (err) {
        toast.error(`Failed to fetch quiz results,${err.response.data.detail}`);
        console.error(err.response.data.detail);
      }
    };

    fetchQuizResult();
  }, [quizId, courseId, axiosPrivate]);

  

  if (!quiz ) {
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

      {quiz.totalMarks !== null && (
        <p className="text-md text-gray-700 mb-6">
          Score: <span className="font-semibold">{quiz.totalMarks}</span> / {quiz.maxMarks}
        </p>
      )}

      {quiz?.quiz?.questions?.map((question, index) => (
        <div key={question.id || index} className="mb-6 border-b border-gray-200 pb-4 last:border-none">
          <p className="font-medium mb-2">
            {index + 1}. {question.question}
          </p>

          {question?.questionType === "MCQ" ? (
            <div className="space-y-2">
              {question?.options?.map((option, optIndex) => {
                const isCorrect = question.correctAnswer === option.option;
                const isSelected = quiz.answers[question.id] === option.option;

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
                    {option.option}
                  </div>
                );
              })}
            </div>
          ) : (
            <>
              <p className="text-sm">
                <span className="font-semibold">Your Answer:</span>{" "}
                {quiz.answers[quiz?.quiz?.questions[0]?.id] || "Not Answered"}
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
